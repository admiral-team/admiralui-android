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
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

@Deprecated("Use TextCellUnit with text style attribute")
class TextTitleCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr), ThemeObserver, CellUnit {

    override lateinit var unitType: CellUnitType

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

        parseAttrs(attrs, R.styleable.TextTitleCellUnit).use {
            text = it.getText(R.styleable.TextTitleCellUnit_admiralText)

            unitType = CellUnitType.from(it.getInt(R.styleable.TextTitleCellUnit_admiralCellUnitType, 0))
            parseTextColors(it)
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
        invalidateTextColors()
    }

    private fun parseTextColors(a: TypedArray) {
        textColorState = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TextTitleCellUnit_admiralTextColorNormalEnabled
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TextTitleCellUnit_admiralTextColorNormalDisabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.TextTitleCellUnit_admiralTextColorPressed
            )
        )
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