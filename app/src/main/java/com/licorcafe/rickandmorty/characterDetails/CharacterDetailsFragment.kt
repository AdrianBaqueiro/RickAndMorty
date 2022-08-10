package com.licorcafe.rickandmorty.characterDetails

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.licorcafe.rickandmorty.R
import com.licorcafe.rickandmorty.domain.model.CharacterId
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharacterDetailsFragment : Fragment(R.layout.fragment_compose) {

    private val characterId: Long by lazy(LazyThreadSafetyMode.NONE) {
        val id = requireArguments().getLong(EXTRA_CHARACTER_ID, -1)
        check(id != -1L) { "Please use newBundle() for creating the arguments" }
        id
    }

    private val locationUrl: String by lazy(LazyThreadSafetyMode.NONE) {
        val id = requireArguments().getString(EXTRA_LOCATION_URL, "")
        check(id != "") { "Please use newBundle() for creating the arguments" }
        id
    }

    private val viewModel: CharacterDetailsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val composeView = view as ComposeView
        composeView.setViewCompositionStrategy(DisposeOnViewTreeLifecycleDestroyed)

        val actions = Channel<CharacterDetailsAction>(Channel.UNLIMITED)

        composeView.setContent {
            CharactersDetailsScreen(
                stateFlow = viewModel.viewState,
                initialState = Loading,
                characterId = characterId,
                locationUrl = locationUrl,
                actions = actions
            )
        }

        lifecycleScope.launchWhenStarted {
            viewModel.start(characterId, locationUrl)
        }
        lifecycleScope.launchWhenStarted {
            viewModel.actions(actions.receiveAsFlow())
        }

        handleEffects()
    }

    private fun handleEffects() {
        lifecycleScope.launchWhenStarted {
            viewModel.effects.map { effect ->
                when (effect) {
                    NavigateUp -> findNavController().navigateUp()
                }
            }.collect()
        }
    }

    companion object {
        private const val EXTRA_CHARACTER_ID = "EXTRA_CHARACTER_ID"
        private const val EXTRA_LOCATION_URL = "EXTRA_LOCATION_URL"


    fun newBundle(characterId: CharacterId, locationUrl: String): Bundle =
        Bundle().apply {
            putLong(EXTRA_CHARACTER_ID, characterId)
            putString(EXTRA_LOCATION_URL, locationUrl)
        }
    }
}

