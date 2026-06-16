package com.example.wikisw.data.repository

import com.example.wikisw.data.api.StarWarsApi
import com.example.wikisw.data.mapper.toDomain
import com.example.wikisw.domain.model.Character
import com.example.wikisw.domain.repository.StarWarsRepository

class StarWarsRepositoryImpl(
    private val api: StarWarsApi
) : StarWarsRepository {

    override suspend fun getCharacters(): List<Character> {
        return api.fetchCharacters().map { it.toDomain() }
    }
}