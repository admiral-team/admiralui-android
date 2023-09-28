package com.admiral.uikit.calendar.common

import android.content.res.Resources
import com.admiral.uikit.calendar.month.MonthModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Clock
import java.time.LocalDate
import java.time.YearMonth

internal interface IMonthsGenerator {
    suspend fun generateItemOnStart(currentTopItem: MonthModel?): MonthModel
    suspend fun generateItemOnEnd(currentBottomItem: MonthModel?): MonthModel

    enum class Direction {
        ON_START,
        ON_END,
    }
}

internal class MonthsGenerator(
    private val resources: Resources,
    private val clock: Clock,
    private val initialYearMonthProvider: () -> YearMonth,
    private val selectionProvider: () -> Selection,
    private val markedDaysProvider: () -> List<LocalDate>,
    private val disabledDaysInfoProvider: () -> DisabledDaysInfo,
    private val highlightedDaysProvider: () -> List<LocalDate>,
) : IMonthsGenerator {
    override suspend fun generateItemOnStart(
        currentTopItem: MonthModel?
    ) = withContext(Dispatchers.IO) {
        return@withContext (currentTopItem
            ?.yearMonth
            ?.minusMonths(MONTHS_OFFSET) ?: initialYearMonthProvider())
            .toCalendarMonthModel(
                resources = resources,
                clock = clock,
                selection = selectionProvider(),
                markedDays = markedDaysProvider(),
                disabledDaysInfo = disabledDaysInfoProvider(),
                highlightedDays = highlightedDaysProvider(),
            )
    }

    override suspend fun generateItemOnEnd(
        currentBottomItem: MonthModel?
    ) = withContext(Dispatchers.IO) {
        return@withContext (currentBottomItem
            ?.yearMonth
            ?.plusMonths(MONTHS_OFFSET) ?: initialYearMonthProvider())
            .toCalendarMonthModel(
                resources = resources,
                clock = clock,
                selection = selectionProvider(),
                markedDays = markedDaysProvider(),
                disabledDaysInfo = disabledDaysInfoProvider(),
                highlightedDays = highlightedDaysProvider(),
            )
    }

    private companion object {
        const val MONTHS_OFFSET = 1L
    }
}