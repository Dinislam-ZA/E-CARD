package com.example.e_card_android.ui.auth.login

sealed class LoginScreenState {
    data object Loading : LoginScreenState()
    data class Error(val message: String): LoginScreenState()
    data object Idle: LoginScreenState()
    data object Success: LoginScreenState()
}