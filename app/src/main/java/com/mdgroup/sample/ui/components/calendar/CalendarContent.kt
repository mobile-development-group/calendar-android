package com.mdgroup.sample.ui.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdgroup.lib.calendar.compose.rememberCalendarState
import com.mdgroup.lib.calendar.core.DayPosition
import com.mdgroup.lib.calendar.core.daysOfWeek
import com.mdgroup.lib.calendar.core.yearMonth
import com.mdgroup.lib.calendar.view.VerticalCalendar
import com.mdgroup.sample.ui.components.AppButton
import com.mdgroup.sample.ui.components.calendar.CalendarConstants.DEFAULT_CALENDAR_MAX_DATE
import com.mdgroup.sample.ui.components.calendar.CalendarConstants.DEFAULT_CALENDAR_MIN_DATE
import com.mdgroup.sample.ui.components.picker.Picker
import com.mdgroup.sample.ui.theme.CalendarThemePreview
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

@Composable
internal fun CalendarContent(
    modifier: Modifier = Modifier,
    isPeriod: Boolean,
    selected: DateRange,
    minDate: LocalDate = DEFAULT_CALENDAR_MIN_DATE,
    maxDate: LocalDate = DEFAULT_CALENDAR_MAX_DATE,
    blockedDates: List<LocalDate> = emptyList(),
    onCloseClick: () -> Unit,
    onSelectedChanged: (DateRange) -> Unit
) {
    Column(
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }

        val minDay = remember { minDate }
        val maxDay = remember { maxDate }

        if (isPeriod) {
            CalendarPeriodContent(
                selected = selected,
                minDate = minDay,
                maxDate = maxDay,
                onSelectedChanged = onSelectedChanged
            )
        } else {
            CalendarDayContent(
                selected = selected,
                minDate = minDay,
                maxDate = maxDay,
                blockedDates = blockedDates,
                onSelectedChanged = onSelectedChanged
            )
        }
    }
}

@Composable
private fun CalendarPeriodContent(
    selected: DateRange = DateRange(),
    minDate: LocalDate,
    maxDate: LocalDate,
    onSelectedChanged: (DateRange) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 2 })

    var daysDateSelection by remember { mutableStateOf(selected) }
    var monthsDateSelection by remember { mutableStateOf(selected) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(bottom = 88.dp)) {
            Picker(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .fillMaxWidth(),
                items = listOf("Month", "Date"),
                selected = pagerState.currentPage,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                }
            )

            HorizontalPager(
                state = pagerState
            ) { index ->
                when (index) {
                    0 -> CalendarYears(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        selection = monthsDateSelection,
                        minDate = minDate,
                        maxDate = maxDate,
                        onSelectedChanged = {
                            monthsDateSelection = it
                        }
                    )

                    1 -> CalendarMonths(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        selected = daysDateSelection,
                        minDate = minDate,
                        maxDate = maxDate,
                        onSelectedChanged = {
                            daysDateSelection = it
                        }
                    )
                }
            }
        }

        AppButton(
            modifier = Modifier
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.BottomCenter),
            text = "Select",
            onClick = {
                when (pagerState.currentPage) {
                    0 -> onSelectedChanged(monthsDateSelection)
                    1 -> onSelectedChanged(daysDateSelection)
                }
            }
        )
    }
}

@Composable
private fun CalendarDayContent(
    selected: DateRange,
    minDate: LocalDate,
    maxDate: LocalDate,
    blockedDates: List<LocalDate> = emptyList(),
    onSelectedChanged: (DateRange) -> Unit
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var dayDateSelection by remember { mutableStateOf(selected) }
    val daysOfWeek = remember { daysOfWeek() }

    val state = rememberCalendarState(
        startMonth = minDate.yearMonth,
        endMonth = maxDate.yearMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Card(
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 76.dp)
                .fillMaxWidth()
        ) {
            VerticalCalendar(
                modifier = Modifier.padding(horizontal = 16.dp),
                state = state,
                contentPadding = PaddingValues(bottom = 100.dp),
                dayContent = { value ->
                    DayView(
                        day = value,
                        maxDate = maxDate,
                        minDate = minDate,
                        blockedDates = blockedDates,
                        selected = dayDateSelection,
                    ) { day ->
                        if (day.position == DayPosition.MonthDate) {
                            dayDateSelection = DateRange(day.date)
                        }
                    }
                },
                monthHeader = { month ->
                    MonthHeaderView(month)
                }
            )
        }

        AppButton(
            modifier = Modifier
                .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                .align(Alignment.BottomCenter),
            text = "Select",
            onClick = { onSelectedChanged(dayDateSelection) }
        )
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