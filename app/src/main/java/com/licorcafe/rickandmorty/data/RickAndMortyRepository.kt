package com.licorcafe.rickandmorty.data

import com.licorcafe.rickandmorty.common.ExceptionManager
import com.licorcafe.rickandmorty.common.NetworkError
import com.licorcafe.rickandmorty.common.ServerError
import com.licorcafe.rickandmorty.common.Unrecoverable
import com.licorcafe.rickandmorty.data.model.toDomain
import com.licorcafe.rickandmorty.domain.model.Character
import com.licorcafe.rickandmorty.domain.model.LocationDetails
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


interface RickAndMortyRepository {
    suspend fun getCharacterList(): List<Character>
    suspend fun getCharacterListPage(pageNumber: Int): List<Character>
    suspend fun getCharacter(characterId: Long): Character
    suspend fun getLocationDetails(locationUrl: String): LocationDetails
}

class RickAndMortyRepositoryImpl(private val rickAndMortyDataSource: RickAndMortyDataSource): RickAndMortyRepository {
    override suspend fun getCharacterList() = withContext(Dispatchers.IO) {
        val characterDtoList = runRefineError { rickAndMortyDataSource.getCharacterList() }
        return@withContext characterDtoList.map { it.toDomain() }
    }

    override suspend fun getCharacterListPage(pageNumber: Int) = withContext(Dispatchers.IO) {
        val characterDtoList = runRefineError { rickAndMortyDataSource.getCharacterListPage(pageNumber) }
        return@withContext characterDtoList.map { it.toDomain() }
    }

    override suspend fun getCharacter(characterId: Long) = withContext(Dispatchers.IO) {
        val characterDto = runRefineError { rickAndMortyDataSource.getCharacter(characterId) }
        return@withContext characterDto.toDomain()
    }

    override suspend fun getLocationDetails(locationUrl: String) = withContext(Dispatchers.IO) {
        val locationDetailsDtoList = runRefineError {  rickAndMortyDataSource.getLocationDetails(locationUrl) }
        return@withContext locationDetailsDtoList.toDomain()
    }

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



