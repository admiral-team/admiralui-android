package com.admiral.uikit.components.tag

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.chip.ChipGroup
import com.admiral.uikit.R
import com.admiral.uikit.ext.pixels

class TagGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ChipGroup(context, attrs, defStyleAttr) {

    init {
        chipSpacingHorizontal = pixels(R.dimen.module_x3)
        chipSpacingVertical = pixels(R.dimen.module_x3)
    }
}