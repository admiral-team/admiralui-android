package com.admiral.uikit.compose.actionbar

import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Immutable
data class ActionBarItem(
    val modifier: Modifier,
    val image: Painter,
    val contentDescription: String,
    val rippleColor: Color,
    val tintColor: Color,
    val backgroundColor: Color,
    val onClickListener: () -> Unit,
    val isEnable: Boolean,
)
