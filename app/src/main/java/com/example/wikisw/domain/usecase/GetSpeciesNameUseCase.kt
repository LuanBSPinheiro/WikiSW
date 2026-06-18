package com.example.wikisw.domain.usecase

import com.example.wikisw.domain.repository.StarWarsRepository

/**
 * UseCase to retrieve the name of a species by its ID.
 */
class GetSpeciesNameUseCase(
    private val repository: StarWarsRepository
) {
    /**
     * Executes the request to get a species name.
     *
     * @param speciesId The ID of the species.
     * @return The name of the species.
     */
    suspend operator fun invoke(speciesId: String): String {
        return repository.getSpeciesName(speciesId)
    }
}
