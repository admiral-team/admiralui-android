package com.admiral.uikit.compose.chip

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme.colors
import com.admiral.themes.compose.withAlpha

@Immutable
data class ChipColor(
    val iconEnabled: Color,
    val iconDisabled: Color,
    val iconPressed: Color,
    val backgroundEnabled: Color,
    val backgroundDisabled: Color,
    val backgroundPressed: Color,
    val textEnabled: Color,
    val textDisabled: Color,
    val textPressed: Color,
) {
    @Composable
    fun getIconColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) iconEnabled else iconDisabled)

    @Composable
    fun getBackgroundColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) backgroundEnabled else backgroundDisabled)

    @Composable
    fun getTextColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) textEnabled else textDisabled)
}

object AdmiralChipColor {
    @Composable
    fun primary(): ChipColor = ChipColor(
        iconEnabled = colors.elementAccent,
        iconDisabled = colors.elementAccent.withAlpha(),
        iconPressed = colors.elementAccentPressed,
        backgroundEnabled = colors.backgroundSelected,
        backgroundDisabled = colors.backgroundSelected,
        backgroundPressed = colors.backgroundSelectedPressed,
        textEnabled = colors.textPrimary,
        textDisabled = colors.textPrimary.withAlpha(),
        textPressed = colors.textPrimary,
    )

    @Composable
    fun additional(): ChipColor = ChipColor(
        iconEnabled = colors.elementPrimary,
        iconDisabled = colors.elementPrimary.withAlpha(),
        iconPressed = colors.elementPrimary,
        backgroundEnabled = colors.backgroundAdditionalOne,
        backgroundDisabled = colors.backgroundAdditionalOne,
        backgroundPressed = colors.backgroundAdditionalOnePressed,
        textEnabled = colors.textPrimary,
        textDisabled = colors.textPrimary.withAlpha(),
        textPressed = colors.textPrimary,
    )
}