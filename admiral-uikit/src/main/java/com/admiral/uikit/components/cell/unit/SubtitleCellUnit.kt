package com.admiral.uikit.components.cell.unit

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import androidx.core.content.res.use
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.Typography
import com.admiral.themes.Typography.Companion.subhead3
import com.admiral.uikit.R
import com.admiral.uikit.core.components.cell.base.CellUnit
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

class SubtitleCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr), CellUnit {

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
        gravity = Gravity.CENTER

        parseAttrs(attrs, R.styleable.SubtitleCellUnit).use {
            text = it.getText(R.styleable.SubtitleCellUnit_admiralSubtitleText)
            textStyle =
                Typography.getStyleById(
                    it.getInt(
                        R.styleable.SubtitleCellUnit_admiralSubtitleTextStyle, subhead3
                    )
                )

            unitType = CellUnitType.from(it.getInt(R.styleable.SubtitleCellUnit_admiralCellUnitType, 0))
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
                R.styleable.TextSecondaryCellUnit_admiralTextColorNormalEnabled
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TextSecondaryCellUnit_admiralTextColorNormalDisabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.TextSecondaryCellUnit_admiralTextColorPressed
            )
        )
    }

    private fun invalidateTextColors() {
        val colorState = ColorState(
            normalEnabled = textColorState?.normalEnabled ?: ThemeManager.theme.palette.textSecondary,
            normalDisabled = textColorState?.normalDisabled ?: ThemeManager.theme.palette.textSecondary.withAlpha(),
            pressed = textColorState?.pressed ?: ThemeManager.theme.palette.textSecondary
        )

        textColor = colorState
    }
}