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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.e_card_android.R
import com.example.e_card_android.ui.common.composables.LoadingState
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = koinViewModel(),
    onSucceedRegistration: () -> Unit
){
    val state = viewModel.state.collectAsState()
    val uiData = viewModel.uiData.collectAsState()

    when(state.value){
        is RegistrationScreenState.Error -> RegistrationScreenErrorState()
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
fun RegistrationScreenErrorState(){

}