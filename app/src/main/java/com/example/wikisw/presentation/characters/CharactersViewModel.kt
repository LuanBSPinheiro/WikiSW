package com.example.wikisw.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wikisw.domain.usecase.GetCharactersUseCase
import com.example.wikisw.domain.usecase.GetPlanetNameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getPlanetNameUseCase: GetPlanetNameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CharactersUiState>(CharactersUiState.Loading)
    private val _planetName = MutableStateFlow("Carregando...")
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()
    val planetName: StateFlow<String> = _planetName.asStateFlow()

    init {
        loadCharacters()
    }

    fun loadCharacters() {
        viewModelScope.launch {
            _uiState.value = CharactersUiState.Loading

            try {
                val characters = getCharactersUseCase()
                _uiState.value = CharactersUiState.Success(characters)
            } catch (e: Exception) {
                _uiState.value = CharactersUiState.Error(
                    message = e.localizedMessage ?: "Ocorreu um erro inesperado."
                )
            }
        }
    }

    fun loadPlanetName(planetId: String) {
        _planetName.value = "Carregando..."

        viewModelScope.launch {
            try {
                val name = getPlanetNameUseCase(planetId)
                _planetName.value = name
            } catch (e: Exception) {
                _planetName.value = "Desconhecido"
            }
        }
    }
}