package com.admiral.uikit.calendar.day

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
        open val isMarked: Boolean,

        /**
         * It means that day has special meaning
         */
        open val isHighlighted: Boolean
    ) : BaseDayModel() {

        /**
         * Normal day.
         */
        data class Normal(
            override val localDate: LocalDate,
            override val isMarked: Boolean,
            override val isHighlighted: Boolean,
        ) : DayModel(
            localDate = localDate,
            isMarked = isMarked,
            isHighlighted = isHighlighted,
        )

        /**
         * Disabled day.
         */
        data class Disabled(
            override val localDate: LocalDate,
            override val isMarked: Boolean,
            override val isHighlighted: Boolean,
        ) : DayModel(
            localDate = localDate,
            isMarked = isMarked,
            isHighlighted = isHighlighted,
        )

        /**
         * Current day in calendar is a current day.
         */
        data class Current(
            override val localDate: LocalDate,
            override val isMarked: Boolean,
            override val isHighlighted: Boolean,
        ) : DayModel(
            localDate = localDate,
            isMarked = isMarked,
            isHighlighted = isHighlighted,
        )

        /**
         * Selected day.
         */
        data class Selected(
            override val localDate: LocalDate,
            override val isMarked: Boolean,
            override val isHighlighted: Boolean,
        ) : DayModel(
            localDate = localDate,
            isMarked = isMarked,
            isHighlighted = isHighlighted,
        )

        /**
         * Selected day but selected brightly
         * (for the first and the last dates in the selected interval).
         */
        data class SelectedBright(
            override val localDate: LocalDate,
            override val isMarked: Boolean,
            override val isHighlighted: Boolean,
        ) : DayModel(
            localDate = localDate,
            isMarked = isMarked,
            isHighlighted = isHighlighted,
        )

        /**
         * Current day in calendar is a highlighted day.
         */
        data class Highlighted(
            override val localDate: LocalDate,
            override val isMarked: Boolean,
            override val isHighlighted: Boolean,
        ) : DayModel(
            localDate = localDate,
            isMarked = isMarked,
            isHighlighted = isHighlighted,
        )
    }
}