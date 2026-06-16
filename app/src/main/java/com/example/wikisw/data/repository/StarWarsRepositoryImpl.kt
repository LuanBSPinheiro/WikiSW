package com.example.wikisw.data.repository

import com.example.wikisw.data.api.StarWarsApi
import com.example.wikisw.domain.model.Character
import com.example.wikisw.domain.repository.StarWarsRepository

class StarWarsRepositoryImpl(
    private val api: StarWarsApi
) : StarWarsRepository {

    override suspend fun getCharacters(): List<Character> {
        val dtos = api.fetchCharacters()

        return dtos.map { dto ->
            val id = dto.url.split("/")
                .last { it.isNotEmpty() }
                .toInt()

            Character(
                id = id,
                name = dto.name
            )
        }
    }
}