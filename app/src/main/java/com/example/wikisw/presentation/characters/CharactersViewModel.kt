package com.example.wikisw.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wikisw.domain.model.Character
import com.example.wikisw.domain.usecase.GetCharacterByIdUseCase
import com.example.wikisw.domain.usecase.GetCharactersUseCase
import com.example.wikisw.domain.usecase.GetPlanetNameUseCase
import com.example.wikisw.domain.usecase.GetSpeciesNameUseCase
import com.example.wikisw.domain.usecase.RefreshCharactersUseCase
import com.example.wikisw.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

/**
 * ViewModel for the Character list and details.
 * Coordinates data fetching and UI state management using reactive flows.
 */
@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val refreshCharactersUseCase: RefreshCharactersUseCase,
    private val getPlanetNameUseCase: GetPlanetNameUseCase,
    private val getSpeciesNameUseCase: GetSpeciesNameUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _onlyFavorites = MutableStateFlow(false)
    val onlyFavorites = _onlyFavorites.asStateFlow()

    private val _uiState = MutableStateFlow<CharactersUiState>(CharactersUiState.Loading)
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    private val _currentCharacter = MutableStateFlow<Character?>(null)
    val currentCharacter = _currentCharacter.asStateFlow()

    private val _detailsState = MutableStateFlow<Pair<String, String>>(Pair("...", "..."))
    val detailsState = _detailsState.asStateFlow()

    init {
        refreshData()
        observeCharactersFlow()
    }

    private fun refreshData() {
        viewModelScope.launch {
            refreshCharactersUseCase()
        }
    }

    private fun observeCharactersFlow() {
        combine(_searchQuery.debounce(300.milliseconds), _onlyFavorites) { query, favorites ->
            Pair(query, favorites)
        }
            .distinctUntilChanged()
            .flatMapLatest { (query, favorites) ->
                getCharactersUseCase(query, favorites)
            }
            .onEach { characters ->
                _uiState.value = CharactersUiState.Success(characters)
            }
            .catch { e ->
                _uiState.value = CharactersUiState.Error(e.localizedMessage ?: "Unexpected Error")
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun onToggleFilterFavorites() {
        _onlyFavorites.value = !_onlyFavorites.value
    }

    /**
     * Observes a single character by ID.
     */
    fun observeCharacter(id: Int) {
        getCharacterByIdUseCase(id)
            .onEach { _currentCharacter.value = it }
            .launchIn(viewModelScope)
    }

    /**
     * Toggles the favorite status of a character.
     */
    fun toggleFavorite(characterId: Int, isCurrentlyFavorite: Boolean) {
        viewModelScope.launch {
            toggleFavoriteUseCase(characterId, !isCurrentlyFavorite)
        }
    }

    /**
     * Loads planet and species names in parallel for a character.
     */
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
