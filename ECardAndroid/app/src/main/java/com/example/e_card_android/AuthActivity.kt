package com.example.e_card_android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.e_card_android.navigation.AuthNavigation
import com.example.e_card_android.ui.theme.ECardAndroidTheme

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ECardAndroidTheme {
                AuthNavigation(onSucceedLogin = {
                    goToMainActivity()
                })
            }
        }
    }

    private fun goToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}