package example.com.data.db.model.dto

import example.com.data.db.model.GameStatisticRow
import example.com.data.db.model.UserVO
import kotlinx.serialization.Serializable

@Serializable
data class AddFriendRequest(
    val user1: Int,
    val user2: Int
)

@Serializable
data class AcceptFriendRequest(
    val user1: Int,
    val user2: Int
)

@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val token: String,
    val user: UserVO
)

@Serializable
data class RegistrationRequest(
    val username: String,
    val password: String
)

@Serializable
sealed class NotificationDto {
    abstract val notificationType: String
    abstract val time: String
    abstract val title: String
}

@Serializable
data class FriendInviteNotificationDto(
    override val notificationType: String,
    override val title: String,
    val sender: UserVO,
    override val time: String = ""
): NotificationDto()

@Serializable
data class GameInviteNotificationDto(
    override val notificationType: String,
    override val title: String,
    val game: GameStatisticRow,
    override val time: String = ""
): NotificationDto()

@Serializable
data class NewsInviteNotificationDto(
    override val notificationType: String,
    override val title: String,
    val description: String,
    override val time: String = ""
): NotificationDto()

