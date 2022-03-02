package com.admiral.uikit.components.datepicker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.postDelayed
import com.admiral.uikit.R
import com.admiral.uikit.components.verticalSelector.VerticalSelector
import com.admiral.uikit.components.verticalSelector.VerticalSelectorItem
import com.admiral.uikit.databinding.AdmiralViewDatePickerBinding
import com.admiral.uikit.layout.ConstraintLayout
import java.time.Month
import java.time.Year
import java.time.YearMonth

internal class DatePicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = AdmiralViewDatePickerBinding
        .inflate(LayoutInflater.from(context), this)

    @Suppress("MagicNumber")
    private val months: List<VerticalSelectorItem.Selectable> =
        mutableListOf<VerticalSelectorItem.Selectable>().apply {
            (0..11L).forEach { i ->
                val month = Month.JANUARY.plus(i)
                add(
                    VerticalSelectorItem.Selectable(
                        id = month.name,
                        title = month.toLocalisedString(),
                        payload = month
                    )
                )
            }
        }

    var initialYearMonth: YearMonth = YearMonth.now()
        set(value) {
            field = value

            with(binding) {
                monthSelector.isInvisible = true
                yearSelector.isInvisible = true

                monthSelector.mode = VerticalSelector.Mode.Finite(
                    items = months,
                    currentItemIndex = months.indexOfFirst { it.payload == value.month }
                )
                yearSelector.mode = VerticalSelector.Mode.Infinite(
                    itemsGenerator = YearsGenerator()
                )
            }
        }

    var yearMonth: YearMonth = initialYearMonth
        set(value) {
            if (field != value) {
                field = value
                onDateChanged(yearMonth)
            }
        }

    var onDateChanged: (YearMonth) -> Unit = {}

    init {
        initialYearMonth = YearMonth.now()
        initSelectors()
    }

    fun setVisibilityForSelectors(visible: Boolean, delayInMilliseconds: Long = 0L) =
        with(binding) {
            postDelayed(delayInMilliseconds) {
                monthSelector.isVisible = visible
                yearSelector.isVisible = visible
            }

        }

    private fun initSelectors() {
        with(binding) {
            monthSelector.onItemSelected = {
                val month = it.payload as Month
                yearMonth = YearMonth.of(yearMonth.year, month)
            }
            yearSelector.onItemSelected = {
                val year = it.payload as Year
                yearMonth = YearMonth.of(year.value, yearMonth.month)
            }
        }
    }

    private fun Month.toLocalisedString(): String {
        return when (this) {
            Month.JANUARY -> resources.getString(R.string.month_january)
            Month.FEBRUARY -> resources.getString(R.string.month_february)
            Month.MARCH -> resources.getString(R.string.month_march)
            Month.APRIL -> resources.getString(R.string.month_april)
            Month.MAY -> resources.getString(R.string.month_may)
            Month.JUNE -> resources.getString(R.string.month_june)
            Month.JULY -> resources.getString(R.string.month_july)
            Month.AUGUST -> resources.getString(R.string.month_august)
            Month.SEPTEMBER -> resources.getString(R.string.month_september)
            Month.OCTOBER -> resources.getString(R.string.month_october)
            Month.NOVEMBER -> resources.getString(R.string.month_november)
            Month.DECEMBER -> resources.getString(R.string.month_december)
        }
    }

    inner class YearsGenerator : VerticalSelector.VerticalSelectorItemsGenerator {
        private fun generateItem(year: Year): VerticalSelectorItem.Selectable {
            return VerticalSelectorItem.Selectable(
                id = year.value.toString(),
                title = year.value.toString(),
                payload = year
            )
        }

        override fun generateItemOnTop(
            currentTopItem: VerticalSelectorItem.Selectable?
        ): VerticalSelectorItem.Selectable {
            val year = (currentTopItem?.payload as? Year)
                ?.minusYears(1)
                ?: Year.of(initialYearMonth.year)
            return generateItem(year)
        }

        override fun generateItemOnBottom(
            currentBottomItem: VerticalSelectorItem.Selectable?
        ): VerticalSelectorItem.Selectable {
            val year = (currentBottomItem?.payload as? Year)
                ?.plusYears(1)
                ?: Year.of(initialYearMonth.year)
            return generateItem(year)
        }
    }
}