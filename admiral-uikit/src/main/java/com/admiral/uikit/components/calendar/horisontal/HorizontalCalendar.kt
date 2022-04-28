package com.admiral.uikit.components.calendar.horisontal

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.VisibleForTesting
import androidx.core.view.doOnLayout
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.admiral.uikit.R
import com.admiral.uikit.components.calendar.common.CalendarState
import com.admiral.uikit.components.calendar.common.IMonthsGenerator
import com.admiral.uikit.components.calendar.common.calculateHeightOfMothView
import com.admiral.uikit.components.calendar.common.getTitle
import com.admiral.uikit.components.calendar.day.BaseDayModel
import com.admiral.uikit.components.calendar.horisontal.recycler.HorizontalMonthsAdapter
import com.admiral.uikit.databinding.AdmiralViewCalendarHorizontalBinding
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.layout.ConstraintLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.time.Clock
import java.time.YearMonth

/**
 * Horizontal calendar view
 */
class HorizontalCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var coroutineScope: CoroutineScope? = null

    private val dayHeight = pixels(R.dimen.admiral_calendar_day_height)

    private val horizontalSpacing =
        pixels(R.dimen.admiral_calendar_month_horizontal_spacing_between_days)

    private val drawableUp = drawable(R.drawable.admiral_ic_chevron_up_outline)

    private val drawableDown = drawable(R.drawable.admiral_ic_chevron_down_outline)

    private val binding = AdmiralViewCalendarHorizontalBinding
        .inflate(LayoutInflater.from(context), this)

    private val layoutManager: LinearLayoutManager
        get() = binding.monthsRecycler.layoutManager as LinearLayoutManager

    private val horizontalCalendarAdapterVertical: HorizontalMonthsAdapter
        get() = binding.monthsRecycler.adapter as HorizontalMonthsAdapter

    private val snapHelper = PagerSnapHelper()

    private val viewModel by lazy {
        findViewTreeViewModelStoreOwner().let { owner ->
            owner ?: throw IllegalStateException()
            ViewModelProvider(owner)[HorizontalCalendarVm::class.java].also {
                it.init(resources = resources, clock = clock)
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var clock: Clock = Clock.systemDefaultZone()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var isDebounceEnabled: Boolean = true

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        @Suppress("ReturnCount")
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val visibleItemCount = recyclerView.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val needMoreTopItems = firstVisibleItemPosition < ITEMS_GENERATOR_THRESHOLD
            val needMoreBottomItems =
                totalItemCount - visibleItemCount - firstVisibleItemPosition < ITEMS_GENERATOR_THRESHOLD

            val view = snapHelper.findSnapView(layoutManager) ?: return
            val position = layoutManager.getPosition(view)
            val yearMonthModel = horizontalCalendarAdapterVertical.currentList[position] ?: return

            viewModel.updateMonthModel(yearMonthModel.yearMonth)

            when {
                needMoreTopItems -> {
                    viewModel.generateAdditionalMonths(IMonthsGenerator.Direction.ON_START)
                }
                needMoreBottomItems -> {
                    viewModel.generateAdditionalMonths(IMonthsGenerator.Direction.ON_END)
                }
                else -> return
            }
        }
    }

    /**
     * Calendar state.
     * You can use it to set/get initial YearMonth, selection, marked and disabled days.
     */
    var calendarState: CalendarState
        get() = viewModel.calendarStateFlow.value
        set(value) = doOnLayout {
            viewModel.updateCalendarState(value)
        }

    /**
     * StateFlow for calendar state changes
     */
    val calendarStateFlow: StateFlow<CalendarState>
        get() = viewModel.calendarStateFlow

    /**
     * Callback for click on a day events
     */
    var onDayClicked: ((BaseDayModel.DayModel) -> Unit)? = null

    init {
        initRecycler()
        initTitleButton()
        initScrollButtons()
        initSelectButton()
        initDatePicker()
    }

    private fun initDatePicker() = with(binding) {
        datePicker.setVisibilityForSelectors(visible = false)
    }

    private fun initScrollButtons() = with(binding) {
        fun scroll(toNext: Boolean) {
            val position = layoutManager.findFirstVisibleItemPosition()
            if (position != RecyclerView.NO_POSITION) {
                if (toNext) {
                    monthsRecycler.smoothScrollToPosition(position + 1)
                } else {
                    monthsRecycler.smoothScrollToPosition(position - 1)
                }
            }
        }

        previous.setOnClickListener { scroll(false) }
        next.setOnClickListener { scroll(true) }
    }

    private fun initTitleButton() = with(binding) {
        calendarTitle.setOnClickListener {
            if (binding.datePicker.isVisible) {
                closeDatePicker(isDateChosen = false)
            } else {
                openDatePicker()
            }
        }
    }

    private fun initSelectButton() = with(binding) {
        select.setOnClickListener {
            closeDatePicker(isDateChosen = true)
        }
    }

    private fun updateCalendarTitleDrawable(isUpIcon: Boolean) = with(binding) {
        calendarTitle.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            if (isUpIcon) drawableUp else drawableDown,
            null
        )
    }

    private fun openDatePicker() = with(binding) {
        monthsRecycler.isInvisible = true
        weekDays.isInvisible = true
        previous.isGone = true
        next.isGone = true
        select.isVisible = true
        datePicker.apply {
            initialYearMonth = viewModel.yearMonthStateFlow.value
            isVisible = true
            setVisibilityForSelectors(visible = true, DATE_PICKER_SMOOTHNESS_DELAY)
        }
        updateCalendarTitleDrawable(isUpIcon = true)
    }

    private fun closeDatePicker(isDateChosen: Boolean) = with(binding) {
        monthsRecycler.isVisible = true
        weekDays.isVisible = true
        previous.isVisible = true
        next.isVisible = true
        select.isGone = true
        datePicker.apply {
            setVisibilityForSelectors(visible = false)
            isGone = true
        }
        updateCalendarTitleDrawable(isUpIcon = false)

        if (isDateChosen) {
            viewModel.updateMonthModel(datePicker.yearMonth)
            viewModel.updateCalendarState(
                calendarState = calendarState.copy(
                    initialYearMonth = datePicker.yearMonth
                )
            )
        }
    }

    private fun initRecycler() = with(binding) {
        monthsRecycler.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            adapter = HorizontalMonthsAdapter(context) { clickedDate ->
                viewModel.handleDayClickedAction(clickedDate)
                onDayClicked?.invoke(clickedDate)
            }
            snapHelper.attachToRecyclerView(this)

            setRecycledViewPool(RecyclerView.RecycledViewPool())
            addOnScrollListener(onScrollListener)
        }
    }

    @Suppress("EXPERIMENTAL_IS_NOT_ENABLED")
    @OptIn(FlowPreview::class)
    private fun createSubscriptions(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            viewModel.monthsStateFlow.collect { months ->
                val isScrollNeeded = horizontalCalendarAdapterVertical.itemCount == 0
                horizontalCalendarAdapterVertical.submitList(months) {
                    if (isScrollNeeded) {
                        binding.monthsRecycler.scrollToPosition(
                            viewModel.initialScrollPosition
                        )
                    }
                }
            }
        }
        coroutineScope.launch {
            val flow = if (isDebounceEnabled) {
                viewModel.yearMonthStateFlow
                    .debounce(TITLE_STATE_CHANGES_DEBOUNCE_IN_MILLISECONDS)
            } else {
                viewModel.yearMonthStateFlow
            }

            flow.collect {
                binding.calendarTitle.text = it.getTitle(resources)
                updateCalendarTitleDrawable(isUpIcon = binding.datePicker.isVisible)
                updateRecyclerViewHeight(it)
            }
        }
    }

    private fun updateRecyclerViewHeight(yearMonth: YearMonth) {
        post {
            binding.monthsRecycler.updateLayoutParams {
                height = yearMonth.calculateHeightOfMothView(
                    isStartFromMonday = true,
                    dayHeight = dayHeight,
                    horizontalSpacing = horizontalSpacing
                )
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
            .also { createSubscriptions(it) }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        coroutineScope?.cancel()
        coroutineScope = null
    }

    private companion object {
        const val ITEMS_GENERATOR_THRESHOLD = 2
        const val TITLE_STATE_CHANGES_DEBOUNCE_IN_MILLISECONDS = 250L
        const val DATE_PICKER_SMOOTHNESS_DELAY = 250L
    }
}