package com.example.e_card_android.ui.auth.registration

sealed class RegistrationScreenState {
    data object Loading : RegistrationScreenState()
    data class Error(val message: String) : RegistrationScreenState()
    data object Idle : RegistrationScreenState()
    data object Success: RegistrationScreenState()
}