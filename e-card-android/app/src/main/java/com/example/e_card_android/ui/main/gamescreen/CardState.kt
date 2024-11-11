package com.example.e_card_android.ui.main.gamescreen

import com.example.e_card_android.data.model.Card

data class CardState(
    val card: Card,
    val offsetX: Float = 0f,
    val offsetY: Float = 0f,
)
