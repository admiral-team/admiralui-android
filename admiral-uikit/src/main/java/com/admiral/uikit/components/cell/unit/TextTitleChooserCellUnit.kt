package com.admiral.uikit.components.cell.unit

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import androidx.core.content.res.use
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.components.cell.base.CellUnit
import com.admiral.uikit.common.components.cell.base.CellUnitType
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels

class TextTitleChooserCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr), ThemeObserver, CellUnit {

    override lateinit var unitType: CellUnitType

    /**
     * Color state for text drawable.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var drawableColor: ColorState? = null
        set(value) {
            field = value
            invalidateDrawableIconTintColors()
        }

    /**
     * Color state for text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var textColorState: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    init {
        textStyle = ThemeManager.theme.typography.title1
        gravity = Gravity.START

        parseAttrs(attrs, R.styleable.TextTitleChooserCellUnit).use {
            text = it.getText(R.styleable.TextTitleChooserCellUnit_admiralText)

            unitType = CellUnitType.from(it.getInt(R.styleable.TextTitleChooserCellUnit_admiralCellUnitType, 0))
            parseTextColors(it)
            parseDrawableColors(it)
        }

        drawable(R.drawable.admiral_ic_chevron_down_outline)?.also {
            compoundDrawablePadding = pixels(R.dimen.module_x1)
            setCompoundDrawablesWithIntrinsicBounds(null, null, it, null)
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
        super.onThemeChanged(theme)
        invalidateDrawableIconTintColors()
        invalidateTextColors()
    }

    private fun parseTextColors(a: TypedArray) {
        textColorState = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TextTitleChooserCellUnit_admiralTextColorNormalEnabled
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TextTitleChooserCellUnit_admiralTextColorNormalDisabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.TextTitleChooserCellUnit_admiralTextColorPressed
            )
        )
    }

    private fun parseDrawableColors(a: TypedArray) {
        drawableColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TextTitleChooserCellUnit_admiralIconTintColorNormalEnabled
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TextTitleChooserCellUnit_admiralIconTintColorNormalDisabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.TextTitleChooserCellUnit_admiralIconTintColorPressed
            )
        )
    }

    private fun invalidateDrawableIconTintColors() {
        val colorState = colorStateList(
            enabled = drawableColor?.normalEnabled ?: ThemeManager.theme.palette.elementPrimary,
            disabled = drawableColor?.normalDisabled ?: ThemeManager.theme.palette.elementPrimary.withAlpha(),
            pressed = drawableColor?.pressed ?: ThemeManager.theme.palette.elementPrimary
        )

        for (drawable in compoundDrawables) {
            drawable?.setTintList(colorState)
        }
    }

    private fun invalidateTextColors() {
        val colorState = ColorState(
            normalEnabled = textColorState?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = textColorState?.normalDisabled ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = textColorState?.pressed ?: ThemeManager.theme.palette.textPrimary
        )

        textColor = colorState
    }
}