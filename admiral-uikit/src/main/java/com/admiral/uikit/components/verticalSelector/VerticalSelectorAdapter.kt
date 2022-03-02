package com.admiral.uikit.components.verticalSelector

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.admiral.uikit.databinding.AdmiralViewVerticalSelectorItemBinding

internal class VerticalSelectorAdapter(
    context: Context
) : ListAdapter<VerticalSelectorItem, RecyclerView.ViewHolder>(
    VerticalSelectorDiffCallback()
) {
    private val layoutInflater = LayoutInflater.from(context)

    override fun getItemViewType(position: Int): Int = getItem(position).viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VerticalSelectorItem.FAKE_VIEW_TYPE -> {
                val view = View(parent.context)
                FakeViewHolder(view)
            }
            VerticalSelectorItem.SELECTABLE_VIEW_TYPE -> {
                val binding = AdmiralViewVerticalSelectorItemBinding.inflate(
                    layoutInflater, parent, false
                )
                SelectableViewHolder(binding)
            }
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SelectableViewHolder -> {
                holder.bind(getItem(position) as VerticalSelectorItem.Selectable)
            }
            is FakeViewHolder -> {
                holder.bind(getItem(position) as VerticalSelectorItem.Fake)
            }
        }
    }

    inner class FakeViewHolder(
        val view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: VerticalSelectorItem.Fake) {
            view.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, item.heightInPixels)
        }
    }

    inner class SelectableViewHolder(
        val binding: AdmiralViewVerticalSelectorItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VerticalSelectorItem.Selectable) {
            with(binding) {
                title.text = item.title
            }
        }
    }
}