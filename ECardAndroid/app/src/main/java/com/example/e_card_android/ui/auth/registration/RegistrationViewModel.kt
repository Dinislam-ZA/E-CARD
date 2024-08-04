package com.example.e_card_android.ui.auth.registration

import com.example.e_card_android.ui.common.viewmodel.MVIBaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class RegistrationViewModel : MVIBaseViewModel<RegistrationScreenEvent, RegistrationScreenState>(RegistrationScreenState.Idle) {

    private val _uiData = MutableStateFlow(RegistrationScreenUIData())
    val uiData = _uiData

    override fun eventHandler(event: RegistrationScreenEvent) {
        TODO("Not yet implemented")
    }
}