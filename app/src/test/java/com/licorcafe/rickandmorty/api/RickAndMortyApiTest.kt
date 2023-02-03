package com.licorcafe.rickandmorty.api

import com.google.gson.Gson
import com.licorcafe.rickandmorty.base.provideRetrofit
import com.licorcafe.rickandmorty.repository.api.RickAndMortyApi
import com.licorcafe.rickandmorty.repository.api.model.CharacterDto
import com.licorcafe.rickandmorty.repository.api.model.LocationDto
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.net.HttpURLConnection
import kotlin.test.assertEquals


class RickAndMortyApiTest {

    private lateinit var rickAndMortyApi: RickAndMortyApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val okHttp = OkHttpClient.Builder().build()
        rickAndMortyApi = provideRetrofit(okHttp, mockWebServer.url("/").toString()).create(RickAndMortyApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    private val character = CharacterDto(
            1,"1","1","1","1","1",
            LocationDto("1","1"),
            LocationDto("1","1"),
            "1", listOf("1"),"1","1"
        )

    @Test
    fun `test get character success, api must return response with http code 200`()  {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(character))
        mockWebServer.enqueue(expectedResponse)

        val result = runBlocking {
            rickAndMortyApi.getCharacter(1)
        }
        assertEquals(result, character)
    }

    @Test(expected = HttpException::class)
    fun `test get character , api must return response with http code 400`()  {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody(Gson().toJson(character))
        mockWebServer.enqueue(expectedResponse)

        runBlocking {
            rickAndMortyApi.getCharacter(1)
        }

    }
}