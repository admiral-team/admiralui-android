package com.admiral.uikit.compose.calendar

data class CalendarDay(
    var year: Int?,
    var month: Int?,
    var day: Int?,
    var isChosen: Boolean? = false
) {
    fun isAfter(day: CalendarDay?): Boolean {
        if (day == null) {
            return true
        }
        return if (this.year ?: 0 > day.year ?: 0) {
            true
        } else if (this.year ?: 0 < day.year ?: 0) {
            false
        } else if (this.year == day.year) {
            if (this.month ?: 0 > day.month ?: 0) {
                true
            } else if (this.month ?: 0 < day.month ?: 0) {
                false
            } else if (this.month == day.month) {
                if (this.day ?: 0 > day.day ?: 0) {
                    true
                } else if (this.day ?: 0 < day.day ?: 0) {
                    false
                } else if (this.day == day.day) {
                    false
                } else false
            } else false
        } else false
    }

    fun isBefore(day: CalendarDay?): Boolean {
        if (day == null) {
            return true
        }
        return if (this.year ?: 0 > day.year ?: 0) {
            false
        } else if (this.year ?: 0 < day.year ?: 0) {
            true
        } else if (this.year == day.year) {
            if (this.month ?: 0 > day.month ?: 0) {
                false
            } else if (this.month ?: 0 < day.month ?: 0) {
                true
            } else if (this.month == day.month) {
                if (this.day ?: 0 > day.day ?: 0) {
                    false
                } else if (this.day ?: 0 < day.day ?: 0) {
                    true
                } else if (this.day == day.day) {
                    false
                } else false
            } else false
        } else false
    }
}