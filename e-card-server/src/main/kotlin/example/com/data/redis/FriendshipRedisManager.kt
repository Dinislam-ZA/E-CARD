package example.com.data.redis

import example.com.data.db.model.FriendInviteNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FriendshipRedisManager(
    redisHost: String,
    redisPort: Int
) : RedisServiceImpl(redisHost, redisPort) {

    suspend fun addFriendRequest(request: FriendInviteNotification, user2Id: Int, ttlSeconds: Int = 86400) {
        val requestKey = "friend_request:${user2Id}_${request.sender}"

        val requestValue = Json.encodeToString(request)

        withContext(Dispatchers.IO) {
            jedis.setex(requestKey, ttlSeconds.toLong(), requestValue)
            jedis.publish("notifications:$user2Id", requestValue)
        }
    }

    suspend fun getFriendRequestsForUser(userId: Int): List<String> {
        return withContext(Dispatchers.IO) {
            jedis.keys("friend_request:${userId}_*")
                .mapNotNull { key -> jedis.get(key) }
        }
    }

    suspend fun removeFriendRequest(user1Id: Int, user2Id: Int) {
        val requestKey = "friend_request:${user2Id}_$user1Id"

        withContext(Dispatchers.IO) {
            jedis.del(requestKey)
        }
    }
}