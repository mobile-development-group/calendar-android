package com.mdgroup.lib.calendar.view

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mdgroup.lib.calendar.compose.NoRippleInteractionSource
import com.mdgroup.lib.calendar.core.CalendarDay
import com.mdgroup.lib.calendar.core.DayPosition
import com.mdgroup.lib.calendar.data.CalendarColors
import com.mdgroup.lib.calendar.data.DateRange
import com.mdgroup.lib.calendar.utils.ContinuousSelectionHelper.isInDateBetweenSelection
import com.mdgroup.lib.calendar.utils.ContinuousSelectionHelper.isOutDateBetweenSelection
import java.time.LocalDate

@Composable
fun DayView(
    day: CalendarDay,
    minDate: LocalDate,
    maxDate: LocalDate,
    blockedDates: List<LocalDate> = emptyList(),
    selected: DateRange,
    colors: CalendarColors,
    onClick: (CalendarDay) -> Unit,
) {
    var textColor = Color.Transparent
    val todayCircleColor = MaterialTheme.colorScheme.primary
    val today = remember { LocalDate.now() }

    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .clickable(
                interactionSource = NoRippleInteractionSource(),
                indication = null,
                enabled = day.position == DayPosition.MonthDate &&
                        day.date >= minDate &&
                        day.date <= maxDate &&
                        !blockedDates.contains(day.date),
                onClick = { onClick(day) }
            )
            .backgroundHighlightSquare(
                day = day,
                minDate = minDate,
                maxDate = maxDate,
                selection = selected,
                blockedDates = blockedDates,
                colors = colors
            ) { textColor = it }
            .padding(vertical = 4.dp)
            .size(40.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        if (day.date == today && !(day.position == DayPosition.InDate || day.position == DayPosition.OutDate)) {
            Canvas(
                modifier = Modifier.size(7.dp),
                onDraw = { drawCircle(color = todayCircleColor) }
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

/**
 * Only for local DayView. For custom DayView use [com.mdgroup.lib.calendar.compose.backgroundHighlightSquare]
 */
@SuppressLint("UnnecessaryComposedModifier")
private fun Modifier.backgroundHighlightSquare(
    day: CalendarDay,
    minDate: LocalDate,
    maxDate: LocalDate,
    selection: DateRange,
    blockedDates: List<LocalDate>,
    colors: CalendarColors,
    textColor: (Color) -> Unit,
): Modifier = composed {
    val (startDate, endDate) = selection
    val padding = 2.dp

    when (day.position) {
        DayPosition.MonthDate -> when {
            startDate == day.date && endDate == null -> {
                textColor(colors.selectedTextColor)
                padding(padding)
                    .background(
                        color = colors.itemSelectedContainerColor,
                        shape = RoundedCornerShape(8.dp)
                    )
            }

            day.date == startDate -> {
                textColor(colors.selectedTextColor)
                padding(all = padding)
                    .background(
                        color = colors.itemSelectedContainerColor,
                        shape = RoundedCornerShape(8.dp)
                    )
            }

            startDate != null && endDate != null && (day.date > startDate && day.date < endDate) -> {
                textColor(colors.intermediateTextColor)
                padding(all = padding)
                    .background(
                        color = colors.itemIntermediateContainerColor,
                        shape = RoundedCornerShape(8.dp)
                    )
            }

            day.date == endDate -> {
                textColor(colors.selectedTextColor)
                padding(all = padding)
                    .background(
                        color = colors.itemIntermediateContainerColor,
                        shape = HalfSizeShape(clipStart = false),
                    )
                    .background(
                        color = colors.itemSelectedContainerColor,
                        shape = RoundedCornerShape(8.dp)
                    )
            }

            day.date == LocalDate.now() -> {
                textColor(colors.chevron)
                padding(all = padding)
            }

            day.date > maxDate || day.date < minDate || blockedDates.contains(day.date) -> {
                textColor(colors.disabledTextColor)
                padding(all = padding)
            }

            else -> {
                textColor(colors.textColor)
                this
            }
        }

        DayPosition.InDate -> {
            textColor(colors.disabledTextColor)
            if (startDate != null && endDate != null &&
                isInDateBetweenSelection(day.date, startDate, endDate)
            ) {
                padding(vertical = padding)
            } else this
        }

        DayPosition.OutDate -> {
            textColor(colors.disabledTextColor)
            if (startDate != null && endDate != null &&
                isOutDateBetweenSelection(day.date, startDate, endDate)
            ) {
                padding(vertical = padding)
            } else this
        }
    }
}

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