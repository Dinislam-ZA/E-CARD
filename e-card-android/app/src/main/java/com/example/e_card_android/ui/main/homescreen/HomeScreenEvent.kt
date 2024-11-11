package com.example.e_card_android.ui.main.homescreen

sealed class HomeScreenEvent {
    data object LoadGamesScreen: HomeScreenEvent()
    data object CreateNewGame: HomeScreenEvent()
}