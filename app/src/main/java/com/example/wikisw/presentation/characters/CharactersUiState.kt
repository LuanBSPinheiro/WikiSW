package com.example.wikisw.presentation.characters

import com.example.wikisw.domain.model.Character

sealed interface CharactersUiState {
    object Loading : CharactersUiState
    data class Success(val characters: List<Character>) : CharactersUiState
    data class Error(val message: String) : CharactersUiState
}