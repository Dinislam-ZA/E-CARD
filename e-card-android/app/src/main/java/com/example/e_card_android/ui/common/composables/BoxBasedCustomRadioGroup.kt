package com.example.e_card_android.ui.common.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BoxBasedCustomRadioGroup(
    modifier: Modifier = Modifier,
    selectedRadio: Int,
    elements: List<String>,
    onSelect: (Int) -> Unit
) {
    Row(
        modifier = modifier
    ) {
        elements.forEachIndexed { index, element ->
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .border(
                        border = BorderStroke(
                            2.dp,
                            if (index == selectedRadio) Color.Black else Color.Gray
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .weight(1f)
                    .clickable { onSelect(index) }
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = element,
                    color = if (index == selectedRadio) Color.Black else Color.Gray,
                )
            }
            if (index < elements.size - 1) {
                Spacer(
                    modifier = Modifier.width(4.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun BoxBasedCustomRadioGroupPreview() {
    BoxBasedCustomRadioGroup(
        modifier = Modifier.height(32.dp),
        selectedRadio = 0,
        elements = listOf("4", "8", "12"),
        onSelect = {}
    )
}