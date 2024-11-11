package com.example.e_card_android.ui.main.gamescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.e_card_android.R
import com.example.e_card_android.data.model.Card
import com.example.e_card_android.ui.theme.EmperorYellow
import com.example.e_card_android.ui.theme.SlaveRed

@Composable
fun GameScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFFFFFFF))
    ) {
        val enemyCards = listOf(
            CardState(Card.BACK),
            CardState(Card.BACK),
            CardState(Card.BACK),
            CardState(Card.BACK),
            CardState(Card.BACK),
        )

        val cards = listOf(
            CardState(Card.EMPEROR),
            CardState(Card.CITIZEN),
            CardState(Card.CITIZEN),
            CardState(Card.CITIZEN),
            CardState(Card.CITIZEN),
        )

        CardDeck(
            modifier = Modifier
                .align(alignment = Alignment.TopEnd)
                .padding(top = 48.dp),
            horizontalArrangement = Arrangement.spacedBy((-32).dp),
            cardSize = 80.dp,
            cards = enemyCards
        )

        CardDeck(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(bottom = 48.dp)
                .height(524.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy((-32).dp, alignment = Alignment.CenterHorizontally),
            cardSize = 100.dp,
            cards = cards
        )

        PlayerBar(
            color = SlaveRed,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.TopCenter)
        )

        PlayerBar(
            color = EmperorYellow,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)
        )

        PositionedImage(
            painter = painterResource(R.drawable.akagi_profile_picture),
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-16).dp, y = 4.dp),
            borderColor = SlaveRed
        )

        PositionedImage(
            painter = painterResource(R.drawable.kaiji_profile_picture),
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomStart)
                .offset(x = 16.dp, y = (-4).dp),
            borderColor = EmperorYellow
        )
    }
}

@Composable
fun PlayerBar(color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(color)
    )
}

@Composable
fun PositionedImage(
    modifier: Modifier = Modifier,
    painter: Painter,
    borderColor: Color
) {
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(CircleShape)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = CircleShape
            )

    )
}


@Preview
@Composable
fun GameScreenPreview() {
    GameScreen()
}