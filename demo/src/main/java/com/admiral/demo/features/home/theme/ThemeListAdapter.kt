package com.admiral.demo.features.home.theme

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.admiral.demo.R
import com.admiral.themes.Theme
import com.admiral.uikit.components.text.TextView

class ThemeListAdapter(
    private val editable: Boolean,
    private val action: (Theme) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<Theme>() {
        override fun areItemsTheSame(oldItem: Theme, newItem: Theme): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Theme, newItem: Theme): Boolean {
            return oldItem == newItem
        }
    }

    private var differ: AsyncListDiffer<Theme> = AsyncListDiffer(this, diffCallback)
    var checkedTheme = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ThemeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_theme_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ThemeViewHolder -> holder.bind(checkedTheme, differ.currentList[position]) {
                if (editable) {
                    checkedTheme = it.name
                    notifyDataSetChanged()
                } else {
                    action.invoke(it)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Theme>) {
        differ.submitList(list)
        checkedTheme = if (editable) list.first().name else ""
    }

    class ThemeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(checkedTheme: String, item: Theme, action: (Theme) -> Unit) = with(itemView) {
            findViewById<TextView>(R.id.tvThemeName).text = item.name
            findViewById<TextView>(R.id.tvChosen).isVisible = checkedTheme == item.name
            setOnClickListener { action.invoke(item) }
        }
    }
}