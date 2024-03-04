package com.admiral.uikit.components.toolbar

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.MenuInflater
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.children
import androidx.core.view.updatePadding
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.components.badge.Badge
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.view.checkable.CheckableGroup
import com.admiral.uikit.core.R as core

class Toolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CheckableGroup(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Color state for background.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var backgroundColor: ColorState? = null
        set(value) {
            field = value
            invalidateBackgroundColor()
        }

    private val items = mutableListOf<ToolbarItem>()

    init {
        background = ContextCompat.getDrawable(context, R.drawable.admiral_bg_rectangle_16dp)
        orientation = HORIZONTAL
        showDividers = SHOW_DIVIDER_MIDDLE
        elevation = pixels(R.dimen.admiral_toolbar_elevation).toFloat()

        parseAttrs(attrs, R.styleable.Toolbar).use {
            if (it.hasValue(R.styleable.Toolbar_menu)) {
                parseMenu(it)
            }
        }

        updatePadding(
            left = pixels(core.dimen.module_x2),
            right = pixels(core.dimen.module_x2)
        )

        onThemeChanged(ThemeManager.theme)
    }

    private fun parseMenu(typedArray: TypedArray) {
        val menu = PopupMenu(context, this).menu

        MenuInflater(context).inflate(typedArray.getResourceId(R.styleable.Toolbar_menu, 0), menu)

        menu
            ?.children
            ?.map { item ->
                ToolbarItem(
                    text = item.title,
                    icon = item.icon,
                )
            }
            ?.let(items::addAll)

        items.forEach(::createView)

        requestOrientation()
    }

    /**
     * Subscribe for theme change.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.subscribe(this)
    }

    /**
     * Unsubscribe for theme change.
     */
    override fun onDetachedFromWindow() {
        ThemeManager.unsubscribe(this)
        super.onDetachedFromWindow()
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateBackgroundColor()
        invalidateItemsColor()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        children.forEach {
            it.isEnabled = enabled
        }
    }

    /**
     * Remove item from the toolbar.
     *
     * @param position of removing item.
     */
    fun removeItem(position: Int) {
        items.removeAt(position)
        val view = getChildAt(position)
        if (view is ToolbarItemView && view.isChecked) {
            clearCheck()
        }
        removeViewAt(position)

        if (items.size == 1) {
            (getChildAt(START_INDEX) as? ToolbarItemView)?.requestHorizontal()
        }
    }

    /**
     * Add new item to the toolbar.
     *
     * @param toolbarItem new [ToolbarItem] for the toolbar.
     */
    fun addItem(toolbarItem: ToolbarItem) {
        items.add(toolbarItem)
        createView(toolbarItem)
        requestOrientation()
    }

    /**
     * Replace all toolbar items.
     *
     * @param items new list of [ToolbarItem].
     */
    fun replaceAll(items: List<ToolbarItem>) {
        removeAllViews()
        clearCheck()
        this.items.clear()
        this.items.addAll(items)
        this.items.forEach(::createView)
    }

    /**
     * Add badge to the menu item.
     *
     * @param position defines items' position.
     * @param badge is a [Badge] to be added.
     */
    fun setBadge(position: Int, badge: Badge) {
        val toolbarItemView = getChildAt(position) as ToolbarItemView

        toolbarItemView.badge = badge
    }

    /**
     * Removes badge from the menu item.
     *
     * @param position defines items' position.
     */
    fun removeBadge(position: Int) {
        val toolbarItemView = getChildAt(position) as ToolbarItemView

        toolbarItemView.badge = null
    }

    private fun createView(toolbarItem: ToolbarItem) {
        val defaultColorState = ColorState(
            normalEnabled = ThemeManager.theme.palette.elementContrast,
            normalDisabled = ThemeManager.theme.palette.elementContrast.withAlpha(),
            checkedEnabled = ThemeManager.theme.palette.elementAccent,
            checkedDisabled = ThemeManager.theme.palette.elementAccent.withAlpha()
        )

        val iconTab = ToolbarItemView(context).apply {
            textColorState = toolbarItem.textColorState ?: defaultColorState
            iconColorState = toolbarItem.iconColorState ?: defaultColorState
            text = toolbarItem.text.toString()
            icon = toolbarItem.icon
        }

        addView(iconTab)
    }

    private fun invalidateItemsColor() {
        val defaultColorState = ColorState(
            normalEnabled = ThemeManager.theme.palette.elementContrast,
            normalDisabled = ThemeManager.theme.palette.elementContrast.withAlpha(),
            checkedEnabled = ThemeManager.theme.palette.elementAccent,
            checkedDisabled = ThemeManager.theme.palette.elementAccent.withAlpha()
        )

        children
            .filter { it is ToolbarItemView }
            .map { it as ToolbarItemView }
            .associateWith { toolbarItemView -> items.find { it.id == toolbarItemView.id } }
            .forEach { (toolbarItemView, toolbarItem) ->
                toolbarItemView.textColorState = toolbarItem?.textColorState ?: defaultColorState
                toolbarItemView.iconColorState = toolbarItem?.iconColorState ?: defaultColorState
            }
    }

    private fun requestOrientation() {
        if (items.size == 1) {
            (getChildAt(START_INDEX) as? ToolbarItemView)?.requestHorizontal()
        } else {
            (getChildAt(START_INDEX) as? ToolbarItemView)?.requestVertical()
        }
    }

    private fun invalidateBackgroundColor() {
        background.setTint(
            backgroundColor?.normalEnabled ?: ThemeManager.theme.palette.backgroundAccentDark
        )
    }

    private companion object {
        private const val START_INDEX = 0
    }
}