package com.admiral.uikit.calendar.common

import android.content.res.Resources
import com.admiral.uikit.calendar.R
import com.admiral.uikit.calendar.day.BaseDayModel
import com.admiral.uikit.calendar.month.MonthModel
import java.time.Clock
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth

internal fun Month.toLocalisedString(resources: Resources): String {
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

internal fun YearMonth.getTitle(resources: Resources): String {
    return resources.getString(
        R.string.calendar_month_title,
        month.toLocalisedString(resources),
        year
    )
}

@Suppress("LongParameterList", "LongMethod")
internal fun YearMonth.toCalendarMonthModel(
    resources: Resources,
    clock: Clock,
    selection: Selection,
    markedDays: List<LocalDate>,
    highlightedDays: List<LocalDate>,
    disabledDaysInfo: DisabledDaysInfo
): MonthModel {
    val currentLocalDate = LocalDate.now(clock)

    val days = (1..this.lengthOfMonth()).map { day ->
        val localDate = LocalDate.of(this.year, this.month, day)
        val isCurrentDay = currentLocalDate == localDate
        val isMarked = markedDays.contains(localDate)
        val isHighlighted = highlightedDays.contains(localDate)
        val isDisabled = disabledDaysInfo.disabledDays.contains(localDate) ||
                (!disabledDaysInfo.isEnabledBeforeCurrentDay && localDate < currentLocalDate) ||
                (!disabledDaysInfo.isEnabledAfterCurrentDay && localDate > currentLocalDate)

        fun checkIsMarked(): BaseDayModel.DayModel {
            return if (isHighlighted) {
                BaseDayModel.DayModel.Highlighted(
                    localDate = localDate,
                    isMarked = isMarked,
                    isHighlighted = isHighlighted,
                )
            } else if (isCurrentDay) {
                BaseDayModel.DayModel.Current(
                    localDate = localDate,
                    isMarked = isMarked,
                    isHighlighted = isHighlighted,
                )
            } else {
                BaseDayModel.DayModel.Normal(
                    localDate = localDate,
                    isMarked = isMarked,
                    isHighlighted = isHighlighted,
                )
            }
        }

        when {
            isDisabled -> {
                BaseDayModel.DayModel.Disabled(
                    localDate = localDate,
                    isMarked = isMarked,
                    isHighlighted = isHighlighted,
                )
            }

            selection is Selection.SingleSelection -> {
                if (selection.date == localDate) {
                    BaseDayModel.DayModel.SelectedBright(
                        localDate = localDate,
                        isMarked = isMarked,
                        isHighlighted = isHighlighted,
                    )
                } else {
                    checkIsMarked()
                }
            }

            selection is Selection.IntervalSelection -> {
                when {
                    selection.startDate == localDate -> {
                        BaseDayModel.DayModel.SelectedBright(
                            localDate = localDate,
                            isMarked = isMarked,
                            isHighlighted = isHighlighted,
                        )
                    }

                    selection.endDate == localDate -> {
                        BaseDayModel.DayModel.SelectedBright(
                            localDate = localDate,
                            isMarked = isMarked,
                            isHighlighted = isHighlighted,
                        )
                    }

                    localDate in selection.startDate..selection.endDate -> {
                        BaseDayModel.DayModel.Selected(
                            localDate = localDate,
                            isMarked = isMarked,
                            isHighlighted = isHighlighted,
                        )
                    }

                    else -> {
                        checkIsMarked()
                    }
                }
            }

            else -> {
                checkIsMarked()
            }
        }
    }

    return MonthModel(
        title = this.getTitle(resources),
        yearMonth = this,
        days = days
    )
}

@Suppress("MagicNumber")
internal fun YearMonth.calculateHeightOfMothView(
    isStartFromMonday: Boolean,
    dayHeight: Int,
    verticalSpacing: Int
): Int {
    val dayCountInOneWeek = 7
    var emptyDayCount = atDay(1).dayOfWeek.value
    if (isStartFromMonday) {
        emptyDayCount--
    }
    val itemCount = this.lengthOfMonth() + emptyDayCount
    val rowCount =
        (itemCount / dayCountInOneWeek + if (itemCount % dayCountInOneWeek != 0) 1 else 0)

    return rowCount * dayHeight + (rowCount - 1) * verticalSpacing
}