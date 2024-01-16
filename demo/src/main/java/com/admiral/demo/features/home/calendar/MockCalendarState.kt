package com.admiral.demo.features.home.calendar

import com.admiral.uikit.components.calendar.common.CalendarState
import com.admiral.uikit.components.calendar.common.DisabledDaysInfo
import com.admiral.uikit.components.calendar.common.SelectionMode
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

private val currentYear = LocalDateTime.now().year

@Suppress("MagicNumber")
val MockCalendarState = CalendarState(
    initialYearMonth = YearMonth.of(currentYear, 1),
    markedDays = listOf(
        LocalDate.of(currentYear, 1, 5),
        LocalDate.of(currentYear, 1, 6),
        LocalDate.of(currentYear, 1, 7)
    ),
    disabledDaysInfo = DisabledDaysInfo(
        disabledDays = listOf(
            LocalDate.of(currentYear, 1, 15),
            LocalDate.of(currentYear, 1, 16),
            LocalDate.of(currentYear, 1, 17)
        )
    ),
    selectionMode = SelectionMode.IntervalSelection,
    highlightedDays = listOf(
        LocalDate.of(currentYear, 1, 19),
        LocalDate.of(currentYear, 1, 20),
        LocalDate.of(currentYear, 1, 21)
    )
)