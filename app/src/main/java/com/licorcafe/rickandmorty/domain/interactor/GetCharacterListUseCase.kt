package com.licorcafe.rickandmorty.domain.interactor

import com.licorcafe.rickandmorty.domain.model.Characters
import com.licorcafe.rickandmorty.repository.RickAndMortyRepository

interface GetCharacterListUseCase {
    suspend fun execute(): Characters
}

class GetCharacterListUseCaseImpl(private val rickAndMortyRepository: RickAndMortyRepository) :
    GetCharacterListUseCase {
    override suspend fun execute(): Characters {
        return Characters(rickAndMortyRepository.getCharacterList())
    }
}
