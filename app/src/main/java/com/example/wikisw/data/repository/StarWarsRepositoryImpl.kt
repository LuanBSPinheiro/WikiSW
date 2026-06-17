package com.example.wikisw.data.repository

import android.util.Log
import com.example.wikisw.data.api.StarWarsApi
import com.example.wikisw.data.cache.CharacterDao
import com.example.wikisw.data.cache.PlanetEntity
import com.example.wikisw.data.mapper.toCache
import com.example.wikisw.data.mapper.toDomain
import com.example.wikisw.domain.model.Character
import com.example.wikisw.domain.repository.StarWarsRepository

class StarWarsRepositoryImpl(
    private val api: StarWarsApi,
    private val dao: CharacterDao
) : StarWarsRepository {

    override suspend fun getCharacters(): List<Character> {
        try {
            val apiResult = api.fetchCharacters()
            Log.d("WikiSW", "Repository: API retornou ${apiResult.size} itens brutos.")

            if (apiResult.isNotEmpty()) {
                val localEntities = apiResult.map { dto ->
                    val domain = dto.toDomain()
                    Log.d("WikiSW", "Repository: Mapeado - ID: ${domain.id}, Nome: ${domain.name}")
                    domain.toCache()
                }

                dao.insertCharacters(localEntities)
                Log.d("WikiSW", "Repository: Inserido no Room com sucesso.")
            }
        } catch (e: Exception) {
            Log.e("WikiSW", "Repository: Falha ao buscar na API, tentando ler cache...", e)
        }

        val cachedEntities = dao.getAllCharacters()
        Log.d("WikiSW", "Repository: Lidos do Room ${cachedEntities.size} itens para a UI.")

        return cachedEntities.map { it.toDomain() }
    }

    override suspend fun getPlanetName(planetId: String): String {
        val cachedPlanet = dao.getPlanetById(planetId)
        if (cachedPlanet != null) return cachedPlanet.name

        return try {
            val apiPlanet = api.fetchPlanet(planetId)
            val entity = PlanetEntity(id = planetId, name = apiPlanet.name)

            dao.insertPlanet(entity)
            apiPlanet.name
        } catch (e: Exception) {
            "Desconhecido ($planetId)"
        }
    }
}