package com.admiral.uikit.components.imageview

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.use
import com.admiral.themes.ColorPaletteEnum
import com.admiral.themes.ColorPaletteEnum.Companion.colorResToToken
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.getIntOrNull
import com.admiral.uikit.ext.parseAttrs

/**
 * Replacement of default [AppCompatImageView].
 */
open class ImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * In case tint color is null, the selected color theme will be used.
     * States: normal, disabled, pressed.
     */
    var imageTintColorState: ColorState? = null
        set(value) {
            field = value
            invalidateImageTintColors()
        }

    /**
     * Invalidate if the view should change it's tint color or not. Use when you need to disable setting the default color.
     */
    var isColored: Boolean = true
        set(value) {
            field = value
            invalidateImageTintColors()
        }

    /**
     * Int enum from palette xml. Took from attribute 'ImageView_admiralIconColorNormalEnabledPalette'.
     * It will generate a brand new color every time the Theme is being changed.
     * If it's not null and imageTintColorState is null, disabled and pressed states will take this color for the image.
     */
    var imageColorNormalEnabledPalette: ColorPaletteEnum? = null
        set(value) {
            field = value
            invalidateImageTintColors()
        }

    init {
        parseAttrs(attrs, R.styleable.ImageView).use {
            parseIconColors(it)

            imageColorNormalEnabledPalette = ColorPaletteEnum.from(
                it.getIntOrNull(R.styleable.ImageView_admiralIconColorNormalEnabledPalette)
            )
            isColored = it.getBoolean(R.styleable.ImageView_admiralIconIsColored, true)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.subscribe(this)
    }

    override fun onDetachedFromWindow() {
        ThemeManager.unsubscribe(this)
        super.onDetachedFromWindow()
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateImageTintColors()
    }

    private fun parseIconColors(a: TypedArray) {
        imageTintColorState = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.ImageView_admiralImageTintColorNormalEnabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.ImageView_admiralImageTintColorPressed
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.ImageView_admiralImageTintColorNormalDisabled
            )
        )
    }

    private fun invalidateImageTintColors() {
        imageTintList = if (isColored) {
            colorStateList(
                enabled = imageTintColorState?.normalEnabled
                    ?: imageColorNormalEnabledPalette.colorResToToken()
                    ?: ThemeManager.theme.palette.elementAccent,
                disabled = imageTintColorState?.normalDisabled
                    ?: imageColorNormalEnabledPalette.colorResToToken()?.withAlpha()
                    ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
                pressed = imageTintColorState?.pressed
                    ?: imageColorNormalEnabledPalette.colorResToToken()
                    ?: ThemeManager.theme.palette.elementAccentPressed
            )
        } else null
    }
}