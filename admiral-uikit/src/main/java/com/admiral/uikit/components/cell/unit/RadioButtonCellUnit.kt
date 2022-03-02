package com.admiral.uikit.components.cell.unit

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.res.use
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.uikit.R
import com.admiral.uikit.common.components.cell.base.CellUnit
import com.admiral.uikit.common.components.cell.base.CellUnitType
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.ext.applyStyle
import com.admiral.uikit.ext.colorStateListForChecked
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

class RadioButtonCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatRadioButton(
    ContextThemeWrapper(context, R.style.Widget_AppCompat_CompoundButton_RadioButton),
    attrs,
    defStyleAttr
), ThemeObserver, CellUnit {

    override lateinit var unitType: CellUnitType

    /**
     * Colors for button tint.
     * States: normalDisabled, normalEnabled, checkedEnabled, checkedDisabled, errorEnabled, errorDisabled.
     * In case state is null, the selected color theme will be used.
     */
    var buttonTintColors: ColorState? = null
        set(value) {
            field = value
            invalidateButtonTintColors()
        }

    var error: Boolean = false
        set(value) {
            field = value
            invalidateButtonTintColors()
        }

    /**
     * Color state for text.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var textColor: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    init {
        parseAttrs(attrs, R.styleable.RadioButtonCellUnit).use {
            error = it.getBoolean(R.styleable.RadioButtonCellUnit_admiralIsError, false)
            unitType = CellUnitType.from(it.getInt(R.styleable.RadioButtonCellUnit_admiralCellUnitType, 0))

            parseTintColors(it)
            parseTextColors(it)
        }

        applyStyle(Typography.getStyle(ThemeManager.theme.typography.subhead3))
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
        invalidateButtonTintColors()
        invalidateTextColors()
    }

    private fun parseTintColors(typedArray: TypedArray) {
        buttonTintColors = ColorState(
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.RadioButtonCellUnit_admiralButtonTintColorNormalEnabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.RadioButtonCellUnit_admiralButtonTintColorNormalDisabled
            ),
            checkedEnabled = typedArray.getColorOrNull(
                R.styleable.RadioButtonCellUnit_admiralButtonTintColorCheckedEnabled
            ),
            checkedDisabled = typedArray.getColorOrNull(
                R.styleable.RadioButtonCellUnit_admiralButtonTintColorCheckedDisabled
            ),
            errorEnabled = typedArray.getColorOrNull(
                R.styleable.RadioButtonCellUnit_admiralButtonTintColorErrorEnabled
            ),
            errorDisabled = typedArray.getColorOrNull(
                R.styleable.RadioButtonCellUnit_admiralButtonTintColorErrorDisabled
            ),
        )
    }

    private fun parseTextColors(typedArray: TypedArray) {
        textColor = ColorState(
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.RadioButtonCellUnit_admiralTextColorNormalEnabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.RadioButtonCellUnit_admiralTextColorNormalDisabled
            ),
            checkedEnabled = typedArray.getColorOrNull(
                R.styleable.RadioButtonCellUnit_admiralTextColorCheckedEnabled
            ),
            checkedDisabled = typedArray.getColorOrNull(
                R.styleable.RadioButtonCellUnit_admiralTextColorCheckedDisabled
            ),
            errorEnabled = typedArray.getColorOrNull(
                R.styleable.RadioButtonCellUnit_admiralTextColorErrorEnabled
            ),
            errorDisabled = typedArray.getColorOrNull(
                R.styleable.RadioButtonCellUnit_admiralTextColorErrorDisabled
            )
        )
    }

    private fun invalidateButtonTintColors() {
        buttonTintList = if (error) {
            colorStateListForChecked(
                checkedEnabled = buttonTintColors?.errorEnabled ?: ThemeManager.theme.palette.elementError,
                checkedDisabled = buttonTintColors?.errorDisabled
                    ?: ThemeManager.theme.palette.elementError.withAlpha(),
                normalEnabled = buttonTintColors?.errorEnabled ?: ThemeManager.theme.palette.elementError,
                normalDisabled = buttonTintColors?.errorDisabled ?: ThemeManager.theme.palette.elementError.withAlpha()
            )
        } else {
            colorStateListForChecked(
                checkedEnabled = buttonTintColors?.checkedEnabled ?: ThemeManager.theme.palette.elementAccent,
                checkedDisabled = buttonTintColors?.checkedDisabled
                    ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
                normalEnabled = buttonTintColors?.normalEnabled ?: ThemeManager.theme.palette.elementAccent,
                normalDisabled = buttonTintColors?.normalDisabled
                    ?: ThemeManager.theme.palette.elementAccent.withAlpha()
            )
        }
    }

    private fun invalidateTextColors() {
        if (error) {
            setTextColor(
                colorStateListForChecked(
                    checkedEnabled = textColor?.errorEnabled ?: ThemeManager.theme.palette.textPrimary,
                    checkedDisabled = textColor?.errorDisabled ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
                    normalEnabled = textColor?.errorEnabled ?: ThemeManager.theme.palette.textPrimary,
                    normalDisabled = textColor?.errorDisabled ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
                )
            )
        } else {
            setTextColor(
                colorStateListForChecked(
                    checkedEnabled = textColor?.checkedEnabled ?: ThemeManager.theme.palette.textPrimary,
                    checkedDisabled = textColor?.checkedDisabled ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
                    normalEnabled = textColor?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
                    normalDisabled = textColor?.normalDisabled ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
                )
            )
        }
    }
}