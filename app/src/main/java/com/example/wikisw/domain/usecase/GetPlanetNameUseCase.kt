package com.example.wikisw.domain.usecase

import com.example.wikisw.domain.repository.StarWarsRepository

/**
 * UseCase to retrieve the name of a planet by its ID.
 * This UseCase handles fetching from cache or API via the repository.
 */
class GetPlanetNameUseCase(
    private val repository: StarWarsRepository
) {
    /**
     * Executes the request to get a planet name.
     *
     * @param planetId The ID of the planet (extracted from the URL).
     * @return The name of the planet or a placeholder if not found.
     */
    suspend operator fun invoke(planetId: String): String {
        return repository.getPlanetName(planetId)
    }
}
