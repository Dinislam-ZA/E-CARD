package com.example.e_card_android.ui.main.homescreen

import com.example.e_card_android.data.model.Game
import com.example.e_card_android.data.model.User

sealed class HomeScreenState {
    data object Loading : HomeScreenState()
    data class Idle(val player: User, val games: List<Game>) : HomeScreenState()
    data class Error(val message: String) : HomeScreenState()
}