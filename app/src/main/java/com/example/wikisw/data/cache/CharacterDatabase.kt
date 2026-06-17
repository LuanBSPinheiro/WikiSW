package com.example.wikisw.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CharacterEntity::class, PlanetEntity::class, SpeciesEntity::class],
    version = 3,
    exportSchema = false
)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
}