package com.example.wikisw.domain.repository

import com.example.wikisw.domain.model.Character

interface StarWarsRepository {
    suspend fun getCharacters(): List<Character>

    suspend fun getPlanetName(planetId: String): String
}