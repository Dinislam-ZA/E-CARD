package example.com.data.db.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

@Serializable
data class GameStatisticRow(
    val id: Int,
    val bet: ULong = 100uL,
    val rounds: Int = 4,
    val description: String = "Tap to join the game",
    val owner: User
)

object Games : IntIdTable() {
    val bet = ulong("bet").default(100uL)
    val rounds = integer("rounds").default(4)
    val description = varchar("description", 255).default("Tap to join the game")
    val owner = reference("owner", Users)
}

class GameDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<GameDao>(Games)

    var bet by Games.bet
    var rounds by Games.rounds
    var description by Games.description
    var owner by UserDao referencedOn Games.owner
}

@Serializable
data class Game(
    val id: Long,
    val title: String,
    var user1: Int? = null,
    var user2: Int? = null,
    var gameStatus: GameStatus = GameStatus.WAITING_FOR_PLAYERS,
    var user1Ready: Boolean = false,
    var user2Ready: Boolean = false,
    var user1Card: Cards = Cards.NoCard,
    var user2Card: Cards = Cards.NoCard,
    var user1Role: UserRole = UserRole.Emperor,
    var user2Role: UserRole = UserRole.Slave,
    var currentRound: Int = 1,
    var user1Cash: Long = 0,
    var user2Cash: Long = 0,
    var roundBet: Long = 0L,
)

enum class Cards {
    Emperor,
    Citizen,
    Slave,
    NoCard,
}

enum class UserRole {
    Emperor,
    Slave
}

data class Move(
    val card: Cards
)

enum class GameStatus {
    WAITING_FOR_PLAYERS,
    WAITING_FOR_START,
    IN_PROGRESS,
    COMPLETED,
}