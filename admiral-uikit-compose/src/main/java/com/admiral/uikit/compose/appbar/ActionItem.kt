package com.admiral.uikit.compose.appbar

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Immutable
data class ActionItem(
    val icon: Painter,
    val color: Color? = null,
    val contentDescription: String? = null,
    val onClick: () -> Unit = {},
)