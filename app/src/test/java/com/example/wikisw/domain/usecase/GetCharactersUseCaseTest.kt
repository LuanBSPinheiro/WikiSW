package com.example.wikisw.domain.usecase

import com.example.wikisw.domain.model.Character
import com.example.wikisw.domain.repository.StarWarsRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetCharactersUseCaseTest {

    private val repository = mock<StarWarsRepository>()
    private val useCase = GetCharactersUseCase(repository)

    @Test
    fun `invoke should return characters from repository`() = runTest {
        // GIVEN
        val mockCharacters = listOf(
            Character(
                id = 1,
                name = "Luke",
                height = "172",
                gender = "male",
                mass = "77",
                hairColor = "blond",
                skinColor = "fair",
                eyeColor = "blue",
                birthYear = "19BBY",
                homeworld = "Tatooine",
                species = "Human",
                isFavorite = false
            )
        )
        whenever(repository.getCharacters("", false)).thenReturn(flowOf(mockCharacters))

        // WHEN
        val result = useCase("", false)

        // THEN
        result.collect { characters ->
            assertEquals(1, characters.size)
            assertEquals("Luke", characters[0].name)
        }
    }
}
