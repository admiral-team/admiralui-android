package com.admiral.uikit.compose.button

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha

@Immutable
data class ButtonColor(
    val textColorEnable: Color,
    val textColorDisable: Color,
    val textColorPressed: Color,
    val backgroundEnable: Color,
    val backgroundDisable: Color,
    val backgroundPressed: Color,
    val iconTintEnable: Color,
    val iconTintDisable: Color,
    val iconTintPressed: Color,
    val borderColor: Color,
)

object AdmiralButtonColor {
    @Composable
    fun primary(
        textColorEnable: Color = AdmiralTheme.colors.textStaticWhite,
        textColorDisable: Color = AdmiralTheme.colors.textStaticWhite.withAlpha(),
        textColorPressed: Color = AdmiralTheme.colors.textStaticWhite,
        backgroundEnable: Color = AdmiralTheme.colors.backgroundAccent,
        backgroundDisable: Color = AdmiralTheme.colors.backgroundAccent.withAlpha(),
        backgroundPressed: Color = AdmiralTheme.colors.backgroundAccent,
        iconTintEnable: Color = AdmiralTheme.colors.elementStaticWhite,
        iconTintDisable: Color = AdmiralTheme.colors.elementStaticWhite.withAlpha(),
        iconTintPressed: Color = AdmiralTheme.colors.elementStaticWhite,
    ) = ButtonColor(
        backgroundEnable = backgroundEnable,
        backgroundDisable = backgroundDisable,
        backgroundPressed = backgroundPressed,
        textColorEnable = textColorEnable,
        textColorDisable = textColorDisable,
        textColorPressed = textColorPressed,
        iconTintEnable = iconTintEnable,
        iconTintDisable = iconTintDisable,
        iconTintPressed = iconTintPressed,
        borderColor = Color.Transparent
    )

    @Composable
    fun secondary(
        textColorEnable: Color = AdmiralTheme.colors.textAccent,
        textColorDisable: Color = AdmiralTheme.colors.textAccent.withAlpha(),
        textColorPressed: Color = AdmiralTheme.colors.textAccentPressed,
        backgroundEnable: Color = Color.Transparent,
        backgroundDisable: Color = Color.Transparent,
        backgroundPressed: Color = Color.Transparent,
        iconTintEnable: Color = AdmiralTheme.colors.elementAccent,
        iconTintDisable: Color = AdmiralTheme.colors.elementAccent.withAlpha(),
        iconTintPressed: Color = AdmiralTheme.colors.elementAccent,
    ) = ButtonColor(
        backgroundEnable = backgroundEnable,
        backgroundDisable = backgroundDisable,
        backgroundPressed = backgroundPressed,
        textColorEnable = textColorEnable,
        textColorDisable = textColorDisable,
        textColorPressed = textColorPressed,
        iconTintEnable = iconTintEnable,
        iconTintDisable = iconTintDisable,
        iconTintPressed = iconTintPressed,
        borderColor = AdmiralTheme.colors.elementAccent,
    )

    @Composable
    fun ghost(
        textColorEnable: Color = AdmiralTheme.colors.textAccent,
        textColorDisable: Color = AdmiralTheme.colors.textAccent.withAlpha(),
        textColorPressed: Color = AdmiralTheme.colors.textAccentPressed,
        backgroundEnable: Color = Color.Transparent,
        backgroundDisable: Color = Color.Transparent,
        backgroundPressed: Color = Color.Transparent,
        iconTintEnable: Color = AdmiralTheme.colors.elementAccent,
        iconTintDisable: Color = AdmiralTheme.colors.elementAccent.withAlpha(),
        iconTintPressed: Color = AdmiralTheme.colors.elementAccent,
    ) = ButtonColor(
        backgroundEnable = backgroundEnable,
        backgroundDisable = backgroundDisable,
        backgroundPressed = backgroundPressed,
        textColorEnable = textColorEnable,
        textColorDisable = textColorDisable,
        textColorPressed = textColorPressed,
        iconTintEnable = iconTintEnable,
        iconTintDisable = iconTintDisable,
        iconTintPressed = iconTintPressed,
        borderColor = Color.Transparent,
    )
}