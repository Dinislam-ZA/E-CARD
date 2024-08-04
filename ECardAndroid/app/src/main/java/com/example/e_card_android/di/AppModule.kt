package com.example.e_card_android.di

import com.example.e_card_android.ui.auth.login.LoginViewModel
import com.example.e_card_android.ui.auth.registration.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel() }
    viewModel { RegistrationViewModel() }
}