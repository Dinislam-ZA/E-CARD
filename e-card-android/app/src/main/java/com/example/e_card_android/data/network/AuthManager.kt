package com.example.e_card_android.data.network

import com.example.e_card_android.data.network.dto.AuthRequest
import com.example.e_card_android.data.network.dto.LoginResponse
import com.example.e_card_android.data.shared_preference.SecurePreferences
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.util.InternalAPI


interface AuthManager {
    suspend fun login(username: String, password: String): HttpStatusCode
    suspend fun register(username: String, password: String): HttpStatusCode
    suspend fun logout(): HttpStatusCode
}

class MockAuthManager(private val preferences: SecurePreferences) : AuthManager {
    override suspend fun login(username: String, password: String): HttpStatusCode {
        preferences.clearCredentials()
        val token = "Opa"
        preferences.saveCredentials(username, password, token, id = 1)
        return HttpStatusCode.OK
    }

    override suspend fun register(username: String, password: String): HttpStatusCode {
        return HttpStatusCode.OK
    }

    override suspend fun logout(): HttpStatusCode {
        preferences.clearCredentials()
        return HttpStatusCode.OK
    }
}

class AuthManagerImpl(
    private val ktorClient: KtorClient,
    private val preferences: SecurePreferences
) : AuthManager {

    private val client = ktorClient.ktorHttpClient

    @OptIn(InternalAPI::class)
    override suspend fun login(username: String, password: String): HttpStatusCode {
        val response = client.post(ktorClient.host + BackendRoutes.Login.route) {
            contentType(ContentType.Application.Json)
            body = AuthRequest(username = username, password = password)
        }
        val body = response.body<LoginResponse>()
        preferences.saveCredentials(username, password, body.token, body.id)
        return response.status
    }

    @OptIn(InternalAPI::class)
    override suspend fun register(username: String, password: String): HttpStatusCode {
        val response = client.post(ktorClient.host + BackendRoutes.Register.route) {
            contentType(ContentType.Application.Json)
            body = AuthRequest(username = username, password = password)
        }
        return response.status
    }

    override suspend fun logout(): HttpStatusCode {
        preferences.clearCredentials()
        return HttpStatusCode.OK
    }

}