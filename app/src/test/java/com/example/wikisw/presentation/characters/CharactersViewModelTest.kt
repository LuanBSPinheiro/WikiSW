package com.example.wikisw.presentation.characters

import com.example.wikisw.domain.model.Character
import com.example.wikisw.domain.usecase.GetCharacterByIdUseCase
import com.example.wikisw.domain.usecase.GetCharactersUseCase
import com.example.wikisw.domain.usecase.GetPlanetNameUseCase
import com.example.wikisw.domain.usecase.GetSpeciesNameUseCase
import com.example.wikisw.domain.usecase.RefreshCharactersUseCase
import com.example.wikisw.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersViewModelTest {

    private val getCharactersUseCase = mock<GetCharactersUseCase>()
    private val getCharacterByIdUseCase = mock<GetCharacterByIdUseCase>()
    private val refreshCharactersUseCase = mock<RefreshCharactersUseCase>()
    private val getPlanetNameUseCase = mock<GetPlanetNameUseCase>()
    private val getSpeciesNameUseCase = mock<GetSpeciesNameUseCase>()
    private val toggleFavoriteUseCase = mock<ToggleFavoriteUseCase>()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should refresh characters and observe flow`() = runTest {
        // GIVEN
        val mockList = listOf(
            Character(
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
                isFavorite = false
            )
        )
        whenever(getCharactersUseCase(any(), any())).thenReturn(flowOf(mockList))

        // WHEN
        val viewModel = CharactersViewModel(
            getCharactersUseCase,
            getCharacterByIdUseCase,
            refreshCharactersUseCase,
            getPlanetNameUseCase,
            getSpeciesNameUseCase,
            toggleFavoriteUseCase
        )

        advanceUntilIdle()

        // THEN
        val currentState = viewModel.uiState.value
        assert(currentState is CharactersUiState.Success)
        assertEquals(1, (currentState as CharactersUiState.Success).characters.size)
        assertEquals("Luke Skywalker", currentState.characters[0].name)
    }
}
