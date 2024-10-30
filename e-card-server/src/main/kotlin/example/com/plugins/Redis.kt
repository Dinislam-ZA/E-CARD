package example.com.plugins

import io.ktor.server.application.*

data class RedisConfig(val host: String, val port: Int)

fun Application.configureRedis(): RedisConfig {
    val host = environment.config.property("redis.host").getString()
    val port = environment.config.property("redis.port").getString().toInt()
    return RedisConfig(host, port)
}
