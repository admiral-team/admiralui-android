package com.admiral.uikit.components.calendar.common.recycler

import androidx.recyclerview.widget.DiffUtil
import com.admiral.uikit.components.calendar.month.MonthModel

internal class MonthsDiffCallback : DiffUtil.ItemCallback<MonthModel>() {
    override fun areItemsTheSame(
        oldItem: MonthModel,
        newItem: MonthModel
    ): Boolean {
        return oldItem.yearMonth == newItem.yearMonth
    }

    override fun areContentsTheSame(
        oldItem: MonthModel,
        newItem: MonthModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: MonthModel, newItem: MonthModel): Any {
        return Payload(newItem)
    }

    data class Payload(val monthModel: MonthModel)
}