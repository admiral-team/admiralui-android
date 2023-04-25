package com.admiral.uikit.components.switcher

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.res.use
import com.admiral.themes.Font
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.ext.applyStyle
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.colorStateListForChecked
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

class Switcher @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SwitchCompat(
    ContextThemeWrapper(context, R.style.Widget_AppCompat_CompoundButton_Switch),
    attrs,
    defStyleAttr
), ThemeObserver {

    /**
     * Colors for thumb tint.
     * States: normalDisabled, normalEnabled, checkedEnabled, checkedDisabled.
     * In case state is null, the selected color theme will be used.
     */
    var thumbTintColors: ColorState? = null
        set(value) {
            field = value
            invalidateThumbTintColors()
        }

    /**
     * Colors for track tint.
     * States: normalDisabled, normalEnabled, checkedEnabled, checkedDisabled.
     * In case state is null, the selected color theme will be used.
     */
    var trackTintColors: ColorState? = null
        set(value) {
            field = value
            invalidateTrackTintColors()
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

    /**
     * Apply style to the text
     */
    var textStyle: Font = ThemeManager.theme.typography.subhead3
        set(value) {
            field = value
            applyStyle(Typography.getStyle(field))
        }

    init {
        parseAttrs(attrs, R.styleable.Switcher).use {
            parseThumbColors(it)
            parseTrackColors(it)
            parseTextColors(it)

            textStyle =
                Typography.getStyleById(
                    it.getInt(
                        R.styleable.Switcher_admiralTextStyle,
                        Typography.subhead3
                    )
                )
        }
    }

    private fun parseTextColors(typedArray: TypedArray) {
        textColor = ColorState(
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.Switcher_admiralTextColorNormalEnabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.Switcher_admiralTextColorNormalDisabled
            ),
            checkedEnabled = typedArray.getColorOrNull(
                R.styleable.Switcher_admiralTextColorCheckedEnabled
            ),
            checkedDisabled = typedArray.getColorOrNull(
                R.styleable.Switcher_admiralTextColorCheckedDisabled
            )
        )
    }

    private fun parseThumbColors(typedArray: TypedArray) {
        thumbTintColors = ColorState(
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.Switcher_admiralThumbTintColorNormalEnabled
            ),
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.Switcher_admiralThumbTintColorNormalDisabled
            ),
            checkedEnabled = typedArray.getColorOrNull(
                R.styleable.Switcher_admiralThumbTintColorCheckedEnabled
            ),
            checkedDisabled = typedArray.getColorOrNull(
                R.styleable.Switcher_admiralThumbTintColorCheckedDisabled
            )
        )
    }

    private fun parseTrackColors(typedArray: TypedArray) {
        trackTintColors = ColorState(
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.Switcher_admiralTrackTintColorNormalEnabled
            ),
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.Switcher_admiralTrackTintColorNormalDisabled
            ),
            checkedEnabled = typedArray.getColorOrNull(
                R.styleable.Switcher_admiralTrackTintColorCheckedEnabled
            ),
            checkedDisabled = typedArray.getColorOrNull(
                R.styleable.Switcher_admiralTrackTintColorCheckedDisabled
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
        invalidateThumbTintColors()
        invalidateTrackTintColors()
        invalidateTextColors()
    }

    private fun invalidateThumbTintColors() {
        thumbTintList =
            colorStateListForChecked(
                checkedEnabled = thumbTintColors?.checkedEnabled
                    ?: ThemeManager.theme.palette.elementStaticWhite,
                checkedDisabled = thumbTintColors?.checkedDisabled
                    ?: ThemeManager.theme.palette.elementStaticWhite,
                normalEnabled = thumbTintColors?.normalEnabled
                    ?: ThemeManager.theme.palette.elementStaticWhite,
                normalDisabled = thumbTintColors?.normalDisabled
                    ?: ThemeManager.theme.palette.elementStaticWhite
            )
    }

    private fun invalidateTrackTintColors() {
        trackTintList =
            colorStateListForChecked(
                checkedEnabled = trackTintColors?.checkedEnabled
                    ?: ThemeManager.theme.palette.elementAccent,
                checkedDisabled = trackTintColors?.checkedDisabled
                    ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
                normalEnabled = trackTintColors?.normalEnabled
                    ?: ThemeManager.theme.palette.elementPrimary,
                normalDisabled = trackTintColors?.normalDisabled
                    ?: ThemeManager.theme.palette.elementPrimary.withAlpha()
            )
    }

    private fun invalidateTextColors() {
        setTextColor(
            colorStateList(
                enabled = textColor?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
                disabled = textColor?.normalDisabled
                    ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
                pressed = textColor?.pressed ?: ThemeManager.theme.palette.textPrimary,
            )
        )
    }
}