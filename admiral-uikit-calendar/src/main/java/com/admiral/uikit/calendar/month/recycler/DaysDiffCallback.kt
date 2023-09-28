package com.admiral.uikit.calendar.month.recycler

import androidx.recyclerview.widget.DiffUtil
import com.admiral.uikit.calendar.day.BaseDayModel

internal class DaysDiffCallback : DiffUtil.ItemCallback<BaseDayModel>() {
    override fun areItemsTheSame(
        oldItem: BaseDayModel,
        newItem: BaseDayModel
    ): Boolean {
        return when {
            oldItem is BaseDayModel.DayModel && newItem is BaseDayModel.DayModel -> {
                oldItem.localDate == newItem.localDate
            }
            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: BaseDayModel,
        newItem: BaseDayModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: BaseDayModel, newItem: BaseDayModel): Any {
        return Payload(newItem)
    }

    data class Payload(val dayModel: BaseDayModel)
}