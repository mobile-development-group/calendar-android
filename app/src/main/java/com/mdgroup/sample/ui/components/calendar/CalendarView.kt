package com.mdgroup.sample.ui.components.calendar

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mdgroup.sample.ui.components.animations.SlideVerticalAnimationVisibility
import com.mdgroup.sample.ui.theme.CalendarThemePreview
import java.time.LocalDate

@Composable
fun CalendarView(
    isVisible: Boolean,
    isPeriod: Boolean,
    selected: DateRange = DateRange(),
    minDate: LocalDate = CalendarConstants.DEFAULT_CALENDAR_MIN_DATE,
    maxDate: LocalDate = CalendarConstants.DEFAULT_CALENDAR_MAX_DATE,
    onCloseClick: () -> Unit,
    onSelectedChanged: (DateRange) -> Unit
) {

    SlideVerticalAnimationVisibility(
        visible = isVisible
    ) {
        CalendarContent(
            isPeriod = isPeriod,
            selected = selected,
            minDate = minDate,
            maxDate = maxDate,
            onCloseClick = onCloseClick,
            onSelectedChanged = onSelectedChanged
        )
    }
}

@Preview
@Composable
fun CalendarViewPreview() {
    CalendarThemePreview {
        CalendarView(
            isVisible = true,
            isPeriod = true,
            onCloseClick = {},
            onSelectedChanged = {}
        )
    }
}