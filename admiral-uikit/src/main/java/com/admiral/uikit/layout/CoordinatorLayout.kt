package com.admiral.uikit.layout

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver

open class CoordinatorLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr), ThemeObserver {

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
        backgroundTintList = ColorStateList.valueOf(ThemeManager.theme.palette.backgroundBasic)
    }
}