package com.example.e_card_android.ui.auth.registration

sealed class RegistrationScreenEvent {
    data class EnterUsername(val text: String) : RegistrationScreenEvent()
    data class EnterPassword(val text: String) : RegistrationScreenEvent()
    data class EnterRepeatedPassword(val text: String) : RegistrationScreenEvent()
    data object TryRegister : RegistrationScreenEvent()
    data object TryAgain: RegistrationScreenEvent()
}