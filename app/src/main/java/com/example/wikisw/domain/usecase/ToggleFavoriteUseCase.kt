package com.example.wikisw.domain.usecase

import com.example.wikisw.domain.repository.StarWarsRepository

/**
 * UseCase to toggle the favorite status of a character.
 */
class ToggleFavoriteUseCase(
    private val repository: StarWarsRepository
) {
    /**
     * Toggles the favorite status for the given character ID.
     *
     * @param characterId The ID of the character to update.
     * @param isFavorite The new favorite status.
     */
    suspend operator fun invoke(characterId: Int, isFavorite: Boolean) {
        repository.toggleFavorite(characterId, isFavorite)
    }
}
