package com.mdgroup.lib.calendar.data

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

object CalendarDefaults {

    /** Default shape for a card. */
    val shape: Shape
        @Composable get() = RoundedCornerShape(16.dp)

    /**
     * Creates a [CalendarColors] that represents colors used calendars.
     *
     * @param containerColor background color.
     * @param chevron next and previous month chevrons.
     * @param itemContainerColor item background color.
     * @param itemSelectedContainerColor selected item background color.
     * @param itemIntermediateContainerColor intermediate items background color.
     * @param itemDisabledContainerColor disabled item background color.
     * @param selectedTextColor selected text color.
     * @param textColor default text color.
     * @param intermediateTextColor intermediate text color.
     * @param disabledTextColor disabled text color.
     */
    @Composable
    fun calendarColors(
        containerColor: Color = Color.Unspecified,
        chevron: Color = MaterialTheme.colorScheme.primary,
        itemContainerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
        itemSelectedContainerColor: Color = MaterialTheme.colorScheme.primary,
        itemIntermediateContainerColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
        itemDisabledContainerColor: Color = MaterialTheme.colorScheme.surfaceContainerLow,
        selectedTextColor: Color = MaterialTheme.colorScheme.onPrimary,
        textColor: Color = MaterialTheme.colorScheme.onSurface,
        intermediateTextColor: Color = MaterialTheme.colorScheme.onSurface,
        disabledTextColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
    ): CalendarColors = CalendarColors(
        containerColor = containerColor,
        chevron = chevron,
        itemContainerColor = itemContainerColor,
        itemSelectedContainerColor = itemSelectedContainerColor,
        itemIntermediateContainerColor = itemIntermediateContainerColor,
        itemDisabledContainerColor = itemDisabledContainerColor,
        selectedTextColor = selectedTextColor,
        textColor = textColor,
        intermediateTextColor = intermediateTextColor,
        disabledTextColor = disabledTextColor
    )

}