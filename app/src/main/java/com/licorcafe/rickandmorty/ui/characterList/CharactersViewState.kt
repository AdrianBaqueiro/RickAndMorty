package com.licorcafe.rickandmorty.ui.characterList

import com.licorcafe.rickandmorty.common.ErrorTextRes
import com.licorcafe.rickandmorty.common.TextRes
import okhttp3.HttpUrl

data class CharactersViewEntity(val id: Long, val name: String, val imageUrl: HttpUrl, val locationUrl: String)

sealed class CharactersViewState

object Loading : CharactersViewState()

data class Content(val characters: List<CharactersViewEntity>) : CharactersViewState()

data class Problem(val stringId: TextRes) : CharactersViewState()

val Problem.isRecoverable: Boolean
    get() = stringId is ErrorTextRes
