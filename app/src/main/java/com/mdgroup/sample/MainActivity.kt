package com.mdgroup.sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdgroup.sample.ui.components.AppButton
import com.mdgroup.sample.ui.components.calendar.CalendarBottomSheet
import com.mdgroup.sample.ui.components.calendar.CalendarDialog
import com.mdgroup.sample.ui.components.calendar.CalendarView
import com.mdgroup.sample.ui.components.calendar.DateRange
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

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            AppButton(
                modifier = Modifier
                    .padding(all = 16.dp),
                text = "Show use animation",
                onClick = { isShowCalendar = !isShowCalendar }
            )

            AppButton(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = "Show as BottomSheet",
                onClick = { isShowBottomSheet = !isShowBottomSheet }
            )

            AppButton(
                modifier = Modifier
                    .padding(all = 16.dp),
                text = "Show as dialog",
                onClick = { isShowDialog = !isShowDialog }
            )
        }
    }

    CalendarView(
        isVisible = isShowCalendar,
        isPeriod = true,
        onCloseClick = {
            isShowCalendar = false
        },
        onSelectedChanged = {
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            isShowCalendar = false
        }
    )

    CalendarBottomSheet(
        showSheet = isShowBottomSheet,
        isPeriod = false,
        selected = DateRange(startDate = LocalDate.now()),
        minDate = LocalDate.now().plusDays(1),
        maxDate = LocalDate.now().plusMonths(6),
        onDismissRequest = { isShowBottomSheet = false },
        onSuccesses = { selectedDate ->
            val date =
                Date.from(selectedDate.date?.atStartOfDay(ZoneId.systemDefault())?.toInstant())
            if (date != null) {
                Toast.makeText(context, date.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    )

    CalendarDialog(
        show = isShowDialog,
        isPeriod = false,
        selected = DateRange(startDate = LocalDate.now()),
        minDate = LocalDate.now().plusDays(1),
        maxDate = LocalDate.now().plusMonths(6),
        onDismissRequest = { isShowDialog = false },
        onSuccesses = { selectedDate ->
            val date =
                Date.from(selectedDate.date?.atStartOfDay(ZoneId.systemDefault())?.toInstant())
            if (date != null) {
                Toast.makeText(context, date.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    CalendarThemePreview {
        MainContent()
    }
}