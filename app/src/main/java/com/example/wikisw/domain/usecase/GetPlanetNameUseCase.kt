package com.example.wikisw.domain.usecase

import com.example.wikisw.domain.repository.StarWarsRepository

class GetPlanetNameUseCase(
    private val repository: StarWarsRepository
) {
    suspend operator fun invoke(planetId: String): String {
        return repository.getPlanetName(planetId)
    }
}