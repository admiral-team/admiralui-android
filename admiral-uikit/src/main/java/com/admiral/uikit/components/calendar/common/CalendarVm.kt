package com.admiral.uikit.components.calendar.common

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.admiral.uikit.components.calendar.day.BaseDayModel
import com.admiral.uikit.components.calendar.month.MonthModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Clock
import java.time.LocalDate

internal abstract class CalendarVm : ViewModel() {
    abstract val initialGenerationItemCount: Int
    abstract val additionalItemCount: Int

    val initialScrollPosition: Int
        get() = initialGenerationItemCount / 2

    private val mutableMonthsStateFlow = MutableStateFlow(listOf<MonthModel>())
    val monthsStateFlow: StateFlow<List<MonthModel>> = mutableMonthsStateFlow.asStateFlow()

    private val mutableCalendarStateFlow = MutableStateFlow(CalendarState())
    val calendarStateFlow: StateFlow<CalendarState> = mutableCalendarStateFlow.asStateFlow()

    private lateinit var monthsGenerator: IMonthsGenerator
    protected lateinit var resources: Resources

    fun init(resources: Resources, clock: Clock) {
        this.resources = resources
        this.monthsGenerator = MonthsGenerator(
            resources = resources,
            clock = clock,
            initialYearMonthProvider = { calendarStateFlow.value.initialYearMonth },
            selectionProvider = { calendarStateFlow.value.selection },
            markedDaysProvider = { calendarStateFlow.value.markedDays },
            disabledDaysInfoProvider = { calendarStateFlow.value.disabledDaysInfo },
            highlightedDaysProvider = { calendarStateFlow.value.highlightedDays }
        )

        viewModelScope.launch {
            calendarStateFlow.collectLatest {
                val months = if (monthsStateFlow.value.isEmpty()) {
                    createInitialItems()
                } else {
                    getUpdatedMonths(it, clock)
                }
                mutableMonthsStateFlow.value = months
            }
        }
    }

    fun updateCalendarState(calendarState: CalendarState) {
        // if initialYearMonth have changed we need to clear months and create them again
        if (calendarStateFlow.value.initialYearMonth != calendarState.initialYearMonth) {
            mutableMonthsStateFlow.value = emptyList()
        }
        mutableCalendarStateFlow.value = calendarState
    }

    private suspend fun getUpdatedMonths(
        calendarState: CalendarState,
        clock: Clock
    ) = withContext(Dispatchers.IO) {
        return@withContext monthsStateFlow.value.map {
            it.yearMonth.toCalendarMonthModel(
                resources = resources,
                clock = clock,
                selection = calendarState.selection,
                markedDays = calendarState.markedDays,
                disabledDaysInfo = calendarState.disabledDaysInfo,
                highlightedDays = calendarState.highlightedDays,
            )
        }
    }

    private suspend fun createInitialItems() = withContext(Dispatchers.IO) {
        val months = mutableListOf<MonthModel>()
        for (i in 0 until initialGenerationItemCount) {
            if (i <= initialScrollPosition) {
                val currentStartItem = months.firstOrNull()
                months.add(0, monthsGenerator.generateItemOnStart(currentStartItem))
            } else {
                val currentEndItem = months.lastOrNull()
                months.add(monthsGenerator.generateItemOnEnd(currentEndItem))
            }
        }
        months
    }

    fun handleDayClickedAction(dayModel: BaseDayModel.DayModel) {
        if (dayModel is BaseDayModel.DayModel.Disabled)
            return

        updateSelection(dayModel.localDate)
    }

    @Suppress("LongMethod")
    private fun updateSelection(clickedDate: LocalDate) {
        val selectionType = calendarStateFlow.value.selectionMode
        val selection = calendarStateFlow.value.selection

        when (selectionType) {
            is SelectionMode.Disabled -> return
            is SelectionMode.SingleSelection -> {
                val currentSelectedDate = (selection as? Selection.SingleSelection)?.date
                mutableCalendarStateFlow.value = calendarStateFlow.value.copy(
                    selection = if (currentSelectedDate == clickedDate) {
                        Selection.EmptySelection
                    } else {
                        Selection.SingleSelection(date = clickedDate)
                    }
                )
            }

            is SelectionMode.IntervalSelection -> {
                val currentStartDate = (selection as? Selection.IntervalSelection)?.startDate
                val currentEndDate = (selection as? Selection.IntervalSelection)?.endDate
                val newSelection = when {
                    currentStartDate == null && currentEndDate == null -> {
                        Selection.IntervalSelection(
                            startDate = clickedDate,
                            endDate = clickedDate
                        )
                    }

                    currentStartDate != null && currentEndDate == null -> {
                        Selection.IntervalSelection(
                            startDate = currentStartDate,
                            endDate = clickedDate
                        )
                    }

                    currentEndDate != null && currentStartDate == null -> {
                        Selection.IntervalSelection(
                            startDate = clickedDate,
                            endDate = currentEndDate
                        )
                    }

                    currentStartDate != null && currentEndDate != null -> {
                        when {
                            currentStartDate == clickedDate || currentEndDate == clickedDate -> {
                                Selection.IntervalSelection(
                                    startDate = clickedDate,
                                    endDate = clickedDate
                                )
                            }

                            clickedDate < currentStartDate -> {
                                Selection.IntervalSelection(
                                    startDate = clickedDate,
                                    endDate = currentEndDate
                                )
                            }

                            clickedDate > currentEndDate -> {
                                Selection.IntervalSelection(
                                    startDate = currentStartDate,
                                    endDate = clickedDate
                                )
                            }

                            else -> Selection.IntervalSelection(
                                startDate = clickedDate,
                                endDate = clickedDate
                            )
                        }
                    }

                    else -> selection
                }
                mutableCalendarStateFlow.value = calendarStateFlow.value.copy(
                    selection = newSelection
                )
            }
        }
    }

    fun generateAdditionalMonths(direction: IMonthsGenerator.Direction) = viewModelScope.launch {
        // we don't need to generate additional months if there are no months yet
        if (monthsStateFlow.value.isEmpty()) return@launch

        mutableMonthsStateFlow.value = getMonthsWithAdditionals(direction)
    }

    private suspend fun getMonthsWithAdditionals(
        direction: IMonthsGenerator.Direction
    ) = withContext(Dispatchers.IO) {
        val months = monthsStateFlow.value.toMutableList()
        when (direction) {
            IMonthsGenerator.Direction.ON_START -> {
                for (i in 0 until additionalItemCount) {
                    val currentTopItem = months.firstOrNull()
                    val month = monthsGenerator.generateItemOnStart(currentTopItem)
                    months.add(0, month)
                    if (months.isNotEmpty()) {
                        months.removeLast()
                    }
                }
            }

            IMonthsGenerator.Direction.ON_END -> {
                for (i in 0 until additionalItemCount) {
                    val currentBottomItem = months.lastOrNull()
                    val month = monthsGenerator.generateItemOnEnd(currentBottomItem)
                    months.add(month)
                    if (months.isNotEmpty()) {
                        months.removeFirst()
                    }
                }
            }
        }

        return@withContext months.toList()
    }
}