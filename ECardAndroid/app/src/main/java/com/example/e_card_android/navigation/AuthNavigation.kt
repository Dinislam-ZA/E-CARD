package com.example.e_card_android.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.e_card_android.ui.auth.login.LoginScreen
import com.example.e_card_android.ui.auth.registration.RegistrationScreen

@Composable
fun AuthNavigation(onSucceedLogin: () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.LOGIN.value) {
        composable(Routes.LOGIN.value) { LoginScreen(
            onRegisterButtonClick = {
                navController.navigate(Routes.REGISTER.value)
            },
            onSucceedLogin = onSucceedLogin
        )}
        composable(Routes.REGISTER.value) { RegistrationScreen(
            onSucceedRegistration = {
                navController.navigateUp()
            }
        ) }
    }
}