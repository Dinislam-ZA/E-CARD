package com.example.e_card_android.ui.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.e_card_android.data.interactors.AuthInteractor
import com.example.e_card_android.ui.common.viewmodel.MVIBaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(private val authInteractor: AuthInteractor) : MVIBaseViewModel<LoginScreenEvent, LoginScreenState>(LoginScreenState.Idle) {

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    override fun eventHandler(event: LoginScreenEvent) {
        when (event){
            is LoginScreenEvent.EnterPassword -> updatePasswordField(event.text)
            is LoginScreenEvent.EnterUsername -> updateUsernameField(event.text)
            LoginScreenEvent.TryLogin -> tryLogin()
            LoginScreenEvent.TryAgain -> _state.value = LoginScreenState.Idle
        }
    }

    private fun updateUsernameField(text: String){
        username = text
    }

    private fun updatePasswordField(text: String){
        password = text
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