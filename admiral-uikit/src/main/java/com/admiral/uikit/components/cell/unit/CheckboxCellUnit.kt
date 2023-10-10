package com.admiral.uikit.components.cell.unit

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.res.use
import com.google.android.material.checkbox.MaterialCheckBox
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography.Companion.getStyle
import com.admiral.uikit.R
import com.admiral.uikit.core.components.cell.base.CellUnit
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.ext.applyStyle
import com.admiral.uikit.ext.colorStateListForChecked
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

class CheckboxCellUnit @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCheckBox(
    ContextThemeWrapper(context, android.R.style.Widget_Material_CompoundButton_CheckBox),
    attrs, defStyleAttr
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
        parseAttrs(attrs, R.styleable.CheckboxCellUnit).use {
            unitType = CellUnitType.from(it.getInt(R.styleable.CheckboxCellUnit_admiralCellUnitType, 0))

            error = it.getBoolean(R.styleable.CheckboxCellUnit_admiralIsError, false)

            parseTintColors(it)
            parseTextColors(it)
        }

        applyStyle(getStyle(ThemeManager.theme.typography.subhead3))
    }

    private fun parseTintColors(typedArray: TypedArray) {
        buttonTintColors = ColorState(
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.CheckboxCellUnit_admiralButtonTintColorNormalEnabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.CheckboxCellUnit_admiralButtonTintColorNormalDisabled
            ),
            checkedEnabled = typedArray.getColorOrNull(
                R.styleable.CheckboxCellUnit_admiralButtonTintColorCheckedEnabled
            ),
            checkedDisabled = typedArray.getColorOrNull(
                R.styleable.CheckboxCellUnit_admiralButtonTintColorCheckedDisabled
            ),
            errorEnabled = typedArray.getColorOrNull(
                R.styleable.CheckboxCellUnit_admiralButtonTintColorErrorEnabled
            ),
            errorDisabled = typedArray.getColorOrNull(
                R.styleable.CheckboxCellUnit_admiralButtonTintColorErrorDisabled
            )
        )
    }

    private fun parseTextColors(typedArray: TypedArray) {
        textColor = ColorState(
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.CheckboxCellUnit_admiralTextColorNormalEnabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.CheckboxCellUnit_admiralTextColorNormalDisabled
            ),
            checkedEnabled = typedArray.getColorOrNull(
                R.styleable.CheckboxCellUnit_admiralTextColorCheckedEnabled
            ),
            checkedDisabled = typedArray.getColorOrNull(
                R.styleable.CheckboxCellUnit_admiralTextColorCheckedDisabled
            ),
            errorEnabled = typedArray.getColorOrNull(
                R.styleable.CheckboxCellUnit_admiralTextColorErrorEnabled
            ),
            errorDisabled = typedArray.getColorOrNull(
                R.styleable.CheckboxCellUnit_admiralTextColorErrorDisabled
            )
        )
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