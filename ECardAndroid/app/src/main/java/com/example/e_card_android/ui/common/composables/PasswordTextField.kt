package com.example.e_card_android.ui.common.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.e_card_android.R

@Composable
fun PasswordTextField(
    password: String,
    isError: Boolean = false,
    supportingText: String = "",
    label: String = stringResource(R.string.password),
    onPasswordChange: (String) -> Unit,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text(label) },
        supportingText = {
            if (supportingText.isNotEmpty())
                Text(supportingText)
        },
        isError = isError,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) {
                ImageVector.vectorResource(R.drawable.eye_show_svgrepo_com)
            } else {
                ImageVector.vectorResource(R.drawable.eye_close_svgrepo_com)
            }
            val description =
                if (passwordVisible) stringResource(R.string.hide_text) else stringResource(
                    R.string.show_text
                )

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = description)
            }
        },
        singleLine = true
    )
}
