package com.mdgroup.sample.ui.components.calendar

import com.mdgroup.lib.calendar.core.atStartOfMonth
import com.mdgroup.lib.calendar.core.nextMonth
import com.mdgroup.lib.calendar.core.previousMonth
import com.mdgroup.lib.calendar.core.yearMonth
import java.time.LocalDate
import java.time.YearMonth

object ContinuousSelectionHelper {

    fun getSelection(
        clickedMonth: YearMonth,
        dateSelection: DateRange,
    ): DateRange {
        val (selectionStartDate, selectionEndDate) = dateSelection
        val firstDay = clickedMonth.atDay(1)
        val endDay = clickedMonth.atDay(clickedMonth.lengthOfMonth())

        return if (selectionStartDate != null) {
            if (firstDay < selectionStartDate || selectionEndDate != null) {
                if (selectionEndDate != null && selectionEndDate < endDay) {
                    DateRange(startDate = selectionStartDate, endDate = endDay)
                } else {
                    DateRange(startDate = firstDay, endDate = null)
                }
            } else if (firstDay != selectionStartDate) {
                DateRange(startDate = selectionStartDate, endDate = endDay)
            } else {
                DateRange(startDate = firstDay, endDate = null)
            }
        } else {
            DateRange(startDate = firstDay, endDate = endDay)
        }
    }

    fun getSelection(
        clickedDate: LocalDate,
        dateSelection: DateRange,
    ): DateRange {
        val (selectionStartDate, selectionEndDate) = dateSelection
        return if (selectionStartDate != null) {
            if (clickedDate < selectionStartDate || selectionEndDate != null) {
                DateRange(startDate = clickedDate, endDate = null)
            } else if (clickedDate != selectionStartDate) {
                DateRange(startDate = selectionStartDate, endDate = clickedDate)
            } else {
                DateRange(startDate = clickedDate, endDate = null)
            }
        } else {
            DateRange(startDate = clickedDate, endDate = null)
        }
    }

    fun isInDateBetweenSelection(
        inDate: LocalDate,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Boolean {
        if (startDate.yearMonth == endDate.yearMonth) return false
        if (inDate.yearMonth == startDate.yearMonth) return true
        val firstDateInThisMonth = inDate.yearMonth.nextMonth.atStartOfMonth()
        return firstDateInThisMonth in startDate..endDate && startDate != firstDateInThisMonth
    }

    fun isOutDateBetweenSelection(
        outDate: LocalDate,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Boolean {
        if (startDate.yearMonth == endDate.yearMonth) return false
        if (outDate.yearMonth == endDate.yearMonth) return true
        val lastDateInThisMonth = outDate.yearMonth.previousMonth.atEndOfMonth()
        return lastDateInThisMonth in startDate..endDate && endDate != lastDateInThisMonth
    }
}
