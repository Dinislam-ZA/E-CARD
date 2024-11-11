package com.example.e_card_android.ui.common.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardBasedCustomRadioGroup(
    modifier: Modifier = Modifier,
    selectedRadio: Int,
    elements: List<String>,
    onSelect: (Int) -> Unit
) {
    Row(
        modifier = modifier
    ) {
        elements.forEachIndexed { index, element ->
            Card(
                shape = RoundedCornerShape(8.dp),
                border = if (index == selectedRadio) BorderStroke(2.dp, Color.Black) else null,
                elevation = CardDefaults.cardElevation(0.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .clickable { onSelect(index) }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = element,
                        color = if (index == selectedRadio) Color.Black else Color.Gray  // Изменение цвета текста для выделенных элементов
                    )
                }
            }
            if (index < elements.size - 1) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

@Preview
@Composable
fun CardBasedCustomRadioGroupPreview() {
    CardBasedCustomRadioGroup(
        modifier = Modifier.height(64.dp),
        selectedRadio = 1,
        elements = listOf("4", "8", "12"),
        onSelect = {}
    )
}
