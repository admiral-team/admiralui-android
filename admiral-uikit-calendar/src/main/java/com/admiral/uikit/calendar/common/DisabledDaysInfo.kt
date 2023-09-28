package com.admiral.uikit.calendar.common

import java.time.LocalDate

/**
 * The model describes current disabled dates.
 */
data class DisabledDaysInfo(
    /**
     * List of disabled dates.
     */
    val disabledDays: List<LocalDate> = emptyList(),

    /**
     * Determines if dates after the current day are available.
     */
    var isEnabledAfterCurrentDay: Boolean = true,

    /**
     * Determines if dates before the current day are available.
     */
    var isEnabledBeforeCurrentDay: Boolean = true
)