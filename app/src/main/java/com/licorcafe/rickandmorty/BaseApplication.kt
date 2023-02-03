package com.licorcafe.rickandmorty

import android.app.Application
import com.licorcafe.rickandmorty.repository.api.network.networkModule
import com.licorcafe.rickandmorty.repository.db.database.rickAndMortyDB
import com.licorcafe.rickandmorty.repository.module.dataSourcesModule
import com.licorcafe.rickandmorty.repository.module.repositoriesModule
import com.licorcafe.rickandmorty.domain.interactor.useCaseModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class BaseApplication : Application() {

    @KoinExperimentalAPI
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(
                listOf(
                    rickAndMortyDB,
                    networkModule("https://rickandmortyapi.com/api/"),
                    repositoriesModule,
                    dataSourcesModule,
                    useCaseModule,
                    viewModelModule,
                    module {
                        single(named("applicationScope")) { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
                    }
                )
            )
        }
    }
}
