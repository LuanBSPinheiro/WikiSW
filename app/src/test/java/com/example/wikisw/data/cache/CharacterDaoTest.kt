package com.example.wikisw.data.cache

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import android.content.Context
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CharacterDaoTest {

    private lateinit var database: CharacterDatabase
    private lateinit var dao: CharacterDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, CharacterDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.characterDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndGetCharacters_should_persist_and_return_correct_data() = runTest {
        // GIVEN
        val localCharacters = listOf(
            CharacterEntity(
                id = 1, name = "Luke Skywalker", height = "172", gender = "male", mass = "77",
                hairColor = "blond", skinColor = "fair", eyeColor = "blue", birthYear = "19BBY",
                homeworld = "Tatooine", species = "Human", isFavorite = false
            ),
            CharacterEntity(
                id = 2, name = "C-3PO", height = "167", gender = "n/a", mass = "75",
                hairColor = "n/a", skinColor = "gold", eyeColor = "yellow", birthYear = "112BBY",
                homeworld = "Tatooine", species = "Droid", isFavorite = false
            )
        )

        // WHEN
        dao.insertCharacters(localCharacters)
        val result = dao.getCharactersFlow("", false).first()

        // THEN
        assertEquals(2, result.size)
        // Note: getCharactersFlow orders by name ASC
        assertEquals("C-3PO", result[0].name)
        assertEquals("Luke Skywalker", result[1].name)
    }

    @Test
    fun updateFavoriteStatus_should_update_correct_character() = runTest {
        // GIVEN
        val char = CharacterEntity(
            id = 1, name = "Luke Skywalker", height = "172", gender = "male", mass = "77",
            hairColor = "blond", skinColor = "fair", eyeColor = "blue", birthYear = "19BBY",
            homeworld = "Tatooine", species = "Human", isFavorite = false
        )
        dao.insertCharacters(listOf(char))

        // WHEN
        dao.updateFavoriteStatus(1, true)
        val result = dao.getCharactersFlow("", true).first()

        // THEN
        assertEquals(1, result.size)
        assertEquals(true, result[0].isFavorite)
    }
}
