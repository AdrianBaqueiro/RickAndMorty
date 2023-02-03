package com.licorcafe.rickandmorty.ui.characterList

import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.licorcafe.rickandmorty.R
import com.licorcafe.rickandmorty.ui.characterDetails.CharacterDetailsFragment
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersFragment : Fragment(R.layout.fragment_compose) {

    private val viewModel: CharactersViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val composeView = view as ComposeView
        composeView.setViewCompositionStrategy(DisposeOnViewTreeLifecycleDestroyed)

        val actions = Channel<CharacterAction>(Channel.UNLIMITED)

        composeView.setContent {
            CharactersScreen(
                stateFlow = viewModel.viewState,
                initialValue = Loading,
                actions = actions
            )
        }

        lifecycleScope.launch {
            viewModel.start()
        }

        lifecycleScope.launch {
            viewModel.actions(actions.receiveAsFlow())
            actions.receiveAsFlow().collect()
        }

        handleEffects()
    }

    private fun handleEffects() {
        lifecycleScope.launch {
            viewModel.effects.map { effect ->
                when (effect) {
                    is NavigateToDetails -> findNavController().navigate(
                        R.id.action_details,
                        CharacterDetailsFragment.newBundle(effect.characterId, effect.locationUrl)
                    )
                }
            }.collect()
        }
    }
}
