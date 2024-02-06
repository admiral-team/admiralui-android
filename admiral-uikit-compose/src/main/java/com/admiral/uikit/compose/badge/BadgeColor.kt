package com.admiral.uikit.compose.badge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.ThemeManagerCompose

@Immutable
data class BadgeColor(
    val backgroundColorNormal: Color,
    val backgroundColorDisable: Color,
    val contentColorEnable: Color,
    val contentColorDisable: Color,
    val borderColor: Color,
)

@Composable
fun normal(
    backgroundColorNormal: Color = elementAccent,
    backgroundColorDisable: Color = elementAdditional,
    contentColorEnable: Color = textStaticWhite,
    contentColorDisable: Color = textSecondary,
    borderColor: Color = backgroundBasic
): BadgeColor = remember(backgroundColorNormal, contentColorEnable, contentColorDisable) {
    BadgeColor(
        backgroundColorNormal = backgroundColorNormal,
        backgroundColorDisable = backgroundColorDisable,
        contentColorEnable = contentColorEnable,
        contentColorDisable = contentColorEnable,
        borderColor = borderColor,
    )
}

@Composable
fun error(
    backgroundColorNormal: Color = Color(palette.elementError),
    backgroundColorDisable: Color = elementAdditional,
    contentColorEnable: Color = textStaticWhite,
    contentColorDisable: Color = textSecondary,
    borderColor: Color = backgroundBasic
): BadgeColor = remember(backgroundColorNormal, contentColorEnable, contentColorDisable) {
    BadgeColor(
        backgroundColorNormal = backgroundColorNormal,
        backgroundColorDisable = backgroundColorDisable,
        contentColorEnable = contentColorEnable,
        contentColorDisable = contentColorDisable,
        borderColor = borderColor,
    )
}

@Composable
fun attention(
    backgroundColorNormal: Color = Color(palette.elementAttention),
    backgroundColorDisable: Color = elementAdditional,
    contentColorEnable: Color = textStaticWhite,
    contentColorDisable: Color = textSecondary,
    borderColor: Color = backgroundBasic
): BadgeColor = remember(backgroundColorNormal, contentColorEnable, contentColorDisable) {
    BadgeColor(
        backgroundColorNormal = backgroundColorNormal,
        backgroundColorDisable = backgroundColorDisable,
        contentColorEnable = contentColorEnable,
        contentColorDisable = contentColorDisable,
        borderColor = borderColor,
    )
}

@Composable
fun success(
    backgroundColorNormal: Color = Color(palette.elementSuccess),
    backgroundColorDisable: Color = elementAdditional,
    contentColorEnable: Color = textStaticWhite,
    contentColorDisable: Color = textSecondary,
    borderColor: Color = backgroundBasic
): BadgeColor = remember(backgroundColorNormal, contentColorEnable, contentColorDisable) {
    BadgeColor(
        backgroundColorNormal = backgroundColorNormal,
        backgroundColorDisable = backgroundColorDisable,
        contentColorEnable = contentColorEnable,
        contentColorDisable = contentColorDisable,
        borderColor = borderColor,
    )
}

@Composable
fun neutral(
    backgroundColorNormal: Color = Color(palette.elementSecondary),
    backgroundColorDisable: Color = elementAdditional,
    contentColorEnable: Color = textStaticWhite,
    contentColorDisable: Color = textSecondary,
    borderColor: Color = backgroundBasic
): BadgeColor = remember(backgroundColorNormal, contentColorEnable, contentColorDisable) {
    BadgeColor(
        backgroundColorNormal = backgroundColorNormal,
        backgroundColorDisable = backgroundColorDisable,
        contentColorEnable = contentColorEnable,
        contentColorDisable = contentColorDisable,
        borderColor = borderColor,
    )
}

@Composable
fun additional(
    backgroundColorNormal: Color = Color(palette.elementPrimary),
    backgroundColorDisable: Color = elementAdditional,
    contentColorEnable: Color = textStaticWhite,
    contentColorDisable: Color = textSecondary,
    borderColor: Color = backgroundBasic
): BadgeColor = remember(backgroundColorNormal, contentColorEnable, contentColorDisable) {
    BadgeColor(
        backgroundColorNormal = backgroundColorNormal,
        backgroundColorDisable = backgroundColorDisable,
        contentColorEnable = contentColorEnable,
        contentColorDisable = contentColorDisable,
        borderColor = borderColor,
    )
}

private val palette = ThemeManagerCompose.theme.value.palette
private val textStaticWhite = Color(palette.textStaticWhite)
private val elementAccent = Color(palette.elementAccent)
private val textSecondary = Color(palette.textSecondary)
private val elementAdditional = Color(palette.elementAdditional)
private val backgroundBasic = Color(palette.backgroundBasic)