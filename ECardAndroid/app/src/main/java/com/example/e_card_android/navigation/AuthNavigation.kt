package com.example.e_card_android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.e_card_android.data.shared_preference.SecurePreferences
import com.example.e_card_android.ui.auth.login.LoginScreen
import com.example.e_card_android.ui.auth.registration.RegistrationScreen
import com.example.e_card_android.ui.common.composables.LoadingState
import org.koin.androidx.compose.get

@Composable
fun AuthNavigation(
    navController: NavHostController,
    onSucceedLogin: () -> Unit
) {
    NavHost(navController = navController, startDestination = Routes.START.value) {
        composable(Routes.START.value) {
            StartScreen(navController, onSucceedLogin)
        }
        composable(Routes.LOGIN.value) {
            LoginScreen(
                onRegisterButtonClick = {
                    navController.navigate(Routes.REGISTER.value)
                },
                onSucceedLogin = onSucceedLogin,
            )
        }
        composable(Routes.REGISTER.value) {
            RegistrationScreen(
                onSucceedRegistration = {
                    navController.navigateUp()
                },
                onNavigateBack = {
                    navController.navigateUp()
                },
            )
        }
    }
}

@Composable
fun StartScreen(
    navController: NavHostController,
    goToMainActivity: () -> Unit
) {
    val securePreferences: SecurePreferences = get<SecurePreferences>()
    LaunchedEffect(Unit) {
        if (securePreferences.isLoggedIn()) {
            goToMainActivity()
        } else {
            navController.navigate(Routes.LOGIN.value) {
                popUpTo("start_screen") { inclusive = true }
            }
        }
    }

    LoadingState()
}