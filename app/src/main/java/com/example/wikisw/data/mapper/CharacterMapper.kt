package com.example.wikisw.data.mapper

import com.example.wikisw.data.api.CharacterDto
import com.example.wikisw.domain.model.Character

fun CharacterDto.toDomain(): Character {
    val cleanedUrl = url.trimEnd('/')
    val characterId = cleanedUrl.substringAfterLast("/").toIntOrNull() ?: 0

    return Character(
        id = characterId,
        name = this.name,
        height = this.height,
        gender = this.gender,
        mass = this.mass,
        hairColor = this.hair_color,
        skinColor = this.skin_color,
        eyeColor = this.eye_color,
        birthYear = this.birth_year,
        homeworld = this.homeworld.trimEnd('/').substringAfterLast("/"),
        species = this.species.firstOrNull()?.trimEnd('/')?.substringAfterLast("/") ?: "Human"
    )
}