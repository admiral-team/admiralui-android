package com.admiral.uikit.components.appbar

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

class AppbarStepper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(
    ContextThemeWrapper(
        context,
        com.google.android.material.R.style.Widget_AppCompat_Toolbar
    ), attrs, defStyleAttr
), ThemeObserver {

    /**
     * Color of tabs in activated state.
     * In case color is null, the selected color theme will be used.
     */
    @ColorRes
    var tabColorAccent: Int? = null
        set(value) {
            field = value
            initializeTabViews()
        }

    /**
     * Color of tabs in default state.
     * In case color is null, the selected color theme will be used.
     */
    @ColorRes
    var tabColorDefault: Int? = null
        set(value) {
            field = value
            initializeTabViews()
        }

    var itemsCount = 2
        set(value) {
            if (value < 1) throw IllegalArgumentException("items count must be equal or more than 1")

            field = value
            initializeTabViews()
        }

    var selectedItemId = 0
        set(value) {
            if (itemsContainerView.getChildAt(field) != null) {
                itemsContainerView.getChildAt(field).isSelected = false
                field = value
                itemsContainerView.getChildAt(field).isSelected = true
            } else {
                throw IllegalArgumentException()
            }
        }

    private val itemsContainerView = LinearLayout(context).apply {
        layoutParams = LayoutParams(TABS_WIDTH.dpToPx(context), LayoutParams.MATCH_PARENT).apply {
            gravity = Gravity.CENTER_HORIZONTAL
        }
        orientation = LinearLayout.HORIZONTAL

        showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        dividerDrawable =
            ContextCompat.getDrawable(context, R.drawable.admiral_devider_space_horizontal_8dp)
    }

    init {
        title = ""
        addView(itemsContainerView)
        selectedItemId = 0

        parseAttrs(attrs, R.styleable.AppbarStepper).use {
            itemsCount = it.getInt(R.styleable.AppbarStepper_admiralTabsCount, 2)
            tabColorAccent = it.getColorOrNull(R.styleable.AppbarStepper_admiralTabsColorAccent)
            tabColorDefault = it.getColorOrNull(R.styleable.AppbarStepper_admiralTabsColorDefault)
        }
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
        initializeTabViews()
    }

    private fun initializeTabViews() {
        itemsContainerView.removeAllViews()
        val tabWidth = (TABS_WIDTH - (itemsCount - 1) * TABS_SPACE) / itemsCount

        for (i in 1..itemsCount) {
            itemsContainerView.addView(
                View(context).apply {
                    background = drawable(R.drawable.admiral_bg_rectangle_8dp)?.mutate()?.also {
                        it.setTintList(
                            ColorStateList(
                                arrayOf(
                                    intArrayOf(android.R.attr.state_selected),
                                    intArrayOf()
                                ),
                                intArrayOf(
                                    tabColorAccent ?: ThemeManager.theme.palette.elementAccent,
                                    tabColorDefault ?: ThemeManager.theme.palette.elementAdditional
                                )
                            )
                        )
                    }
                    layoutParams = FrameLayout.LayoutParams(
                        tabWidth.dpToPx(context),
                        TABS_HEIGHT.dpToPx(context)
                    )
                }
            )
        }
    }

    private companion object {
        const val TABS_WIDTH = 136
        const val TABS_HEIGHT = 4
        const val TABS_SPACE = 8
    }
}