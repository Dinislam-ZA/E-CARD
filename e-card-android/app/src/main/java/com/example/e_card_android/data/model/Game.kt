package com.example.e_card_android.data.model

data class Game(
    val id: Int,
    val bet: Long = 100,
    val rounds: Int = 4,
    val description: String = "Tap to join the game",
    val owner: User
)

enum class Card {
    EMPEROR,
    CITIZEN,
    SLAVE,
    BACK,
}