package com.example.wikisw.data.api

import retrofit2.http.GET
import retrofit2.http.Path

data class CharacterDto(
    val name: String,
    val url: String,
    val height: String = "172",
    val gender: String = "male",
    val mass: String = "77",
    val hair_color: String = "blond",
    val skin_color: String = "fair",
    val eye_color: String = "blue",
    val birth_year: String = "19BBY",
    val homeworld: String = "Tatooine",
    val species: List<String> = emptyList()
)

data class PlanetDto(
    val name: String,
)

interface StarWarsApi {
    @GET("api/people")
    suspend fun fetchCharacters(): List<CharacterDto>

    @GET("api/planets/{id}")
    suspend fun fetchPlanet(@Path("id") id: String): PlanetDto
}