package com.admiral.uikit.calendar.week

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.admiral.uikit.calendar.R
import com.admiral.uikit.calendar.databinding.AdmiralViewWeekDaysBinding
import com.admiral.uikit.core.layout.ConstraintLayout

class WeekDaysView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = AdmiralViewWeekDaysBinding
        .inflate(LayoutInflater.from(context), this)

    /**
     * Indicate that we want to start a week from Monday
     * true -> |пн| |вт| |ср| |чт| |пт| |сб| |вс|
     * false -> |вс| |пн| |вт| |ср| |чт| |пт| |сб|
     */
    var isStartFromMonday: Boolean = true
        set(value) {
            field = value
            updateWeekDays()
        }

    init {
        isStartFromMonday = true
    }

    private fun updateWeekDays() {
        with(binding) {
            if (isStartFromMonday) {
                day1.setText(R.string.day_of_week_monday)
                day2.setText(R.string.day_of_week_tuesday)
                day3.setText(R.string.day_of_week_wednesday)
                day4.setText(R.string.day_of_week_thursday)
                day5.setText(R.string.day_of_week_friday)
                day6.setText(R.string.day_of_week_saturday)
                day7.setText(R.string.day_of_week_sunday)
            } else {
                day1.setText(R.string.day_of_week_sunday)
                day2.setText(R.string.day_of_week_monday)
                day3.setText(R.string.day_of_week_tuesday)
                day4.setText(R.string.day_of_week_wednesday)
                day5.setText(R.string.day_of_week_thursday)
                day6.setText(R.string.day_of_week_friday)
                day7.setText(R.string.day_of_week_saturday)
            }
        }
    }
}