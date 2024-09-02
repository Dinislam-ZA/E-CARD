package example.com

import example.com.model.UserRepositoryImpl
import example.com.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val userRepository = UserRepositoryImpl()
    configureSecurity()
    configureSerialization()
    configureDatabases()
    configureSockets()
    configureRouting(userRepository)
}
