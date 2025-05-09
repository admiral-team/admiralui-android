package com.admiral.uikit.components.checkbox

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.res.use
import com.google.android.material.checkbox.MaterialCheckBox
import com.admiral.themes.Font
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.uikit.R
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.ext.applyStyle
import com.admiral.uikit.ext.colorStateListForChecked
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

class CheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialCheckBox(
    ContextThemeWrapper(context, android.R.style.Widget_Material_CompoundButton_CheckBox),
    attrs, defStyleAttr
), ThemeObserver {

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

    /**
     * Apply style to the text
     */
    var textStyle: Font = ThemeManager.theme.typography.subhead3
        set(value) {
            field = value
            applyStyle(Typography.getStyle(field))
        }

    var isError: Boolean = false
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
        parseAttrs(attrs, R.styleable.CheckBox).use {
            isError = it.getBoolean(R.styleable.CheckBox_admiralIsError, false)

            textStyle =
                Typography.getStyleById(
                    it.getInt(
                        R.styleable.CheckBox_admiralTextStyle,
                        Typography.subhead3
                    )
                )

            parseTintColors(it)
            parseTextColors(it)
        }
    }

    private fun parseTintColors(typedArray: TypedArray) {
        buttonTintColors = ColorState(
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.CheckBox_admiralButtonTintColorNormalEnabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.CheckBox_admiralButtonTintColorNormalDisabled
            ),
            checkedEnabled = typedArray.getColorOrNull(
                R.styleable.CheckBox_admiralButtonTintColorCheckedEnabled
            ),
            checkedDisabled = typedArray.getColorOrNull(
                R.styleable.CheckBox_admiralButtonTintColorCheckedDisabled
            ),
            errorEnabled = typedArray.getColorOrNull(
                R.styleable.CheckBox_admiralButtonTintColorErrorEnabled
            ),
            errorDisabled = typedArray.getColorOrNull(
                R.styleable.CheckBox_admiralButtonTintColorErrorDisabled
            )
        )
    }

    private fun parseTextColors(typedArray: TypedArray) {
        textColor = ColorState(
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.CheckBox_admiralTextColorNormalEnabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.CheckBox_admiralTextColorNormalDisabled
            ),
            checkedEnabled = typedArray.getColorOrNull(
                R.styleable.CheckBox_admiralTextColorCheckedEnabled
            ),
            checkedDisabled = typedArray.getColorOrNull(
                R.styleable.CheckBox_admiralTextColorCheckedDisabled
            ),
            errorEnabled = typedArray.getColorOrNull(
                R.styleable.CheckBox_admiralTextColorErrorEnabled
            ),
            errorDisabled = typedArray.getColorOrNull(
                R.styleable.CheckBox_admiralTextColorErrorDisabled
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
        buttonTintList = if (isError) {
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
        if (isError) {
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