package com.admiral.uikit.components.control

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.res.use
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.common.foundation.ColorState

class LinerPageControl @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TabLayout(context, attrs, defStyleAttr), ThemeObserver {

    private var viewPager: ViewPager? = null
        set(value) {
            field = value
            invalidateTabs()
        }

    /**
     * In case tint color is null, the selected color theme will be used.
     * States: normal, disabled.
     */
    var backgroundColors: ColorState? = null
        set(value) {
            field = value
            for (i in 0 until tabCount) {
                val tabAt = getTabAt(i)
                tabAt?.customView?.background = invalidateBackground()
            }
        }

    init {
        tabGravity = GRAVITY_CENTER
        setSelectedTabIndicator(null)
        tabRippleColor = null

        parseAttrs(attrs, R.styleable.LinerPageControl).use {
            backgroundColors = ColorState(
                normalEnabled = it.getColorOrNull(R.styleable.LinerPageControl_admiralBackgroundColorNormalEnabled),
                normalDisabled = it.getColorOrNull(R.styleable.LinerPageControl_admiralBackgroundColorNormalDisabled)
            )
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
        for (i in 0 until tabCount) {
            val tabAt = getTabAt(i)
            tabAt?.customView?.background = invalidateBackground()
        }
    }

    fun removeTab(position: Int) {
        removeTabAt(position)
    }

    fun addTab() {
        addTab(
            newTab().apply {
                createCustomTab()
            }
        )
    }

    fun setTabsCount(count: Int) {
        if (count > tabCount) {
            for (i in tabCount until count) {
                addTab()
            }
        } else {
            for (i in count until tabCount) {
                removeTab(0)
            }
        }
    }

    private fun invalidateTabs() {
        for (i in 0 until tabCount) {
            val tabAt = getTabAt(i)
            tabAt?.createCustomTab()
        }
    }

    private fun Tab.createCustomTab() {
        ViewCompat.setPaddingRelative(
            this.view,
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TAB_PADDING, resources.displayMetrics)
                .toInt(),
            0,
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TAB_PADDING, resources.displayMetrics)
                .toInt(),
            0
        )

        customView = View(context).apply {
            setPadding(0, 0, 0, 0)
            background = invalidateBackground()

            layoutParams = LayoutParams(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    VIEW_SIZE_WIDTH,
                    resources.displayMetrics
                ).toInt(),
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    VIEW_SIZE_HEIGHT,
                    resources.displayMetrics
                ).toInt()
            )
        }
    }

    private fun invalidateBackground(): Drawable? {
        return drawable(R.drawable.admiral_bg_rectangle_8dp)?.mutate()?.also {
            it.setTintList(
                ColorStateList(
                    arrayOf(
                        intArrayOf(android.R.attr.state_selected),
                        intArrayOf()
                    ),
                    intArrayOf(
                        backgroundColors?.normalEnabled ?: ThemeManager.theme.palette.elementSecondary,
                        backgroundColors?.normalDisabled ?: ThemeManager.theme.palette.elementAdditional
                    )
                )
            )
        }
    }

    override fun setupWithViewPager(viewPager: ViewPager?) {
        super.setupWithViewPager(viewPager)
        this.viewPager = viewPager
    }

    private companion object {
        const val VIEW_SIZE_WIDTH = 16f
        const val VIEW_SIZE_HEIGHT = 4f
        const val TAB_PADDING = 4f
    }
}