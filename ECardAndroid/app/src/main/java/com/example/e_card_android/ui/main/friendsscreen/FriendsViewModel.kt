package com.example.e_card_android.ui.main.friendsscreen

import androidx.lifecycle.viewModelScope
import com.example.e_card_android.data.repositories.UserRepository
import com.example.e_card_android.ui.common.viewmodel.MVIBaseViewModel
import kotlinx.coroutines.launch

class FriendsViewModel(
    private val repository: UserRepository,
) :
    MVIBaseViewModel<FriendsScreenEvent, FriendsScreenState>(FriendsScreenState.Loading) {

    init {
        eventHandler(FriendsScreenEvent.LoadFriendsList)
    }

    override fun eventHandler(event: FriendsScreenEvent) {
        when (event) {
            FriendsScreenEvent.LoadFriendsList -> getFriends()
        }
    }

    private fun getFriends() {
        viewModelScope.launch {
            try {
                _state.value = FriendsScreenState.Loading
                val users = repository.getFriends()
                _state.value = FriendsScreenState.FriendsList(users)
            } catch (e: Exception) {
                _state.value = FriendsScreenState.Error
            }
        }
    }
}