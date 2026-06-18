package com.example.wikisw.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("""
        SELECT * FROM characters 
        WHERE name LIKE '%' || :searchQuery || '%' 
        AND (:onlyFavorites = 0 OR isFavorite = 1)
        ORDER BY name ASC
    """)
    fun getCharactersFlow(searchQuery: String, onlyFavorites: Boolean): Flow<List<CharacterEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    @Query("UPDATE characters SET isFavorite = :isFavorite WHERE id = :characterId")
    suspend fun updateFavoriteStatus(characterId: Int, isFavorite: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanet(planet: PlanetEntity)

    @Query("SELECT * FROM planets WHERE id = :id")
    suspend fun getPlanetById(id: String): PlanetEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertSpecies(species: SpeciesEntity)
    @Query("SELECT * FROM species WHERE id = :id")
    suspend fun getSpeciesById(id: String): SpeciesEntity?

    @Query("SELECT * FROM characters WHERE id = :characterId")
    fun getCharacterByIdFlow(characterId: Int): Flow<CharacterEntity?>
}