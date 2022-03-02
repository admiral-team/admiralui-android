package com.admiral.uikit.components.verticalSelector

import androidx.recyclerview.widget.DiffUtil

internal class VerticalSelectorDiffCallback : DiffUtil.ItemCallback<VerticalSelectorItem>() {
    override fun areItemsTheSame(
        oldItem: VerticalSelectorItem,
        newItem: VerticalSelectorItem
    ): Boolean {
        return when {
            oldItem is VerticalSelectorItem.Selectable && newItem is VerticalSelectorItem.Selectable -> {
                oldItem.id == newItem.id
            }
            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: VerticalSelectorItem,
        newItem: VerticalSelectorItem
    ): Boolean {
        return oldItem == newItem
    }
}