package com.example.wikisw.domain.model

data class Character(
    val id: Int,
    val name: String,
    val height: String,
    val gender: String,
    val mass: String,
    val hairColor: String,
    val skinColor: String,
    val eyeColor: String,
    val birthYear: String,
    val homeworld: String,
    val species: String,
    val isFavorite: Boolean
)