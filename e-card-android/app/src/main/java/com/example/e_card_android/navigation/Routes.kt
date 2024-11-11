package com.example.e_card_android.navigation

enum class Routes(val value: String) {
    START("Start"),
    HOME("Game Lobby"),
    SETTINGS("Settings"),
    GAME("Game"),
    LOGIN("Login"),
    REGISTER("Register"),
    PLAYERS("Players statistic"),
    FRIENDS("Friends"),
    NOTIFICATIONS("Notifications")
}

data class GameState(
    var currentRound: Int,
    var firstPlayerScore: Int,
    var secondPlayerScore: Int,
    var firstPlayerReady: Boolean,
    var secondPlayerReady: Boolean
)