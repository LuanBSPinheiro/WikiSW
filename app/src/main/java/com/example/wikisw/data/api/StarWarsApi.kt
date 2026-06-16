package com.example.wikisw.data.api

import retrofit2.http.GET

data class CharacterDto(
    val name: String,
    val url: String
)

interface StarWarsApi {
    // O swapi.info retorna um Array/Lista direto de personagens no endpoint /people, diferente do antigo swapi.dev
    @GET("api/people")
    suspend fun fetchCharacters(): List<CharacterDto>
}