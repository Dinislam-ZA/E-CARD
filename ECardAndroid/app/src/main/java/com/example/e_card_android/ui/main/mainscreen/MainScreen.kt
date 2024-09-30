package com.example.e_card_android.ui.main.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun MainScreen(){
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        Text("Main screen")
    }
}