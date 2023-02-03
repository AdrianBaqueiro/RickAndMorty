package com.licorcafe.rickandmorty.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.licorcafe.rickandmorty.repository.db.CharacterDao
import com.licorcafe.rickandmorty.repository.db.database.AppDataBase
import com.licorcafe.rickandmorty.repository.db.model.CharacterEntity
import com.licorcafe.rickandmorty.repository.db.model.LocationEntity
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test


class CharacterDaoTest {
    private lateinit var database: AppDataBase
    private lateinit var characterDao: CharacterDao

    @Before
    fun setupDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries().build()

        characterDao = database.characterDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    private val characterEntity = CharacterEntity(
        1,"1","1","1","1","1",
        LocationEntity("1","1"),
        LocationEntity("1","1"),
        "1", listOf("1"),"1","1"
    )

    @Test
    fun insert_character_into_db_check_if_exist() {
        characterDao.insertCharacter(characterEntity)
        assertThat(characterDao.getCharacterEntity(1), equalTo(characterEntity))
    }

    @Test
    fun insert_character_list_into_db_check_if_exist() {
        characterDao.insertCharacters(listOf(characterEntity))
        assertThat(characterDao.getAllCharactersEntity(), equalTo(listOf(characterEntity)))
    }

    @Test
    fun update_character_list_into_db_check_if_change() {
        characterDao.insertCharacter(characterEntity)
        characterDao.updateCharacter(characterEntity.copy(name="test"))
        assertThat(characterDao.getCharacterEntity(1), equalTo(characterEntity.copy(name="test")))
    }

    @Test
    fun delete_character_list_into_db_check_if_remove() {
        characterDao.insertCharacter(characterEntity)
        characterDao.deleteCharacterEntity(characterEntity)
        assertThat(characterDao.getAllCharactersEntity().count(), equalTo(0))
    }
}