package com.admiral.uikit.components.cell.unit

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.themes.Typography.Companion.body1
import com.admiral.uikit.R
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.core.components.cell.base.CellUnit
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.resources.R as res
import com.admiral.uikit.core.R as core

class TextButtonChooserCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr), ThemeObserver, CellUnit {

    override lateinit var unitType: CellUnitType

    var icon: Drawable? =
        ContextCompat.getDrawable(context, res.drawable.admiral_ic_chevron_down_outline)
        set(value) {
            field = value
            setDrawableEnd(icon)
            invalidateDrawableIconTintColors()
        }

    /**
     * Color state for text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var textColors: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    /**
     * Color state for icon' tint.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var iconTintColors: ColorState? = null
        set(value) {
            field = value
            invalidateDrawableIconTintColors()
        }

    init {
        gravity = Gravity.START

        parseAttrs(attrs, R.styleable.TextButtonChooserCellUnit).use {
            text = it.getText(R.styleable.TextButtonChooserCellUnit_admiralText)
            textStyle =
                Typography.getStyleById(
                    it.getInt(
                        R.styleable.TextButtonChooserCellUnit_admiralTextStyle,
                        body1
                    )
                )

            icon = it.getDrawable(R.styleable.TextButtonChooserCellUnit_admiralIcon)
            unitType = CellUnitType.from(
                it.getInt(
                    R.styleable.TextButtonChooserCellUnit_admiralCellUnitType,
                    0
                )
            )

            parseTextColors(it)
            parseIconTintColors(it)
        }

        isClickable = true
        isFocusable = true
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
        invalidateTextColors()
        invalidateDrawableIconTintColors()
    }

    private fun parseTextColors(a: TypedArray) {
        textColors = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TextButtonChooserCellUnit_admiralTextColorNormalEnabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.TextButtonChooserCellUnit_admiralTextColorPressed
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TextButtonChooserCellUnit_admiralTextColorNormalDisabled
            )
        )
    }

    private fun parseIconTintColors(a: TypedArray) {
        iconTintColors = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TextButtonChooserCellUnit_admiralIconTintColorNormalEnabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.TextButtonChooserCellUnit_admiralIconTintColorPressed
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TextButtonChooserCellUnit_admiralIconTintColorNormalDisabled
            )
        )
    }

    private fun invalidateTextColors() {
        val colorState = ColorState(
            normalEnabled = textColors?.normalEnabled ?: ThemeManager.theme.palette.textAccent,
            normalDisabled = textColors?.normalDisabled
                ?: ThemeManager.theme.palette.textAccent.withAlpha(),
            pressed = textColors?.pressed ?: ThemeManager.theme.palette.textAccentPressed
        )

        textColor = colorState
    }

    private fun setDrawableEnd(drawable: Drawable?) {
        drawable?.colored(textColors?.normalEnabled ?: ThemeManager.theme.palette.textAccent).also {
            compoundDrawablePadding = pixels(core.dimen.module_x1)
            setCompoundDrawablesWithIntrinsicBounds(null, null, it, null)
        }
    }

    private fun invalidateDrawableIconTintColors() {
        val colorState = colorStateList(
            enabled = iconTintColors?.normalEnabled ?: ThemeManager.theme.palette.textAccent,
            pressed = iconTintColors?.pressed ?: ThemeManager.theme.palette.textAccentPressed,
            disabled = iconTintColors?.normalDisabled
                ?: ThemeManager.theme.palette.textAccent.withAlpha()
        )

        for (drawable in compoundDrawables) {
            drawable?.setTintList(colorState)
        }
    }
}