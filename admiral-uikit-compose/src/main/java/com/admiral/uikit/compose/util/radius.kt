package com.admiral.uikit.compose.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val noneRadius = 0.dp

enum class ComponentsRadius(val value: Dp) {
    NONE(noneRadius),
    RADIUS_4(DIMEN_X1),
    RADIUS_8(DIMEN_X2),
    RADIUS_12(DIMEN_X3),
    RADIUS_16(DIMEN_X4),
    RADIUS_20(DIMEN_X5);
}