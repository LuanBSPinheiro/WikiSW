package com.example.wikisw.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "species")
data class SpeciesEntity(
    @PrimaryKey val id: String,
    val name: String
)