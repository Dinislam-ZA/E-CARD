package com.example.e_card_android

import android.app.Application
import com.example.e_card_android.di.appModule
import com.example.e_card_android.di.dataModule
import com.example.e_card_android.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule, dataModule, networkModule)
        }
    }
}