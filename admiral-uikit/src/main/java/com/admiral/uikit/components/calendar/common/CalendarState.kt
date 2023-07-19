package com.admiral.uikit.components.calendar.common

import java.time.LocalDate
import java.time.YearMonth

/**
 * Calendar state model.
 */
data class CalendarState(

    /**
     * Initial [YearMonth] will be shown when you open Calendar view.
     */
    val initialYearMonth: YearMonth = YearMonth.now(),

    /**
     * SelectionMode describes the mode for user interaction.
     */
    val selectionMode: SelectionMode = SelectionMode.IntervalSelection,

    /**
     * Selection describes current selected dates.
     */
    val selection: Selection = Selection.EmptySelection,

    /**
     * Disabled days info describes current disabled dates.
     */
    val disabledDaysInfo: DisabledDaysInfo = DisabledDaysInfo(),

    /**
     * Marked days describes dates with some events.
     */
    val markedDays: List<LocalDate> = emptyList(),

    /**
     * Highlighted days describes days that must be highlighted
     */
    val highlightedDays: List<LocalDate> = emptyList(),
)