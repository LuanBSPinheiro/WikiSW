package com.example.wikisw.domain.usecase

import com.example.wikisw.domain.repository.StarWarsRepository

/**
 * UseCase to refresh the character data from the remote API and update the local cache.
 */
class RefreshCharactersUseCase(
    private val repository: StarWarsRepository
) {
    /**
     * Triggers a refresh of the character records.
     */
    suspend operator fun invoke() {
        repository.refreshCharacters()
    }
}
