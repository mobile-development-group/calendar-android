package com.mdgroup.sample.ui.components.calendar

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mdgroup.sample.ui.components.calendar.CalendarContent
import com.mdgroup.lib.calendar.data.DateRange
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarBottomSheet(
    showSheet: Boolean,
    isPeriod: Boolean,
    selected: DateRange = DateRange(),
    minDate: LocalDate = CalendarConstants.DEFAULT_CALENDAR_MIN_DATE,
    maxDate: LocalDate = CalendarConstants.DEFAULT_CALENDAR_MAX_DATE,
    blockedDates: List<LocalDate> = emptyList(),
    onDismissRequest: () -> Unit,
    onSuccesses: (DateRange) -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainerLow
) {
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showSheet) {
        ModalBottomSheet(
            modifier = Modifier.padding(top = 48.dp),
            onDismissRequest = onDismissRequest,
            sheetState = sheetState,
            containerColor = containerColor,
            shape = BottomSheetDefaults.ExpandedShape,
            dragHandle = null,
            content = {
                CalendarContent(
                    isPeriod = isPeriod,
                    selected = selected,
                    minDate = minDate,
                    maxDate = maxDate,
                    blockedDates = blockedDates,
                    onCloseClick = {
                        coroutineScope.launch {
                            sheetState.hide()
                            onDismissRequest()
                        }
                    },
                    onSelectedChanged = {
                        onSuccesses(it)
                        coroutineScope.launch {
                            sheetState.hide()
                            onDismissRequest()
                        }
                    }
                )
            }
        )
    }
}