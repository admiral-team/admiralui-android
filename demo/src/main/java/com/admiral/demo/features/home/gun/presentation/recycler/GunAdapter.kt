package com.admiral.demo.features.home.gun.presentation.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.admiral.demo.R
import com.admiral.demo.databinding.ItemGunButtonBinding
import com.admiral.demo.databinding.ItemGunStandrdTabsBinding
import com.admiral.demo.databinding.ItemGunTextFieldBinding
import com.admiral.demo.features.home.gun.presentation.model.GunItem
import com.admiral.uikit.components.tabs.StandardTab

class GunAdapter(
    context: Context,
    private val onLongClickAction: (id: String) -> Unit
) : ListAdapter<GunItem, RecyclerView.ViewHolder>(
    GunItemDiffCallback()
) {
    private val layoutInflater = LayoutInflater.from(context)

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is GunItem.Button -> BUTTON_TYPE
            is GunItem.TextField -> TEXT_FIELD_TYPE
            is GunItem.StandardTabs -> STANDARD_TABS_TYPE
            else -> throw IllegalStateException("Unknown GunItem type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            BUTTON_TYPE -> {
                val binding = ItemGunButtonBinding.inflate(
                    layoutInflater, parent, false
                )
                GunButtonViewHolder(binding)
            }
            TEXT_FIELD_TYPE -> {
                val binding = ItemGunTextFieldBinding.inflate(
                    layoutInflater, parent, false
                )
                GunTextFieldViewHolder(binding)
            }
            STANDARD_TABS_TYPE -> {
                val binding = ItemGunStandrdTabsBinding.inflate(
                    layoutInflater, parent, false
                )
                GunStandardTabsViewHolder(binding)
            }
            else -> throw IllegalStateException("Unknown viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GunButtonViewHolder -> {
                holder.bind(getItem(position) as GunItem.Button)
            }
            is GunTextFieldViewHolder -> {
                holder.bind(getItem(position) as GunItem.TextField)
            }
            is GunStandardTabsViewHolder -> {
                holder.bind(getItem(position) as GunItem.StandardTabs)
            }
            else -> throw IllegalStateException("Unknown ViewHolder")
        }
    }

    // region ViewHolders
    inner class GunButtonViewHolder(
        val binding: ItemGunButtonBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        lateinit var item: GunItem.Button

        init {
            binding.root.setOnLongClickListener {
                onLongClickAction(item.id)
                true
            }
        }

        fun bind(item: GunItem.Button) {
            this.item = item

            binding.button.apply {
                text = item.text
                buttonSize = item.size
                buttonStyle = item.style
                isEnabled = item.isEnabled
                isLoading = item.isLoading
                drawableEnd = if (item.isIconEnabled) {
                    ContextCompat.getDrawable(context, R.drawable.admiral_ic_web_outline)
                } else null
            }
        }
    }

    inner class GunTextFieldViewHolder(
        val binding: ItemGunTextFieldBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        lateinit var item: GunItem.TextField

        init {
            binding.root.setOnLongClickListener {
                onLongClickAction(item.id)
                true
            }
        }

        fun bind(item: GunItem.TextField) {
            this.item = item

            binding.textField.apply {
                inputText = item.text
                isEnabled = item.isEnabled
                isError = item.isError
                isEditEnabled = item.isReadOnly.not()
                additionalText = item.additionalText
                optionalText = item.optionalLabel
                icon = if (item.isIconEnabled) {
                    ContextCompat.getDrawable(context, R.drawable.admiral_ic_web_outline)
                } else null
            }
        }
    }

    inner class GunStandardTabsViewHolder(
        val binding: ItemGunStandrdTabsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        lateinit var item: GunItem.StandardTabs

        init {
            binding.root.setOnLongClickListener {
                onLongClickAction(item.id)
                true
            }
        }

        fun bind(item: GunItem.StandardTabs) {
            this.item = item

            binding.tabs.apply {
                isEnabled = item.isEnabled
                removeAllViews()
                item.titles.forEach { title ->
                    addView(
                        StandardTab(context = this.context).apply {
                            text = title
                        }
                    )
                }
                post {
                    children.forEach {
                        it.updateLayoutParams<LinearLayoutCompat.LayoutParams> {
                            weight = 1.0f
                        }
                    }
                }
            }
        }
    }
    // endregion

    companion object {
        const val BUTTON_TYPE = 0
        const val TEXT_FIELD_TYPE = 1
        const val STANDARD_TABS_TYPE = 2
    }
}