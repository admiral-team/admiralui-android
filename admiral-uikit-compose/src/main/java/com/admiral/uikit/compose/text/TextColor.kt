package com.admiral.uikit.compose.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha

@Immutable
data class TextColor(
    val textColorNormal: Color,
    val textColorDisabled: Color,
)

object AdmiralTextColor {
    @Composable
    fun primary(
        textColorNormal: Color = AdmiralTheme.colors.textPrimary,
        textColorDisabled: Color = AdmiralTheme.colors.textPrimary.withAlpha(),
    ): TextColor = TextColor(
        textColorNormal = textColorNormal,
        textColorDisabled = textColorDisabled,
    )
}