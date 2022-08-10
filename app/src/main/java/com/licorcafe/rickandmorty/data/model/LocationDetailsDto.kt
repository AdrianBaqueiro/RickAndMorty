package com.licorcafe.rickandmorty.data.model

import com.licorcafe.rickandmorty.domain.model.LocationDetails
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

fun LocationDetailsDto.toDomain(): LocationDetails = LocationDetails.create(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = residents,
    url = url,
    created = created
)
