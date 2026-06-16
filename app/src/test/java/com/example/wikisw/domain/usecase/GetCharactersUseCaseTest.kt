package com.example.wikisw.domain.usecase

import com.example.wikisw.domain.model.Character
import com.example.wikisw.domain.repository.StarWarsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetCharactersUseCaseTest {

    private val repositoryMock = mock<StarWarsRepository>()

    private val useCase = GetCharactersUseCase(repositoryMock)

    @Test
    fun invoke_should_return_characters_from_repository() = runTest {
        val mockCharacters = listOf(Character(id = 1, name = "Luke Skywalker"))
        whenever(repositoryMock.getCharacters()).doReturn(mockCharacters)

        val result = useCase()

        assertEquals(1, result.size)
        assertEquals("Luke Skywalker", result[0].name)
    }
}