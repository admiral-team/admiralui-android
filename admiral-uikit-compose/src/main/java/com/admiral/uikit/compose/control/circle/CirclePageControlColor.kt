package com.admiral.uikit.compose.control.circle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme

@Immutable
data class CirclePageControlColor(
    val progressColor: Color,
    val iconColor: Color,
    val iconBackground: Color,
)

object AdmiralCirclePageControlColor {
    @Composable
    fun default(
        progressColor: Color = AdmiralTheme.colors.elementAccent,
        iconColor: Color = AdmiralTheme.colors.elementStaticWhite,
        iconBackground: Color = AdmiralTheme.colors.backgroundAccent,
    ) = CirclePageControlColor(
        progressColor = progressColor,
        iconColor = iconColor,
        iconBackground = iconBackground,
    )
}