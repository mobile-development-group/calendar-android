package com.mdgroup.sample.ui.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mdgroup.lib.calendar.core.CalendarDay
import com.mdgroup.lib.calendar.core.DayPosition
import com.mdgroup.sample.ui.components.calendar.ContinuousSelectionHelper.isInDateBetweenSelection
import com.mdgroup.sample.ui.components.calendar.ContinuousSelectionHelper.isOutDateBetweenSelection
import com.mdgroup.sample.ui.theme.CalendarThemePreview
import java.time.LocalDate

private class HalfSizeShape(private val clipStart: Boolean) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val half = size.width / 2f
        val offset = if (layoutDirection == LayoutDirection.Ltr) {
            if (clipStart) Offset(half, 0f) else Offset.Zero
        } else {
            if (clipStart) Offset.Zero else Offset(half, 0f)
        }
        return Outline.Rectangle(Rect(offset, Size(half, size.height)))
    }
}

fun Modifier.backgroundHighlightSquare(
    day: CalendarDay,
    minDate: LocalDate,
    maxDate: LocalDate,
    selection: DateRange,
    blockedDates: List<LocalDate>,
    textColor: (Color) -> Unit,
): Modifier = composed {
    val selectionColor = MaterialTheme.colorScheme.primary
    val continuousSelectionColor = MaterialTheme.colorScheme.surface
    val defaultTextColor = MaterialTheme.colorScheme.onSurface

    val (startDate, endDate) = selection
    val padding = 2.dp

    when (day.position) {
        DayPosition.MonthDate -> {
            when {
                startDate == day.date && endDate == null -> {
                    textColor(MaterialTheme.colorScheme.onPrimary)
                    padding(padding)
                        .background(color = selectionColor, shape = RoundedCornerShape(8.dp))
                }

                day.date == startDate -> {
                    textColor(MaterialTheme.colorScheme.onPrimary)
                    padding(all = padding)
                        .background(color = selectionColor, shape = RoundedCornerShape(8.dp))
                }

                startDate != null && endDate != null && (day.date > startDate && day.date < endDate) -> {
                    textColor(selectionColor)
                    padding(all = padding)
                        .background(
                            color = continuousSelectionColor,
                            shape = RoundedCornerShape(8.dp)
                        )
                }

                day.date == endDate -> {
                    textColor(MaterialTheme.colorScheme.onPrimary)
                    padding(all = padding)
                        .background(
                            color = continuousSelectionColor,
                            shape = HalfSizeShape(clipStart = false),
                        )
                        .background(color = selectionColor, shape = RoundedCornerShape(8.dp))
                }

                day.date == LocalDate.now() -> {
                    textColor(selectionColor)
                    padding(all = padding)
                }

                day.date > maxDate || day.date < minDate || blockedDates.contains(day.date) -> {
                    textColor(MaterialTheme.colorScheme.surfaceContainerLowest)
                    padding(all = padding)
                }

                else -> {
                    textColor(defaultTextColor)
                    this
                }
            }
        }

        DayPosition.InDate -> {
            textColor(MaterialTheme.colorScheme.surfaceContainerLowest)
            if (startDate != null && endDate != null &&
                isInDateBetweenSelection(day.date, startDate, endDate)
            ) {
                padding(vertical = padding)
            } else this
        }

        DayPosition.OutDate -> {
            textColor(MaterialTheme.colorScheme.surfaceContainerLowest)
            if (startDate != null && endDate != null &&
                isOutDateBetweenSelection(day.date, startDate, endDate)
            ) {
                padding(vertical = padding)
            } else this
        }
    }
}

@Preview
@Composable
private fun CalendarContentPreview() {
    CalendarThemePreview {
        CalendarContent(
            isPeriod = false,
            selected = DateRange(
                startDate = LocalDate.now().minusDays(20),
                endDate = LocalDate.now().minusDays(7)
            ),
            onCloseClick = {},
            onSelectedChanged = {}
        )
    }
}