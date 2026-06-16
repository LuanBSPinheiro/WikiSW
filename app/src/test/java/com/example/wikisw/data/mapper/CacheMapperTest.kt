package com.example.wikisw.data.mapper

import com.example.wikisw.data.cache.CharacterEntity
import com.example.wikisw.domain.model.Character
import org.junit.Assert.assertEquals
import org.junit.Test

class CacheMapperTest {

    @Test
    fun entityToDomain_should_map_correctly() {
        // GIVEN
        val entity = CharacterEntity(id = 1, name = "Luke Skywalker")

        // WHEN
        val domain: Character = entity.toDomain()

        // THEN
        assertEquals(1, domain.id)
        assertEquals("Luke Skywalker", domain.name)
    }

    @Test
    fun domainToEntity_should_map_correctly() {
        // GIVEN
        val domain = Character(id = 2, name = "C-3PO")

        // WHEN
        val entity: CharacterEntity = domain.toCache()

        // THEN
        assertEquals(2, entity.id)
        assertEquals("C-3PO", entity.name)
    }
}