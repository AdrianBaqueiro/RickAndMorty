package com.licorcafe.rickandmorty.repository.db.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.licorcafe.rickandmorty.domain.model.Character
import com.licorcafe.rickandmorty.domain.model.Location

data class LocationEntity(
    val locationName: String,
    val locationUrl: String,
)

@Entity(tableName = "character")
data class CharacterEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    var gender: String,
    @Embedded(prefix = "origin")
    var origin: LocationEntity,
    @Embedded(prefix = "location")
    var location: LocationEntity,
    var image: String,
    var episode: List<String>,
    var url: String,
    var created: String,
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
        ): CharacterEntity {
            return CharacterEntity(
                id = id,
                name = name,
                status = status,
                species = species,
                type = type,
                gender = gender,
                origin = LocationEntity(origin.name, origin.url),
                location = LocationEntity(location.name, location.url),
                image = image,
                episode = episode,
                url = url,
                created = created
            )
        }
    }
}

fun CharacterEntity.toDomain(): Character = Character.create(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = Location(origin.locationName, origin.locationUrl),
    location = Location(location.locationName, location.locationUrl),
    image = image,
    episode = episode,
    url = url,
    created = created
)
