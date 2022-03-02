package com.admiral.demo.model.icon

import com.admiral.demo.model.BaseItem

class IconCategoryListItem(private val iconCategory: String) : BaseItem {
    override fun getId(): Int {
        return iconCategory.hashCode()
    }

    override fun getItem(): Any {
        return iconCategory
    }

    companion object {
        fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is String && newItem is String) {
                oldItem == newItem
            } else {
                false
            }
        }
    }
}