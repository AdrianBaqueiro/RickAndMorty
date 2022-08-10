package com.licorcafe.rickandmorty

import com.licorcafe.rickandmorty.characterDetails.CharacterDetailsViewModel
import com.licorcafe.rickandmorty.characterList.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CharactersViewModel(get()) }
    viewModel { CharacterDetailsViewModel(get()) }
}
