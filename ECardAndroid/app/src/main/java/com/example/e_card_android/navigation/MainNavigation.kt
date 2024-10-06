package com.example.e_card_android.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.e_card_android.ui.main.friendsscreen.FriendsScreen
import com.example.e_card_android.ui.main.mainscreen.MainScreen
import com.example.e_card_android.ui.main.usersscreen.UserScreen

@Composable
fun MainNavigation(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(navController = navController, startDestination = Routes.HOME.value) {
        composable(Routes.HOME.value) {
            MainScreen(paddingValues = innerPadding)
        }
        composable(Routes.SETTINGS.value) {
            UserScreen(paddingValues = innerPadding)
        }
        composable(Routes.PLAYERS.value) {
            UserScreen(paddingValues = innerPadding)
        }
        composable(Routes.FRIENDS.value) {
            FriendsScreen(paddingValues = innerPadding)
        }
    }
}