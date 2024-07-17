package com.admiral.uikit.compose.notifications.toast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme.colors

@Immutable
data class ToastNotificationColor(
    val text: Color,
    val link: Color,
    val iconTint: Color,
    val closeIconTint: Color,
    val background: Color,
)

object AdmiralToastNotificationColor {
    @Composable
    fun default(
        text: Color = colors.textPrimary,
        link: Color = colors.textAccent,
        iconTint: Color = colors.elementAccent,
        closeIconTint: Color = colors.elementPrimary,
        background: Color = colors.backgroundAdditionalOne,
    ) = ToastNotificationColor(
        text = text,
        link = link,
        iconTint = iconTint,
        closeIconTint = closeIconTint,
        background = background
    )
}