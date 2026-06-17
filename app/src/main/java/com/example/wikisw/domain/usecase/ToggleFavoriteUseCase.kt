package com.example.wikisw.domain.usecase

import com.example.wikisw.domain.repository.StarWarsRepository

class ToggleFavoriteUseCase(private val repository: StarWarsRepository) {
    suspend operator fun invoke(characterId: Int, isFavorite: Boolean) {
        repository.toggleFavorite(characterId, isFavorite)
    }
}