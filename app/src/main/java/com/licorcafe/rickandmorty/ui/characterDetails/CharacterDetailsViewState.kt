package com.licorcafe.rickandmorty.ui.characterDetails

import com.licorcafe.rickandmorty.common.TextRes

import okhttp3.HttpUrl

data class CharacterDetailsViewEntity(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: String,
    val thumbnail: HttpUrl,
    val locationName: String,
    val locationType: String,
    val locationDimension: String
)


sealed class CharactersDetailsViewState {
    val title: String
        get() = if (this is Content) character.name else ""
}

object Loading : CharactersDetailsViewState()

data class Content(
    val character: CharacterDetailsViewEntity
) : CharactersDetailsViewState()

data class Problem(val stringId: TextRes) : CharactersDetailsViewState()
