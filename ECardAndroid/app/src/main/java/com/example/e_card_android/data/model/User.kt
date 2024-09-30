package com.example.e_card_android.data.model

data class User(
    val id: Int,
    val username: String,
    val money: Long = 0,
    val profilePictureUrl: String = "https://randomuser.me/api/portraits/men/1.jpg"
)
