package com.licorcafe.rickandmorty.repository.module

import com.licorcafe.rickandmorty.repository.RickAndMortyDataSource
import com.licorcafe.rickandmorty.repository.RickAndMortyDataSourceImpl
import com.licorcafe.rickandmorty.repository.RickAndMortyRepository
import com.licorcafe.rickandmorty.repository.RickAndMortyRepositoryImpl
import com.licorcafe.rickandmorty.repository.api.RickAndMortyApi
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val dataSourcesModule = module {
    single { get<Retrofit>(named("defaultRetrofit")).create(RickAndMortyApi::class.java) }
    single<RickAndMortyDataSource> { RickAndMortyDataSourceImpl(get()) }
}


val repositoriesModule = module {
    single { RickAndMortyRepositoryImpl(get(), get(), get()) } bind RickAndMortyRepository::class
}
