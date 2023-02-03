package com.licorcafe.rickandmorty.repository

import com.licorcafe.rickandmorty.common.ExceptionManager
import com.licorcafe.rickandmorty.repository.api.model.CharacterDto
import com.licorcafe.rickandmorty.repository.api.model.LocationDto
import com.licorcafe.rickandmorty.repository.api.model.toDB
import com.licorcafe.rickandmorty.repository.db.CharacterDao
import com.licorcafe.rickandmorty.repository.db.LocationDetailsDao
import io.mockk.*
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class RickAndMortyRepositoryTest {

    private lateinit var rickAndMortyRepository: RickAndMortyRepository
    private lateinit var rickAndMortyDataSource: RickAndMortyDataSource
    private lateinit var characterDao: CharacterDao
    private lateinit var locationDetailsDao: LocationDetailsDao

    private val characterList = listOf(
        CharacterDto(
            1,"1","1","1","1","1",
            LocationDto("1","1"),
            LocationDto("1","1"),
            "1", listOf("1"),"1","1"
        )
    )

    @Before
    fun setUp() {
        rickAndMortyDataSource = mockk()
        characterDao = mockk()
        locationDetailsDao = mockk()

        rickAndMortyRepository = RickAndMortyRepositoryImpl(
            rickAndMortyDataSource,
            characterDao,
            locationDetailsDao
        )
    }

    @Test
    fun `When get all characters list return a list of characters`() {
        coEvery { rickAndMortyDataSource.getCharacterList() } returns characterList
        coEvery { characterDao.insertCharacters(characterList.map { it.toDB() }) } just Runs
        coEvery { characterDao.getAllCharactersEntity()} returns characterList.map { it.toDB() }

        runBlocking {
            rickAndMortyRepository.getCharacterList()
        }
        coVerify {
            rickAndMortyDataSource.getCharacterList()
        }

        verify {
            characterDao.insertCharacters(any())
            characterDao.getAllCharactersEntity()
        }
    }

    @Test(expected = ExceptionManager::class)
    fun `When get all characters list failed because httpException`() {
        coEvery { rickAndMortyDataSource.getCharacterList() }  throws HttpException(Response.error<ResponseBody>(500 ,
            "network error".toResponseBody("plain/text".toMediaTypeOrNull())
        ))

        runBlocking {
            rickAndMortyRepository.getCharacterList()
        }
    }

    @Test
    fun `When get character return a list of characters`() {
        coEvery { rickAndMortyDataSource.getCharacter(1) } returns characterList[0]
        coEvery { characterDao.insertCharacter(characterList[0].toDB() ) } just Runs
        coEvery { characterDao.getCharacterEntity(1)} returns characterList[0].toDB()

        runBlocking {
            rickAndMortyRepository.getCharacter(1)
        }
        coVerify {
            rickAndMortyDataSource.getCharacter(any())
        }

        verify {
            characterDao.insertCharacter(any())
            characterDao.getCharacterEntity(any())
        }
    }

    @Test(expected = ExceptionManager::class)
    fun `When get character failed because httpException`() {
        coEvery { rickAndMortyDataSource.getCharacter(1) }  throws HttpException(Response.error<ResponseBody>(500 ,
            "network error".toResponseBody("plain/text".toMediaTypeOrNull())
        ))

        runBlocking {
            rickAndMortyRepository.getCharacter(1)
        }
    }
}