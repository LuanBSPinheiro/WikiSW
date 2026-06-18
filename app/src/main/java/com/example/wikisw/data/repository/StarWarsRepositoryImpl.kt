package com.example.wikisw.data.repository

import android.util.Log
import com.example.wikisw.data.api.StarWarsApi
import com.example.wikisw.data.cache.CharacterDao
import com.example.wikisw.data.cache.PlanetEntity
import com.example.wikisw.data.cache.SpeciesEntity
import com.example.wikisw.data.mapper.toEntity
import com.example.wikisw.data.mapper.toDomain
import com.example.wikisw.domain.model.Character
import com.example.wikisw.domain.repository.StarWarsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of [StarWarsRepository] that coordinates data between the network and local cache.
 */
class StarWarsRepositoryImpl(
    private val api: StarWarsApi,
    private val dao: CharacterDao
) : StarWarsRepository {

    override suspend fun refreshCharacters() {
        try {
            val apiCharacters = api.fetchCharacters()
            val entities = apiCharacters.map { it.toEntity() }
            dao.insertCharacters(entities)
        } catch (e: Exception) {
            Log.e("StarWarsRepository", "Error refreshing characters", e)
            // Error propagation could be improved here with a Result wrapper
        }
    }

    override fun getCharacters(searchQuery: String, onlyFavorites: Boolean): Flow<List<Character>> {
        return dao.getCharactersFlow(searchQuery, onlyFavorites).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun toggleFavorite(characterId: Int, isFavorite: Boolean) {
        dao.updateFavoriteStatus(characterId, isFavorite)
    }

    override suspend fun getPlanetName(planetId: String): String {
        val cached = dao.getPlanetById(planetId)
        if (cached != null) return cached.name

        return try {
            val res = api.fetchPlanet(planetId)
            dao.insertPlanet(PlanetEntity(planetId, res.name))
            res.name
        } catch (e: Exception) {
            Log.e("StarWarsRepository", "Error fetching planet $planetId", e)
            "Desconhecido ($planetId)"
        }
    }

    override suspend fun getSpeciesName(speciesId: String): String {
        val cached = dao.getSpeciesById(speciesId)
        if (cached != null) return cached.name

        return try {
            val res = api.fetchSpecies(speciesId)
            dao.insertSpecies(SpeciesEntity(speciesId, res.name))
            res.name
        } catch (e: Exception) {
            Log.e("StarWarsRepository", "Error fetching species $speciesId", e)
            "Desconhecido ($speciesId)"
        }
    }

    override fun getCharacterById(characterId: Int): Flow<Character?> {
        return dao.getCharacterByIdFlow(characterId).map { entity ->
            entity?.toDomain()
        }
    }
}
