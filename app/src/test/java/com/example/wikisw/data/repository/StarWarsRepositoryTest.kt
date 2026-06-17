package com.example.wikisw.data.repository

import com.example.wikisw.data.api.CharacterDto
import com.example.wikisw.data.api.StarWarsApi
import com.example.wikisw.data.cache.CharacterDao
import com.example.wikisw.data.cache.CharacterEntity
import com.example.wikisw.domain.repository.StarWarsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.io.IOException

class StarWarsRepositoryTest {

    private val apiMock = mock<StarWarsApi>()
    private val daoMock = mock<CharacterDao>()

    private val repository: StarWarsRepository = StarWarsRepositoryImpl(apiMock, daoMock)

    @Test
    fun getCharacters_should_fetch_from_api_and_save_to_cache_on_success() = runTest {
        // GIVEN
        val mockApiResult = listOf(CharacterDto(name = "Luke Skywalker", url = "https://swapi.info/api/people/1"))
        whenever(apiMock.fetchCharacters()).doReturn(mockApiResult)
        whenever(daoMock.getAllCharacters()).doReturn(emptyList())

        // WHEN
        val result = repository.getCharacters()

        // THEN
        assertEquals(1, result.size)
        assertEquals("Luke Skywalker", result[0].name)

        verify(daoMock).insertCharacters(any())
    }

    @Test
    fun getCharacters_should_return_cached_data_when_api_fails() = runTest {
        // GIVEN
        whenever(apiMock.fetchCharacters()).doThrow(IOException("No internet"))
        val cachedEntities = listOf(CharacterEntity(id = 2, name = "C-3PO"))
        whenever(daoMock.getAllCharacters()).doReturn(cachedEntities)

        // WHEN
        val result = repository.getCharacters()

        // THEN
        assertEquals(1, result.size)
        assertEquals("C-3PO", result[0].name)
        assertEquals(2, result[0].id)
    }
}