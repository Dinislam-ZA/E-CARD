package example.com.services

import example.com.data.db.model.*
import example.com.data.db.repositories.GameRepository
import example.com.data.redis.GamesRedisManager
import kotlinx.serialization.json.Json

interface GameService {
    suspend fun createGame(title: String, ownerId: Int)
    suspend fun getActiveGames(): List<Game>
    suspend fun getWaitingGames(): List<Game>
    suspend fun getInvitesForUser(userId: Int): List<GameInviteNotification>
    suspend fun joinToGame(gameId: Long, userId: Int)
    suspend fun leaveGame(gameId: Long, userId: Int)
    suspend fun startGame(gameId: Long)
    suspend fun makeMove(gameId: Long, userId: Int, move: Move)
    suspend fun processMove(gameId: Long)
    suspend fun endGame(gameId: Long, onGameEnded: () -> Unit)
}

class GameServiceImpl(
    private val redisService: GamesRedisManager,
    val repository: GameRepository
) : GameService {
    override suspend fun createGame(title: String, ownerId: Int) = redisService.createGame(title, ownerId)

    override suspend fun getActiveGames(): List<Game> =
        redisService.getActiveGames().map { Json.decodeFromString<Game>(it) }

    override suspend fun getWaitingGames(): List<Game> =
        redisService.getActiveGames().map { Json.decodeFromString<Game>(it) }
            .filter { it.gameStatus == GameStatus.WAITING_FOR_PLAYERS }

    override suspend fun getInvitesForUser(userId: Int): List<GameInviteNotification> =
        redisService.getGameInvitesForUser(userId).map { Json.decodeFromString<GameInviteNotification>(it) }

    override suspend fun joinToGame(gameId: Long, userId: Int) {
        val game = Json.decodeFromString<Game>(redisService.getGameById(gameId))
        if (game.user1 == null)
            game.user1 = userId
        else
            game.user2 = userId
        game.gameStatus = GameStatus.WAITING_FOR_START
        redisService.updateGame(game)
    }

    override suspend fun leaveGame(gameId: Long, userId: Int) {
        val game = Json.decodeFromString<Game>(redisService.getGameById(gameId))
        if (game.user1 == userId)
            game.user1 = null
        else if (game.user2 == userId)
            game.user2 = null
        if (game.user1 == null && game.user2 == null)
            redisService.deleteGame(gameId)
        else
            game.gameStatus = GameStatus.WAITING_FOR_PLAYERS
        redisService.updateGame(game)
    }

    override suspend fun startGame(gameId: Long) {
        val game = Json.decodeFromString<Game>(redisService.getGameById(gameId))
        game.gameStatus = GameStatus.IN_PROGRESS
        redisService.updateGame(game)
    }

    override suspend fun makeMove(gameId: Long, userId: Int, move: Move) {
        val game = Json.decodeFromString<Game>(redisService.getGameById(gameId))
        if (game.user1 == userId)
            game.user1Card = move.card
        else if (game.user2 == userId)
            game.user2Card = move.card

        redisService.updateGame(game)
    }

    override suspend fun processMove(gameId: Long) {
        val game = Json.decodeFromString<Game>(redisService.getGameById(gameId))

        val bet = game.roundBet

        if (game.user1Card == Cards.NoCard) game.user1Cash -= bet
        if (game.user2Card == Cards.NoCard) game.user2Cash -= bet

        when (game.user1Card to game.user2Card) {
            Cards.Emperor to Cards.Citizen -> game.user2Cash -= bet
            Cards.Emperor to Cards.Slave -> game.user1Cash -= bet * 4
            Cards.Citizen to Cards.Emperor -> game.user1Cash -= bet
            Cards.Citizen to Cards.Slave -> game.user2Cash -= bet
            Cards.Slave to Cards.Emperor -> game.user2Cash -= bet * 4
            Cards.Slave to Cards.Citizen -> game.user1Cash -= bet
        }

        resetRound(game)
        redisService.updateGame(game)
    }

    override suspend fun endGame(gameId: Long, onGameEnded: () -> Unit) {
        val game = Json.decodeFromString<Game>(redisService.getGameById(gameId))
        game.gameStatus = GameStatus.WAITING_FOR_START
        redisService.updateGame(game)
        // TODO: где нибудь здесь игра будет сохранятся в историю пользователя в GameRepository, но это потом
        onGameEnded() // Вот здесь будет вычет денег у пользователей в базе данных
    }

    private fun resetRound(game: Game) = game.apply {
        currentRound++
        user1Card = Cards.NoCard
        user2Card = Cards.NoCard
    }
}