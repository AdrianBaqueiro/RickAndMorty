package com.licorcafe.rickandmorty.repository.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.licorcafe.rickandmorty.repository.db.CharacterDao
import com.licorcafe.rickandmorty.repository.db.converter.Converter
import com.licorcafe.rickandmorty.repository.db.LocationDetailsDao
import com.licorcafe.rickandmorty.repository.db.model.CharacterEntity
import com.licorcafe.rickandmorty.repository.db.model.LocationDetailsEntity

@Database(
    entities = [
        CharacterEntity::class,
        LocationDetailsEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converter::class)

abstract class AppDataBase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun locationDetailsDao(): LocationDetailsDao
}