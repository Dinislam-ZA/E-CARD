package com.example.e_card_android.ui.main.homescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_card_android.data.model.Game
import com.example.e_card_android.data.model.User
import com.example.e_card_android.ui.common.composables.CardBasedCustomRadioGroup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGameDialog(
    owner: User,
    onCreateGame: (Game) -> Unit,
    onDismiss: () -> Unit
) {
    var bet by remember { mutableLongStateOf(100L) }
    var rounds by remember { mutableIntStateOf(4) }
    var description by remember { mutableStateOf("Tap to join the game") }

    val availableRounds = listOf("4", "8", "12")

    ModalBottomSheet(onDismissRequest = { onDismiss() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Create a New Game",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = bet.toString(),
                onValueChange = { bet = it.toLongOrNull() ?: 100L },
                label = { Text("Bet (Default: 100)") },
                modifier = Modifier.fillMaxWidth()
            )


            Text(text = "Select number of rounds")
            CardBasedCustomRadioGroup(
                selectedRadio = availableRounds.indexOf(rounds.toString()),
                elements = availableRounds,
                onSelect = { selected ->
                    rounds = availableRounds[selected].toInt()
                }
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = { onDismiss() }) {
                    Text("Cancel")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                    val gameId = (1..1000).random()

                    val newGame = Game(
                        id = gameId,
                        bet = bet,
                        rounds = rounds,
                        description = description,
                        owner = owner
                    )

                    onCreateGame(newGame)

                    onDismiss()
                }) {
                    Text("Create")
                }
            }
        }
    }
}

@Preview
@Composable
fun CreateGameDialogPreview(){
    CreateGameDialog(
        owner = User(0, "Dinislam"),
        onCreateGame = {

        },
        onDismiss = {

        }
    )
}
