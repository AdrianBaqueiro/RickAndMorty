package com.licorcafe.rickandmorty.domain.model

import com.licorcafe.rickandmorty.repository.db.model.CharacterEntity

typealias CharacterId = Long
typealias LocationUrl = String

data class Location(val name: String, val url: String)

data class Character(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
) {
    companion object {
        fun create(
            id: Long,
            name: String,
            status: String,
            species: String,
            type: String,
            gender: String,
            origin: Location,
            location: Location,
            image: String,
            episode: List<String>,
            url: String,
            created: String
        ): Character {
            return Character(
                id = id,
                name = name,
                status = status,
                species = species,
                type = type,
                gender = gender,
                origin = origin,
                location = location,
                image = image,
                episode = episode,
                url = url,
                created = created
            )
        }
    }
}

data class Characters(val characters: List<Character>)
data class CharacterDetails(val characters: Character, val locationDetails: LocationDetails)

fun Character.toDB(): CharacterEntity = CharacterEntity.create(
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
