package com.example.e_card_android.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

class KtorClient {
    val host = "http://10.0.2.2"
    val ktorHttpClient by lazy {
        HttpClient(Android){
            install(HttpTimeout) {
                requestTimeoutMillis = TimeUnit.SECONDS.toMillis(30) // Таймаут для каждого запроса
                connectTimeoutMillis = TimeUnit.SECONDS.toMillis(10) // Таймаут подключения
                socketTimeoutMillis = TimeUnit.SECONDS.toMillis(30)  // Таймаут ожидания ответа
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = true
                })
            }

            HttpResponseValidator {
                validateResponse { response ->
                    val statusCode = response.status.value
                    if (statusCode >= 300) {
                        throw ResponseException(response, "HTTP error with status code $statusCode")
                    }
                }
            }
        }
    }
}