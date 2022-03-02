package com.admiral.uikit.common.foundation

import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import com.admiral.uikit.common.ext.withAlpha

/**
 * Used as color state in all components as replacement of [ColorStateList].
 */
data class ColorState(
    @ColorInt var normalEnabled: Int? = null,
    @ColorInt var normalDisabled: Int? = null,
    @ColorInt var selectedEnabled: Int? = null,
    @ColorInt var selectedDisabled: Int? = null,
    @ColorInt var checkedEnabled: Int? = null,
    @ColorInt var checkedDisabled: Int? = null,
    @ColorInt var pressed: Int? = null,
    @ColorInt var focused: Int? = null,
    @ColorInt var errorEnabled: Int? = null,
    @ColorInt var errorDisabled: Int? = null
) {
    init {
        if (normalDisabled == null) normalDisabled = normalEnabled?.withAlpha()
        if (selectedEnabled == null) selectedEnabled = normalEnabled
        if (selectedEnabled == null) selectedEnabled = normalEnabled
        if (selectedDisabled == null) selectedDisabled = normalEnabled
        if (checkedEnabled == null) checkedEnabled = normalEnabled
        if (checkedDisabled == null) checkedDisabled = normalEnabled
        if (pressed == null) pressed = normalEnabled
        if (focused == null) focused = normalEnabled
        if (errorEnabled == null) errorEnabled = normalEnabled
        if (errorDisabled == null) errorDisabled = normalEnabled
    }
}

data class ColorStateStepper(
    @ColorInt val done: Int? = null,
    @ColorInt val current: Int? = null,
    @ColorInt val next: Int? = null,
    @ColorInt val error: Int? = null,
    @ColorInt val disabled: Int? = null
)