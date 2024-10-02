package com.example.e_card_android.di

import com.example.e_card_android.ui.auth.login.LoginViewModel
import com.example.e_card_android.ui.auth.registration.RegistrationViewModel
import com.example.e_card_android.utils.DefaultResourceProvider
import com.example.e_card_android.utils.ResourceProvider
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegistrationViewModel(get(), get()) }
    single<ResourceProvider> { DefaultResourceProvider(androidContext()) }
}