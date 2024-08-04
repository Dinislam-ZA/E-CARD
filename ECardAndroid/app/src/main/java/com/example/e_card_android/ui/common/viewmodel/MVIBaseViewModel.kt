package com.example.e_card_android.ui.common.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class MVIBaseViewModel<T, S>(private val initialState: S) : ViewModel() {

    private val _state = MutableStateFlow<S>(initialState)
    val state = _state.asStateFlow()

    abstract fun eventHandler(event: T)
}