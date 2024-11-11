package com.example.e_card_android.di

import com.example.e_card_android.data.network.AuthManager
import com.example.e_card_android.data.network.KtorClient
import com.example.e_card_android.data.network.MockAuthManager
import org.koin.dsl.module

val networkModule = module {
    single<AuthManager> { MockAuthManager(get()) }
    single { KtorClient() }
}