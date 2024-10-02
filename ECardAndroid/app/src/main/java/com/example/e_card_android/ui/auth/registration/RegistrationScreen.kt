package com.example.e_card_android.ui.auth.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.example.e_card_android.ui.common.composables.PasswordTextField
import com.example.e_card_android.ui.common.viewmodel.FieldState
import com.example.e_card_android.ui.common.viewmodel.FieldValidationState
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = koinViewModel(),
    onSucceedRegistration: () -> Unit
) {
    val state = viewModel.state.collectAsState()

    val openAlertDialog = remember { mutableStateOf(false) }

    when (state.value) {
        is RegistrationScreenState.Error -> {
            openAlertDialog.value = true
            RegistrationScreenErrorState(
                openAlertDialog = openAlertDialog,
                errorText = (state.value as RegistrationScreenState.Error).message,
                onConfirmation = {
                viewModel.eventHandler(RegistrationScreenEvent.TryAgain)
            })
        }

        RegistrationScreenState.Idle -> RegistrationScreenIdleState(
            viewModel.username,
            viewModel.password,
            viewModel.repeatPassword,
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
    username: FieldState,
    password: FieldState,
    repeatPassword: FieldState,
    eventHandler: (RegistrationScreenEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            label = { Text(stringResource(R.string.username_label)) },
            value = username.value,
            supportingText = {
                if (username.error is FieldValidationState.Error)
                    Text(username.error.message)
            },
            isError = username.error !is FieldValidationState.Valid,
            onValueChange = {
                eventHandler(RegistrationScreenEvent.EnterUsername(it))
            }
        )
        Spacer(modifier = Modifier.height(24.dp))
        PasswordTextField(
            password = password.value,
            supportingText = if (password.error is FieldValidationState.Error) password.error.message else "",
            isError = password.error !is FieldValidationState.Valid,
            onPasswordChange = {
                eventHandler(RegistrationScreenEvent.EnterPassword(it))
            },
            label = stringResource(R.string.password)
        )
        Spacer(modifier = Modifier.height(24.dp))
        PasswordTextField(
            password = repeatPassword.value,
            supportingText = if (repeatPassword.error is FieldValidationState.Error) repeatPassword.error.message else "",
            isError = repeatPassword.error !is FieldValidationState.Valid,
            onPasswordChange = {
                eventHandler(RegistrationScreenEvent.EnterRepeatedPassword(it))
            },
            label = stringResource(R.string.repeat_password)
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
fun RegistrationScreenErrorState(
    openAlertDialog: MutableState<Boolean>,
    errorText: String,
    onConfirmation: () -> Unit
) {
    if (openAlertDialog.value) {
        ErrorAlertDialog(
            dialogTitle = "Registration error",
            dialogText = errorText,
            onConfirmation = onConfirmation
        )
    }
}