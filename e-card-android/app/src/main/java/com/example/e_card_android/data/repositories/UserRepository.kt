package com.example.e_card_android.data.repositories

import android.security.identity.UnknownAuthenticationKeyException
import com.example.e_card_android.data.model.User
import com.example.e_card_android.data.network.BackendRoutes
import com.example.e_card_android.data.network.KtorClient
import com.example.e_card_android.data.network.dto.GetPlayersResponse
import com.example.e_card_android.data.shared_preference.SecurePreferences
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpStatusCode

interface UserRepository {

    suspend fun getAllPlayers(): List<User>

    suspend fun getFriends(id: Int): List<User>

    suspend fun getThisPlayerFriends(): List<User>

    suspend fun sendFriendRequest(user: User): HttpStatusCode

    suspend fun acceptFriendRequest(): HttpStatusCode
}

class MockUserRepository() : UserRepository {

    private val allUsersList = listOf(
        User(id = 1, username = "Alice123", money = 5000),
        User(id = 2, username = "Bob456", money = 2300),
        User(id = 3, username = "Charlie789", money = 1500),
        User(id = 4, username = "Dave101", money = 7800),
        User(id = 5, username = "Eve202", money = 4200),
        User(id = 6, username = "Frank303", money = 3600),
        User(id = 7, username = "Grace404", money = 9100),
        User(id = 8, username = "Heidi505", money = 2750),
        User(id = 9, username = "Ivan606", money = 3300),
        User(id = 10, username = "Judy707", money = 6200)
    )

    private val friendsSet = allUsersList.subList(3, 8).toMutableSet()

    override suspend fun getAllPlayers(): List<User> =
        allUsersList.onEach { user -> user.isFriends = friendsSet.contains(user) }

    override suspend fun getFriends(id: Int): List<User> =
        friendsSet.toList().onEach { it.isFriends = true }

    override suspend fun getThisPlayerFriends(): List<User> = getFriends(1)


    override suspend fun sendFriendRequest(user: User): HttpStatusCode {
        user.isFriends = true
        friendsSet.add(user)
        return HttpStatusCode.OK
    }

    override suspend fun acceptFriendRequest(): HttpStatusCode {
        return HttpStatusCode.OK
    }
}

class UserRepositoryImpl(
    private val ktorClient: KtorClient,
    private val sharedPreferences: SecurePreferences
) : UserRepository {

    private val client = ktorClient.ktorHttpClient
    private val token = sharedPreferences.getToken()
    private val header = "Bearer $token"

    override suspend fun getAllPlayers(): List<User> {
        val response = client.get(ktorClient.host + BackendRoutes.Players) {
            headers {
                append("Authorization", header)
            }
        }
        val body = response.body<GetPlayersResponse>()
        return body.players
    }

    override suspend fun getFriends(id: Int): List<User> {
        val response = client.get(ktorClient.host + BackendRoutes.GetFriends(id)) {
            headers {
                append("Authorization", header)
            }
        }
        val body = response.body<GetPlayersResponse>()
        return body.players
    }

    override suspend fun getThisPlayerFriends(): List<User> {
        val id = sharedPreferences.getId()
        if (id != null)
            return getFriends(id)
        else
            throw Exception()
    }

    override suspend fun sendFriendRequest(user: User): HttpStatusCode {
        TODO("Not yet implemented")
    }

    override suspend fun acceptFriendRequest(): HttpStatusCode {
        TODO("Not yet implemented")
    }
}