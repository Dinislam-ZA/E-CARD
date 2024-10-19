package example.com.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AddFriendRequest(
    val user1: Int,
    val user2: Int
)

@Serializable
data class AcceptFriendRequest(
    val user1: Int,
    val user2: Int
)

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class RegistrationRequest(
    val username: String,
    val password: String
)