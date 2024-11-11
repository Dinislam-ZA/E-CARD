package com.example.e_card_android.data.network

sealed class BackendRoutes(val route: String) {
    data object Login : BackendRoutes("/login")
    data object Register : BackendRoutes("/register")
    data object Players : BackendRoutes("/users")
    data object SendFriendRequest : BackendRoutes("/friends/add")
    data object AcceptFriendRequest : BackendRoutes("/friends/accept")
    data class GetFriends(val id: Int) : BackendRoutes("/friends/{$id}")
    data class GetFriendsRequests(val id: Int) : BackendRoutes("/friends/requests/{$id}")
}