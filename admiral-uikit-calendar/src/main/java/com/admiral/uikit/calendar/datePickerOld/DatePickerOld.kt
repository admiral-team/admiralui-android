package com.admiral.uikit.calendar.datePickerOld

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.uikit.calendar.R
import com.admiral.uikit.core.layout.ConstraintLayout
import java.time.YearMonth

internal class DatePickerOld @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    /**
     * Color for the divider.
     * In case tint color is null, the selected color theme will be used.
     */
    @ColorInt
    var dividerColor: Int? = null
        set(value) {
            field = value
            invalidateDividerColor()
        }

    /**
     * Color for the text.
     * In case tint color is null, the selected color theme will be used.
     */
    @ColorInt
    var textColor: Int? = null
        set(value) {
            field = value
            invalidateTextColor()
        }

    /**
     * Defines first value of the range for the year picker.
     */
    var startYear = START_YEAR
        set(value) {
            field = value
            invalidateYearsRange()
        }

    /**
     * Defines last value of the range for the year picker.
     */
    var endYear = END_YEAR
        set(value) {
            field = value
            invalidateYearsRange()
        }

    /**
     * Defines values of the range for the month picker.
     */
    var months: Array<String> =
        arrayOf("Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек")
        set(value) {
            field = value
            invalidateMonthRange()
        }

    /**
     * Find value by the position and make it as chosen at the year picker.
     */
    var yearPickerValue = 0
        set(value) {
            field = value
            yearNumberPicker.value = field
        }
        get() {
            return yearNumberPicker.value
        }

    /**
     * Find value by the position and make it as chosen at the month picker.
     */
    var monthPickerValue = 0
        set(value) {
            field = value
            monthNumberPicker.value = field
        }
        get() {
            return monthNumberPicker.value
        }

    private val monthNumberPicker: NumberPicker by lazy { findViewById(R.id.monthNumberPicker) }
    private val yearNumberPicker: NumberPicker by lazy { findViewById(R.id.yearNumberPicker) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_date_picker_old, this)

        monthNumberPicker.minValue = 0
        monthNumberPicker.wrapSelectorWheel = false

        yearNumberPicker.minValue = 0
        yearNumberPicker.wrapSelectorWheel = false

        invalidateYearsRange()
        invalidateMonthRange()
    }

    /**
     * Subscribe for theme change.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.subscribe(this)
    }

    /**
     * Unsubscribe for theme change.
     */
    override fun onDetachedFromWindow() {
        ThemeManager.unsubscribe(this)
        super.onDetachedFromWindow()
    }

    override fun onThemeChanged(theme: Theme) {
        super.onThemeChanged(theme)
        invalidateDividerColor()
        invalidateTextColor()
        invalidateBackgroundColor()
    }

    fun getChosenYear(): Int {
        return yearNumberPicker.displayedValues[yearPickerValue].toInt()
    }

    fun getChosenYearMonth(): YearMonth {
        return YearMonth.of(getChosenYear(), monthPickerValue + 1)
    }

    fun setDate(yearMonth: YearMonth) {
        val years = (startYear..endYear)
        val chosenYearPosition = years.indexOf(yearMonth.year)

        yearNumberPicker.value = chosenYearPosition
        monthNumberPicker.value = yearMonth.monthValue - 1
    }

    private fun invalidateBackgroundColor() {
        setBackgroundColor(ThemeManager.theme.palette.backgroundBasic)
    }

    private fun invalidateDividerColor() {
        monthNumberPicker.dividerColor = dividerColor ?: ThemeManager.theme.palette.elementAccent
        yearNumberPicker.dividerColor = dividerColor ?: ThemeManager.theme.palette.elementAccent

        monthNumberPicker.invalidate()
        yearNumberPicker.invalidate()
    }

    private fun invalidateTextColor() {
        monthNumberPicker.textColor = textColor ?: ThemeManager.theme.palette.textPrimary
        yearNumberPicker.textColor = textColor ?: ThemeManager.theme.palette.textPrimary

        monthNumberPicker.selectedTextColor = textColor ?: ThemeManager.theme.palette.textPrimary
        yearNumberPicker.selectedTextColor = textColor ?: ThemeManager.theme.palette.textPrimary

        monthNumberPicker.invalidate()
        yearNumberPicker.invalidate()
    }

    private fun invalidateYearsRange() {
        val years: Array<String> = (startYear..endYear).map {
            it.toString()
        }.toTypedArray()

        yearNumberPicker.maxValue = years.size - 1
        yearNumberPicker.displayedValues = years
    }

    private fun invalidateMonthRange() {
        monthNumberPicker.maxValue = months.size - 1
        monthNumberPicker.displayedValues = months
    }

    private companion object {
        const val START_YEAR = 2015
        const val END_YEAR = 2021
    }
}