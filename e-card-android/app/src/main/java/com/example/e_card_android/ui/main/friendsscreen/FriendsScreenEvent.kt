package com.example.e_card_android.ui.main.friendsscreen

sealed class FriendsScreenEvent {
    data object LoadFriendsList: FriendsScreenEvent()
    data class SearchPlayers(val query: String): FriendsScreenEvent()
}