package com.licorcafe.rickandmorty.characterDetails

import androidx.lifecycle.ViewModel
import com.licorcafe.rickandmorty.R
import com.licorcafe.rickandmorty.common.ErrorTextRes
import com.licorcafe.rickandmorty.common.ExceptionManager
import com.licorcafe.rickandmorty.common.IdTextRes
import com.licorcafe.rickandmorty.common.NetworkError
import com.licorcafe.rickandmorty.common.PlaceholderString
import com.licorcafe.rickandmorty.common.ServerError
import com.licorcafe.rickandmorty.common.Unrecoverable
import com.licorcafe.rickandmorty.domain.interactor.GetCharacterDetailsUseCase
import com.licorcafe.rickandmorty.domain.model.Character
import com.licorcafe.rickandmorty.domain.model.CharacterId
import com.licorcafe.rickandmorty.domain.model.LocationDetails
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.HttpUrl.Companion.toHttpUrl
import java.util.concurrent.CancellationException


class CharacterDetailsViewModel(
    private val characterDetailsUseCase: GetCharacterDetailsUseCase
): ViewModel() {

    private val _viewState: MutableStateFlow<CharactersDetailsViewState> = MutableStateFlow(Loading)
    val viewState: Flow<CharactersDetailsViewState>
        get() = _viewState.filterNotNull()

    private val _viewEffects = Channel<CharacterDetailsEffect>(Channel.UNLIMITED)

    val effects: Flow<CharacterDetailsEffect>
        get() = _viewEffects.receiveAsFlow()

    suspend fun runEffect(effect: CharacterDetailsEffect) = _viewEffects.send(effect)

    suspend fun start(characterId: CharacterId, locationUrl: String) {
        _viewState.value = Loading
        val state = try {
            val (character, locationDetails)= characterDetailsUseCase.execute(characterId, locationUrl)
            val characterVE = character.toViewEntity(locationDetails)
            Content(characterVE)
        } catch (e: CancellationException) {
            throw e
        } catch (t: Throwable) {
            mapError(t)
        }
        _viewState.value = state

    }

    suspend fun actions(actions: Flow<CharacterDetailsAction>) {
        actions.collect {
            when (it) {
                is Refresh -> start(it.characterId, it.locationUrl)
                Up -> runEffect(NavigateUp)
            }
        }
    }

    private fun mapError(t: Throwable) = when (t) {
        is ExceptionManager -> when (t.error) {
            is NetworkError -> Problem(ErrorTextRes(R.string.error_recoverable_network))
            is ServerError -> Problem(ErrorTextRes(R.string.error_recoverable_server))
            is Unrecoverable -> Problem(IdTextRes(R.string.error_unrecoverable))
        }
        else -> Problem(IdTextRes(R.string.error_unrecoverable))
    }

    private fun Character.toViewEntity(attribution: LocationDetails): CharacterDetailsViewEntity =
        CharacterDetailsViewEntity(
            name = PlaceholderString(R.string.character_details_name, name),
            status = PlaceholderString(R.string.character_details_status, status),
            species = PlaceholderString(R.string.character_details_species, species),
            gender = PlaceholderString(R.string.character_details_gender, gender),
            origin = PlaceholderString(R.string.character_details_origin, origin.name),
            thumbnail = image.toHttpUrl(),
            locationName = PlaceholderString(R.string.character_details_location_details_name, attribution.name),
            locationType = PlaceholderString(R.string.character_details_location_details_type, attribution.type),
            locationDimension = PlaceholderString(R.string.character_details_location_details_dimension, attribution.dimension),
        )
}
