package com.example.e_card_android.ui.main.homescreen

import com.example.e_card_android.data.repositories.GameRepository
import com.example.e_card_android.data.repositories.UserRepository
import com.example.e_card_android.ui.common.viewmodel.MVIBaseViewModel
import com.example.e_card_android.utils.ResourceProvider

class HomeViewModel(
    private val userRepository: UserRepository,
    private val gameRepository: GameRepository,
    private val resourceProvider: ResourceProvider
) : MVIBaseViewModel<HomeScreenEvent, HomeScreenState>(HomeScreenState.Loading) {

    override fun eventHandler(event: HomeScreenEvent) {
        when(event){
            HomeScreenEvent.LoadGamesScreen -> TODO()
            HomeScreenEvent.CreateNewGame -> TODO()
        }
    }

    private fun getPlayerData(){

    }

    private fun getActiveGames(){

    }
}