package example.com.data.db.model

import kotlinx.serialization.Serializable

@Serializable
sealed class Notification {
    abstract val notificationType: String
}

@Serializable
data class FriendInviteNotification(
    override val notificationType: String = "FriendRequest",
    val sender: Int,
): Notification()

@Serializable
data class GameInviteNotification(
    override val notificationType: String = "GameInvite",
    val gameOwner: Int,
): Notification()

@Serializable
data class NewsNotification(
    override val notificationType: String = "News",
    val description: String,
): Notification()