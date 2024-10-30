package example.com.di

import example.com.data.db.repositories.GameRepository
import example.com.data.db.repositories.GameRepositoryImpl
import example.com.data.db.repositories.UserRepository
import example.com.data.db.repositories.UserRepositoryImpl
import org.koin.dsl.module

val dataModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    single<GameRepository> { GameRepositoryImpl() }
}