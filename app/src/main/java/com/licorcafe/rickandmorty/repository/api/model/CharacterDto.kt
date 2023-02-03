package com.licorcafe.rickandmorty.repository.api.model

import com.licorcafe.rickandmorty.domain.model.Character
import com.licorcafe.rickandmorty.domain.model.Location
import com.licorcafe.rickandmorty.repository.db.model.CharacterEntity
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(val name: String, val url: String)

@Serializable
data class CharacterDto(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationDto,
    val location: LocationDto,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

fun CharacterDto.toDomain(): Character = Character.create(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = Location(origin.name, origin.url),
    location = Location(location.name, location.url),
    image = image,
    episode = episode,
    url = url,
    created = created
)

fun CharacterDto.toDB(): CharacterEntity = CharacterEntity.create(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = Location(origin.name, origin.url),
    location = Location(location.name, location.url),
    image = image,
    episode = episode,
    url = url,
    created = created
)

