package com.admiral.uikit.components.chip

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.res.use
import com.google.android.material.chip.Chip
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.uikit.R
import com.admiral.uikit.ext.applyStyle
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState

open class Chip @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.chipStyle
) : Chip(ContextThemeWrapper(context, R.style.AdmiralThemeOverlay_Chip), attrs, defStyleAttr),
    ThemeObserver {

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
     * Color state for icon.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var iconColor: ColorState? = null
        set(value) {
            field = value
            invalidateCloseIconColor()
            invalidateIconColor()
        }

    init {
        parseAttrs(attrs, R.styleable.Chip).use {
            parseBackgroundColors(it)
            parseTextColors(it)
            parseIconColors(it)

            text = it.getString(R.styleable.Chip_admiralText)
            isCloseIconVisible = it.getBoolean(R.styleable.Chip_closeIconEnabled, false)
            chipIcon = it.getDrawable(R.styleable.Chip_chipIcon)
        }

        isClickable = true
        isFocusable = true

        // set to null since we don't want to show shadows
        stateListAnimator = null
        val chipHeight = pixels(R.dimen.admiral_chip_height)
        chipMinHeight = chipHeight.toFloat()
        height = chipHeight

        invalidateBackgroundColor()
        invalidateCloseIconColor()
        invalidateIconColor()
        invalidateTextColor()

        invalidatePadding()

        applyStyle(Typography.getStyle(ThemeManager.theme.typography.body1))
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
        invalidateBackgroundColor()
        invalidateCloseIconColor()
        invalidateIconColor()
        invalidateTextColor()
    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        invalidatePadding()
    }

    private fun parseBackgroundColors(a: TypedArray) {
        backgroundColor = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.Chip_admiralBackgroundColorNormalEnabled),
            pressed = a.getColorOrNull(R.styleable.Chip_admiralBackgroundColorPressed),
            normalDisabled = a.getColorOrNull(R.styleable.Chip_admiralBackgroundColorNormalDisabled)
        )
    }

    private fun parseTextColors(a: TypedArray) {
        textColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.Chip_admiralTextColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.Chip_admiralTextColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.Chip_admiralTextColorNormalDisabled,
            ),
            focused = a.getColorOrNull(
                R.styleable.Chip_admiralTextColorPressed,
            )
        )
    }

    private fun parseIconColors(a: TypedArray) {
        iconColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.Chip_admiralIconTintColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.Chip_admiralIconTintColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.Chip_admiralIconTintColorNormalDisabled,
            ),
            focused = a.getColorOrNull(
                R.styleable.Chip_admiralIconTintColorPressed,
            )
        )
    }

    private fun invalidatePadding() {
        setEnsureMinTouchTargetSize(false)

        // Available states: (where [] - chipStartPadding and chipEndPadding)
        // []icon text close[]
        // []text close[]
        // []icon text[]
        // []icon[]
        // []text[]
        // As we can see space between icon and text must be in case when text isn't empty

        chipEndPadding = pixels(R.dimen.module_x4).toFloat()
        chipStartPadding = pixels(R.dimen.module_x4).toFloat()

        closeIconEndPadding = 0f
        closeIconStartPadding = pixels(R.dimen.module_x2).toFloat()

        iconEndPadding = if (text.isEmpty()) 0f else pixels(R.dimen.module_x2).toFloat()

        textEndPadding = 0f
        textStartPadding = 0f
    }

    private fun invalidateIconColor() {
        chipIconTint = colorStateList(
            enabled = iconColor?.normalEnabled
                ?: ThemeManager.theme.palette.elementAccent,
            disabled = iconColor?.normalDisabled
                ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
            pressed = iconColor?.pressed
                ?: ThemeManager.theme.palette.elementAccentPressed
        )
    }

    private fun invalidateCloseIconColor() {
        closeIconTint = colorStateList(
            enabled = iconColor?.normalEnabled
                ?: ThemeManager.theme.palette.elementAccent,
            disabled = iconColor?.normalDisabled
                ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
            pressed = iconColor?.pressed
                ?: ThemeManager.theme.palette.elementAccentPressed
        )
    }

    private fun invalidateBackgroundColor() {
        chipBackgroundColor = colorStateList(
            enabled = backgroundColor?.normalEnabled
                ?: ThemeManager.theme.palette.backgroundSelected,
            disabled = backgroundColor?.normalDisabled
                ?: ThemeManager.theme.palette.backgroundSelected,
            pressed = backgroundColor?.pressed
                ?: ThemeManager.theme.palette.backgroundSelectedPressed
        )
    }

    private fun invalidateTextColor() {
        setTextColor(
            colorStateList(
                enabled = textColor?.normalEnabled
                    ?: ThemeManager.theme.palette.textPrimary,
                disabled = textColor?.normalDisabled
                    ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
                pressed = textColor?.pressed
                    ?: ThemeManager.theme.palette.textPrimary
            )
        )
    }
}