package com.admiral.uikit.components.calendar.month.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.admiral.uikit.components.calendar.day.BaseDayModel
import com.admiral.uikit.databinding.AdmiralItemCalendarDayBinding

internal class DaysAdapter(
    context: Context,
    private val dayClickedAction: (BaseDayModel.DayModel) -> Unit
) : ListAdapter<BaseDayModel, DaysAdapter.DayViewHolder>(
    DaysDiffCallback()
) {
    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val binding = AdmiralItemCalendarDayBinding.inflate(
            layoutInflater, parent, false
        )
        return DayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(getItem(position) as BaseDayModel)
    }

    inner class DayViewHolder(
        val binding: AdmiralItemCalendarDayBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        lateinit var item: BaseDayModel

        init {
            binding.root.onClickAction = {
                (item as? BaseDayModel.DayModel)?.let {
                    dayClickedAction(it)
                }
            }
        }

        fun bind(item: BaseDayModel) {
            this.item = item
            binding.root.model = item
        }
    }
}