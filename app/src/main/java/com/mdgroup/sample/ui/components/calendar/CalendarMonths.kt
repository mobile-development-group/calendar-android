package com.mdgroup.sample.ui.components.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdgroup.lib.calendar.compose.rememberCalendarState
import com.mdgroup.lib.calendar.core.DayPosition
import com.mdgroup.lib.calendar.core.daysOfWeek
import com.mdgroup.lib.calendar.core.yearMonth
import com.mdgroup.lib.calendar.view.VerticalCalendar
import com.mdgroup.sample.ui.components.calendar.ContinuousSelectionHelper.getSelection
import com.mdgroup.sample.ui.theme.CalendarThemePreview
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

private const val DATETIME_FORMAT = "dd.MM.yyyy"

@Composable
fun CalendarMonths(
    modifier: Modifier = Modifier,
    minDate: LocalDate,
    maxDate: LocalDate,
    selected: DateRange = DateRange(),
    onSelectedChanged: (DateRange) -> Unit
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { minDate.yearMonth }
    val endMonth = remember { maxDate.yearMonth }
    val daysOfWeek = remember { daysOfWeek() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
    )

    var dateSelection by remember { mutableStateOf(selected) }

    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        modifier = modifier.fillMaxWidth(),
    ) {
        Column {
            Row(modifier = Modifier.padding(16.dp)) {
                TextField(
                    modifier = Modifier.weight(1f),
                    label = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            text = "From"
                        )
                    },
                    value = dateSelection.startDate?.format(
                        DateTimeFormatter.ofPattern(DATETIME_FORMAT)
                    ) ?: "",
                    readOnly = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null
                        )
                    },
                    onValueChange = {}
                )

                Spacer(modifier = Modifier.width(16.dp))

                TextField(
                    modifier = Modifier.weight(1f),
                    label = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            text = "To"
                        )
                    },
                    value = dateSelection.endDate?.format(
                        DateTimeFormatter.ofPattern(DATETIME_FORMAT)
                    ) ?: "",
                    readOnly = true,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null
                        )
                    },
                    onValueChange = {}
                )
            }

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
                        onClick = { day ->
                            if (day.position == DayPosition.MonthDate) {
                                dateSelection = getSelection(
                                    clickedDate = day.date,
                                    dateSelection = dateSelection,
                                )
                                onSelectedChanged(dateSelection)
                            }
                        }
                    )
                },
                monthHeader = { month ->
                    MonthHeaderView(month)
                }
            )
        }
    }
}

@Preview
@Composable
private fun CalendarMonthsPreview() {
    CalendarThemePreview {
        CalendarMonths(
            minDate = LocalDate.now().minusMonths(1),
            maxDate = LocalDate.now().plusMonths(1),
            selected = DateRange(
                startDate = LocalDate.now().minusDays(20),
                endDate = LocalDate.now().minusDays(7)
            ),
            onSelectedChanged = {}
        )
    }
}