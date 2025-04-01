package com.mdgroup.lib.calendar.view

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.mdgroup.lib.calendar.compose.rememberCalendarState
import com.mdgroup.lib.calendar.core.CalendarMonth
import com.mdgroup.lib.calendar.core.DayPosition
import com.mdgroup.lib.calendar.core.daysOfWeek
import com.mdgroup.lib.calendar.core.yearMonth
import com.mdgroup.lib.calendar.data.CalendarColors
import com.mdgroup.lib.calendar.data.CalendarDefaults
import com.mdgroup.lib.calendar.data.DateRange
import com.mdgroup.lib.calendar.utils.ContinuousSelectionHelper.getSelection
import java.time.LocalDate
import java.time.YearMonth

/**
 * For select period/date by months
 *
 * ┌─────────────────────────────────────────┐
 * │               January 2025              │
 * ├─────┬─────┬─────┬─────┬─────┬─────┬─────┤
 * │ MON │ TUE │ WED │ THU │ FRI │ SAT │ SUN │
 * ├─────┼─────┼─────┼─────┼─────┼─────┼─────┤
 * │  1  │  2  │   3 │   4 │   5 │   6 │   7 │
 * │  8  │  9  │  10 │  11 │  12 │  13 │  14 │
 * │ 15  │ 16  │  17 │  18 │  19 │  20 │  21 │
 * │ 22  │ 23  │  24 │  25 │  26 │  27 │  28 │
 * │ 29  │ 30  │  31 │     │     │     │     │
 * └─────┴─────┴─────┴─────┴─────┴─────┴─────┘
 **/
@Composable
fun CalendarMonths(
    modifier: Modifier = Modifier,
    minDate: LocalDate,
    maxDate: LocalDate,
    isPeriod: Boolean,
    blockedDates: List<LocalDate> = emptyList(),
    selected: DateRange = DateRange(),
    shape: Shape = CalendarDefaults.shape,
    colors: CalendarColors = CalendarDefaults.calendarColors(),
    onSelectedChanged: (DateRange) -> Unit,
    monthHeader: (@Composable ColumnScope.(CalendarMonth) -> Unit)? = null,
    monthBody: (@Composable ColumnScope.(CalendarMonth, content: @Composable () -> Unit) -> Unit)? = null,
    monthFooter: (@Composable ColumnScope.(CalendarMonth) -> Unit)? = null,
    monthContainer: (@Composable LazyItemScope.(CalendarMonth, container: @Composable () -> Unit) -> Unit)? = null
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { minDate.yearMonth }
    val endMonth = remember { maxDate.yearMonth }
    val daysOfWeek = remember { daysOfWeek() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )

    var dateSelection by remember { mutableStateOf(selected) }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = colors.containerColor)
    ) {
        VerticalCalendar(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp),
            state = state,
            contentPadding = PaddingValues(bottom = 100.dp),
            dayContent = { value ->
                DayView(
                    day = value,
                    maxDate = maxDate,
                    minDate = minDate,
                    selected = dateSelection,
                    blockedDates = blockedDates,
                    colors = colors,
                    onClick = { day ->
                        if (day.position == DayPosition.MonthDate) {
                            dateSelection = if (isPeriod) {
                                getSelection(clickedDate = day.date, dateSelection = dateSelection)
                            } else {
                                DateRange(day.date)
                            }
                            onSelectedChanged(dateSelection)
                        }
                    }
                )
            },
            monthHeader = monthHeader,
            monthBody = monthBody,
            monthFooter = monthFooter,
            monthContainer = monthContainer
        )
    }
}