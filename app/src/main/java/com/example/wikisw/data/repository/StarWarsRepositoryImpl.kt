package com.example.wikisw.data.repository

import com.example.wikisw.data.api.StarWarsApi
import com.example.wikisw.data.cache.CharacterDao
import com.example.wikisw.data.mapper.toCache
import com.example.wikisw.data.mapper.toDomain
import com.example.wikisw.domain.model.Character
import com.example.wikisw.domain.repository.StarWarsRepository

class StarWarsRepositoryImpl(
    private val api: StarWarsApi,
    private val dao: CharacterDao
) : StarWarsRepository {

    override suspend fun getCharacters(): List<Character> {
        return try {
            val apiCharacters = api.fetchCharacters()
            val domainList = apiCharacters.map { it.toDomain() }
            val entities = domainList.map { it.toCache() }

            dao.insertCharacters(entities)

            domainList
        } catch (e: Exception) {
            val cachedEntities = dao.getAllCharacters()

            cachedEntities.map { it.toDomain() }
        }
    }
}