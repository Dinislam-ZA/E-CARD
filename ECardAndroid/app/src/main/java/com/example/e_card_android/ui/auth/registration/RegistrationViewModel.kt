package com.example.e_card_android.ui.auth.registration

import android.net.http.HttpException
import android.net.http.NetworkException
import androidx.lifecycle.viewModelScope
import com.example.e_card_android.data.interactors.AuthInteractor
import com.example.e_card_android.ui.common.viewmodel.MVIBaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(private val authInteractor: AuthInteractor) : MVIBaseViewModel<RegistrationScreenEvent, RegistrationScreenState>(RegistrationScreenState.Idle) {

    private val _uiData = MutableStateFlow(RegistrationScreenUIData())
    val uiData = _uiData

    override fun eventHandler(event: RegistrationScreenEvent) {
        when (event){
            is RegistrationScreenEvent.EnterPassword -> updatePasswordField(event.text)
            is RegistrationScreenEvent.EnterRepeatedPassword -> updateRepeatPasswordField(event.text)
            is RegistrationScreenEvent.EnterUsername -> updateUsernameField(event.text)
            RegistrationScreenEvent.TryRegister -> tryRegister()
            RegistrationScreenEvent.TryAgain -> _state.value = RegistrationScreenState.Idle
        }
    }

    private fun tryRegister(){
        viewModelScope.launch {
            try {
                _state.value = RegistrationScreenState.Loading
                delay(3000)
                authInteractor.register(uiData.value.username, uiData.value.password)
                _state.value = RegistrationScreenState.Success
            }
            catch (_: Exception){
                _state.value = RegistrationScreenState.Error("Fuck")
            }
        }
    }

    private fun updateUsernameField(text: String){
        _uiData.value = _uiData.value.copy(username = text)
    }

    private fun updatePasswordField(text: String){
        _uiData.value = _uiData.value.copy(password = text)
    }

    private fun updateRepeatPasswordField(text: String){
        _uiData.value = _uiData.value.copy(repeatPassword = text)
    }

}