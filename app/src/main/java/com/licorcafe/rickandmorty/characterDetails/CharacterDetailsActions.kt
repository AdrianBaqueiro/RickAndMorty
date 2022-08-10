package com.licorcafe.rickandmorty.characterDetails

import com.licorcafe.rickandmorty.domain.model.CharacterId

sealed class CharacterDetailsAction
data class Refresh(val characterId: CharacterId, val locationUrl: String) : CharacterDetailsAction()
object Up : CharacterDetailsAction()

sealed class CharacterDetailsEffect
object NavigateUp : CharacterDetailsEffect()
