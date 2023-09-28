package com.admiral.uikit.calendar.month

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.admiral.uikit.calendar.R
import com.admiral.uikit.calendar.common.calculateHeightOfMothView
import com.admiral.uikit.calendar.databinding.AdmiralViewCalendarMonthBinding
import com.admiral.uikit.calendar.day.BaseDayModel
import com.admiral.uikit.calendar.month.recycler.DaysAdapter
import com.admiral.uikit.calendar.month.recycler.DaysSpacingDecoration
import com.admiral.uikit.core.ext.pixels
import java.time.YearMonth

internal class MonthView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RecyclerView(context, attrs, defStyleAttr) {

    private val binding = AdmiralViewCalendarMonthBinding
        .inflate(LayoutInflater.from(context), this)

    private val dayHeight = pixels(R.dimen.admiral_calendar_day_height)

    /**
     * Current month model.
     */
    var model = MonthModel(title = "", yearMonth = YearMonth.now(), days = emptyList())
        set(value) {
            field = value
            updateDays()
        }

    /**
     * Indicate that we want to start a week from Monday
     * true -> |пн| |вт| |ср| |чт| |пт| |сб| |вс|
     * false -> |вс| |пн| |вт| |ср| |чт| |пт| |сб|
     */
    var isStartFromMonday: Boolean = true

    init {
        overScrollMode = OVER_SCROLL_NEVER
        clipToPadding = false
    }

    fun initRecycler(
        parentWidth: Int,
        viewPool: RecycledViewPool,
        dayClickedAction: (BaseDayModel.DayModel) -> Unit,
        dayVerticalSpacingPx: Int = pixels(R.dimen.module_x5)
    ) {
        binding.root.apply {
            setHasFixedSize(true)
            setRecycledViewPool(viewPool)

            layoutManager = GridLayoutManager(
                context,
                SPAN,
                VERTICAL,
                false
            ).also { it.initialPrefetchItemCount = INITIAL_PREFETCH_ITEM_COUNT }

            adapter = DaysAdapter(context, dayClickedAction)

            val itemWidth = pixels(R.dimen.admiral_calendar_day_width)
            val recyclerHorizontalMargin = pixels(R.dimen.module_x4)
            val horizontalSpacing =
                ((parentWidth - 2 * recyclerHorizontalMargin) - SPAN * itemWidth) / (SPAN - 1)

            addItemDecoration(
                DaysSpacingDecoration(
                    span = SPAN,
                    dayVerticalSpacingPx = dayVerticalSpacingPx / 2,
                    dayHorizontalSpacingPx = horizontalSpacing,
                )
            )

            updateRecyclerViewHeight(dayVerticalSpacingPx = dayVerticalSpacingPx)
        }
    }

    private fun updateDays() {
        val items = createDays()
        val adapter = adapter as? DaysAdapter

        adapter?.submitList(items)
    }

    private fun updateRecyclerViewHeight(dayVerticalSpacingPx: Int) {
        post {
            updateLayoutParams {
                height = model.yearMonth.calculateHeightOfMothView(
                    isStartFromMonday = isStartFromMonday,
                    dayHeight = dayHeight,
                    verticalSpacing = dayVerticalSpacingPx / 2
                )
            }
        }
    }

    private fun createDays(): List<BaseDayModel> {
        var emptyDayCount = model.yearMonth.atDay(1).dayOfWeek.value
        if (isStartFromMonday) {
            emptyDayCount--
        }

        return List(emptyDayCount) { BaseDayModel.Unknown } + model.days
    }

    private companion object {
        const val SPAN = 7
        const val INITIAL_PREFETCH_ITEM_COUNT = 37
    }
}