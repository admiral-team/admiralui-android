package com.admiral.uikit.components.calendar.horisontal.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.admiral.uikit.components.calendar.common.recycler.MonthsDiffCallback
import com.admiral.uikit.components.calendar.day.BaseDayModel
import com.admiral.uikit.components.calendar.month.MonthModel
import com.admiral.uikit.databinding.AdmiralItemHorizontalCalendarBinding

internal class HorizontalMonthsAdapter(
    context: Context,
    private val dayClickedAction: (BaseDayModel.DayModel) -> Unit
) : ListAdapter<MonthModel, HorizontalMonthsAdapter.CalendarMonthViewHolder>(
    MonthsDiffCallback()
) {
    private val layoutInflater = LayoutInflater.from(context)
    private val viewPool = RecyclerView.RecycledViewPool()
        .also { it.setMaxRecycledViews(0, MAX_RECYCLED_VIEWS) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarMonthViewHolder {
        val binding = AdmiralItemHorizontalCalendarBinding.inflate(
            layoutInflater, parent, false
        ).also {
            it.calendarMonth.initRecycler(
                parentWidth = parent.width,
                viewPool = viewPool,
                dayClickedAction = dayClickedAction
            )
        }

        return CalendarMonthViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarMonthViewHolder, position: Int) {
        holder.bind(getItem(position) as MonthModel)
    }

    inner class CalendarMonthViewHolder(
        val binding: AdmiralItemHorizontalCalendarBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MonthModel) {
            with(binding) {
                calendarMonth.model = model
            }
        }
    }

    private companion object {
        const val MAX_RECYCLED_VIEWS = 500
    }
}