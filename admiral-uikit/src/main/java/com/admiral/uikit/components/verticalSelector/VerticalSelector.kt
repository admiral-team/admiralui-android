package com.admiral.uikit.components.verticalSelector

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.uikit.R
import com.admiral.uikit.databinding.AdmiralVerticalSelectorBinding
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.layout.ConstraintLayout

internal class VerticalSelector @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = AdmiralVerticalSelectorBinding
        .inflate(LayoutInflater.from(context), this)

    private val verticalSelectorAdapter = VerticalSelectorAdapter(context)

    private val snapHelper = LinearSnapHelper()

    private val selectorItemHeight: Int
        get() = pixels(R.dimen.admiral_vertical_selector_item_height)

    private val layoutManager: LinearLayoutManager
        get() = binding.recycler.layoutManager as LinearLayoutManager

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        @Suppress("ReturnCount")
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val currentMode = mode

            if (currentMode == Mode.Unidentified)
                return

            val view = snapHelper.findSnapView(layoutManager) ?: return
            val position = layoutManager.getPosition(view)
            val item = verticalSelectorAdapter.currentList[position] ?: return

            if (item is VerticalSelectorItem.Selectable) {
                selectable = item
            }

            if (currentMode is Mode.Infinite) {
                val visibleItemCount = recyclerView.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val needMoreTopItems = firstVisibleItemPosition < ITEMS_GENERATOR_THRESHOLD
                val needMoreBottomItems =
                    totalItemCount - visibleItemCount - firstVisibleItemPosition < ITEMS_GENERATOR_THRESHOLD

                val newList = verticalSelectorAdapter.currentList.toMutableList()

                when {
                    needMoreTopItems -> {
                        val currentTopItem =
                            newList.firstOrNull() as? VerticalSelectorItem.Selectable
                        if (newList.isNotEmpty()) {
                            newList.removeLast()
                        }
                        newList.add(
                            0,
                            currentMode.itemsGenerator.generateItemOnTop(currentTopItem)
                        )
                    }
                    needMoreBottomItems -> {
                        val currentBottomItem =
                            newList.lastOrNull() as? VerticalSelectorItem.Selectable
                        if (newList.isNotEmpty()) {
                            newList.removeFirst()
                        }
                        newList.add(
                            currentMode.itemsGenerator.generateItemOnBottom(currentBottomItem)
                        )
                    }
                }

                verticalSelectorAdapter.submitList(newList)
            }
        }
    }

    var mode: Mode = Mode.Unidentified
        set(value) {
            field = value
            when (value) {
                is Mode.Finite -> {
                    setItemsForFiniteMode(value)
                }
                is Mode.Infinite -> {
                    setItemsForInfiniteMode(value)
                }
                is Mode.Unidentified -> {
                    verticalSelectorAdapter.submitList(emptyList())
                }
            }
        }

    var onItemSelected: (VerticalSelectorItem.Selectable) -> Unit = {}

    var selectable: VerticalSelectorItem.Selectable? = null
        set(value) {
            if (field != value) {
                field = value
                value?.let { onItemSelected(it) }
            }
        }

    init {
        initRecycler()
    }

    override fun onThemeChanged(theme: Theme) {
        super.onThemeChanged(theme)
        invalidateDividersColor()
        invalidateGradientsColor()
    }

    private fun invalidateGradientsColor() {
        val color = ThemeManager.theme.palette.backgroundBasic
        with(binding) {
            topGradient.background = (topGradient.background as GradientDrawable)
                .mutate().apply { setTint(color) }
            bottomGradient.background = (bottomGradient.background as GradientDrawable)
                .mutate().apply { setTint(color) }
        }
    }

    private fun invalidateDividersColor() {
        val color = ThemeManager.theme.palette.elementAccent
        with(binding) {
            topDivider.setBackgroundColor(color)
            bottomDivider.setBackgroundColor(color)
        }
    }

    private fun initRecycler() {
        with(binding) {
            recycler.adapter = verticalSelectorAdapter
            snapHelper.attachToRecyclerView(recycler)
            recycler.addOnScrollListener(onScrollListener)
        }
    }

    @Suppress("MagicNumber")
    private fun setItemsForFiniteMode(mode: Mode.Finite) {
        post {
            val fakeItemsHeight = (height - selectorItemHeight) / 2
            val fakeItem = VerticalSelectorItem.Fake(fakeItemsHeight)
            val items = mutableListOf<VerticalSelectorItem>().apply {
                add(fakeItem)
                addAll(mode.items)
                add(fakeItem)
            }
            val position = mode.currentItemIndex + 1

            verticalSelectorAdapter.submitList(items) {
                layoutManager.scrollToPositionWithOffset(position, fakeItemsHeight)
            }
        }
    }

    private fun setItemsForInfiniteMode(mode: Mode.Infinite) {
        val items = mutableListOf<VerticalSelectorItem>()
        val centralPosition = INITIAL_GENERATION_ITEM_COUNT / 2

        for (i in 0 until INITIAL_GENERATION_ITEM_COUNT) {
            if (i < centralPosition) {
                val currentTopItem =
                    items.firstOrNull() as? VerticalSelectorItem.Selectable
                items.add(0, mode.itemsGenerator.generateItemOnTop(currentTopItem))
            } else {
                val currentBottomItem =
                    items.lastOrNull() as? VerticalSelectorItem.Selectable
                items.add(mode.itemsGenerator.generateItemOnBottom(currentBottomItem))
            }
        }

        post {
            verticalSelectorAdapter.submitList(items) {
                val offset = (height + selectorItemHeight) / 2
                layoutManager.scrollToPositionWithOffset(centralPosition, offset)
            }
        }
    }

    interface VerticalSelectorItemsGenerator {
        fun generateItemOnTop(
            currentTopItem: VerticalSelectorItem.Selectable?
        ): VerticalSelectorItem.Selectable

        fun generateItemOnBottom(
            currentBottomItem: VerticalSelectorItem.Selectable?
        ): VerticalSelectorItem.Selectable
    }

    sealed class Mode {
        object Unidentified : Mode()

        data class Finite(
            val items: List<VerticalSelectorItem>,
            val currentItemIndex: Int
        ) : Mode()

        data class Infinite(val itemsGenerator: VerticalSelectorItemsGenerator) : Mode()
    }

    private companion object {
        const val ITEMS_GENERATOR_THRESHOLD = 2
        const val INITIAL_GENERATION_ITEM_COUNT = 30
    }
}