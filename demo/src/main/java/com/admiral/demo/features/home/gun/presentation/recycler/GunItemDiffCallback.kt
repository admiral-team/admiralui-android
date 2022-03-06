package com.admiral.demo.features.home.gun.presentation.recycler

import androidx.recyclerview.widget.DiffUtil
import com.admiral.demo.features.home.gun.presentation.model.GunItem

class GunItemDiffCallback : DiffUtil.ItemCallback<GunItem>() {
    override fun areItemsTheSame(
        oldItem: GunItem,
        newItem: GunItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: GunItem,
        newItem: GunItem
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: GunItem, newItem: GunItem): Any {
        return Payload(newItem)
    }

    data class Payload(val gunItem: GunItem)
}