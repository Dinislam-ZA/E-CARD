package com.example.e_card_android.ui.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow<LoginScreenState>(LoginScreenState.Idle)
    val state : StateFlow<LoginScreenState> = _state.asStateFlow()

    private val _uiFields = MutableStateFlow<LoginScreenUIData>(LoginScreenUIData())
    val uiFields = _uiFields.asStateFlow()

    fun handleEvents(event: LoginScreenEvent){
        when (event){
            is LoginScreenEvent.EnterPassword -> updatePasswordField(event.text)
            is LoginScreenEvent.EnterUsername -> updateUsernameField(event.text)
            LoginScreenEvent.TryLogin -> tryLogin()
        }
    }

    private fun updateUsernameField(text: String){
        _uiFields.value = _uiFields.value.copy(username = text)
    }

    private fun updatePasswordField(text: String){
        _uiFields.value = _uiFields.value.copy(password = text)
    }

    private fun tryLogin(){
        Log.d("tryLogin()", "${state.value}")
    }
}