package com.example.e_card_android.ui.auth.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.e_card_android.R
import com.example.e_card_android.data.network.AuthManager
import com.example.e_card_android.ui.common.viewmodel.FieldState
import com.example.e_card_android.ui.common.viewmodel.FieldValidationState
import com.example.e_card_android.ui.common.viewmodel.MVIBaseViewModel
import com.example.e_card_android.utils.ResourceProvider
import com.example.e_card_android.utils.safeApiCall
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val authInteractor: AuthManager,
    private val resourceProvider: ResourceProvider
) : MVIBaseViewModel<RegistrationScreenEvent, RegistrationScreenState>(RegistrationScreenState.Idle) {

    var username by mutableStateOf(FieldState())
        private set

    var password by mutableStateOf(FieldState())
        private set

    var repeatPassword by mutableStateOf(FieldState())
        private set

    override fun eventHandler(event: RegistrationScreenEvent) {
        when (event) {
            is RegistrationScreenEvent.EnterPassword -> updatePasswordField(event.text)
            is RegistrationScreenEvent.EnterRepeatedPassword -> updateRepeatPasswordField(event.text)
            is RegistrationScreenEvent.EnterUsername -> updateUsernameField(event.text)
            RegistrationScreenEvent.TryRegister -> tryRegister()
            RegistrationScreenEvent.TryAgain -> _state.value = RegistrationScreenState.Idle
        }
    }

    private fun tryRegister() {
        viewModelScope.launch {
            safeApiCall(
                resourceProvider = resourceProvider,
                onError = {
                    _state.value = RegistrationScreenState.Error(it)
                },
                block = {
                    _state.value = RegistrationScreenState.Loading
                    authInteractor.register(username.value, password.value)
                    _state.value = RegistrationScreenState.Success
                }
            )
        }
    }

    private fun updateUsernameField(text: String) {
        val error = validateRequiredField(text, resourceProvider.getString(R.string.username))
        username = FieldState(value = text, error = error)
    }

    private fun updatePasswordField(text: String) {
        val error = validateRequiredField(text, resourceProvider.getString(R.string.password))
        password = FieldState(value = text, error = error)
    }

    private fun updateRepeatPasswordField(text: String) {
        var error =
            validateRequiredField(text, resourceProvider.getString(R.string.repeat_password))
        if (error == FieldValidationState.Valid)
            error = validatePasswordMatch(text, password.value)
        repeatPassword = FieldState(value = text, error = error)
    }

    private fun validateRequiredField(value: String, fieldName: String): FieldValidationState {
        return if (value.isEmpty())
            FieldValidationState.Error(
                resourceProvider.getString(
                    R.string.shouldn_t_be_empty,
                    fieldName
                )
            )
        else
            FieldValidationState.Valid
    }

    private fun validatePasswordMatch(
        password: String,
        repeatPassword: String
    ): FieldValidationState {
        return if (password == repeatPassword) FieldValidationState.Valid
        else FieldValidationState.Error(resourceProvider.getString(R.string.passwords_don_t_match))
    }

}