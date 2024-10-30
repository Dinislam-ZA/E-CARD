package example.com.data.redis

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPubSub

interface RedisManager {
    fun subscribeToNotifications(user2Id: Int, onMessage: (String) -> Unit)
    fun close()
}

abstract class RedisServiceImpl(
    redisHost: String = "localhost",
    redisPort: Int = 6379,
) : RedisManager {

    protected val jedis = Jedis(redisHost, redisPort)

    override fun subscribeToNotifications(user2Id: Int, onMessage: (String) -> Unit) {
        val pubSub = object : JedisPubSub() {
            override fun onMessage(channel: String, message: String) {
                onMessage(message)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                println("Подписка на канал notifications:$user2Id началась")
                jedis.subscribe(pubSub, "notifications:$user2Id")
            } catch (e: Exception) {
                println("Ошибка при подписке: ${e.message}")
            } finally {
                println("Подписка на канал notifications:$user2Id завершена")
            }
        }
    }

    override fun close() {
        jedis.close()
    }
}
