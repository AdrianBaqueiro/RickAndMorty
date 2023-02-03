package com.licorcafe.rickandmorty.repository.api.model

import com.licorcafe.rickandmorty.repository.db.model.LocationDetailsEntity
import kotlinx.serialization.Serializable

@Serializable
data class LocationDetailsDto(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)

fun LocationDetailsDto.toDB(): LocationDetailsEntity = LocationDetailsEntity.create(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = residents,
    url = url,
    created = created
)
