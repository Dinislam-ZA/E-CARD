package com.example.e_card_android.ui.main.usersscreen

sealed class UsersScreenEvent {
    data object LoadUsersList: UsersScreenEvent()
}