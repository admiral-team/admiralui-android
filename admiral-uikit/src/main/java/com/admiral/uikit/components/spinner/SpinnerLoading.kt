package com.admiral.uikit.components.spinner

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.admiral.themes.ColorPaletteEnum
import com.admiral.themes.ColorPaletteEnum.Companion.colorResToToken
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.components.imageview.ImageView
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.drawable

class SpinnerLoading @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * [Spinner] that shows the indicator.
     */
    var spinner = Spinner(context).apply {
        layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER
        }
        indeterminateDrawable = ContextCompat.getDrawable(context, R.drawable.admiral_progress_bar_states_small)
    }

    /**
     * Sets [Spinner] style.
     */
    var isContrast = true
        set(value) {
            field = value
            adjustContrast()
        }

    /**
     * [ImageView] placed at the center of the [Spinner].
     */
    var icon = ImageView(context).apply {
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        ).apply {
            gravity = Gravity.CENTER
            setPadding(4.dpToPx(context))
        }
        imageColorNormalEnabledPalette = ColorPaletteEnum.ELEMENT_STATIC_WHITE
        setImageDrawable(drawable(R.drawable.admiral_ic_small_close_outline))
    }

    /**
     * Indicates if there should be a colored circle for the background.
     */
    var isBackgroundColored: Boolean = false
        set(value) {
            field = value
            invalidateBackground()
        }

    /**
     * [ColorPaletteEnum] for the background.
     */
    var backgroundColorPaletteEnum: ColorPaletteEnum = ColorPaletteEnum.BACKGROUND_ACCENT
        set(value) {
            field = value
            invalidateBackground()
        }

    init {
        addView(icon)
        addView(spinner)
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
        invalidateBackground()
    }

    private fun adjustContrast() {
        spinner.isContrast = isContrast

        if (isContrast) {
            icon.imageColorNormalEnabledPalette = ColorPaletteEnum.ELEMENT_STATIC_WHITE
            backgroundColorPaletteEnum = ColorPaletteEnum.ELEMENT_ACCENT
        } else {
            icon.imageColorNormalEnabledPalette = ColorPaletteEnum.ELEMENT_ACCENT
            backgroundColorPaletteEnum = ColorPaletteEnum.ELEMENT_STATIC_WHITE
        }
    }

    private fun invalidateBackground() {
        if (isBackgroundColored) {
            background = drawable(R.drawable.admiral_bg_round)
            backgroundTintList = ColorStateList.valueOf(
                backgroundColorPaletteEnum.colorResToToken() ?: ThemeManager.theme.palette.elementAccent
            )
        } else {
            background = null
        }
    }
}