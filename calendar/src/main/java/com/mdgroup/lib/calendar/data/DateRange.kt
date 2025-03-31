package com.mdgroup.lib.calendar.data

import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class DateRange(
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null
) {
    val date = startDate

    val daysBetween by lazy(LazyThreadSafetyMode.NONE) {
        if (startDate == null || endDate == null) null else {
            ChronoUnit.DAYS.between(startDate, endDate)
        }
    }
}