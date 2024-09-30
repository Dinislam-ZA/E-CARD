package com.example.e_card_android.ui.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_card_android.R
import com.example.e_card_android.ui.common.composables.ErrorAlertDialog
import com.example.e_card_android.ui.common.composables.LoadingState
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    onRegisterButtonClick: () -> Unit,
    onSucceedLogin: () -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val uiData = viewModel.uiFields.collectAsState()

    val openAlertDialog = remember { mutableStateOf(false) }
    when (state.value) {
        is LoginScreenState.Error -> LoginErrorStateScreen(
            openAlertDialog,
            onConfirmation = {
                viewModel.handleEvents(LoginScreenEvent.TryAgain)
            })
        is LoginScreenState.Idle -> LoginIdleStateScreen(
            uiData.value,
            eventHandler = {
                viewModel.handleEvents(it)
            },
            onRegisterButtonClick = onRegisterButtonClick
        )

        LoginScreenState.Loading -> LoadingState()
        LoginScreenState.Success -> onSucceedLogin()
    }
}

@Composable
private fun LoginIdleStateScreen(
    uiData: LoginScreenUIData,
    eventHandler: (LoginScreenEvent) -> Unit,
    onRegisterButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TextField(
            value = uiData.username,
            onValueChange = {
                eventHandler(LoginScreenEvent.EnterUsername(it))
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = uiData.password,
            onValueChange = {
                eventHandler(LoginScreenEvent.EnterPassword(it))
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = {
            eventHandler(LoginScreenEvent.TryLogin)
        }) {
            Text(text = stringResource(R.string.login_button_text))
        }
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedButton(onClick = onRegisterButtonClick) {
            Text(text = stringResource(R.string.registration_button_text))
        }
    }
}

@Composable
fun LoginErrorStateScreen(openAlertDialog: MutableState<Boolean>, onConfirmation: () -> Unit) {
    if (openAlertDialog.value) {
        ErrorAlertDialog(
            dialogTitle = "Registration error",
            dialogText = "Error while registration was occured. Check yout internet connection and try again",
            onConfirmation = onConfirmation
        )
    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            onRegisterButtonClick = {
            },
            onSucceedLogin = {

            }
        )
    }
}