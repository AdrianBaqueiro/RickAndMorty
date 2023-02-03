package com.licorcafe.rickandmorty.repository

import com.licorcafe.rickandmorty.repository.api.RickAndMortyApi
import com.licorcafe.rickandmorty.repository.api.model.CharacterDto
import com.licorcafe.rickandmorty.repository.api.model.LocationDetailsDto

interface RickAndMortyDataSource {
    suspend fun getCharacterList(): List<CharacterDto>
    suspend fun getCharacterListPage(pageNumber: Int): List<CharacterDto>
    suspend fun getCharacter(characterId: Long): CharacterDto
    suspend fun getLocationDetails(locationUrl: String): LocationDetailsDto
}

class RickAndMortyDataSourceImpl(private val rickAndMortyApi: RickAndMortyApi): RickAndMortyDataSource {
    override suspend fun getCharacterList(): List<CharacterDto> {
        return rickAndMortyApi.getCharacterList().results
    }

    override suspend fun getCharacterListPage(pageNumber: Int): List<CharacterDto> {
        return rickAndMortyApi.getCharacterListPage(pageNumber).results
    }

    override suspend fun getCharacter(characterId: Long): CharacterDto {
        return rickAndMortyApi.getCharacter(characterId)
    }

    override suspend fun getLocationDetails(locationUrl: String): LocationDetailsDto {
        return rickAndMortyApi.getLocation(locationUrl)
    }
}

