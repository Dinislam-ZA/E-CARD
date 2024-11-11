package com.example.e_card_android.ui.main.homescreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_card_android.data.model.Game
import com.example.e_card_android.data.model.User
import com.example.e_card_android.ui.common.composables.GameCard

@Composable
fun HomeScreen(paddingValues: PaddingValues) {

    val player = User(0, "Dinislam")
    var showCreateGameModalDialog by remember { mutableStateOf(false) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        HomeScreenIdleState(player = player)

        FloatingActionButton(
            onClick = {
                showCreateGameModalDialog = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }

        if (showCreateGameModalDialog)
            CreateGameDialog(
                owner = player,
                onDismiss = {
                    showCreateGameModalDialog = false
                },
                onCreateGame = {
                    showCreateGameModalDialog = false
                }
            )
    }
}

@Composable
fun HomeScreenIdleState(
    player: User
) {
    Column(modifier = Modifier.fillMaxSize()) {
        PlayerProfileHeader(
            user = player
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        val games = listOf(
            Game(id = 1, bet = 150, rounds = 4, owner = User(1, "Alice")),
            Game(id = 2, bet = 300, rounds = 8, owner = User(2, "Bob")),
            Game(id = 3, bet = 500, rounds = 12, owner = User(3, "Charlie")),
            Game(id = 4, bet = 200, rounds = 4, owner = User(4, "Dave")),
            Game(id = 5, bet = 1000, rounds = 8, owner = User(5, "Eve")),
        )
        
        LazyColumn {
            items(games) { game ->
                GameCard(game = game)
            }
        }
    }
}