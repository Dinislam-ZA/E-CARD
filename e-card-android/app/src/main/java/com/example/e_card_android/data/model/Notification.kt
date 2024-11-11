package com.example.e_card_android.data.model

import android.text.format.DateFormat

sealed class Notification(open val title: String, open val time: DateFormat) {
    data class GameInviteNotification(
        override val title: String,
        override val time: DateFormat,
        val game: Game
    ) : Notification(title, time)

    data class FriendInviteNotification(
        override val title: String,
        override val time: DateFormat,
        val sender: User
    ) : Notification(title, time)

    data class NewsNotification(
        override val title: String,
        override val time: DateFormat,
        val description: String
    ) : Notification(title, time)
}