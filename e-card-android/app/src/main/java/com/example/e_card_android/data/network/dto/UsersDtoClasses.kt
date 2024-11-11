package com.example.e_card_android.data.network.dto

import com.example.e_card_android.data.model.User

data class GetPlayersResponse(
    val players: List<User>
)

data class AddFriendRequest(
    val user1: Int,
    val user2: Int
)

data class AcceptFriendRequest(
    val user1: Int,
    val user2: Int
)