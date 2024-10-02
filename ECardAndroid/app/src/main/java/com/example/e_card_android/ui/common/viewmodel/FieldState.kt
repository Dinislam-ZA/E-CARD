package com.example.e_card_android.ui.common.viewmodel

sealed class FieldValidationState {
    data object Valid : FieldValidationState()
    data class Error(val message: String): FieldValidationState()
}

data class FieldState(
    val value: String = "",
    val error: FieldValidationState = FieldValidationState.Valid
)