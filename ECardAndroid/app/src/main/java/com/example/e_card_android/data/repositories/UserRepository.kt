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

    override suspend fun getFriends(): List<User> = listOf(
        User(id = 1, username = "Alice123", money = 5000),
        User(id = 3, username = "Charlie789", money = 1500),
        User(id = 6, username = "Frank303", money = 3600),
        User(id = 8, username = "Heidi505", money = 2750),
        User(id = 9, username = "Ivan606", money = 3300),
        User(id = 10, username = "Judy707", money = 6200)
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