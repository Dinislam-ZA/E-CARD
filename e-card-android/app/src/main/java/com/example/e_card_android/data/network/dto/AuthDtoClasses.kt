package com.example.e_card_android.data.network.dto

data class AuthRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val id: Int,
    val token: String
)