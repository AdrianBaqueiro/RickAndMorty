package com.licorcafe.rickandmorty.repository.api.model

import kotlinx.serialization.Serializable

@Serializable
data class Paginated<A>(val results: List<A>)

typealias PaginatedEnvelope<A> = Paginated<A>
