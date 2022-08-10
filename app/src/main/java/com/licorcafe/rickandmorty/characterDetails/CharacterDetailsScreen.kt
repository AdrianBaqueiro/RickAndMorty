package com.licorcafe.rickandmorty.characterDetails


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.licorcafe.rickandmorty.common.RickAndMortyLoading
import com.licorcafe.rickandmorty.common.RickAndMortyProblem
import com.licorcafe.rickandmorty.common.ExtendedTheme
import com.licorcafe.rickandmorty.domain.model.CharacterId
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

@Composable
fun CharactersDetailsScreen(
    stateFlow: Flow<CharactersDetailsViewState>,
    initialState: CharactersDetailsViewState,
    characterId: CharacterId,
    locationUrl: String,
    actions: Channel<CharacterDetailsAction>
) {
    val state by stateFlow.collectAsState(initialState)

    Column {
        CharacterAppBar(state, actions)

        when (val viewState = state) {
            is Content -> CharacterContent(content = viewState)
            Loading -> RickAndMortyLoading()
            is Problem -> RickAndMortyProblem(textRes = viewState.stringId) {
                actions.trySend(Refresh(characterId, locationUrl))
            }
        }
    }
}

@Composable
private fun CharacterAppBar(
    state: CharactersDetailsViewState,
    actions: Channel<CharacterDetailsAction>
) {
    TopAppBar(
        title = { Text(text = state.title) },
        backgroundColor = ExtendedTheme.colors.primary,
        navigationIcon = {
            IconButton(onClick = { actions.trySend(Up).getOrThrow() }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "")
            }
        }
    )
}

@Composable
fun CharacterContent(content: Content) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .testTag("CharacterDetailsContent")
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(content.character.thumbnail)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = content.character.name.replacement,
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
        )
        Text(
            text = stringResource(
                content.character.name.stringId,
                content.character.name.replacement
            )
        )
        Text(
            text = stringResource(
                content.character.status.stringId,
                content.character.status.replacement
            )
        )
        Text(
            text = stringResource(
                content.character.species.stringId,
                content.character.species.replacement
            )
        )
        Text(
            text = stringResource(
                content.character.gender.stringId,
                content.character.gender.replacement
            )
        )
        Text(
            text = stringResource(
                content.character.origin.stringId,
                content.character.origin.replacement
            )
        )
        Text(
            text = stringResource(
                content.character.locationName.stringId,
                content.character.locationName.replacement
            )
        )
        Text(
            text = stringResource(
                content.character.locationType.stringId,
                content.character.locationType.replacement
            )
        )
        Text(
            text = stringResource(
                content.character.locationDimension.stringId,
                content.character.locationDimension.replacement
            )
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
