package com.admiral.uikit.calendar.vertical.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.admiral.uikit.calendar.common.recycler.MonthsDiffCallback
import com.admiral.uikit.calendar.databinding.AdmiralItemVerticalCalendarBinding
import com.admiral.uikit.calendar.day.BaseDayModel
import com.admiral.uikit.calendar.month.MonthModel

internal class VerticalMonthsAdapter(
    context: Context,
    private val dayVerticalSpacingPx: Int,
    private val dayClickedAction: (BaseDayModel.DayModel) -> Unit,
) : ListAdapter<MonthModel, VerticalMonthsAdapter.CalendarMonthViewHolder>(
    MonthsDiffCallback()
) {
    private val layoutInflater = LayoutInflater.from(context)
    private val viewPool = RecyclerView.RecycledViewPool()
        .also { it.setMaxRecycledViews(0, MAX_RECYCLED_VIEWS) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarMonthViewHolder {
        val binding = AdmiralItemVerticalCalendarBinding.inflate(
            layoutInflater, parent, false
        ).also {
            it.calendarMonth.initRecycler(
                parentWidth = parent.width,
                viewPool = viewPool,
                dayClickedAction = dayClickedAction,
                dayVerticalSpacingPx = dayVerticalSpacingPx,
            )
        }

        return CalendarMonthViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarMonthViewHolder, position: Int) {
        holder.bind(getItem(position) as MonthModel)
    }

    inner class CalendarMonthViewHolder(
        val binding: AdmiralItemVerticalCalendarBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MonthModel) {
            with(binding) {
                title.text = model.title
                calendarMonth.model = model
            }
        }
    }

    private companion object {
        const val MAX_RECYCLED_VIEWS = 500
    }
}