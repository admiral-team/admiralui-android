package com.admiral.uikit.compose.badge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme

@Immutable
data class BadgeColor(
    val backgroundColorNormal: Color,
    val backgroundColorDisable: Color,
    val contentColorEnable: Color,
    val contentColorDisable: Color,
    val borderColor: Color,
)

object AdmiralBadgeColor {
    @Composable
    fun normal(
        backgroundColorNormal: Color = AdmiralTheme.colors.elementAccent,
        backgroundColorDisable: Color = AdmiralTheme.colors.elementAdditional,
        contentColorEnable: Color = AdmiralTheme.colors.textStaticWhite,
        contentColorDisable: Color = AdmiralTheme.colors.textSecondary,
        borderColor: Color = AdmiralTheme.colors.backgroundBasic
    ) = BadgeColor(
        backgroundColorNormal = backgroundColorNormal,
        backgroundColorDisable = backgroundColorDisable,
        contentColorEnable = contentColorEnable,
        contentColorDisable = contentColorDisable,
        borderColor = borderColor,
    )

    @Composable
    fun error(
        backgroundColorNormal: Color = AdmiralTheme.colors.elementError,
        backgroundColorDisable: Color = AdmiralTheme.colors.elementAdditional,
        contentColorEnable: Color = AdmiralTheme.colors.textStaticWhite,
        contentColorDisable: Color = AdmiralTheme.colors.textSecondary,
        borderColor: Color = AdmiralTheme.colors.backgroundBasic
    ) = BadgeColor(
        backgroundColorNormal = backgroundColorNormal,
        backgroundColorDisable = backgroundColorDisable,
        contentColorEnable = contentColorEnable,
        contentColorDisable = contentColorDisable,
        borderColor = borderColor,
    )

    @Composable
    fun attention(
        backgroundColorNormal: Color = AdmiralTheme.colors.elementAttention,
        backgroundColorDisable: Color = AdmiralTheme.colors.elementAdditional,
        contentColorEnable: Color = AdmiralTheme.colors.textStaticWhite,
        contentColorDisable: Color = AdmiralTheme.colors.textSecondary,
        borderColor: Color = AdmiralTheme.colors.backgroundBasic
    ) = BadgeColor(
        backgroundColorNormal = backgroundColorNormal,
        backgroundColorDisable = backgroundColorDisable,
        contentColorEnable = contentColorEnable,
        contentColorDisable = contentColorDisable,
        borderColor = borderColor,
    )


    @Composable
    fun success(
        backgroundColorNormal: Color = AdmiralTheme.colors.elementSuccess,
        backgroundColorDisable: Color = AdmiralTheme.colors.elementAdditional,
        contentColorEnable: Color = AdmiralTheme.colors.textStaticWhite,
        contentColorDisable: Color = AdmiralTheme.colors.textSecondary,
        borderColor: Color = AdmiralTheme.colors.backgroundBasic
    ) = BadgeColor(
        backgroundColorNormal = backgroundColorNormal,
        backgroundColorDisable = backgroundColorDisable,
        contentColorEnable = contentColorEnable,
        contentColorDisable = contentColorDisable,
        borderColor = borderColor,
    )

    @Composable
    fun neutral(
        backgroundColorNormal: Color = AdmiralTheme.colors.elementSecondary,
        backgroundColorDisable: Color = AdmiralTheme.colors.elementAdditional,
        contentColorEnable: Color = AdmiralTheme.colors.textStaticWhite,
        contentColorDisable: Color = AdmiralTheme.colors.textSecondary,
        borderColor: Color = AdmiralTheme.colors.backgroundBasic
    ) = BadgeColor(
        backgroundColorNormal = backgroundColorNormal,
        backgroundColorDisable = backgroundColorDisable,
        contentColorEnable = contentColorEnable,
        contentColorDisable = contentColorDisable,
        borderColor = borderColor,
    )

    @Composable
    fun additional(
        backgroundColorNormal: Color = AdmiralTheme.colors.elementPrimary,
        backgroundColorDisable: Color = AdmiralTheme.colors.elementAdditional,
        contentColorEnable: Color = AdmiralTheme.colors.textStaticWhite,
        contentColorDisable: Color = AdmiralTheme.colors.textSecondary,
        borderColor: Color = AdmiralTheme.colors.backgroundBasic
    ) = BadgeColor(
        backgroundColorNormal = backgroundColorNormal,
        backgroundColorDisable = backgroundColorDisable,
        contentColorEnable = contentColorEnable,
        contentColorDisable = contentColorDisable,
        borderColor = borderColor,
    )
}