package com.example.e_card_android.data.model

data class Game(
    val id: Int,
    val bet: Long = 100,
    val rounds: Int = 4
)