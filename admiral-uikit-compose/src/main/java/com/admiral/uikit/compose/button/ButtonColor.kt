package com.admiral.uikit.compose.button

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.core.ext.withAlpha

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
        textColorEnable: Color = textStaticWhite,
        textColorDisable: Color = textStaticWhiteWithAlfa,
        textColorPressed: Color = textStaticWhite,
        backgroundEnable: Color = backgroundAccent,
        backgroundDisable: Color = backgroundAccentWithAlfa,
        backgroundPressed: Color = backgroundAccent,
        iconTintEnable: Color = elementStaticWhite,
        iconTintDisable: Color = elementStaticWhiteWithAlfa,
        iconTintPressed: Color = elementStaticWhite,
    ): ButtonColor = remember(
        backgroundEnable,
        backgroundDisable,
        backgroundPressed,
        textColorEnable,
        textColorDisable,
        textColorPressed,
        iconTintEnable,
        iconTintDisable,
        iconTintPressed,
    ) {
        ButtonColor(
            backgroundEnable = backgroundEnable,
            backgroundDisable = backgroundDisable,
            backgroundPressed = backgroundPressed,
            textColorEnable = textColorEnable,
            textColorDisable = textColorDisable,
            textColorPressed = textColorPressed,
            iconTintEnable = iconTintEnable,
            iconTintDisable = iconTintDisable,
            iconTintPressed = iconTintPressed,
            borderColor = transparent
        )
    }

    @Composable
    fun secondary(
        textColorEnable: Color = textAccent,
        textColorDisable: Color = textAccentWithAlfa,
        textColorPressed: Color = textAccentPressed,
        backgroundEnable: Color = transparent,
        backgroundDisable: Color = transparent,
        backgroundPressed: Color = transparent,
        iconTintEnable: Color = elementAccent,
        iconTintDisable: Color = elementAccentWithAlfa,
        iconTintPressed: Color = elementAccent,
    ): ButtonColor = remember(
        backgroundEnable,
        backgroundDisable,
        backgroundPressed,
        textColorEnable,
        textColorDisable,
        textColorPressed,
        iconTintEnable,
        iconTintDisable,
        iconTintPressed,
    ) {
        ButtonColor(
            backgroundEnable = backgroundEnable,
            backgroundDisable = backgroundDisable,
            backgroundPressed = backgroundPressed,
            textColorEnable = textColorEnable,
            textColorDisable = textColorDisable,
            textColorPressed = textColorPressed,
            iconTintEnable = iconTintEnable,
            iconTintDisable = iconTintDisable,
            iconTintPressed = iconTintPressed,
            borderColor = elementAccent,
        )
    }

    @Composable
    fun ghost(
        textColorEnable: Color = textAccent,
        textColorDisable: Color = textAccentWithAlfa,
        textColorPressed: Color = textAccentPressed,
        backgroundEnable: Color = transparent,
        backgroundDisable: Color = transparent,
        backgroundPressed: Color = transparent,
        iconTintEnable: Color = elementAccent,
        iconTintDisable: Color = elementAccentWithAlfa,
        iconTintPressed: Color = elementAccent,
    ): ButtonColor = remember(
        backgroundEnable,
        backgroundDisable,
        backgroundPressed,
        textColorEnable,
        textColorDisable,
        textColorPressed,
        iconTintEnable,
        iconTintDisable,
        iconTintPressed,
    ) {
        ButtonColor(
            backgroundEnable = backgroundEnable,
            backgroundDisable = backgroundDisable,
            backgroundPressed = backgroundPressed,
            textColorEnable = textColorEnable,
            textColorDisable = textColorDisable,
            textColorPressed = textColorPressed,
            iconTintEnable = iconTintEnable,
            iconTintDisable = iconTintDisable,
            iconTintPressed = iconTintPressed,
            borderColor = transparent,
        )
    }
}

private val palette = ThemeManagerCompose.theme.value.palette
private val textStaticWhite = Color(palette.textStaticWhite)
private val textStaticWhiteWithAlfa = Color(palette.textStaticWhite.withAlpha())
private val textAccent = Color(palette.textAccent)
private val textAccentWithAlfa = Color(palette.textAccent.withAlpha())
private val textAccentPressed = Color(palette.textAccentPressed)
private val backgroundAccent = Color(palette.backgroundAccent)
private val backgroundAccentWithAlfa = Color(palette.backgroundAccent.withAlpha())
private val elementStaticWhite = Color(palette.elementStaticWhite)
private val elementStaticWhiteWithAlfa = Color(palette.elementStaticWhite.withAlpha())
private val elementAccent = Color(palette.elementAccent)
private val elementAccentWithAlfa = Color(palette.elementAccent.withAlpha())
private val transparent = Color(palette.transparent)