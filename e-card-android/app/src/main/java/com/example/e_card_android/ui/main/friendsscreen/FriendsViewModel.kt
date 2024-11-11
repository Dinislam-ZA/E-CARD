package com.example.e_card_android.ui.main.friendsscreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.e_card_android.data.model.User
import com.example.e_card_android.data.repositories.UserRepository
import com.example.e_card_android.ui.common.viewmodel.MVIBaseViewModel
import kotlinx.coroutines.launch

class FriendsViewModel(
    private val repository: UserRepository,
) :
    MVIBaseViewModel<FriendsScreenEvent, FriendsScreenState>(FriendsScreenState.Loading) {

    var query by mutableStateOf("")
        private set

    init {
        eventHandler(FriendsScreenEvent.LoadFriendsList)
    }

    override fun eventHandler(event: FriendsScreenEvent) {
        when (event) {
            FriendsScreenEvent.LoadFriendsList -> getFriends()
            is FriendsScreenEvent.SearchPlayers -> searchPlayers(event.query)
        }
    }

    private fun getFriends() {
        viewModelScope.launch {
            try {
                _state.value = FriendsScreenState.Loading
                val users = repository.getFriends(2)
                _state.value = FriendsScreenState.FriendsList(users)
            } catch (e: Exception) {
                _state.value = FriendsScreenState.Error
            }
        }
    }

    private fun searchPlayers(newQuery:String) {
        changeQuery(newQuery)
        if (query.isEmpty())
            getFriends()
        else{
            viewModelScope.launch {
                try {
                    _state.value = FriendsScreenState.Loading
                    val users = repository.getAllPlayers().filter { user -> user.username.contains(query) }
                    Log.d("Users list", users.toString())
                    _state.value = FriendsScreenState.FriendsList(users)
                } catch (e: Exception) {
                    _state.value = FriendsScreenState.Error
                }
            }
        }
    }

    private fun sendFriendsRequest(user: User){
        viewModelScope.launch {
            repository.sendFriendRequest(user)
        }
    }

    private fun changeQuery(text: String){
        query = text
    }
}