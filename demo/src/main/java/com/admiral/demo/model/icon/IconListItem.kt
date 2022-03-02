package com.admiral.demo.model.icon

import com.admiral.demo.model.BaseItem

class IconListItem(private val iconModel: Icon) : BaseItem {
    override fun getId(): Int {
        return iconModel.hashCode()
    }

    override fun getItem(): Any {
        return iconModel
    }

    companion object {
        fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return if (oldItem is Icon && newItem is Icon) {
                oldItem.id == newItem.id && oldItem.name == newItem.name
            } else {
                false
            }
        }
    }
}