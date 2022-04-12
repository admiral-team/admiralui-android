package com.admiral.uikit.components.text

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.use
import androidx.core.view.doOnLayout
import com.admiral.themes.ColorPaletteEnum
import com.admiral.themes.ColorPaletteEnum.Companion.colorResToToken
import com.admiral.themes.Font
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.themes.Typography.Companion.subhead1
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.ext.applyStyle
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.colorStateListUnion
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.getIntOrNull
import com.admiral.uikit.ext.parseAttrs

/**
 * Replacement of default [AppCompatTextView].
 */
open class TextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Apply style of our [TextView]
     */
    var textStyle: Font = ThemeManager.theme.typography.body2
        set(value) {
            field = value
            applyStyle(Typography.getStyle(field))
            invalidateTextColor()
        }

    /**
     * Color state for text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var textColor: ColorState? = null
        set(value) {
            field = value
            invalidateTextColor()
        }

    /**
     * Color of text for the normal enabled state from palette.
     */
    var textColorNormalEnabledPalette: ColorPaletteEnum? = null
        set(value) {
            field = value
            invalidateTextColor()
        }

    /**
     * Color of text for the pressed state from palette.
     */
    var textColorPressedPalette: ColorPaletteEnum? = null
        set(value) {
            field = value
            invalidateTextColor()
        }

    /**
     * Standard text color, sets for all states if they are not defined.
     */
    @ColorRes
    var androidTextColor: Int? = null
        set(value) {
            field = value
            invalidateTextColor()
        }

    /**
     * Color state for CompoundDrawables.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var compoundDrawablesColorState: ColorState? = null
        set(value) {
            field = value
            if (value != null) {
                isCompoundDrawableColored = true
                invalidateCompoundDrawablesColor()
            }
        }

    /**
     * CompoundDrawables color for the normal enabled state from palette.
     */
    var compoundDrawablesNormalEnabledPalette: ColorPaletteEnum? = null
        set(value) {
            field = value
            if (value != null) {
                isCompoundDrawableColored = true
                invalidateCompoundDrawablesColor()
            }
        }

    /**
     * CompoundDrawables color for the pressed state from palette.
     */
    var compoundDrawablesPressedPalette: ColorPaletteEnum? = null
        set(value) {
            field = value
            if (value != null) {
                isCompoundDrawableColored = true
                invalidateCompoundDrawablesColor()
            }
        }

    /**
     * If the value is true, all the CompoundDrawables will be colored.
     * Otherwise they will have default drawable's color.
     */
    var isCompoundDrawableColored: Boolean = false

    init {
        parseAttrs(attrs, R.styleable.TextView).use {
            textStyle = Typography.getStyleById(it.getInt(R.styleable.TextView_admiralTextStyle, subhead1))

            textColor = ColorState(
                normalEnabled = it.getColorOrNull(R.styleable.TextView_admiralTextColorNormalEnabled),
                normalDisabled = it.getColorOrNull(R.styleable.TextView_admiralTextColorNormalDisabled),
                pressed = it.getColorOrNull(R.styleable.TextView_admiralTextColorPressed),
            )
            textColorNormalEnabledPalette =
                ColorPaletteEnum.from(it.getIntOrNull(R.styleable.TextView_admiralTextColorNormalEnabledPalette))
            textColorPressedPalette =
                ColorPaletteEnum.from(it.getIntOrNull(R.styleable.TextView_admiralTextColorPressedPalette))
            compoundDrawablesNormalEnabledPalette =
                ColorPaletteEnum.from(it.getIntOrNull(R.styleable.TextView_admiralCompoundDrawablesNormalEnabledPalette))
            compoundDrawablesPressedPalette =
                ColorPaletteEnum.from(it.getIntOrNull(R.styleable.TextView_admiralCompoundDrawablesPressedPalette))
            androidTextColor = it.getColorOrNull(R.styleable.TextView_android_textColor)
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
        invalidateTextColor()
        invalidateCompoundDrawablesColor()
    }

    override fun setCompoundDrawables(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        super.setCompoundDrawables(left, top, right, bottom)
        invalidateCompoundDrawablesColor()
    }

    override fun setCompoundDrawablesWithIntrinsicBounds(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
        invalidateCompoundDrawablesColor()
    }

    private fun invalidateCompoundDrawablesColor() {
        doOnLayout {
            compoundDrawables.forEach {
                if (isCompoundDrawableColored) {
                    it?.setTintList(
                        colorStateList(
                            enabled = compoundDrawablesColorState?.normalEnabled
                                ?: compoundDrawablesNormalEnabledPalette?.colorResToToken()
                                ?: ThemeManager.theme.palette.elementPrimary,
                            disabled = compoundDrawablesColorState?.normalDisabled
                                ?: compoundDrawablesNormalEnabledPalette?.colorResToToken()
                                    ?.withAlpha()
                                ?: ThemeManager.theme.palette.elementPrimary.withAlpha(),
                            pressed = compoundDrawablesColorState?.pressed
                                ?: compoundDrawablesPressedPalette?.colorResToToken()
                                ?: compoundDrawablesNormalEnabledPalette?.colorResToToken()
                                ?: ThemeManager.theme.palette.elementPrimary
                        )
                    )
                }
            }
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        invalidateCompoundDrawablesColor()
    }

    private fun invalidateTextColor() {
        setTextColor(
            colorStateListUnion(
                normalEnabled = textColor?.normalEnabled
                    ?: textColorNormalEnabledPalette?.colorResToToken()
                    ?: androidTextColor
                    ?: ThemeManager.theme.palette.textPrimary,
                normalDisabled = textColor?.normalDisabled
                    ?: textColorNormalEnabledPalette?.colorResToToken()
                    ?: androidTextColor
                    ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
                pressed = textColor?.pressed
                    ?: textColorPressedPalette?.colorResToToken()
                    ?: textColorNormalEnabledPalette?.colorResToToken()
                    ?: androidTextColor
                    ?: ThemeManager.theme.palette.textPrimary,
                checkedEnabled = textColor?.checkedEnabled
                    ?: textColorNormalEnabledPalette?.colorResToToken()
                    ?: androidTextColor
                    ?: ThemeManager.theme.palette.textAccent,
                checkedDisabled = textColor?.checkedDisabled
                    ?: textColorNormalEnabledPalette?.colorResToToken()
                    ?: androidTextColor
                    ?: ThemeManager.theme.palette.textAccent.withAlpha(),
            )
        )
    }
}
