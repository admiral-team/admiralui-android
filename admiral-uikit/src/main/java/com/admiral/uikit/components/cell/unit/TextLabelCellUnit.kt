package com.admiral.uikit.components.cell.unit

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.admiral.themes.Font
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.themes.Typography.Companion.subhead1
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

class TextLabelCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver, CellUnit {

    override lateinit var unitType: CellUnitType

    /**
     * In case tint color is null, the selected color theme will be used.
     * States: normal, disabled, focused.
     */
    var textColors: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    /**
     * Apply style for the [TextView]
     */
    var textStyle: Font = ThemeManager.theme.typography.subhead1
        set(value) {
            field = value
            textLabelTextView.textStyle = value
        }

    /**
     * In case tint color is null, the selected color theme will be used.
     * States: normal, disabled, pressed.
     */
    var backgroundColors: ColorState? = null
        set(value) {
            field = value
            invalidateBackgroundColors()
        }

    /**
     * Text for this cell
     */
    var text: String?
        get() = textLabelTextView.text?.toString()
        set(value) {
            textLabelTextView.text = value
        }

    private val textLabelTextView: TextView by lazy { findViewById(R.id.textLabelTextView) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_cell_unit_text_label, this)

        textLabelTextView.background = drawable(R.drawable.admiral_bg_round)

        parseAttrs(attrs, R.styleable.TextLabelCellUnit).use {
            parseTextColors(it)
            parseBackgroundColors(it)

            text = it.getText(R.styleable.TextLabelCellUnit_admiralText)?.toString()
            textStyle = Typography.getStyleById(it.getInt(R.styleable.TextLabelCellUnit_admiralTextStyle, subhead1))

            unitType = CellUnitType.from(it.getInt(R.styleable.TextLabelCellUnit_admiralCellUnitType, 0))
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

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        textLabelTextView.isEnabled = enabled
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateBackgroundColors()
        invalidateTextColors()
    }

    private fun parseBackgroundColors(a: TypedArray) {
        backgroundColors = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TextLabelCellUnit_admiralBackgroundColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TextLabelCellUnit_admiralBackgroundColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TextLabelCellUnit_admiralBackgroundColorNormalDisabled,
            )
        )
    }

    private fun parseTextColors(a: TypedArray) {
        textColors = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.TextLabelCellUnit_admiralTextColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.TextLabelCellUnit_admiralTextColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.TextLabelCellUnit_admiralTextColorNormalDisabled,
            )
        )
    }

    private fun invalidateBackgroundColors() {
        textLabelTextView.backgroundTintList = colorStateList(
            enabled = backgroundColors?.normalEnabled ?: ThemeManager.theme.palette.backgroundSecondary,
            disabled = backgroundColors?.normalDisabled ?: ThemeManager.theme.palette.backgroundSecondary.withAlpha(),
            pressed = backgroundColors?.pressed ?: ThemeManager.theme.palette.backgroundSecondary
        )
    }

    private fun invalidateTextColors() {
        val colorState2 = ColorState(
            normalEnabled = textColors?.normalEnabled ?: ThemeManager.theme.palette.textStaticWhite,
            normalDisabled = textColors?.normalDisabled ?: ThemeManager.theme.palette.textStaticWhite.withAlpha(),
            pressed = textColors?.pressed ?: ThemeManager.theme.palette.textStaticWhite,
        )

        textLabelTextView.textColor = colorState2
    }
}