package com.mdgroup.sample.ui.components.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import java.time.LocalDate

@Composable
fun CalendarDialog(
    show: Boolean,
    isPeriod: Boolean,
    selected: DateRange = DateRange(),
    minDate: LocalDate = CalendarConstants.DEFAULT_CALENDAR_MIN_DATE,
    maxDate: LocalDate = CalendarConstants.DEFAULT_CALENDAR_MAX_DATE,
    blockedDates: List<LocalDate> = emptyList(),
    onDismissRequest: () -> Unit,
    onSuccesses: (DateRange) -> Unit
) {
    if (show) {
        Dialog(onDismissRequest = onDismissRequest) {
            Box(
                modifier = Modifier.clip(shape = RoundedCornerShape(12.dp))
            ) {
                CalendarContent(
                    modifier = Modifier.height(600.dp),
                    isPeriod = isPeriod,
                    selected = selected,
                    minDate = minDate,
                    maxDate = maxDate,
                    blockedDates = blockedDates,
                    onCloseClick = {
                        onDismissRequest()
                    },
                    onSelectedChanged = {
                        onSuccesses(it)
                        onDismissRequest()
                    }
                )
            }
        }
    }
}