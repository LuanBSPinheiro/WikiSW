package com.example.wikisw.data.mapper

import com.example.wikisw.data.cache.CharacterEntity
import com.example.wikisw.domain.model.Character

fun CharacterEntity.toDomain(): Character {
    return Character(
        id = this.id,
        name = this.name
    )
}

fun Character.toCache(): CharacterEntity {
    return CharacterEntity(
        id = this.id,
        name = this.name
    )
}