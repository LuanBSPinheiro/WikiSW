package com.example.wikisw.domain.usecase

import com.example.wikisw.domain.model.Character
import com.example.wikisw.domain.repository.StarWarsRepository

class GetCharactersUseCase(
    private val repository: StarWarsRepository
) {
    suspend operator fun invoke(): List<Character> {
        return repository.getCharacters()
    }
}