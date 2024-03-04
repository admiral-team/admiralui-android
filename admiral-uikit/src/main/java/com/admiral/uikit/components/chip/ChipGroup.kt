package com.admiral.uikit.components.chip

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.children
import com.admiral.uikit.ext.pixels
import com.google.android.material.chip.ChipGroup
import com.admiral.uikit.core.R as core

class ChipGroup @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ChipGroup(context, attrs, defStyleAttr) {

    init {
        chipSpacingHorizontal = pixels(core.dimen.module_x3)
        chipSpacingVertical = pixels(core.dimen.module_x3)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        children.forEach { it.isEnabled = enabled }
    }
}