package com.admiral.uikit.common.components.button

import androidx.annotation.DimenRes
import com.admiral.uikit.common.R

/**
 * Define sizes for Button
 */
sealed class ButtonSize(
    @DimenRes val padding: Int,
    @Deprecated("Replaced with `defaultWidth`")
    @DimenRes val minWidth: Int,
    @DimenRes val defaultWidth: Int
) {

    object Big : ButtonSize(
        padding = R.dimen.module_x3,
        minWidth = R.dimen.admiral_btn_big_default_width,
        defaultWidth = R.dimen.admiral_btn_big_default_width
    )

    object Medium : ButtonSize(
        padding = R.dimen.module_x3,
        minWidth = R.dimen.admiral_btn_medium_default_width,
        defaultWidth = R.dimen.admiral_btn_medium_default_width
    )

    object Small : ButtonSize(
        padding = R.dimen.module_x2,
        minWidth = R.dimen.admiral_btn_small_default_width,
        defaultWidth = R.dimen.admiral_btn_small_default_width
    )
}
