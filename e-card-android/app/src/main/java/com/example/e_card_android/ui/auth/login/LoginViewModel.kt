package com.example.e_card_android.ui.auth.login

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

class LoginViewModel(
    private val authInteractor: AuthManager,
    private val resourceProvider: ResourceProvider
) : MVIBaseViewModel<LoginScreenEvent, LoginScreenState>(LoginScreenState.Idle) {

    var username by mutableStateOf(FieldState())
        private set

    var password by mutableStateOf(FieldState())
        private set

    override fun eventHandler(event: LoginScreenEvent) {
        when (event) {
            is LoginScreenEvent.EnterPassword -> updatePasswordField(event.text)
            is LoginScreenEvent.EnterUsername -> updateUsernameField(event.text)
            LoginScreenEvent.TryLogin -> tryLogin()
            LoginScreenEvent.TryAgain -> _state.value = LoginScreenState.Idle
        }
    }

    private fun tryLogin() {
        if (!validateAllFields()) return

        viewModelScope.launch {
            safeApiCall(
                resourceProvider,
                onError = {
                    _state.value = LoginScreenState.Error(it)
                },
                block = {
                    _state.value = LoginScreenState.Loading
                    authInteractor.login(username.value, password.value)
                    _state.value = LoginScreenState.Success
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

    private fun validateAllFields(): Boolean {
        val usernameError =
            validateRequiredField(username.value, resourceProvider.getString(R.string.username))
        val passwordError =
            validateRequiredField(password.value, resourceProvider.getString(R.string.password))

        this.username = username.copy(error = usernameError)
        this.password = password.copy(error = passwordError)

        return usernameError is FieldValidationState.Valid && passwordError is FieldValidationState.Valid
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
}