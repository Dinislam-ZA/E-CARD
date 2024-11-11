package com.example.e_card_android.ui.main.gamescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.e_card_android.data.model.Card

@Composable
fun CardDeck(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(16.dp),
    cardSize: Dp,
    cards: List<CardState>,
) {

    LazyRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.Bottom
    ) {
        items(cards) { card ->
            GameCard(modifier = Modifier
                .size(cardSize),
                card = card.card
            )
        }
    }
}

@Preview
@Composable
fun CardDeckPreview() {
    val cards = listOf(Card.SLAVE, Card.EMPEROR, Card.CITIZEN, Card.BACK)
    CardDeck(
        modifier = Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        cardSize = 64.dp,
        cards = cards
    )
}