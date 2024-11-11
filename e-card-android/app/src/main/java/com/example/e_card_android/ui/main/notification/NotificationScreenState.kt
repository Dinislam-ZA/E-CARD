package com.example.e_card_android.ui.main.notification

import com.example.e_card_android.data.model.Notification

sealed class NotificationScreenState {
    data class Idle(val notifications: Notification)
}