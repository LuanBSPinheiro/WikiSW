package com.example.wikisw.data.repository

import com.example.wikisw.data.api.CharacterDto
import com.example.wikisw.data.api.StarWarsApi
import com.example.wikisw.data.cache.CharacterDao
import com.example.wikisw.data.cache.CharacterEntity
import com.example.wikisw.domain.repository.StarWarsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class StarWarsRepositoryTest {

    private val apiMock = mock<StarWarsApi>()
    private val daoMock = mock<CharacterDao>()

    private val repository: StarWarsRepository = StarWarsRepositoryImpl(apiMock, daoMock)

    @Test
    fun `refreshCharacters should fetch from api and save to cache`() = runTest {
        // GIVEN
        val mockApiResult = listOf(
            CharacterDto(
                name = "Luke Skywalker",
                url = "https://swapi.info/api/people/1/"
            )
        )
        whenever(apiMock.fetchCharacters()).doReturn(mockApiResult)

        // WHEN
        repository.refreshCharacters()

        // THEN
        verify(apiMock).fetchCharacters()
        verify(daoMock).insertCharacters(any())
    }

    @Test
    fun `getCharacters should return characters from dao flow`() = runTest {
        // GIVEN
        val cachedEntities = listOf(
            CharacterEntity(
                id = 2, name = "C-3PO", height = "167", gender = "n/a", mass = "75",
                hairColor = "n/a", skinColor = "gold", eyeColor = "yellow", birthYear = "112BBY",
                homeworld = "Tatooine", species = "Droid"
            )
        )
        whenever(daoMock.getCharactersFlow("", false)).thenReturn(flowOf(cachedEntities))

        // WHEN
        val result = repository.getCharacters("", false).first()

        // THEN
        assertEquals(1, result.size)
        assertEquals("C-3PO", result[0].name)
    }
}
