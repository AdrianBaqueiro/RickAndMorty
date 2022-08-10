package com.licorcafe.rickandmorty.domain.model

data class LocationDetails(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
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
        ): LocationDetails {
            return LocationDetails(
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
