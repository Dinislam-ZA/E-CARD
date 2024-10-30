package example.com.di

import example.com.plugins.RedisConfig
import example.com.data.redis.FriendshipRedisManager
import example.com.data.redis.GamesRedisManager
import org.koin.core.qualifier.named
import org.koin.dsl.module

// TODO: Вряд ли это можно назвать appModule, надо бы переименовать
fun appModule(redisConfig: RedisConfig) = module {
    single<FriendshipRedisManager>(named("friends")) { FriendshipRedisManager(redisConfig.host, redisConfig.port) }
    single<GamesRedisManager>(named("games")) { GamesRedisManager(redisConfig.host, redisConfig.port) }
}