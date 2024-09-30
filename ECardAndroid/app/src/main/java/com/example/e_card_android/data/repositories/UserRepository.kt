package com.example.e_card_android.data.repositories

import com.example.e_card_android.data.model.User
import io.ktor.http.HttpStatusCode

interface UserRepository {

    suspend fun getAllUsers(): List<User>

    suspend fun getFriends(): List<User>

    suspend fun sendFriendRequest(): HttpStatusCode

    suspend fun acceptFriendRequest(): HttpStatusCode
}

class MockUserRepository(): UserRepository {
    override suspend fun getAllUsers(): List<User> = listOf(
        User(id = 1, username = "Alice123"),
        User(id = 2, username = "Bob456"),
        User(id = 3, username = "Charlie789"),
        User(id = 4, username = "Dave101"),
        User(id = 5, username = "Eve202"),
        User(id = 6, username = "Frank303"),
        User(id = 7, username = "Grace404"),
        User(id = 8, username = "Heidi505"),
        User(id = 9, username = "Ivan606"),
        User(id = 10, username = "Judy707")
    )

    override suspend fun getFriends(): List<User> = listOf(
        User(id = 1, username = "Alice123"),
        User(id = 3, username = "Charlie789"),
        User(id = 6, username = "Frank303"),
        User(id = 8, username = "Heidi505"),
        User(id = 9, username = "Ivan606"),
        User(id = 10, username = "Judy707")
    )

    override suspend fun sendFriendRequest(): HttpStatusCode {
        return HttpStatusCode.OK
    }

    override suspend fun acceptFriendRequest(): HttpStatusCode {
        return HttpStatusCode.OK
    }
}

class UserRepositoryImpl {
}