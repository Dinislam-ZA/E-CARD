package com.example.e_card_android.ui.main.gamescreen

// TODO: Скорее всего проще заменить на data class
sealed class GameScreenState {
    data object WaitingForPlayers : GameScreenState()
    data object WaitingForStart: GameScreenState()
    data object InProgress : GameScreenState()
    data object Error: GameScreenState()
}