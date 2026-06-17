package com.example.wikisw.domain.usecase

import com.example.wikisw.domain.model.Character
import com.example.wikisw.domain.repository.StarWarsRepository
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase(
    private val repository: StarWarsRepository
) {
    suspend fun refresh() {
        repository.refreshCharacters()
    }

    fun executeFlow(searchQuery: String, onlyFavorites: Boolean): Flow<List<Character>> {
        return repository.getCharacters(searchQuery, onlyFavorites)
    }
}