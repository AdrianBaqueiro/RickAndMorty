package com.licorcafe.rickandmorty.ui.characterList

import com.licorcafe.rickandmorty.domain.model.CharacterId
import com.licorcafe.rickandmorty.domain.model.LocationUrl

sealed class CharacterAction
object Refresh : CharacterAction()
data class LoadDetails(val id: CharacterId, val url: LocationUrl) : CharacterAction()

sealed class CharactersEffect
data class NavigateToDetails(val characterId: CharacterId, val locationUrl: LocationUrl) : CharactersEffect()
