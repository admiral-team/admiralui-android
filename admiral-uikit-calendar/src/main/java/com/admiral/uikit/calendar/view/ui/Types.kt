package com.admiral.uikit.calendar.view.ui

import android.view.View
import com.admiral.uikit.calendar.view.model.CalendarDay
import com.admiral.uikit.calendar.view.model.CalendarMonth

open class ViewContainer(val view: View)

interface DayBinder<T : ViewContainer> {
    fun create(view: View): T
    fun bind(container: T, day: CalendarDay)
}

interface MonthHeaderFooterBinder<T : ViewContainer> {
    fun create(view: View): T
    fun bind(container: T, month: CalendarMonth)
}

typealias MonthScrollListener = (CalendarMonth) -> Unit
