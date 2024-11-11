package com.example.e_card_android.ui.main.gamescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import com.example.e_card_android.R
import com.example.e_card_android.data.model.Card
import kotlin.math.roundToInt

@Composable
fun GameCard(
    modifier: Modifier = Modifier,
    card: Card
){
    var offsetX by remember { mutableFloatStateOf(0f) }
    var offsetY by remember { mutableFloatStateOf(0f) }

    Image(
        modifier = modifier
            .wrapContentSize()
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        contentScale = ContentScale.FillBounds,
        painter = painterResource(
            when(card){
                Card.EMPEROR -> R.drawable.e_card_emperor
                Card.CITIZEN -> R.drawable.e_card_citizen
                Card.SLAVE -> R.drawable.e_card_slave
                Card.BACK -> R.drawable.e_card_back
            }
        ),
        contentDescription = stringResource(R.string.game_card)
    )
}

@Preview
@Composable
fun GameCardPreview(){
    GameCard(card = Card.SLAVE)
}