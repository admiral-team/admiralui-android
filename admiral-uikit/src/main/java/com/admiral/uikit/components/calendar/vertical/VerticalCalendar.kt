package com.admiral.uikit.components.calendar.vertical

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.admiral.uikit.R
import com.admiral.uikit.components.calendar.common.CalendarState
import com.admiral.uikit.components.calendar.common.IMonthsGenerator
import com.admiral.uikit.components.calendar.vertical.recycler.VerticalMonthsSpacingDecorator
import com.admiral.uikit.components.calendar.vertical.recycler.VerticalMonthsAdapter
import com.admiral.uikit.databinding.AdmiralViewCalendarVerticalBinding
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.layout.FrameLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Vertical calendar view
 */
class VerticalCalendar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var coroutineScope: CoroutineScope? = null

    private val binding = AdmiralViewCalendarVerticalBinding
        .inflate(LayoutInflater.from(context), this)

    private val layoutManager: LinearLayoutManager
        get() = binding.monthsRecycler.layoutManager as LinearLayoutManager

    private val verticalCalendarAdapterVertical: VerticalMonthsAdapter
        get() = binding.monthsRecycler.adapter as VerticalMonthsAdapter

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        findViewTreeViewModelStoreOwner().let { owner ->
            owner ?: throw IllegalStateException()
            ViewModelProvider(owner)[VerticalCalendarVm::class.java].also {
                it.init(resources = resources)
            }
        }
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val visibleItemCount = recyclerView.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val needMoreTopItems = firstVisibleItemPosition < ITEMS_GENERATOR_THRESHOLD
            val needMoreBottomItems =
                totalItemCount - visibleItemCount - firstVisibleItemPosition < ITEMS_GENERATOR_THRESHOLD

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
    var calendarState: CalendarState = CalendarState()
        get() = viewModel.calendarStateFlow.value
        set(value) {
            field = value
            viewModel.updateCalendarState(value)
        }

    init {
        initRecycler()
    }

    private fun initRecycler() {
        binding.monthsRecycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = VerticalMonthsAdapter(context) { clickedDate ->
                viewModel.handleDayClickedAction(clickedDate)
            }
            setRecycledViewPool(RecyclerView.RecycledViewPool())
            addOnScrollListener(onScrollListener)
            addItemDecoration(
                VerticalMonthsSpacingDecorator(
                    spacingInPx = pixels(R.dimen.admiral_vertical_calendar_spacing_between_months)
                )
            )
        }
    }

    private fun createStateSubscription(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            viewModel.monthsStateFlow.collect {
                val isScrollNeeded = verticalCalendarAdapterVertical.itemCount == 0
                verticalCalendarAdapterVertical.submitList(it) {
                    if (isScrollNeeded) {
                        binding.monthsRecycler.scrollToPosition(
                            viewModel.initialScrollPosition
                        )
                    }
                }
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
            .also { createStateSubscription(it) }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        coroutineScope?.cancel()
        coroutineScope = null
    }

    private companion object {
        const val ITEMS_GENERATOR_THRESHOLD = 2
    }
}