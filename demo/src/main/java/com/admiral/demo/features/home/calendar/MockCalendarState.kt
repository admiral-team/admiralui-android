package com.admiral.demo.features.home.calendar

import com.admiral.uikit.components.calendar.common.CalendarState
import com.admiral.uikit.components.calendar.common.DisabledDaysInfo
import com.admiral.uikit.components.calendar.common.SelectionMode
import java.time.LocalDate
import java.time.YearMonth

@Suppress("MagicNumber")
val MockCalendarState = CalendarState(
    initialYearMonth = YearMonth.of(2021, 1),
    markedDays = listOf(
        LocalDate.of(2020, 1, 5),
        LocalDate.of(2020, 1, 6),
        LocalDate.of(2020, 1, 7)
    ),
    disabledDaysInfo = DisabledDaysInfo(
        disabledDays = listOf(
            LocalDate.of(2020, 1, 15),
            LocalDate.of(2020, 1, 16),
            LocalDate.of(2020, 1, 17)
        )
    ),
    selectionMode = SelectionMode.IntervalSelection
)