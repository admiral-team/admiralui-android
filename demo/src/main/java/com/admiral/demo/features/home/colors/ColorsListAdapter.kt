package com.admiral.demo.features.home.colors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.admiral.demo.R
import com.admiral.themes.ThemeManager
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.text.TextView

class ColorsListAdapter(
    private val editable: Boolean,
    private val action: (ColorModel) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<ColorListModel>() {
        override fun areItemsTheSame(oldItem: ColorListModel, newItem: ColorListModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ColorListModel, newItem: ColorListModel): Boolean {
            return oldItem == newItem
        }
    }

    private var differ: AsyncListDiffer<ColorListModel> = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TITLE -> TitleViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_color_title_row,
                    parent,
                    false
                ).apply {
                    findViewById<TextView>(R.id.textTitle).apply {
                        textStyle = ThemeManager.theme.typography.headline
                        textColor =
                            ColorState(normalEnabled = ThemeManager.theme.palette.textSecondary)
                    }
                }
            )

            else -> ColorViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_color_row,
                    parent,
                    false
                ).apply {
                    findViewById<TextView>(R.id.tvColorHex).apply {
                        textStyle = ThemeManager.theme.typography.subhead3
                        textColor =
                            ColorState(normalEnabled = ThemeManager.theme.palette.textSecondary)
                    }
                }
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ColorViewHolder -> holder.bind(
                differ.currentList[position] as ColorModel,
                editable,
                action
            )

            is TitleViewHolder -> holder.bind(differ.currentList[position] as TitleModel)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (differ.currentList[position]) {
            is TitleModel -> TITLE
            is ColorModel -> COLOR
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<ColorListModel>) {
        differ.submitList(list)
    }

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: TitleModel) {
            (itemView as TextView).text = item.title
        }
    }

    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ColorModel, editable: Boolean, action: (ColorModel) -> Unit) =
            with(itemView) {
                findViewById<View>(R.id.backgroundViewColor).background.setTint(item.color)
                findViewById<TextView>(R.id.tvColorName).text = item.name
                findViewById<TextView>(R.id.tvColorHex).text = item.hex
                findViewById<View>(R.id.backgroundViewColorBorder).isVisible =
                    item.hex.contains("FFFFFF")

                if (editable) {
                    findViewById<ImageView>(R.id.imageViewChevron).isVisible = true
                    setOnClickListener { action.invoke(item) }
                } else {
                    findViewById<ImageView>(R.id.imageViewChevron).isVisible = false
                    setOnClickListener(null)
                }
            }
    }

    private companion object {
        private const val TITLE = 0
        private const val COLOR = 1
    }
}