package com.mdgroup.sample.ui.components.picker

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdgroup.sample.ui.theme.CalendarThemePreview

@Composable
fun Picker(
    modifier: Modifier = Modifier,
    items: List<String>,
    selected: Int = 0,
    enabled: Boolean = true,
    withBorder: Boolean = false,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    onClick: (Int) -> Unit
) {

    var selectedIndex by remember(selected) { mutableIntStateOf(selected) }

    Card(
        modifier = modifier.height(48.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = if (withBorder)
            BorderStroke(1.dp, MaterialTheme.colorScheme.secondaryContainer)
        else null
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items.forEachIndexed { index, message ->
                val isSelected = index == selectedIndex

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(all = 4.dp)
                ) {
                    PickerItem(
                        label = message,
                        selected = isSelected,
                        enabled = enabled,
                        background = containerColor,
                        onClick = {
                            if (selectedIndex != index) {
                                selectedIndex = index
                                onClick(index)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PickerItem(
    label: String,
    selected: Boolean,
    enabled: Boolean,
    background: Color,
    onClick: () -> Unit
) {
    val containerColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        background
    }

    val disabledContainerColor = if (selected) {
        MaterialTheme.colorScheme.surfaceContainerLow
    } else {
        background
    }

    val textColor = if (selected) {
        if (enabled) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.secondary
        }
    } else {
        MaterialTheme.colorScheme.secondary
    }

    val textStyle = MaterialTheme.typography.bodyMedium

    Card(
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            disabledContainerColor = disabledContainerColor
        ),
        onClick = onClick,
        enabled = enabled
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                color = textColor,
                style = textStyle,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF2D2D2D)
@Composable
private fun PickerLightPreview() {
    val items = listOf("Today", "Yesterday", "7 days", "30 days", "Other")

    CalendarThemePreview {
        Column {
            Picker(
                modifier = Modifier.height(40.dp),
                items = items,
                selected = 0,
                containerColor = MaterialTheme.colorScheme.surface,
                onClick = {}
            )
            Picker(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(40.dp),
                items = items,
                selected = 0,
                enabled = false,
                containerColor = MaterialTheme.colorScheme.surface,
                onClick = {}
            )
            Picker(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .height(40.dp),
                items = items,
                selected = 0,
                enabled = false,
                onClick = {}
            )
        }
    }
}