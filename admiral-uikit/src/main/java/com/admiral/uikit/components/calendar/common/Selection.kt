package com.admiral.uikit.components.calendar.common

import java.time.LocalDate

/**
 * The model describes selected dates.
 */
sealed class Selection {

    /**
     * Selection is empty.
     */
    object EmptySelection : Selection()

    /**
     * Single date selected.
     */
    data class SingleSelection(val date: LocalDate) : Selection()

    /**
     * Interval between two dates selected.
     */
    data class IntervalSelection(
        val startDate: LocalDate,
        val endDate: LocalDate
    ) : Selection()
}