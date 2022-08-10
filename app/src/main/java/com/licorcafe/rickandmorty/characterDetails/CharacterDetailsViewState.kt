package com.licorcafe.rickandmorty.characterDetails

import com.licorcafe.rickandmorty.common.PlaceholderString
import com.licorcafe.rickandmorty.common.TextRes
import okhttp3.HttpUrl

data class CharacterDetailsViewEntity(
    val name: PlaceholderString,
    val status: PlaceholderString,
    val species: PlaceholderString,
    val gender: PlaceholderString,
    val origin: PlaceholderString,
    val thumbnail: HttpUrl,
    val locationName: PlaceholderString,
    val locationType: PlaceholderString,
    val locationDimension: PlaceholderString
)

sealed class CharactersDetailsViewState {
    val title: String
        get() = if (this is Content) character.name.replacement else ""
}

object Loading : CharactersDetailsViewState()

data class Content(
    val character: CharacterDetailsViewEntity
) : CharactersDetailsViewState()

data class Problem(val stringId: TextRes) : CharactersDetailsViewState()
