package com.example.e_card_android.ui.main.usersscreen

import com.example.e_card_android.data.model.User

sealed class UsersScreenState {
    data class AllUsers(val users: List<User>): UsersScreenState()
    data object Error: UsersScreenState()
    data object Loading: UsersScreenState()
}