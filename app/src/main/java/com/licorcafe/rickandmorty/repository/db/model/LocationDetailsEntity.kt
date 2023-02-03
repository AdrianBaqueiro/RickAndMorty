package com.licorcafe.rickandmorty.repository.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.licorcafe.rickandmorty.domain.model.LocationDetails

@Entity(tableName = "location_details")
data class LocationDetailsEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    var url: String,
    var created: String
) {
    companion object {
        fun create(
            id: Int,
            name: String,
            type: String,
            dimension: String,
            residents: List<String>,
            url: String,
            created: String
        ): LocationDetailsEntity {
            return LocationDetailsEntity(
                id = id,
                name = name,
                type = type,
                dimension = dimension,
                residents = residents,
                url = url,
                created = created
            )

        }
    }
}

fun LocationDetailsEntity.toDomain(): LocationDetails = LocationDetails.create(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
    residents = residents,
    url = url,
    created = created
)