package com.example.e_card_android.ui.auth.login

sealed class LoginScreenEvent {
    data object TryLogin : LoginScreenEvent()
    data object TryAgain : LoginScreenEvent()
    data class EnterUsername(val text:String) : LoginScreenEvent()
    data class EnterPassword(val text:String) : LoginScreenEvent()
}