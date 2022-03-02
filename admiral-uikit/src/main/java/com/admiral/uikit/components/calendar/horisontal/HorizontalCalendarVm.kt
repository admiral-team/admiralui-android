package com.admiral.uikit.components.calendar.horisontal

import com.admiral.uikit.components.calendar.common.CalendarVm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.YearMonth

internal class HorizontalCalendarVm : CalendarVm() {
    override val initialGenerationItemCount: Int = INITIAL_GENERATION_ITEM_COUNT
    override val additionalItemCount: Int = ADDITIONAL_ITEM_COUNT

    private val mutableYearMonthStateFlow = MutableStateFlow(calendarStateFlow.value.initialYearMonth)
    val yearMonthStateFlow: StateFlow<YearMonth> = mutableYearMonthStateFlow.asStateFlow()

    fun updateMonthModel(yearMonth: YearMonth) {
        mutableYearMonthStateFlow.value = yearMonth
    }

    private companion object {
        const val INITIAL_GENERATION_ITEM_COUNT = 24
        const val ADDITIONAL_ITEM_COUNT = 5
    }
}