package com.example.e_card_android.ui.main.usersscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.e_card_android.data.model.User
import com.example.e_card_android.ui.common.composables.ErrorStateHolder
import com.example.e_card_android.ui.common.composables.LoadingState
import com.example.e_card_android.ui.common.composables.UserCard
import org.koin.androidx.compose.koinViewModel


@Composable
fun UserScreen(
    viewModel: UsersScreenViewModel = koinViewModel(),
    paddingValues: PaddingValues
) {
    val state = viewModel.state.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when (state.value) {
            is UsersScreenState.Success -> UsersList((state.value as UsersScreenState.Success).users)
            UsersScreenState.Error -> ErrorStateHolder(
                message = "Error has occurred! Please try again...",
                tryAgain = {
                    viewModel.eventHandler(UsersScreenEvent.LoadUsersList)
                }
            )

            UsersScreenState.Loading -> LoadingState()
        }
    }
}

@Composable
fun UsersList(
    users: List<User>
) {
    LazyColumn {
        items(users.size) { index ->
            UserCard(
                user = users[index],
                index = index + 1,
                showRating = true,
            )
        }
    }
}

@Preview
@Composable
fun UserScreenPreview() {
    UserScreen(paddingValues = PaddingValues())
}