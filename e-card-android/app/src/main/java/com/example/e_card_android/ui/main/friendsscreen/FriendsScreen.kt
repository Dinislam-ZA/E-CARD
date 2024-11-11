@file:Suppress("UNUSED_EXPRESSION")

package com.example.e_card_android.ui.main.friendsscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.e_card_android.R
import com.example.e_card_android.data.model.User
import com.example.e_card_android.ui.common.composables.ErrorStateHolder
import com.example.e_card_android.ui.common.composables.LoadingState
import com.example.e_card_android.ui.common.composables.UserCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    viewModel: FriendsViewModel = koinViewModel(),
    paddingValues: PaddingValues
) {
    val state = viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        // TODO: Мб заменить на обычный TextField? А то мне не нравится то, как я его юзаю...
        DockedSearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            query = viewModel.query,
            active = false,
            onQueryChange = {
                viewModel.eventHandler(FriendsScreenEvent.SearchPlayers(it))
            },
            onActiveChange = {
                false
            },
            onSearch = {},
            placeholder = { Text(stringResource(R.string.friends_search_bar_placeholder)) }
        ) {}
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
    Spacer(modifier = Modifier.height(8.dp))
    LazyColumn {
        items(users.size) { index ->
            UserCard(
                user = users[index],
            )
        }
    }
}