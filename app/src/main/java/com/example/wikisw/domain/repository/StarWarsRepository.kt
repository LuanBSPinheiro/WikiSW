package com.example.wikisw.domain.repository

import com.example.wikisw.domain.model.Character
import kotlinx.coroutines.flow.Flow

interface StarWarsRepository {
    suspend fun refreshCharacters()

    fun getCharacters(searchQuery: String, onlyFavorites: Boolean): Flow<List<Character>>

    suspend fun toggleFavorite(characterId: Int, isFavorite: Boolean)

    suspend fun getPlanetName(planetId: String): String
    suspend fun getSpeciesName(speciesId: String): String
    fun getCharacterById(characterId: Int): Flow<Character?>
}