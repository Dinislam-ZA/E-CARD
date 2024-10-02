package com.example.e_card_android.data.interactors

import com.example.e_card_android.data.shared_preference.SecurePreferences
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.TimeoutCancellationException


interface AuthInteractor {
    suspend fun login(username: String, password: String): HttpStatusCode
    suspend fun register(username: String, password: String): HttpStatusCode
    suspend fun logout(): HttpStatusCode
}

class MockAuthInteractor(val preferences: SecurePreferences): AuthInteractor {
    override suspend fun login(username: String, password: String): HttpStatusCode {
        preferences.clearCredentials()
        val token = "Opa"
        preferences.saveCredentials(username, password, token)
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

//    class AuthInteractorImpl(val preferences: SecurePreferences): AuthInteractor{
//
//        private val client = KtorClient.ktorHttpClient
//
//        override suspend fun login() {
//            try {
//                val response = client.post {
//                    url {
//                        host = KtorClient.host
//                        path(BackendRoutes.Login.route)
//                        parameters.append("username", "")
//                        parameters.append("password", "")
//                    }
//                }
//                return response
//            }
//            catch (_:Exception){
//
//            }
//        }
//
//        override suspend fun register() {
//            TODO("Not yet implemented")
//        }
//
//        override suspend fun logout() {
//            TODO("Not yet implemented")
//        }
//
//    }
//}