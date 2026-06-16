package com.example.wikisw.data.cache

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import android.content.Context
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class) // Robolectric simula o ambiente do Android na JVM local
class CharacterDaoTest {

    private lateinit var database: CharacterDatabase
    private lateinit var dao: CharacterDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, CharacterDatabase::class.java).build()
        dao = database.characterDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertAndGetAllCharacters_should_persist_and_return_correct_data() = runTest {
        // GIVEN
        val localCharacters = listOf(
            CharacterEntity(id = 1, name = "Luke Skywalker"),
            CharacterEntity(id = 2, name = "C-3PO")
        )

        // WHEN
        dao.insertCharacters(localCharacters)
        val result = dao.getAllCharacters()

        // THEN
        assertEquals(2, result.size)
        assertEquals("Luke Skywalker", result[0].name)
        assertEquals(2, result[1].id)
    }
}