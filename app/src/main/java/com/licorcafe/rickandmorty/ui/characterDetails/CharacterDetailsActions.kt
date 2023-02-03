package com.licorcafe.rickandmorty.ui.characterDetails

import com.licorcafe.rickandmorty.domain.model.CharacterId

sealed class CharacterDetailsAction
data class Refresh(val characterId: CharacterId, val locationUrl: String) : CharacterDetailsAction()
data class SaveChanges(val characterDetailsViewEntity: CharacterDetailsViewEntity) : CharacterDetailsAction()
object Up : CharacterDetailsAction()

sealed class CharacterDetailsEffect
object NavigateUp : CharacterDetailsEffect()
