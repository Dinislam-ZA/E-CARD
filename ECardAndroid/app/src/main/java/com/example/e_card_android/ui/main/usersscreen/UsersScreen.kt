package com.example.e_card_android.ui.main.usersscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun UserScreen(){
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        Text(text = "Users Screen", fontSize = 24.sp)
    }
}