package com.example.e_card_android.ui.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_card_android.data.interactors.AuthInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(val authInteractor: AuthInteractor) : ViewModel() {

    private val _state = MutableStateFlow<LoginScreenState>(LoginScreenState.Idle)
    val state : StateFlow<LoginScreenState> = _state.asStateFlow()

    private val _uiFields = MutableStateFlow(LoginScreenUIData())
    val uiFields = _uiFields.asStateFlow()

    fun handleEvents(event: LoginScreenEvent){
        when (event){
            is LoginScreenEvent.EnterPassword -> updatePasswordField(event.text)
            is LoginScreenEvent.EnterUsername -> updateUsernameField(event.text)
            LoginScreenEvent.TryLogin -> tryLogin()
            LoginScreenEvent.TryAgain -> _state.value = LoginScreenState.Idle
        }
    }

    private fun updateUsernameField(text: String){
        _uiFields.value = _uiFields.value.copy(username = text)
    }

    private fun updatePasswordField(text: String){
        _uiFields.value = _uiFields.value.copy(password = text)
    }

    private fun tryLogin(){
        viewModelScope.launch {
            try {
                _state.value = LoginScreenState.Loading
                delay(3000)
                _state.value = LoginScreenState.Success
            }
            catch (_: Exception){
                _state.value = LoginScreenState.Error("")
            }
        }
    }
}