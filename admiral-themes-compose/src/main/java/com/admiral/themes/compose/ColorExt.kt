package com.admiral.themes.compose

import androidx.annotation.FloatRange
import androidx.compose.ui.graphics.Color

fun Color.withAlpha(@FloatRange(from = 0.0, to = 1.0) value: Float = 0.6f) =
    this.copy(alpha = value)