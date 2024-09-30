package com.example.e_card_android.ui.main.usersscreen

import com.example.e_card_android.data.model.User
import com.example.e_card_android.data.repositories.UserRepository
import com.example.e_card_android.ui.common.viewmodel.MVIBaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsersScreenViewModel(val repository: UserRepository): MVIBaseViewModel<UsersScreenEvent, UsersScreenState>(UsersScreenState.Loading) {

    private val _uiData = MutableStateFlow(listOf<User>())
    val uiData = _uiData.asStateFlow()

    override fun eventHandler(event: UsersScreenEvent) {
        TODO("Not yet implemented")
    }
}