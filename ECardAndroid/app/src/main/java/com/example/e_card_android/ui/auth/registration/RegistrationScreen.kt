package com.example.e_card_android.ui.auth.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.e_card_android.R
import com.example.e_card_android.ui.common.composables.ErrorAlertDialog
import com.example.e_card_android.ui.common.composables.LoadingState
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = koinViewModel(),
    onSucceedRegistration: () -> Unit
){
    val state = viewModel.state.collectAsState()
    val uiData = viewModel.uiData.collectAsState()

    val openAlertDialog = remember { mutableStateOf(false) }

    when(state.value){
        is RegistrationScreenState.Error ->{
            openAlertDialog.value = true
            RegistrationScreenErrorState(openAlertDialog, onConfirmation = {
                viewModel.eventHandler(RegistrationScreenEvent.TryAgain)
            })
        }
        RegistrationScreenState.Idle -> RegistrationScreenIdleState(
            uiData = uiData.value,
            eventHandler = {
                viewModel.eventHandler(it)
            },
        )
        RegistrationScreenState.Loading -> LoadingState()
        RegistrationScreenState.Success -> onSucceedRegistration()
    }
}

@Composable
fun RegistrationScreenIdleState(
    uiData: RegistrationScreenUIData,
    eventHandler: (RegistrationScreenEvent) -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TextField(
            value = uiData.username,
            onValueChange = {
                eventHandler(RegistrationScreenEvent.EnterUsername(it))
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = uiData.password,
            onValueChange = {
                eventHandler(RegistrationScreenEvent.EnterPassword(it))
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = uiData.repeatPassword,
            onValueChange = {
                eventHandler(RegistrationScreenEvent.EnterRepeatedPassword(it))
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedButton(onClick = {
            eventHandler(RegistrationScreenEvent.TryRegister)
        }) {
            Text(text = stringResource(R.string.register_button_text))
        }
    }
}

@Composable
fun RegistrationScreenErrorState(openAlertDialog: MutableState<Boolean>, onConfirmation: () -> Unit) {
    if (openAlertDialog.value){
        ErrorAlertDialog(
            dialogTitle = "Registration error",
            dialogText = "Error while registration was occured. Check yout internet connection and try again",
            onConfirmation = onConfirmation
        )
    }
}