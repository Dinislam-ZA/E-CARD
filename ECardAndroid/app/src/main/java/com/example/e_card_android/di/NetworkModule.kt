package com.example.e_card_android.di

import com.example.e_card_android.data.interactors.AuthInteractor
import com.example.e_card_android.data.interactors.MockAuthInteractor
import org.koin.dsl.module

val networkModule = module {
    single<AuthInteractor> { MockAuthInteractor(get()) }
}