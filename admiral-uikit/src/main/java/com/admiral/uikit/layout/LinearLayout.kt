package com.admiral.uikit.layout

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.admiral.themes.ColorPaletteEnum
import com.admiral.themes.ColorPaletteEnum.Companion.colorResToToken
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.getIntOrNull
import com.admiral.uikit.ext.parseAttrs

open class LinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Color state for background.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var backgroundColors: ColorState? = null
        set(value) {
            field = value
            invalidateBackground()
        }

    /**
     * If this value is true background will be transparent
     */
    var isBackgroundTransparent: Boolean = false
        set(value) {
            field = value
            invalidateBackground()
        }

    /**
     * Color of background from the palette for the normal enabled state.
     */
    var backgroundColorNormalEnabledPalette: ColorPaletteEnum? = null
        set(value) {
            field = value
            invalidateBackground()
        }

    /**
     * Color of background from the palette for the pressed state.
     */
    var backgroundColorPressedPalette: ColorPaletteEnum? = null
        set(value) {
            field = value
            invalidateBackground()
        }

    /**
     * Color of background from the palette for the normal enabled state.
     */
    @Deprecated("Use `backgroundColorNormalEnabledPalette` instead")
    @Suppress("VariableNaming")
    var backgroundСolorNormalEnabledPalette: ColorPaletteEnum? = null
        get() = backgroundColorNormalEnabledPalette
        set(value) {
            field = value
            backgroundColorNormalEnabledPalette = value
        }

    init {
        parseAttrs(attrs, R.styleable.LinearLayout).use {
            isBackgroundTransparent = it.getBoolean(
                R.styleable.LinearLayout_admiralBackgroundIsTransparent,
                false
            )
            backgroundColorNormalEnabledPalette = ColorPaletteEnum.from(
                it.getIntOrNull(
                    R.styleable.LinearLayout_admiralBackgroundColorNormalEnabledPalette
                )
            )
            backgroundColorPressedPalette = ColorPaletteEnum.from(
                it.getIntOrNull(
                    R.styleable.LinearLayout_admiralBackgroundColorPressedPalette
                )
            )
        }
        invalidateBackground()
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

    private fun invalidateBackground() {
        if (background != null) {
            if (isBackgroundTransparent) {
                backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(context, R.color.transparent)
                )
            } else {
                background =
                    background.mutate().colored(
                        colorStateList(
                            enabled = backgroundColors?.normalEnabled
                                ?: backgroundColorNormalEnabledPalette.colorResToToken()
                                ?: ThemeManager.theme.palette.backgroundBasic,
                            disabled = backgroundColors?.normalDisabled
                                ?: backgroundColorNormalEnabledPalette.colorResToToken()
                                    ?.withAlpha()
                                ?: backgroundColors?.normalEnabled
                                ?: ThemeManager.theme.palette.backgroundBasic,
                            pressed = backgroundColors?.pressed
                                ?: backgroundColorPressedPalette.colorResToToken()
                                ?: backgroundColors?.normalEnabled
                                ?: ThemeManager.theme.palette.backgroundBasic
                        )
                    )
            }
        } else {
            if (isBackgroundTransparent) {
                setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
            } else {
                setBackgroundColor(
                    backgroundColors?.normalEnabled
                        ?: backgroundColorNormalEnabledPalette.colorResToToken()
                        ?: ThemeManager.theme.palette.backgroundBasic
                )
            }
        }
    }
}