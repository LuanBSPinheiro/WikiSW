package com.example.wikisw.domain.usecase

import com.example.wikisw.domain.model.Character
import com.example.wikisw.domain.repository.StarWarsRepository
import kotlinx.coroutines.flow.Flow

/**
 * UseCase to retrieve a single Star Wars character by their unique ID.
 */
class GetCharacterByIdUseCase(
    private val repository: StarWarsRepository
) {
    /**
     * Executes the flow to retrieve a character by ID.
     *
     * @param characterId The unique identifier of the character.
     * @return A [Flow] containing the [Character] if found, or null otherwise.
     */
    operator fun invoke(characterId: Int): Flow<Character?> {
        return repository.getCharacterById(characterId)
    }
}
