package example.com.plugins

import example.com.di.appModule
import example.com.di.dataModule
import example.com.di.domainModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin(redisConfig: RedisConfig){
    install(Koin) {
        slf4jLogger()
        modules(appModule(redisConfig), dataModule, domainModule)
    }
}