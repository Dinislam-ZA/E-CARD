package com.example.e_card_android.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

object KtorClient {
    val host = "http://10.0.2.2"
    val ktorHttpClient by lazy {
        HttpClient(Android)
    }
}