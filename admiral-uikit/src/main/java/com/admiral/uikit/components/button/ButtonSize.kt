package com.admiral.uikit.components.button

import androidx.annotation.DimenRes
import com.admiral.uikit.common.R
import com.admiral.resources.R as res

/**
 * Define sizes for [Button]
 */
@Deprecated("Use com.admiral.uikit.common.components.button.ButtonSize instead")
sealed class ButtonSize(
    @DimenRes val padding: Int,
    @DimenRes val minWidth: Int
) {

    object Big : ButtonSize(
        padding = res.dimen.module_x3,
        minWidth = R.dimen.admiral_btn_big_default_width
    )

    object Medium : ButtonSize(
        padding = res.dimen.module_x3,
        minWidth = R.dimen.admiral_btn_medium_default_width
    )

    object Small : ButtonSize(
        padding = res.dimen.module_x2,
        minWidth = R.dimen.admiral_btn_small_default_width
    )
}
