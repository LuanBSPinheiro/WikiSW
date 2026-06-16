package com.example.wikisw.data.repository

import com.example.wikisw.data.api.CharacterDto
import com.example.wikisw.data.api.StarWarsApi
import com.example.wikisw.domain.repository.StarWarsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class StarWarsRepositoryTest {

    private val apiMock = mock<StarWarsApi>()

    private val repository: StarWarsRepository = StarWarsRepositoryImpl(apiMock)

    @Test
    fun getCharacters_should_return_mapped_characters_on_success() = runTest {
        val mockApiResponse = listOf(
            CharacterDto(
                name = "Luke Skywalker",
                url = "https://swapi.info/api/people/1"
            )
        )
        whenever(apiMock.fetchCharacters()).doReturn(mockApiResponse)

        // WHEN
        val result = repository.getCharacters()

        // THEN
        assertEquals(1, result.size)
        assertEquals("Luke Skywalker", result[0].name)
        assertEquals(1, result[0].id)
    }
}