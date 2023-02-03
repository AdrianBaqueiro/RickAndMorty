package com.licorcafe.rickandmorty.repository.db.database

import android.app.Application
import androidx.room.Room
import com.licorcafe.rickandmorty.repository.db.CharacterDao
import com.licorcafe.rickandmorty.repository.db.LocationDetailsDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val rickAndMortyDB = module {
    fun provideDataBase(application: Application): AppDataBase {
        return Room.databaseBuilder(application, AppDataBase::class.java, "RickAndMortyDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCharacterDao(dataBase: AppDataBase): CharacterDao {
        return dataBase.characterDao()
    }

    fun provideLocationDetailsDao(dataBase: AppDataBase): LocationDetailsDao {
        return dataBase.locationDetailsDao()
    }

    single { provideDataBase(androidApplication()) }
    single { provideCharacterDao(get()) }
    single { provideLocationDetailsDao(get()) }
}