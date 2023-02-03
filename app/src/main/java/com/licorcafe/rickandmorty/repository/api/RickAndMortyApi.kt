package com.licorcafe.rickandmorty.repository.api

import com.licorcafe.rickandmorty.repository.api.model.CharacterDto
import com.licorcafe.rickandmorty.repository.api.model.LocationDetailsDto
import com.licorcafe.rickandmorty.repository.api.model.PaginatedEnvelope
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacterList(): PaginatedEnvelope<CharacterDto>

    @GET("character/?page={pageNumber}")
    suspend fun getCharacterListPage(@Path("pageNumber") pageNumber: Int): PaginatedEnvelope<CharacterDto>

    @GET("character/{characterId}")
    suspend fun getCharacter(@Path("characterId") characterId: Long): CharacterDto

    @GET
    suspend fun getLocation(@Url locationUrl: String): LocationDetailsDto

}
