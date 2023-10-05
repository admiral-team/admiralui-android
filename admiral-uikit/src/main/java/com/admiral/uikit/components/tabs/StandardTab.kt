package com.admiral.uikit.components.tabs

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity.CENTER
import android.view.View
import androidx.core.content.res.use
import androidx.core.view.updatePadding
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.ext.colorStateListForChecked
import com.admiral.uikit.ext.colorStateListUnion
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.coloredDrawable
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.ext.ripple
import com.admiral.uikit.view.checkable.CheckableTextView
import com.admiral.uikit.view.checkable.CheckableView
import com.admiral.resources.R as res

class StandardTab @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CheckableTextView(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Colors for tab stroke in checked state.
     * States: checkedEnabled, checkedDisabled.
     * In case state is null, the selected color theme will be used.
     */
    var strokeColorState: ColorState? = null
        set(value) {
            field = value
            invalidateColors()
        }

    /**
     * Colors for tab stroke in checked state.
     * States: checkedEnabled, checkedDisabled.
     * In case state is null, the selected color theme will be used.
     */
    var textColorState: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    internal var isRightDividerVisible: Boolean = false
        set(value) {
            field = value
            invalidateDivider()
        }

    init {
        textStyle = ThemeManager.theme.typography.subhead3
        addOnCheckChangeListener(object : CheckableView.OnCheckedChangeListener {
            override fun onCheckedChanged(view: View, isChecked: Boolean) {
                textStyle =
                    (if (isChecked) ThemeManager.theme.typography.subhead2 else ThemeManager.theme.typography.subhead3)
                invalidateTextColors()
            }
        })

        parseAttrs(attrs, R.styleable.StandardTab).use {
            parseStrokeColors(it)
            parseTextColors(it)
        }

        gravity = CENTER
        isClickable = true
        isFocusable = true

        updatePadding(
            top = context.pixels(res.dimen.module_x2),
            bottom = context.pixels(res.dimen.module_x2)
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
        ThemeManager.subscribe(this)
        super.onDetachedFromWindow()
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateColors()
        invalidateTextColors()
        invalidateDivider()
    }

    override fun setChecked(checked: Boolean) {
        super.setChecked(checked)
        invalidateColors()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        invalidateDivider()
    }

    private fun invalidateDivider() {
        val color = if (isEnabled) {
            ThemeManager.theme.palette.elementAdditional
        } else {
            ThemeManager.theme.palette.elementAdditional.withAlpha()
        }

        val divider = if (isRightDividerVisible) {
            drawable(R.drawable.admiral_bg_tabs_logo_divider)?.colored(color)
        } else {
            null
        }

        setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            divider,
            null
        )
    }

    private fun parseStrokeColors(a: TypedArray) {
        strokeColorState = ColorState(
            checkedEnabled = a.getColorOrNull(R.styleable.StandardTab_admiralStrokeColorCheckedEnabled),
            checkedDisabled = a.getColorOrNull(R.styleable.StandardTab_admiralStrokeColorCheckedDisabled)
        )
    }

    private fun parseTextColors(typedArray: TypedArray) {
        textColorState = ColorState(
            checkedEnabled = typedArray.getColorOrNull(
                R.styleable.StandardTab_admiralTextColorCheckedEnabled
            ),
            checkedDisabled = typedArray.getColorOrNull(
                R.styleable.StandardTab_admiralTextColorCheckedDisabled
            ),
            normalEnabled = typedArray.getColorOrNull(
                R.styleable.StandardTab_admiralTextColorNormalEnabled
            ),
            normalDisabled = typedArray.getColorOrNull(
                R.styleable.StandardTab_admiralTextColorNormalDisabled
            )
        )
    }

    private fun invalidateColors() {
        val stateList = colorStateListUnion(
            normalEnabled = Color.TRANSPARENT,
            normalDisabled = Color.TRANSPARENT,
            pressed = strokeColorState?.checkedEnabled ?: ThemeManager.theme.palette.elementAccent,
            checkedEnabled = strokeColorState?.checkedEnabled
                ?: ThemeManager.theme.palette.elementAccent,
            checkedDisabled = strokeColorState?.checkedEnabled
                ?: ThemeManager.theme.palette.elementAccent.withAlpha(),
        )

        val color = strokeColorState?.checkedEnabled ?: ThemeManager.theme.palette.elementAccent
        val content = context.coloredDrawable(R.drawable.admiral_bg_btn_stroked_2dp, stateList)
        val mask = context.drawable(R.drawable.admiral_bg_btn_mask)

        background = ripple(color.withAlpha(RIPPLE_ALPHA), if (isChecked) content else null, mask)
    }

    private fun invalidateTextColors() {
        setTextColor(
            colorStateListForChecked(
                checkedEnabled = textColorState?.checkedEnabled
                    ?: ThemeManager.theme.palette.textPrimary,
                checkedDisabled = textColorState?.checkedDisabled
                    ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
                normalEnabled = textColorState?.normalEnabled
                    ?: ThemeManager.theme.palette.textPrimary,
                normalDisabled = textColorState?.normalDisabled
                    ?: ThemeManager.theme.palette.textPrimary.withAlpha()
            )
        )
    }

    private companion object {
        private const val RIPPLE_ALPHA = 0.2f
    }
}