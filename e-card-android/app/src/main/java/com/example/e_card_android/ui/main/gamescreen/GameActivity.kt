package com.example.e_card_android.ui.main.gamescreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.e_card_android.ui.theme.ECardAndroidTheme

class GameActivity: ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            ECardAndroidTheme {

            }
        }
    }

}

