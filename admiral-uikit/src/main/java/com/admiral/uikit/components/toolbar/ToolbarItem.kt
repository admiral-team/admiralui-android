package com.admiral.uikit.components.toolbar

import android.graphics.drawable.Drawable
import android.view.View
import com.admiral.uikit.common.foundation.ColorState

data class ToolbarItem(
    val text: CharSequence?,
    val icon: Drawable?,
    val id: Int = View.generateViewId(),
    val textColorState: ColorState? = null,
    val iconColorState: ColorState? = null
)