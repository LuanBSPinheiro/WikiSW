package com.example.wikisw.domain.usecase

import com.example.wikisw.domain.model.Character
import com.example.wikisw.domain.repository.StarWarsRepository
import kotlinx.coroutines.flow.Flow

/**
 * UseCase to retrieve a list of Star Wars characters based on search query and favorite status.
 *
 * This class follows the Single Responsibility Principle (SRP) and provides a reactive
 * flow of data from the repository.
 */
class GetCharactersUseCase(
    private val repository: StarWarsRepository
) {
    /**
     * Executes the flow to retrieve characters.
     *
     * @param searchQuery The search string to filter characters by name.
     * @param onlyFavorites Whether to return only characters marked as favorites.
     * @return A [Flow] containing the list of [Character] objects.
     */
    operator fun invoke(searchQuery: String, onlyFavorites: Boolean): Flow<List<Character>> {
        return repository.getCharacters(searchQuery, onlyFavorites)
    }
}
