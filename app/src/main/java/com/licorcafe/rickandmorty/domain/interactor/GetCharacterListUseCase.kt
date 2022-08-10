package com.licorcafe.rickandmorty.domain.interactor

import com.licorcafe.rickandmorty.data.RickAndMortyRepository
import com.licorcafe.rickandmorty.domain.model.Characters

interface GetCharacterListUseCase {
    suspend fun execute(): Characters
}

class GetCharacterListUseCaseImpl(private val rickAndMortyRepository: RickAndMortyRepository) :
    GetCharacterListUseCase {
    override suspend fun execute(): Characters {
        return Characters(rickAndMortyRepository.getCharacterList())
    }
}
