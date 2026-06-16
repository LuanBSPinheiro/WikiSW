package com.example.wikisw.data.mapper

import com.example.wikisw.data.api.CharacterDto
import com.example.wikisw.domain.model.Character


fun CharacterDto.toDomain(): Character {
    val id = this.url.split("/")
        .last { it.isNotEmpty() }
        .toInt()

    return Character(
        id = id,
        name = this.name
    )
}