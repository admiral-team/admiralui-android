package com.admiral.uikit.foundation

import android.content.res.ColorStateList
import androidx.annotation.ColorInt

/**
 * Used as color state in all components as replacement of [ColorStateList].
 */
@Deprecated("Use com.admiral.uikit.common.foundation.ColorState instead")
data class ColorState(
    @ColorInt val normalEnabled: Int? = null,
    @ColorInt val normalDisabled: Int? = null,
    @ColorInt val selectedEnabled: Int? = null,
    @ColorInt val selectedDisabled: Int? = null,
    @ColorInt val checkedEnabled: Int? = null,
    @ColorInt val checkedDisabled: Int? = null,
    @ColorInt val pressed: Int? = null,
    @ColorInt val focused: Int? = null,
    @ColorInt val errorEnabled: Int? = null,
    @ColorInt val errorDisabled: Int? = null
)

@Deprecated("Use com.admiral.uikit.common.foundation.ColorStateStepper instead")
data class ColorStateStepper(
    @ColorInt val done: Int? = null,
    @ColorInt val current: Int? = null,
    @ColorInt val next: Int? = null,
    @ColorInt val error: Int? = null,
    @ColorInt val disabled: Int? = null
)