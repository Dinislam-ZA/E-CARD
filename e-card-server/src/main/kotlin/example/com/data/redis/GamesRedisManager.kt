package example.com.data.redis

import example.com.data.db.model.Game
import example.com.data.db.model.GameInviteNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GamesRedisManager(
    redisHost: String,
    redisPort: Int
) : RedisServiceImpl(redisHost, redisPort) {

    suspend fun getActiveGames() = jedis.keys("game:*").mapNotNull { key -> jedis.get(key) }

    suspend fun getGameById(gameId:Long): String = jedis.get("game:$gameId")

    suspend fun createGame(title: String, ownerId: Int) {
        val gameId = jedis.incr("nextGameId");
        val key = "game:$gameId"
        val game = Game(gameId, title, user1 = ownerId)
        val gameValue = Json.encodeToString(game)

        jedis.set(key, gameValue)
    }

    suspend fun updateGame(game: Game){
        val key = "game:${game.id}"
        val gameValue = Json.encodeToString(game)

        jedis.set(key, gameValue)
    }

    suspend fun deleteGame(gameId: Long){
        val key = "game:$gameId"
        jedis.del(key)
    }

    suspend fun sendGameInvite(invite: GameInviteNotification, user2Id: Int, ttlSeconds: Int = 86400) {
        val requestKey = "game_invite:${user2Id}_${invite.gameOwner}"

        val requestValue = Json.encodeToString(invite)

        withContext(Dispatchers.IO) {
            jedis.setex(requestKey, ttlSeconds.toLong(), requestValue)
            jedis.publish("notifications:$user2Id", requestValue)
        }
    }

    suspend fun getGameInvitesForUser(user2Id: Int): List<String> {
        return withContext(Dispatchers.IO) {
            jedis.keys("game_invite:${user2Id}_*")
                .mapNotNull { key -> jedis.get(key) }
        }
    }

    suspend fun removeFriendRequest(user1Id: Int, user2Id: Int) {
        val requestKey = "game_invite:${user2Id}_$user1Id"

        withContext(Dispatchers.IO) {
            jedis.del(requestKey)
        }
    }

}