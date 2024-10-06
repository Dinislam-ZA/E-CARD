package com.example.e_card_android.ui.main.friendsscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.e_card_android.data.model.User
import com.example.e_card_android.ui.common.composables.ErrorStateHolder
import com.example.e_card_android.ui.common.composables.LoadingState
import com.example.e_card_android.ui.common.composables.UserCard
import org.koin.androidx.compose.koinViewModel

@Composable
fun FriendsScreen(
    viewModel: FriendsViewModel = koinViewModel(),
    paddingValues: PaddingValues
) {
    val state = viewModel.state.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ){
        when (state.value) {
            FriendsScreenState.Error -> ErrorStateHolder(
                message = "Try again lil bro",
                tryAgain = {
                    viewModel.eventHandler(FriendsScreenEvent.LoadFriendsList)
                }
            )

            is FriendsScreenState.FriendsList -> FriendsList((state.value as FriendsScreenState.FriendsList).users)
            FriendsScreenState.Loading -> LoadingState()
        }
    }
}

@Composable
fun FriendsList(
    users: List<User>
) {
    LazyColumn {
        items(users.size) { index ->
            UserCard(
                user = users[index],
            )
        }
    }
}