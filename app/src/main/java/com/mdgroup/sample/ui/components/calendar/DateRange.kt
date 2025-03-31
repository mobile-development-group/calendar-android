package com.mdgroup.sample.ui.components.calendar

import java.time.LocalDate
import java.time.format.DateTimeFormatter
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

    override fun toString(): String {
        val format = "dd.MM.yyyy"
        return "${startDate?.format(DateTimeFormatter.ofPattern(format)) ?: ""} - ${endDate?.format(DateTimeFormatter.ofPattern(format)) ?: ""}"
    }
}