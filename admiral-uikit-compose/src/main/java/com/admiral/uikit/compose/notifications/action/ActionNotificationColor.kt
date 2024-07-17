package com.admiral.uikit.compose.notifications.action

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme.colors

@Immutable
data class ActionNotificationColor(
    val text: Color,
    val closeButton: Color,
    val progress: Color,
    val background: Color,
)

object AdmiralActionNotificationColor {
    @Composable
    fun default(
        text: Color = colors.textPrimary,
        closeButton: Color = colors.elementAccent,
        progress: Color = colors.elementAccent,
        background: Color = colors.backgroundAdditionalOne,
    ) = ActionNotificationColor(
        text = text,
        closeButton = closeButton,
        progress = progress,
        background = background
    )
}