package com.example.wikisw.domain.model

data class Character(
    val id: Int,
    val name: String,
    val height: String,
    val gender: String,
    val mass: String,
    val hairColor: String = "N/A",
    val skinColor: String = "N/A",
    val eyeColor: String = "N/A",
    val birthYear: String = "N/A",
    val homeworld: String = "N/A",
    val species: String = "N/A"
)