package com.mdgroup.sample

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdgroup.lib.calendar.data.DateRange
import com.mdgroup.sample.ui.components.AppButton
import com.mdgroup.sample.ui.components.calendar.CalendarBottomSheet
import com.mdgroup.sample.ui.components.calendar.CalendarDialog
import com.mdgroup.sample.ui.components.calendar.AnimatedCalendarView
import com.mdgroup.sample.ui.theme.CalendarTheme
import com.mdgroup.sample.ui.theme.CalendarThemePreview
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendarTheme {
                MainContent()
            }
        }
    }
}

@Composable
private fun MainContent() {
    val context = LocalContext.current

    var isShowCalendar by remember { mutableStateOf(false) }
    var isShowBottomSheet by remember { mutableStateOf(false) }
    var isShowDialog by remember { mutableStateOf(false) }

    var isPeriod by remember { mutableStateOf(false) }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select period"
                )
                Switch(
                    checked = isPeriod,
                    onCheckedChange = {
                        isPeriod = !isPeriod
                    }
                )
            }

            AppButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Show use animation",
                onClick = { isShowCalendar = !isShowCalendar }
            )

            AppButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Show as BottomSheet",
                onClick = { isShowBottomSheet = !isShowBottomSheet }
            )

            AppButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Show as dialog",
                onClick = { isShowDialog = !isShowDialog }
            )
        }
    }

    AnimatedCalendarView(
        isVisible = isShowCalendar,
        isPeriod = isPeriod,
        onCloseClick = {
            isShowCalendar = false
        },
        onSelectedChanged = { selectedDate ->
            showSelectedDate(context, selectedDate)
            isShowCalendar = false
        }
    )

    CalendarBottomSheet(
        showSheet = isShowBottomSheet,
        isPeriod = isPeriod,
        selected = DateRange(startDate = LocalDate.now()),
        minDate = LocalDate.now().plusDays(1),
        maxDate = LocalDate.now().plusMonths(6),
        onDismissRequest = { isShowBottomSheet = false },
        onSuccesses = { selectedDate ->
            showSelectedDate(context, selectedDate)
        }
    )

    CalendarDialog(
        show = isShowDialog,
        isPeriod = isPeriod,
        selected = DateRange(startDate = LocalDate.now()),
        minDate = LocalDate.now().plusDays(1),
        maxDate = LocalDate.now().plusMonths(6),
        onDismissRequest = { isShowDialog = false },
        onSuccesses = { selectedDate ->
            showSelectedDate(context, selectedDate)
        }
    )
}

private fun showSelectedDate(context: Context, dateRange: DateRange) {
    val date =
        Date.from(dateRange.date?.atStartOfDay(ZoneId.systemDefault())?.toInstant())
    if (date != null) {
        Toast.makeText(context, date.toString(), Toast.LENGTH_SHORT).show()
    }
}

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    CalendarThemePreview {
        MainContent()
    }
}