package com.licorcafe.rickandmorty.base


import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.conscrypt.BuildConfig
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalSerializationApi::class)
fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
    val contentType = "application/json".toMediaType()
    val converter = Json {
        ignoreUnknownKeys = true
    }
    return Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(converter.asConverterFactory(contentType))
        .client(okHttpClient)
        .build()
}

fun provideOkHttpClient(): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()

    okHttpClient.connectTimeout(30, TimeUnit.SECONDS)
    okHttpClient.readTimeout(40, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        val logInterceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
        okHttpClient.addInterceptor(logInterceptor)
    }
    return okHttpClient.build()
}




