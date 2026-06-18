package com.example.wikisw.data.mapper

import com.example.wikisw.data.cache.CharacterEntity
import com.example.wikisw.domain.model.Character
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterMapperTest {

    @Test
    fun `toDomain should correctly map CharacterEntity to Character`() {
        // GIVEN
        val entity = CharacterEntity(
            id = 1,
            name = "Luke Skywalker",
            height = "172",
            gender = "male",
            mass = "77",
            hairColor = "blond",
            skinColor = "fair",
            eyeColor = "blue",
            birthYear = "19BBY",
            homeworld = "Tatooine",
            species = "Human",
            isFavorite = true
        )

        // WHEN
        val domain = entity.toDomain()

        // THEN
        assertEquals(entity.id, domain.id)
        assertEquals(entity.name, domain.name)
        assertEquals(entity.height, domain.height)
        assertEquals(entity.gender, domain.gender)
        assertEquals(entity.mass, domain.mass)
        assertEquals(entity.hairColor, domain.hairColor)
        assertEquals(entity.skinColor, domain.skinColor)
        assertEquals(entity.eyeColor, domain.eyeColor)
        assertEquals(entity.birthYear, domain.birthYear)
        assertEquals(entity.homeworld, domain.homeworld)
        assertEquals(entity.species, domain.species)
        assertEquals(entity.isFavorite, domain.isFavorite)
    }

    @Test
    fun `toCache should correctly map Character to CharacterEntity`() {
        // GIVEN
        val domain = Character(
            id = 2,
            name = "C-3PO",
            height = "167",
            gender = "n/a",
            mass = "75",
            hairColor = "n/a",
            skinColor = "gold",
            eyeColor = "yellow",
            birthYear = "112BBY",
            homeworld = "Tatooine",
            species = "Droid",
            isFavorite = false
        )

        // WHEN
        val entity = domain.toCache()

        // THEN
        assertEquals(domain.id, entity.id)
        assertEquals(domain.name, entity.name)
        assertEquals(domain.height, entity.height)
        assertEquals(domain.gender, entity.gender)
        assertEquals(domain.mass, entity.mass)
        assertEquals(domain.hairColor, entity.hairColor)
        assertEquals(domain.skinColor, entity.skinColor)
        assertEquals(domain.eyeColor, entity.eyeColor)
        assertEquals(domain.birthYear, entity.birthYear)
        assertEquals(domain.homeworld, entity.homeworld)
        assertEquals(domain.species, entity.species)
        assertEquals(domain.isFavorite, entity.isFavorite)
    }
}
