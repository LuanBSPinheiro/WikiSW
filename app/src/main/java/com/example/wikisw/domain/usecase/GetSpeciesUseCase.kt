package com.example.wikisw.domain.usecase

import com.example.wikisw.domain.repository.StarWarsRepository

class GetSpeciesNameUseCase(private val repository: StarWarsRepository) {
    suspend operator fun invoke(speciesId: String): String = repository.getSpeciesName(speciesId)
}