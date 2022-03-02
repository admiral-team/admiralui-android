package com.admiral.demo.features.home.icons

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.admiral.demo.R
import com.admiral.demo.model.BaseItem
import com.admiral.demo.model.icon.Icon
import com.admiral.demo.model.icon.IconCategoryListItem
import com.admiral.demo.model.icon.IconItemTypes
import com.admiral.demo.model.icon.IconListItem
import com.admiral.themes.ThemeManager
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.text.TextView
import java.util.*

class IconsListAdapter(private val onClickListener: (Float, Float, String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<BaseItem>() {
        override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
            return oldItem.getId() == newItem.getId()
        }

        override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
            return IconListItem.areContentsTheSame(oldItem, newItem) ||
                    IconCategoryListItem.areContentsTheSame(oldItem, newItem)
        }
    }

    private var differ: AsyncListDiffer<BaseItem> = AsyncListDiffer(this, diffCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: ArrayList<BaseItem>) {
        differ.submitList(list)
    }

    override fun getItemViewType(position: Int): Int {
        return if (differ.currentList[position] is IconListItem) {
            IconItemTypes.ICON.type
        } else {
            IconItemTypes.ICON_CATEGORY.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            IconItemTypes.ICON.type -> IconViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    IconItemTypes.ICON.type,
                    parent,
                    false
                ).apply {
                    findViewById<com.admiral.uikit.components.text.TextView>(R.id.tvIconName).apply {
                        textStyle = ThemeManager.theme.typography.caption2
                        textColor = ColorState(normalEnabled = ThemeManager.theme.palette.textSecondary)
                    }
                }
            )
            IconItemTypes.ICON_CATEGORY.type -> IconCategoryViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    IconItemTypes.ICON_CATEGORY.type,
                    parent,
                    false
                ).apply {
                    findViewById<com.admiral.uikit.components.text.TextView>(R.id.iconCategory).apply {
                        textStyle = ThemeManager.theme.typography.headline
                        textColor = ColorState(normalEnabled = ThemeManager.theme.palette.textSecondary)
                    }
                }
            )
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is IconViewHolder -> {
                holder.bind(differ.currentList[position] as IconListItem, onClickListener)
            }
            is IconCategoryViewHolder -> {
                holder.bind(differ.currentList[position] as IconCategoryListItem)
            }
        }
    }

    class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: IconListItem, onClickListener: (Float, Float, String) -> Unit) = with(itemView) {
            val id = (item.getItem() as Icon).id
            val name = (item.getItem() as Icon).name.replace(" Solid", "").replace(" Outline", "")

            findViewById<TextView>(R.id.tvIconName).text = name

            val resources: Resources = context.resources
            val resourceId: Int = resources.getIdentifier(id, "drawable", context.packageName)

            val iconView = findViewById<ImageView>(R.id.icon)
            iconView.setImageDrawable(ContextCompat.getDrawable(context, resourceId)?.also {
                it.mutate()
                it.setTint(ThemeManager.theme.palette.elementAccent)
            })

            itemView.setOnClickListener {
                val originalPos = IntArray(2)
                iconView.getLocationInWindow(originalPos)
                val x = originalPos[0]
                val y = originalPos[1]
                onClickListener(x.toFloat(), y.toFloat(), name)
            }
        }
    }

    class IconCategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: IconCategoryListItem) = with(itemView) {
            val iconCategory = (item.getItem() as String)

            findViewById<TextView>(R.id.iconCategory).text = iconCategory.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.getDefault()
                ) else it.toString()
            }
        }
    }
}