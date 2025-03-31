package com.mdgroup.sample.ui.components.calendar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mdgroup.lib.calendar.core.CalendarDay
import com.mdgroup.lib.calendar.core.DayPosition
import com.mdgroup.sample.shared.NoRippleInteractionSource
import java.time.LocalDate

@Composable
internal fun DayView(
    day: CalendarDay,
    minDate: LocalDate,
    maxDate: LocalDate,
    blockedDates: List<LocalDate> = emptyList(),
    selected: DateRange,
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
                onClick = { onClick(day) },
            )
            .backgroundHighlightSquare(
                day = day,
                minDate = minDate,
                maxDate = maxDate,
                selection = selected,
                blockedDates = blockedDates
            ) { textColor = it }
            .padding(vertical = 4.dp)
            .size(40.dp),
        contentAlignment = Alignment.TopCenter,
    ) {
        if (day.date == today && day.date >= minDate && day.date <= maxDate) {
            Canvas(
                modifier = Modifier.size(7.dp),
                onDraw = {
                    drawCircle(color = todayCircleColor)
                }
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