package com.example.e_card_android.di

import com.example.e_card_android.data.shared_preference.SecurePreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single { SecurePreferences(androidContext()) }
}