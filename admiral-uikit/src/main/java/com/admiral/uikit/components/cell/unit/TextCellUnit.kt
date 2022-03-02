package com.admiral.uikit.components.cell.unit

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import androidx.core.content.res.use
import com.admiral.themes.ColorPaletteEnum
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.themes.Typography.Companion.body1
import com.admiral.uikit.R
import com.admiral.uikit.common.components.cell.base.CellUnit
import com.admiral.uikit.common.components.cell.base.CellUnitType
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.getIntOrNull
import com.admiral.uikit.ext.parseAttrs

class TextCellUnit @JvmOverloads constructor(
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

    var text: String = ""
        set(value) {
            setText(value)
        }

    init {
        gravity = Gravity.START

        parseAttrs(attrs, R.styleable.TextCellUnit).use {
            it.getText(R.styleable.TextCellUnit_admiralText)?.let { charSequence ->
                text = charSequence.toString()
            }
            textStyle = Typography.getStyleById(
                it.getInt(R.styleable.TextCellUnit_admiralTextStyle, body1)
            )

            unitType = CellUnitType.from(it.getInt(R.styleable.TextCellUnit_admiralCellUnitType, 0))

            parseTextColors(it)
            textColorNormalEnabledPalette =
                ColorPaletteEnum.from(it.getIntOrNull(R.styleable.TextCellUnit_admiralTextColorNormalEnabledPalette))
            androidTextColor = it.getIntOrNull(R.styleable.TextCellUnit_android_textColor)
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
                R.styleable.TextCellUnit_admiralTextColorNormalEnabled
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TextCellUnit_admiralTextColorNormalDisabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.TextCellUnit_admiralTextColorPressed
            )
        )
    }

    private fun invalidateTextColors() {
        textColor = textColorState
    }
}