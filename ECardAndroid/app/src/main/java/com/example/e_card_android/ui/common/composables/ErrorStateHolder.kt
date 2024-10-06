package com.example.e_card_android.ui.common.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.e_card_android.R

@Composable
fun ErrorStateHolder(
    message: String,
    tryAgain: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(message)
        Button(onClick = tryAgain) {
            Text(stringResource(R.string.try_again))
        }
    }
}