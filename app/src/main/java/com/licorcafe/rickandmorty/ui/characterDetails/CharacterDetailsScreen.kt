package com.licorcafe.rickandmorty.ui.characterDetails


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.licorcafe.rickandmorty.R
import com.licorcafe.rickandmorty.common.ExtendedTheme
import com.licorcafe.rickandmorty.common.RickAndMortyLoading
import com.licorcafe.rickandmorty.common.RickAndMortyProblem
import com.licorcafe.rickandmorty.domain.model.CharacterId
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

var characterDetailsScreenView = mutableStateOf<CharacterDetailsViewEntity?>(null)

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
            is Content -> CharacterContent(content = viewState, actions)
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
        },
        actions = {
            if(state is Content) {
                IconButton(onClick = {
                    characterDetailsScreenView.value?.let {
                        SaveChanges(it)
                    }?.let {
                        actions.trySend(it).getOrThrow()
                    }
                    actions.trySend(Up).getOrThrow()
                },
                ) {
                    Icon(Icons.Filled.Done, contentDescription = "")
                }
            }
        }
    )
}

@Composable
fun CharacterContent(content: Content, actions: Channel<CharacterDetailsAction>) {
    characterDetailsScreenView = remember {
        mutableStateOf(content.character)
    }

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
            contentDescription = content.character.name,
            modifier = Modifier
                .aspectRatio(1.5f)
                .fillMaxWidth()
        )
        characterDetailsScreenView.value?.let { characterDetails ->
            TextFieldComponent(value = characterDetails.name,
                onValueChange = { newText ->
                    characterDetailsScreenView.value = characterDetails.copy(name = newText)
                },
                label = stringResource(R.string.character_details_name)
            )
            TextFieldComponent(value = characterDetails.status,
                onValueChange = { newText ->
                    content.character = characterDetails.copy(status = newText)
                    characterDetailsScreenView.value = characterDetails.copy(status = newText)
                },
                label = stringResource(R.string.character_details_status)
            )
            TextFieldComponent(value = characterDetails.species,
                onValueChange = { newText ->
                    characterDetailsScreenView.value = characterDetails.copy(species = newText)
                },
                label = stringResource(R.string.character_details_species)
            )
            TextFieldComponent(value = characterDetails.gender,
                onValueChange = { newText ->
                    characterDetailsScreenView.value = characterDetails.copy(gender = newText)
                },
                label = stringResource(R.string.character_details_gender)
            )
            TextFieldComponent(value = characterDetails.origin,
                onValueChange = { newText ->
                    characterDetailsScreenView.value = characterDetails.copy(origin = newText)
                },
                label = stringResource(R.string.character_details_origin),
                readOnly = true
            )
            TextFieldComponent(value = characterDetails.locationName,
                onValueChange = { newText ->
                    characterDetailsScreenView.value = characterDetails.copy(locationName = newText)
                },
                label = stringResource(R.string.character_details_location_details_name),
                readOnly = true
            )
            TextFieldComponent(value = characterDetails.locationType,
                onValueChange = { newText ->
                    characterDetailsScreenView.value = characterDetails.copy(locationType = newText)
                },
                label = stringResource(R.string.character_details_location_details_type),
                readOnly = true
            )
            TextFieldComponent(value = characterDetails.locationDimension,
                onValueChange = { newText ->
                    characterDetailsScreenView.value = characterDetails.copy(locationDimension = newText)
                },
                label = stringResource(R.string.character_details_location_details_dimension),
                readOnly = true
            )
        }
    }
}

@Composable
fun TextFieldComponent(value: String, onValueChange: (String) -> Unit, label: String, readOnly: Boolean = false) {
    TextField(
        value = value,
        onValueChange = { textFieldValue -> onValueChange(textFieldValue) },
        label = { Text(label, color = ExtendedTheme.colors.primary) },
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        readOnly = readOnly,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = ExtendedTheme.colors.primary,
            disabledLabelColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}
