package com.admiral.uikit.calendar.month

import com.admiral.uikit.calendar.day.BaseDayModel
import java.time.YearMonth

internal data class MonthModel(
    val title: String,
    val yearMonth: YearMonth,
    val days: List<BaseDayModel>,
)