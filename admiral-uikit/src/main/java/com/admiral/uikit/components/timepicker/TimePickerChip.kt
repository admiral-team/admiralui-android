package com.admiral.uikit.components.timepicker

import android.content.Context
import android.util.AttributeSet
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.core.R
import com.admiral.uikit.ext.applyStyle
import com.admiral.uikit.ext.colorStateListUnion
import com.google.android.material.chip.Chip

internal class TimePickerChip @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Chip(context, attrs, defStyleAttr), ThemeObserver {

    init {
        applyStyle(R.style.TimePickerChips)

        invalidateBackgroundColor()
        invalidateTextColor()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.subscribe(this)
    }

    override fun onDetachedFromWindow() {
        ThemeManager.unsubscribe(this)
        super.onDetachedFromWindow()
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateBackgroundColor()
        invalidateTextColor()
    }

    private fun invalidateBackgroundColor() {
        chipBackgroundColor = colorStateListUnion(
            normalEnabled = ThemeManager.theme.palette.backgroundAdditionalOne,
            checkedEnabled = ThemeManager.theme.palette.backgroundSelected,
            pressed = ThemeManager.theme.palette.backgroundSelected
        )
    }

    private fun invalidateTextColor() {
        setTextColor(
            colorStateListUnion(
                normalEnabled = ThemeManager.theme.palette.textPrimary,
                checkedEnabled = ThemeManager.theme.palette.textAccent,
                pressed = ThemeManager.theme.palette.textAccent
            )
        )
    }
}