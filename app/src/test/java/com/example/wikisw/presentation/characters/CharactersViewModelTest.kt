package com.example.wikisw.presentation.characters

import com.example.wikisw.domain.model.Character
import com.example.wikisw.domain.usecase.GetCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersViewModelTest {

    private val getCharactersUseCaseMock = mock<GetCharactersUseCase>()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadCharacters_should_emit_Success_state_with_data() = runTest {
        // GIVEN
        val mockList = listOf(Character(id = 1, name = "Luke Skywalker"))
        whenever(getCharactersUseCaseMock()).doReturn(mockList)

        // WHEN
        val viewModel = CharactersViewModel(getCharactersUseCaseMock)

        // THEN
        val currentState = viewModel.uiState.value
        assert(currentState is CharactersUiState.Success)
        assertEquals(1, (currentState as CharactersUiState.Success).characters.size)
        assertEquals("Luke Skywalker", currentState.characters[0].name)
    }
}