package com.licorcafe.rickandmorty.data

import com.licorcafe.rickandmorty.data.model.CharacterDto
import com.licorcafe.rickandmorty.data.model.LocationDetailsDto
import com.licorcafe.rickandmorty.data.model.PaginatedEnvelope
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
