package com.licorcafe.rickandmorty.repository.api.network

import com.licorcafe.rickandmorty.base.provideOkHttpClient
import com.licorcafe.rickandmorty.base.provideRetrofit
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun networkModule(baseUrl: String) = module {
    single(named("baseUrl")) { baseUrl }
    single(named("defaultRetrofit")) {
        provideRetrofit(
            get(named("defaultOkHttpClient")),
            get(named("baseUrl"))
        )
    }
    single(named("defaultOkHttpClient")) {
        provideOkHttpClient()
    }
}