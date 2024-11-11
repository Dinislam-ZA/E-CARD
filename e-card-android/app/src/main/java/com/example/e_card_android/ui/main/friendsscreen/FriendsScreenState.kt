package com.example.e_card_android.ui.main.friendsscreen

import com.example.e_card_android.data.model.User


sealed class FriendsScreenState {
    data class FriendsList(val users: List<User>) : FriendsScreenState()
    data object Error : FriendsScreenState()
    data object Loading : FriendsScreenState()
}