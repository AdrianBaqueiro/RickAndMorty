package com.licorcafe.rickandmorty.data.module

import com.licorcafe.rickandmorty.data.RickAndMortyApi
import com.licorcafe.rickandmorty.data.RickAndMortyDataSource
import com.licorcafe.rickandmorty.data.RickAndMortyDataSourceImpl
import com.licorcafe.rickandmorty.data.RickAndMortyRepository
import com.licorcafe.rickandmorty.data.RickAndMortyRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val dataSourcesModule = module {
    single { get<Retrofit>(named("defaultRetrofit")).create(RickAndMortyApi::class.java) }
    single<RickAndMortyDataSource> { RickAndMortyDataSourceImpl(get()) }
}


val repositoriesModule = module {
    single { RickAndMortyRepositoryImpl(get()) } bind RickAndMortyRepository::class
}
