package com.admiral.uikit.view.calendar.ui

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.admiral.uikit.view.calendar.InternalCalendarView
import com.admiral.uikit.view.calendar.model.CalendarDay
import com.admiral.uikit.view.calendar.model.NO_INDEX
import com.admiral.uikit.view.calendar.model.ScrollMode
import java.time.YearMonth

internal class CalendarLayoutManager(
    private val calView: InternalCalendarView,
    @RecyclerView.Orientation orientation: Int
) :
    LinearLayoutManager(calView.context, orientation, false) {

    private val adapter: CalendarAdapter
        get() = calView.adapter as CalendarAdapter

    private val context: Context
        get() = calView.context

    fun scrollToMonth(month: YearMonth) {
        val position = adapter.getAdapterPosition(month)
        if (position == NO_INDEX) return
        scrollToPositionWithOffset(position, 0)
        calView.post { adapter.notifyMonthScrollListenerIfNeeded() }
    }

    fun smoothScrollToMonth(month: YearMonth) {
        val position = adapter.getAdapterPosition(month)
        if (position == NO_INDEX) return
        startSmoothScroll(CalendarSmoothScroller(position, null))
    }

    fun smoothScrollToDay(day: CalendarDay) {
        val monthPosition = adapter.getAdapterPosition(day)
        if (monthPosition == NO_INDEX) return
        // Can't target a specific day in a paged calendar.
        val isPaged = calView.scrollMode == ScrollMode.PAGED
        startSmoothScroll(CalendarSmoothScroller(monthPosition, if (isPaged) null else day))
    }

    fun scrollToDay(day: CalendarDay) {
        val monthPosition = adapter.getAdapterPosition(day)
        if (monthPosition == NO_INDEX) return
        scrollToPositionWithOffset(monthPosition, 0)
        // Can't target a specific day in a paged calendar.
        if (calView.scrollMode == ScrollMode.PAGED) {
            calView.post { adapter.notifyMonthScrollListenerIfNeeded() }
        } else {
            calView.post {
                val viewHolder =
                    calView.findViewHolderForAdapterPosition(monthPosition) as? MonthViewHolder ?: return@post
                val offset = calculateDayViewOffsetInParent(day, viewHolder.itemView)
                scrollToPositionWithOffset(monthPosition, -offset)
                calView.post { adapter.notifyMonthScrollListenerIfNeeded() }
            }
        }
    }

    private fun calculateDayViewOffsetInParent(day: CalendarDay, itemView: View): Int {
        val dayView = itemView.findViewWithTag<View>(day.date.hashCode()) ?: return 0
        val rect = Rect()
        dayView.getDrawingRect(rect)
        (itemView as ViewGroup).offsetDescendantRectToMyCoords(dayView, rect)
        return if (calView.isVertical) rect.top + calView.monthMarginTop else rect.left + calView.monthMarginStart
    }

    private inner class CalendarSmoothScroller(position: Int, val day: CalendarDay?) :
        LinearSmoothScroller(context) {

        init {
            targetPosition = position
        }

        override fun getVerticalSnapPreference(): Int = SNAP_TO_START

        override fun getHorizontalSnapPreference(): Int = SNAP_TO_START

        override fun calculateDyToMakeVisible(view: View, snapPreference: Int): Int {
            val dy = super.calculateDyToMakeVisible(view, snapPreference)
            if (day == null) {
                return dy
            }
            val offset = calculateDayViewOffsetInParent(day, view)
            return dy - offset
        }

        override fun calculateDxToMakeVisible(view: View, snapPreference: Int): Int {
            val dx = super.calculateDxToMakeVisible(view, snapPreference)
            if (day == null) {
                return dx
            }
            val offset = calculateDayViewOffsetInParent(day, view)
            return dx - offset
        }
    }
}
