package com.licorcafe.rickandmorty.domain.interactor

import com.licorcafe.rickandmorty.domain.model.Character
import com.licorcafe.rickandmorty.domain.model.CharacterDetails
import com.licorcafe.rickandmorty.repository.RickAndMortyRepository

interface GetCharacterDetailsUseCase {
    suspend fun execute(characterId: Long, locationUrl: String): CharacterDetails
    suspend fun getCharacter(characterId: Long): Character
    suspend fun saveCharacterDetails(character: Character)
}

class GetCharacterDetailsUseCaseImpl(private val rickAndMortyRepository: RickAndMortyRepository) :
    GetCharacterDetailsUseCase {
    override suspend fun execute(characterId: Long, locationUrl: String): CharacterDetails {
        val character = rickAndMortyRepository.getCharacter(characterId)
        val locationDetails = rickAndMortyRepository.getLocationDetails(locationUrl)
        return CharacterDetails(character, locationDetails)
    }

    override suspend fun getCharacter(characterId: Long) =
        rickAndMortyRepository.getCharacter(characterId)


    override suspend fun saveCharacterDetails(character: Character) =
        rickAndMortyRepository.updateCharacter(character)

}
