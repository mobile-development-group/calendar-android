package com.mdgroup.sample.ui.components.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mdgroup.lib.calendar.core.CalendarMonth
import com.mdgroup.lib.calendar.data.displayText
import java.time.format.TextStyle
import java.util.Locale

@Composable
internal fun MonthHeaderView(month: CalendarMonth) {
    val daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }

    Column(modifier = Modifier.padding(top = 16.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = month.yearMonth.displayText(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
        Row(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth()
        ) {
            for (dayOfWeek in daysOfWeek) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                        .uppercase(locale = Locale.getDefault()),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}