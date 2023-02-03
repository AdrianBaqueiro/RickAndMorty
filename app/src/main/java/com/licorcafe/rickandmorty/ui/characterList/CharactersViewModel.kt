package com.licorcafe.rickandmorty.ui.characterList

import androidx.lifecycle.ViewModel
import com.licorcafe.rickandmorty.R
import com.licorcafe.rickandmorty.common.*
import com.licorcafe.rickandmorty.domain.interactor.GetCharacterListUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.HttpUrl.Companion.toHttpUrl


class CharactersViewModel(
    private val characterListUseCase: GetCharacterListUseCase
): ViewModel() {

    private val _viewState: MutableStateFlow<CharactersViewState> = MutableStateFlow(Loading)
    val viewState: Flow<CharactersViewState>
        get() = _viewState.filterNotNull()

    private val _viewEffects = Channel<CharactersEffect>(Channel.UNLIMITED)

    val effects: Flow<CharactersEffect>
        get() = _viewEffects.receiveAsFlow()

    suspend fun runEffect(effect: CharactersEffect) = _viewEffects.send(effect)

    suspend fun start() {
        _viewState.value = Loading
        val state = try {
            val (characters) = characterListUseCase.execute()
            val charactersVE = characters.map { CharactersViewEntity(it.id, it.name, it.image.toHttpUrl(), it.location.url) }
            Content(charactersVE)
        } catch (e: CancellationException) {
            throw e
        } catch (t: Throwable) {
            mapError(t)
        }
        _viewState.value = state

    }

    suspend fun actions(actions: Flow<CharacterAction>) {
        actions.collect {
            when (it) {
                is LoadDetails -> runEffect(NavigateToDetails(it.id, it.url))
                Refresh -> start()
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
}
