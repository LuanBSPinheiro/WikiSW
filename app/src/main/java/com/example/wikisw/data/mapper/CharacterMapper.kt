package com.example.wikisw.data.mapper

import com.example.wikisw.data.api.CharacterDto
import com.example.wikisw.data.cache.CharacterEntity

fun CharacterDto.toEntity(): CharacterEntity {
    val characterId = url.trimEnd('/').substringAfterLast("/").toIntOrNull() ?: 0
    val planetId = homeworld.trimEnd('/').substringAfterLast("/")
    val speciesId = species.firstOrNull()?.trimEnd('/')?.substringAfterLast("/") ?: "1" // Default para Humano caso venha vazio

    return CharacterEntity(
        id = characterId,
        name = name,
        height = height,
        gender = gender,
        mass = mass,
        hairColor = hair_color,
        skinColor = skin_color,
        eyeColor = eye_color,
        birthYear = birth_year,
        homeworld = planetId,
        species = speciesId,
        isFavorite = false // Por padrão, dados novos da API começam desfavoritados
    )
}