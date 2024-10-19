package example.com.di

import example.com.data.db.repositories.UserRepository
import example.com.data.db.repositories.UserRepositoryImpl
import example.com.services.UserService
import example.com.services.UserServiceImpl
import org.koin.dsl.module

val appModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    single<UserService> {UserServiceImpl(get())}
}