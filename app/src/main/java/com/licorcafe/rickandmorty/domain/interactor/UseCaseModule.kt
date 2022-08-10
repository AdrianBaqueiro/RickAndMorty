package com.licorcafe.rickandmorty.domain.interactor

import org.koin.dsl.bind
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetCharacterDetailsUseCaseImpl(get()) } bind GetCharacterDetailsUseCase::class
    factory { GetCharacterListUseCaseImpl(get()) } bind GetCharacterListUseCase::class
}
