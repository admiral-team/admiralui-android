package com.example.demo_separated_components

import com.admiral.uikit.calendar.common.CalendarState
import com.admiral.uikit.calendar.common.DisabledDaysInfo
import com.admiral.uikit.calendar.common.SelectionMode
import java.time.LocalDate
import java.time.YearMonth

@Suppress("MagicNumber")
val MockCalendarState = CalendarState(
    initialYearMonth = YearMonth.of(2021, 1),
    markedDays = listOf(
        LocalDate.of(2021, 1, 5),
        LocalDate.of(2021, 1, 6),
        LocalDate.of(2021, 1, 7)
    ),
    disabledDaysInfo = DisabledDaysInfo(
        disabledDays = listOf(
            LocalDate.of(2021, 1, 15),
            LocalDate.of(2021, 1, 16),
            LocalDate.of(2021, 1, 17)
        )
    ),
    selectionMode = SelectionMode.IntervalSelection,
    highlightedDays = listOf(
        LocalDate.of(2021, 1, 19),
        LocalDate.of(2021, 1, 20),
        LocalDate.of(2021, 1, 21)
    )
)