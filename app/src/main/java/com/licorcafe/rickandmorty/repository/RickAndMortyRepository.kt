package com.licorcafe.rickandmorty.repository

import com.licorcafe.rickandmorty.common.ExceptionManager
import com.licorcafe.rickandmorty.common.NetworkError
import com.licorcafe.rickandmorty.common.ServerError
import com.licorcafe.rickandmorty.common.Unrecoverable
import com.licorcafe.rickandmorty.repository.api.model.toDB
import com.licorcafe.rickandmorty.repository.api.model.toDomain
import com.licorcafe.rickandmorty.repository.db.CharacterDao
import com.licorcafe.rickandmorty.repository.db.LocationDetailsDao
import com.licorcafe.rickandmorty.repository.db.model.CharacterEntity
import com.licorcafe.rickandmorty.repository.db.model.LocationDetailsEntity
import com.licorcafe.rickandmorty.repository.db.model.toDomain
import com.licorcafe.rickandmorty.domain.model.Character
import com.licorcafe.rickandmorty.domain.model.LocationDetails
import com.licorcafe.rickandmorty.domain.model.toDB
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


interface RickAndMortyRepository {
    suspend fun getCharacterList(): List<Character>
    suspend fun getCharacterListPage(pageNumber: Int): List<Character>
    suspend fun getCharacter(characterId: Long): Character
    suspend fun updateCharacter(character: Character)
    suspend fun getLocationDetails(locationUrl: String): LocationDetails
}

class RickAndMortyRepositoryImpl(
    private val rickAndMortyDataSource: RickAndMortyDataSource,
    private val characterDao: CharacterDao,
    private val LocationDetailsDao: LocationDetailsDao
): RickAndMortyRepository {
    override suspend fun getCharacterList() = withContext(Dispatchers.IO) {
        runRefineError { saveCharacterList(rickAndMortyDataSource.getCharacterList().map { it.toDB() }) }
        val characterDBList = getStoredCharacters()
        return@withContext characterDBList.map { it.toDomain() }
    }

    override suspend fun getCharacterListPage(pageNumber: Int) = withContext(Dispatchers.IO) {
        val characterDtoList = runRefineError { rickAndMortyDataSource.getCharacterListPage(pageNumber) }
        return@withContext characterDtoList.map { it.toDomain() }
    }

    override suspend fun getCharacter(characterId: Long) = withContext(Dispatchers.IO) {
        runRefineError { insertCharacter(rickAndMortyDataSource.getCharacter(characterId).toDB()) }
        val characterDto = getStoredCharacter(characterId)
        return@withContext characterDto.toDomain()
    }

    override suspend fun getLocationDetails(locationUrl: String) = withContext(Dispatchers.IO) {
        runRefineError {  saveLocationDetail(rickAndMortyDataSource.getLocationDetails(locationUrl).toDB()) }
        val locationDetailsDtoList = getStoreLocationDetails(locationUrl)
        return@withContext locationDetailsDtoList.toDomain()
    }

    override suspend fun updateCharacter(character: Character) {
        saveCharacter(character.toDB())
    }

    private fun saveCharacterList(characterList: List<CharacterEntity>) {
        characterDao.insertCharacters(characterList)
    }
    private fun insertCharacter(character: CharacterEntity)  = characterDao.insertCharacter(character)
    private fun saveCharacter(character: CharacterEntity)  = characterDao.updateCharacter(character)

    private fun getStoredCharacters() = characterDao.getAllCharactersEntity()
    private fun getStoredCharacter(id: Long) = characterDao.getCharacterEntity(id)

    private fun saveLocationDetail(locationDetails: LocationDetailsEntity) {
        LocationDetailsDao.insertLocationDetailsEntity(locationDetails)
    }
    private fun getStoreLocationDetails(locationUrl: String) = LocationDetailsDao.getLocationDetailsEntity(locationUrl)

    private suspend fun <A> runRefineError(f: suspend () -> A): A =
        try {
            f()
        } catch (e: CancellationException) {
            throw e
        } catch (e: HttpException) {
            throw when (e.code()) {
                in 500..599 -> ExceptionManager(
                    ServerError(e.code(), e.message())
                )
                else -> ExceptionManager(Unrecoverable(e))
            }
        } catch (e: IOException) {
            throw ExceptionManager(NetworkError(e))
        } catch (e: Throwable) {
            throw ExceptionManager(Unrecoverable(e))
        }
}



