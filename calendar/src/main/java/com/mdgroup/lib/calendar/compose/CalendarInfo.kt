package com.mdgroup.lib.calendar.compose

import com.mdgroup.lib.calendar.core.OutDateStyle
import java.time.DayOfWeek

internal data class CalendarInfo(
    val indexCount: Int,
    private val firstDayOfWeek: DayOfWeek? = null,
    private val outDateStyle: OutDateStyle? = null,
)
