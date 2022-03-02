package com.admiral.uikit.components.calendar.month

import com.admiral.uikit.components.calendar.day.BaseDayModel
import java.time.YearMonth

internal data class MonthModel(
    val title: String,
    val yearMonth: YearMonth,
    val days: List<BaseDayModel>,
)