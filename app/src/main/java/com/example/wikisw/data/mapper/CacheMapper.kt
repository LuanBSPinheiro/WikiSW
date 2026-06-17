package com.example.wikisw.data.mapper

import com.example.wikisw.data.cache.CharacterEntity
import com.example.wikisw.domain.model.Character

fun CharacterEntity.toDomain(): Character {
    return Character(
        id = this.id,
        name = this.name,
        height = this.height,
        gender = this.gender,
        mass = this.mass,
        hairColor = this.hairColor,
        skinColor = this.skinColor,
        eyeColor = this.eyeColor,
        birthYear = this.birthYear,
        homeworld = this.homeworld,
        species = this.species
    )
}

fun Character.toCache(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name,
        height = this.height,
        gender = this.gender,
        mass = this.mass,
        hairColor = this.hairColor,
        skinColor = this.skinColor,
        eyeColor = this.eyeColor,
        birthYear = this.birthYear,
        homeworld = this.homeworld,
        species = this.species
    )
}