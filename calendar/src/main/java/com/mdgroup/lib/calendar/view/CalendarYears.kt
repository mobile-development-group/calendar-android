package com.mdgroup.lib.calendar.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.mdgroup.lib.calendar.compose.NoRippleInteractionSource
import com.mdgroup.lib.calendar.data.CalendarColors
import com.mdgroup.lib.calendar.data.CalendarDefaults
import com.mdgroup.lib.calendar.data.DateRange
import com.mdgroup.lib.calendar.data.displayText
import com.mdgroup.lib.calendar.utils.ContinuousSelectionHelper
import java.time.LocalDate
import java.time.Month
import java.time.Year

/**
 * For select period by months
 *
 * ┌────────────────────────────────────────────┐
 * │  <                2025                  >  │
 * ├──────────────┬──────────────┬──────────────┤
 * │    January   │   February   │     March    │
 * ├──────────────┼──────────────┼──────────────┤
 * │    April     │     May      │     June     │
 * ├──────────────┼──────────────┼──────────────┤
 * │    July      │   August     │  September   │
 * ├──────────────┼──────────────┼──────────────┤
 * │   October    │  November    │  December    │
 * └──────────────┴──────────────┴──────────────┘
 **/
@Composable
fun CalendarYears(
    modifier: Modifier = Modifier,
    minDate: LocalDate,
    maxDate: LocalDate,
    selection: DateRange = DateRange(),
    shape: Shape = CalendarDefaults.shape,
    colors: CalendarColors = CalendarDefaults.calendarColors(),
    itemBorder: BorderStroke? = null,
    onSelectedChanged: (DateRange) -> Unit
) {
    var currentYear by remember { mutableStateOf(Year.now()) }
    var dateSelection by remember { mutableStateOf(selection) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = colors.containerColor)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .padding(all = 12.dp)
                    .height(32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = { currentYear = currentYear.minusYears(1) }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        tint = colors.chevron,
                        contentDescription = "Previous year"
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = currentYear.value.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.chevron
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = { currentYear = currentYear.plusYears(1) }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        tint = colors.chevron,
                        contentDescription = "Next year"
                    )
                }
            }

            LazyVerticalGrid(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 12.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                content = {
                    for (month in Month.entries) {
                        val yearMonth = currentYear.atMonth(month)
                        val day = LocalDate.of(currentYear.value, month, 1)
                        val startDate = selection.startDate
                        val endDate = selection.endDate

                        val isChecked = if (startDate != null && endDate != null) {
                            day in startDate..endDate
                        } else day == startDate || day == endDate

                        // Elements between the first and last selected elements (for change color)
                        val isIntermediate = if (startDate != null && endDate != null) {
                            startDate.plusMonths(1) <= day && day.plusMonths(1) < endDate
                        } else false

                        item {
                            MonthItem(
                                text = yearMonth.month.displayText(),
                                checked = isChecked,
                                intermediate = isIntermediate,
                                enabled = day in minDate..maxDate,
                                colors = colors,
                                border = itemBorder,
                                onClick = {
                                    dateSelection = if (isChecked) {
                                        DateRange(yearMonth.atDay(1), null)
                                    } else {
                                        ContinuousSelectionHelper.getSelection(
                                            clickedMonth = currentYear.atMonth(month),
                                            dateSelection = dateSelection,
                                        )
                                    }
                                    onSelectedChanged(dateSelection)
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
private fun MonthItem(
    text: String,
    checked: Boolean,
    intermediate: Boolean,
    enabled: Boolean,
    colors: CalendarColors,
    border: BorderStroke? = null,
    onClick: () -> Unit
) {
    val containerColor = if (checked) {
        if (intermediate) {
            colors.itemIntermediateContainerColor
        } else {
            colors.itemSelectedContainerColor
        }
    } else colors.itemContainerColor

    val textColor = if (checked) {
        if (intermediate) {
            colors.intermediateTextColor
        } else {
            colors.selectedTextColor
        }
    } else if (enabled) {
        colors.textColor
    } else {
        colors.disabledTextColor
    }

    Row(
        modifier = Modifier
            .height(40.dp)
            .clickable(
                interactionSource = NoRippleInteractionSource(),
                indication = null,
                onClick = onClick
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            modifier = Modifier.weight(1f),
            enabled = enabled,
            onClick = onClick,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = containerColor,
                disabledContainerColor = containerColor
            ),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
            shape = MaterialTheme.shapes.medium,
            border = border,
            content = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall,
                    color = textColor
                )
            }
        )
    }
}