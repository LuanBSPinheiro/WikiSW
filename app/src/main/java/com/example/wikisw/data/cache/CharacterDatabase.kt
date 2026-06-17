package com.example.wikisw.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterEntity::class, PlanetEntity::class], version = 2, exportSchema = false)
abstract class CharacterDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
}