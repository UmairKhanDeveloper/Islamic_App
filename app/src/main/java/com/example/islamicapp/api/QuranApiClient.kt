package com.example.islamicapp.api

import com.example.islamicapp.apiclient.Hadiths
import com.example.islamicapp.apiclient.Quran
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json


object QuranApiClient {

    @OptIn(ExperimentalSerializationApi::class)
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                    prettyPrint = true
                }
            )
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println("QuranApi Log: $message")
                }
            }
        }

        install(HttpTimeout) {
            socketTimeoutMillis = Constant.TIMEOUT
            connectTimeoutMillis = Constant.TIMEOUT
            requestTimeoutMillis = Constant.TIMEOUT
        }

        defaultRequest {
            url(Constant.QURAN_BASE_URL)
            headers {
                append("x-rapidapi-host", "al-quran1.p.rapidapi.com")
                append("x-rapidapi-key", Constant.QURAN_API_KEY)
            }
        }
    }

    suspend fun fetchSurah(id: Int): Quran {
        return client.get("$id").body()
    }

    suspend fun fetchAllSurahs(): List<Quran> = coroutineScope {
        val deferredList = (1..114).map { id ->
            async {
                try {
                    fetchSurah(id)
                } catch (e: Exception) {
                    println("Error fetching surah $id: ${e.message}")
                    null
                }
            }
        }
        deferredList.mapNotNull { it.await() }
    }
}


object HadithApiClient {
    @OptIn(ExperimentalSerializationApi::class)
    val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
                prettyPrint = true
            })
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println("HadithApi Log: $message")
                }
            }
        }
        install(HttpTimeout) {
            socketTimeoutMillis = Constant.TIMEOUT
            connectTimeoutMillis = Constant.TIMEOUT
            requestTimeoutMillis = Constant.TIMEOUT
        }

        defaultRequest {
            url(Constant.HADITH_BASE_URL)
        }
    }

    suspend fun fetchHadiths(): Hadiths {
        return client.get("hadiths/?apiKey=${Constant.HADITH_API_KEY}").body()
    }
}



