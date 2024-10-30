package example.com

import example.com.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val redisConfig = configureRedis()
    configureKoin(redisConfig)
    configureSecurity()
    configureSerialization()
    configureDatabases()
    configureSockets()
    configureRouting()
}
