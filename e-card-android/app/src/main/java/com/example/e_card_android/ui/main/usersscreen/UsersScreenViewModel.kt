package com.example.e_card_android.ui.main.usersscreen

import androidx.lifecycle.viewModelScope
import com.example.e_card_android.data.repositories.UserRepository
import com.example.e_card_android.ui.common.viewmodel.MVIBaseViewModel
import kotlinx.coroutines.launch

class UsersScreenViewModel(private val repository: UserRepository) :
    MVIBaseViewModel<UsersScreenEvent, UsersScreenState>(UsersScreenState.Loading) {

    init {
        eventHandler(UsersScreenEvent.LoadUsersList)
    }

    override fun eventHandler(event: UsersScreenEvent) {
        when (event) {
            UsersScreenEvent.LoadUsersList -> getAllPlayers()
        }
    }

    // TODO: Поработать над исключениями
    private fun getAllPlayers() {
        viewModelScope.launch {
            try {
                _state.value = UsersScreenState.Loading
                // TODO: Сортировку надо убрать отсюда и перекинуть в MockRepository, но вообще она будет на бэке
                val users = repository.getAllPlayers().sortedByDescending { it.money }
                _state.value = UsersScreenState.Success(users)
            } catch (e: Exception) {
                _state.value = UsersScreenState.Error
            }
        }
    }

}