package com.example.e_card_android.data.shared_preference

import androidx.security.crypto.EncryptedSharedPreferences


import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.MasterKey

class SecurePreferences(context: Context) {

    private val sharedPreferences = createEncryptedSharedPreferences(context)

    private fun createEncryptedSharedPreferences(context: Context): SharedPreferences {
        val masterKeyAlias = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            masterKeyAlias,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveCredentials(username: String, password: String, token: String, id: Int) {
        sharedPreferences.edit().apply {
            putString("username", username)
            putString("password", password)
            putString("token", token)
            putInt("id", id)  // Сохранение ID
            apply()
        }
    }

    fun getUsername(): String? {
        return sharedPreferences.getString("username", null)
    }

    fun getPassword(): String? {
        return sharedPreferences.getString("password", null)
    }

    fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    fun getId(): Int? {
        return if (sharedPreferences.contains("id")) {
            sharedPreferences.getInt("id", -1)  // Возвращаем id или -1, если он отсутствует
        } else {
            null
        }
    }

    fun clearCredentials() {
        sharedPreferences.edit().apply {
            remove("username")
            remove("password")
            remove("token")
            remove("id")  // Очистка ID
            apply()
        }
    }

    fun isLoggedIn(): Boolean = (getToken() != null)
}

