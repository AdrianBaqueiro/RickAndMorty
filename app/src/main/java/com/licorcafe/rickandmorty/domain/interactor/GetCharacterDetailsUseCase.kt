package com.licorcafe.rickandmorty.domain.interactor

import com.licorcafe.rickandmorty.data.RickAndMortyRepository
import com.licorcafe.rickandmorty.domain.model.CharacterDetails

interface GetCharacterDetailsUseCase {
    suspend fun execute(characterId: Long, locationUrl: String): CharacterDetails
}

class GetCharacterDetailsUseCaseImpl(private val rickAndMortyRepository: RickAndMortyRepository) :
    GetCharacterDetailsUseCase {
    override suspend fun execute(characterId: Long, locationUrl: String): CharacterDetails {
        val character = rickAndMortyRepository.getCharacter(characterId)
        val locationDetails = rickAndMortyRepository.getLocationDetails(locationUrl)
        return CharacterDetails(character, locationDetails)
    }
}
