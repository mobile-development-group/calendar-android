package com.mdgroup.lib.calendar.data

import java.time.DayOfWeek
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

// E.g DayOfWeek.SATURDAY.daysUntil(DayOfWeek.TUESDAY) = 3
fun DayOfWeek.daysUntil(other: DayOfWeek) = (7 + (other.value - value)) % 7

fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.month.displayText(short = short)} ${this.year}"
}

fun Month.displayText(short: Boolean = false): String {
    return getDisplayName(
        if (short) TextStyle.SHORT else TextStyle.FULL_STANDALONE,
        Locale.getDefault()
    )
        // It's strange but for some reason sometimes December comes back as an empty line after getDisplayName
        // it is fixed it
        .ifEmpty { toString().lowercase() }
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}