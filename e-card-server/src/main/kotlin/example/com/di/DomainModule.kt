package example.com.di

import example.com.services.GameService
import example.com.services.GameServiceImpl
import example.com.services.UserService
import example.com.services.UserServiceImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val domainModule = module {
    single<UserService> { UserServiceImpl(get(), get(qualifier = named("friends"))) }
    single<GameService> {GameServiceImpl(get(), get(qualifier = named("games")))}
}