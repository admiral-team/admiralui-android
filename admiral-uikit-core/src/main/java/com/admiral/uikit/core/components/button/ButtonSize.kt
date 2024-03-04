package com.admiral.uikit.core.components.button

import androidx.annotation.DimenRes
import com.admiral.uikit.core.R as core

/**
 * Define sizes for Button
 */
sealed class ButtonSize(
    @DimenRes val padding: Int,
    @DimenRes val defaultWidth: Int
) {

    object Big : ButtonSize(
        padding = core.dimen.module_x4,
        defaultWidth = core.dimen.admiral_btn_big_default_width
    )

    object Medium : ButtonSize(
        padding = core.dimen.module_x4,
        defaultWidth = core.dimen.admiral_btn_medium_default_width
    )

    object Small : ButtonSize(
        padding = core.dimen.module_x2,
        defaultWidth = core.dimen.admiral_btn_small_default_width
    )
}
