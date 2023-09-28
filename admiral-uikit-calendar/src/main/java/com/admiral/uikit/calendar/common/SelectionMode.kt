package com.admiral.uikit.calendar.common

/**
 * The model describes the mode for user interaction.
 */
sealed class SelectionMode {

    /**
     * Selection mode when user can't select any dates.
     */
    object Disabled : SelectionMode()

    /**
     * Selection mode when user can select only one date.
     */
    object SingleSelection : SelectionMode()

    /**
     * Selection mode when user can select dates between two dates.
     */
    object IntervalSelection : SelectionMode()
}