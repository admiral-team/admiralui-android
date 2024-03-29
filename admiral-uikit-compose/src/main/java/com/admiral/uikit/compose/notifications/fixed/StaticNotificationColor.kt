package com.admiral.uikit.compose.notifications.fixed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.AdmiralTheme.colors
import com.admiral.themes.compose.withAlpha

@Immutable
data class StaticNotificationColor(
    val backgroundEnable: Color,
    val backgroundDisable: Color,
    val notificationTextEnable: Color,
    val notificationTextDisable: Color,
    val linkTextEnable: Color,
    val linkTextDisable: Color,
    val linkTextPressed: Color,
    val iconEnable: Color,
    val iconDisable: Color,
) {
    @Composable
    fun getBackgroundEnable(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) backgroundEnable else backgroundDisable)

    @Composable
    fun getNotificationTextColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) notificationTextEnable else notificationTextDisable)

    @Composable
    fun getTextLinkColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) linkTextEnable else linkTextDisable)

    @Composable
    fun getIconColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) iconEnable else iconDisable)
}

object AdmiralStaticNotificationColors {
    @Composable
    fun info(
        backgroundEnable: Color = colors.backgroundSelected,
        backgroundDisable: Color = colors.backgroundSelected.withAlpha(),
        notificationTextEnable: Color = colors.textPrimary,
        notificationTextDisable: Color = colors.textPrimary.withAlpha(),
        linkTextEnable: Color = colors.textAccent,
        linkTextDisable: Color = colors.textAccent.withAlpha(),
        linkPressed: Color = colors.elementAccentPressed,
        iconEnable: Color = colors.elementAccent,
        iconDisable: Color = colors.elementAccent.withAlpha(),
    ) = StaticNotificationColor(
        backgroundEnable = backgroundEnable,
        backgroundDisable = backgroundDisable,
        notificationTextEnable = notificationTextEnable,
        notificationTextDisable = notificationTextDisable,
        linkTextEnable = linkTextEnable,
        linkTextDisable = linkTextDisable,
        linkTextPressed = linkPressed,
        iconEnable = iconEnable,
        iconDisable = iconDisable,
    )

    @Composable
    fun attention(
        backgroundEnable: Color = colors.backgroundAttention,
        backgroundDisable: Color = colors.backgroundAttention.withAlpha(),
        notificationTextEnable: Color = colors.textPrimary,
        notificationTextDisable: Color = colors.textPrimary.withAlpha(),
        linkTextEnable: Color = colors.textAccent,
        linkTextDisable: Color = colors.textAccent.withAlpha(),
        linkPressed: Color = colors.elementAccentPressed,
        iconEnable: Color = colors.elementAttention,
        iconDisable: Color = colors.elementAttention.withAlpha(),
    ) = StaticNotificationColor(
        backgroundEnable = backgroundEnable,
        backgroundDisable = backgroundDisable,
        notificationTextEnable = notificationTextEnable,
        notificationTextDisable = notificationTextDisable,
        linkTextEnable = linkTextEnable,
        linkTextDisable = linkTextDisable,
        linkTextPressed = linkPressed,
        iconEnable = iconEnable,
        iconDisable = iconDisable,
    )

    @Composable
    fun success(
        backgroundEnable: Color = colors.backgroundSuccess,
        backgroundDisable: Color = colors.backgroundSuccess.withAlpha(),
        notificationTextEnable: Color = colors.textPrimary,
        notificationTextDisable: Color = colors.textPrimary.withAlpha(),
        linkTextEnable: Color = colors.textAccent,
        linkTextDisable: Color = colors.textAccent.withAlpha(),
        linkPressed: Color = colors.elementAccentPressed,
        iconEnable: Color = colors.elementSuccess,
        iconDisable: Color = colors.elementSuccess.withAlpha(),
    ) = StaticNotificationColor(
        backgroundEnable = backgroundEnable,
        backgroundDisable = backgroundDisable,
        notificationTextEnable = notificationTextEnable,
        notificationTextDisable = notificationTextDisable,
        linkTextEnable = linkTextEnable,
        linkTextDisable = linkTextDisable,
        linkTextPressed = linkPressed,
        iconEnable = iconEnable,
        iconDisable = iconDisable,
    )

    @Composable
    fun error(
        backgroundEnable: Color = colors.backgroundError,
        backgroundDisable: Color = colors.backgroundError.withAlpha(),
        notificationTextEnable: Color = colors.textPrimary,
        notificationTextDisable: Color = colors.textPrimary.withAlpha(),
        linkTextEnable: Color = colors.textAccent,
        linkTextDisable: Color = colors.textAccent.withAlpha(),
        linkPressed: Color = colors.elementAccentPressed,
        iconEnable: Color = colors.elementError,
        iconDisable: Color = colors.elementError.withAlpha(),
    ) = StaticNotificationColor(
        backgroundEnable = backgroundEnable,
        backgroundDisable = backgroundDisable,
        notificationTextEnable = notificationTextEnable,
        notificationTextDisable = notificationTextDisable,
        linkTextEnable = linkTextEnable,
        linkTextDisable = linkTextDisable,
        linkTextPressed = linkPressed,
        iconEnable = iconEnable,
        iconDisable = iconDisable,
    )
}