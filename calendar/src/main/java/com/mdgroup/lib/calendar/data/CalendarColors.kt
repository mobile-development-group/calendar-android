package com.mdgroup.lib.calendar.data

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
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
@Immutable
data class CalendarColors(
    val containerColor: Color,
    val chevron: Color,
    val itemContainerColor: Color,
    val itemSelectedContainerColor: Color,
    val itemIntermediateContainerColor: Color,
    val itemDisabledContainerColor: Color,
    val selectedTextColor: Color,
    val textColor: Color,
    val intermediateTextColor: Color,
    val disabledTextColor: Color
)