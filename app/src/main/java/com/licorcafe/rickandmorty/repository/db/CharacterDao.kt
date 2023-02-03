package com.licorcafe.rickandmorty.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.licorcafe.rickandmorty.repository.db.model.CharacterEntity

@Dao
interface CharacterDao {
    @Query("SELECT * FROM character WHERE id = :id LIMIT 1")
    fun getCharacterEntity(id: Long): CharacterEntity

    @Query("SELECT * FROM character WHERE id = :id LIMIT 1")
    fun getLiveCharacterEntity(id: Long): LiveData<CharacterEntity>

    @Query("SELECT * FROM character")
    fun getAllCharactersEntity(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCharacter(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCharacters(characters: List<CharacterEntity>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCharacter(character: CharacterEntity)

    @Delete
    fun deleteCharacterEntity(character: CharacterEntity)
}
