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
class TitleCellUnit @JvmOverloads constructor(
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
        textStyle = ThemeManager.theme.typography.body1
        gravity = Gravity.CENTER

        parseAttrs(attrs, R.styleable.TitleCellUnit).use {
            text = it.getText(R.styleable.TitleCellUnit_admiralTitleText)

            unitType = CellUnitType.from(it.getInt(R.styleable.TitleCellUnit_admiralCellUnitType, 0))
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
                R.styleable.TitleCellUnit_admiralTitleColorNormalEnabled
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TitleCellUnit_admiralTitleColorNormalDisabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.TitleCellUnit_admiralTitleColorPressed
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