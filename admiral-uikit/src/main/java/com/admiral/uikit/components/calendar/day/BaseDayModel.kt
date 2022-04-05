package com.admiral.uikit.components.calendar.day

import java.time.LocalDate

sealed class BaseDayModel {
    object Unknown : BaseDayModel()

    /**
     * Calendar day model
     */
    sealed class DayModel(
        /**
         * Local date of this day
         */
        open val localDate: LocalDate,
        /**
         * It means that day has special point mark
         */
        open val isMarked: Boolean
    ) : BaseDayModel() {

        /**
         * Normal day.
         */
        data class Normal(
            override val localDate: LocalDate,
            override val isMarked: Boolean
        ) : DayModel(
            localDate = localDate,
            isMarked = isMarked
        )

        /**
         * Disabled day.
         */
        data class Disabled(
            override val localDate: LocalDate,
            override val isMarked: Boolean
        ) : DayModel(
            localDate = localDate,
            isMarked = isMarked
        )

        /**
         * Current day in calendar is highlighted day
         */
        data class Highlighted(
            override val localDate: LocalDate,
            override val isMarked: Boolean
        ) : DayModel(
            localDate = localDate,
            isMarked = isMarked
        )

        /**
         * Selected day.
         */
        data class Selected(
            override val localDate: LocalDate,
            override val isMarked: Boolean
        ) : DayModel(
            localDate = localDate,
            isMarked = isMarked
        )

        /**
         * Selected day but selected brightly
         * (for the first and the last dates in the selected interval).
         */
        data class SelectedBright(
            override val localDate: LocalDate,
            override val isMarked: Boolean
        ) : DayModel(
            localDate = localDate,
            isMarked = isMarked
        )
    }
}