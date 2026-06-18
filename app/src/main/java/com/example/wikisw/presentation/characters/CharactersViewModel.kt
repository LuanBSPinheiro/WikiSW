package com.example.wikisw.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wikisw.domain.model.Character
import com.example.wikisw.domain.usecase.GetCharactersUseCase
import com.example.wikisw.domain.usecase.GetPlanetNameUseCase
import com.example.wikisw.domain.usecase.GetSpeciesNameUseCase
import com.example.wikisw.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getSpeciesNameUseCase: GetSpeciesNameUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    val getPlanetNameUseCase: GetPlanetNameUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _onlyFavorites = MutableStateFlow(false)
    private val _currentCharacter = MutableStateFlow<Character?>(null)

    val searchQuery = _searchQuery.asStateFlow()
    val currentCharacter = _currentCharacter.asStateFlow()
    val onlyFavorites = _onlyFavorites.asStateFlow()

    private val _uiState = MutableStateFlow<CharactersUiState>(CharactersUiState.Loading)
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    private val _detailsState =
        MutableStateFlow<Pair<String, String>>(Pair("Carregando...", "Carregando..."))
    val detailsState = _detailsState.asStateFlow()

    init {
        viewModelScope.launch {
            getCharactersUseCase.refresh()
        }

        observeCharactersFlow()
    }

    private fun observeCharactersFlow() {
        _searchQuery
            .debounce(300.milliseconds)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                getCharactersUseCase.executeFlow(query, _onlyFavorites.value)
            }
            .onEach { characters ->
                _uiState.value = CharactersUiState.Success(characters)
            }
            .catch { e ->
                _uiState.value = CharactersUiState.Error(e.localizedMessage ?: "Erro local")
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun observeCharacter(id: Int) {
        getCharactersUseCase.executeSingle(id)
            .onEach { _currentCharacter.value = it }
            .launchIn(viewModelScope)
    }

    fun onToggleFilterFavorites() {
        _onlyFavorites.value = !_onlyFavorites.value
        viewModelScope.launch {
            getCharactersUseCase.executeFlow(_searchQuery.value, _onlyFavorites.value)
                .collect { characters -> _uiState.value = CharactersUiState.Success(characters) }
        }
    }

    fun toggleFavorite(characterId: Int, isCurrentlyFavorite: Boolean) {
        viewModelScope.launch {
            toggleFavoriteUseCase(characterId, !isCurrentlyFavorite)
        }
    }

    fun loadDetailsInParallel(planetId: String, speciesId: String) {
        _detailsState.value = Pair("Carregando...", "Carregando...")

        viewModelScope.launch {
            val planetDeferred = async { getPlanetNameUseCase(planetId) }
            val speciesDeferred = async { getSpeciesNameUseCase(speciesId) }

            val planetName = planetDeferred.await()
            val speciesName = speciesDeferred.await()

            _detailsState.value = Pair(planetName, speciesName)
        }
    }
}