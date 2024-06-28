package com.admiral.uikit.compose.actionbar

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Immutable
data class ActionBarSecondaryItem(
    val icon: Painter,
    val iconContentDescription: String,
    val description: String?,
    val backgroundColorNormalEnabled: Color,
    val backgroundColorPressed: Color,
    val descriptionColorNormalEnabled: Color,
    val descriptionColorPressed: Color,
    val action: () -> Unit
)