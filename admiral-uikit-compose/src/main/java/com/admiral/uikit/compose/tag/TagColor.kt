package com.admiral.uikit.compose.tag

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme.colors
import com.admiral.themes.compose.withAlpha

@Immutable
data class TagColor(
    val iconEnable: Color,
    val iconDisable: Color,
    val iconPressed: Color,
    val backgroundEnable: Color,
    val backgroundDisable: Color,
    val backgroundPressed: Color,
    val textEnable: Color,
    val textDisable: Color,
    val textPressed: Color,
) {
    @Composable
    fun getIconColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) iconEnable else iconDisable)

    @Composable
    fun getBackgroundColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) backgroundEnable else backgroundDisable)

    @Composable
    fun getTextColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) textEnable else textDisable)
}

object AdmiralTagColor {
    @Composable
    fun blue(): TagColor = TagColor(
        iconEnable = colors.elementAccent,
        iconDisable = colors.elementAccent.withAlpha(),
        iconPressed = colors.elementAccentPressed,
        backgroundEnable = colors.backgroundSelected,
        backgroundDisable = colors.backgroundSelected,
        backgroundPressed = colors.backgroundSelectedPressed,
        textEnable = colors.textPrimary,
        textDisable = colors.textPrimary.withAlpha(),
        textPressed = colors.textPrimary,
    )

    @Composable
    fun gray(): TagColor = TagColor(
        iconEnable = colors.elementPrimary,
        iconDisable = colors.elementPrimary.withAlpha(),
        iconPressed = colors.elementPrimary,
        backgroundEnable = colors.backgroundAdditionalOne,
        backgroundDisable = colors.backgroundAdditionalOne,
        backgroundPressed = colors.backgroundAdditionalOnePressed,
        textEnable = colors.textPrimary,
        textDisable = colors.textPrimary.withAlpha(),
        textPressed = colors.textPrimary,
    )

    @Composable
    fun green(): TagColor = TagColor(
        iconEnable = colors.elementSuccess,
        iconDisable = colors.elementSuccess.withAlpha(),
        iconPressed = colors.elementSuccessPressed,
        backgroundEnable = colors.backgroundSuccess,
        backgroundDisable = colors.backgroundSuccess,
        backgroundPressed = colors.backgroundSuccess,
        textEnable = colors.textPrimary,
        textDisable = colors.textPrimary.withAlpha(),
        textPressed = colors.textPrimary,
    )

    @Composable
    fun orange(): TagColor = TagColor(
        iconEnable = colors.elementAttention,
        iconDisable = colors.elementAttention.withAlpha(),
        iconPressed = colors.elementAttentionPressed,
        backgroundEnable = colors.backgroundAttention,
        backgroundDisable = colors.backgroundAttention,
        backgroundPressed = colors.backgroundAttention,
        textEnable = colors.textPrimary,
        textDisable = colors.textPrimary.withAlpha(),
        textPressed = colors.textPrimary,
    )

    @Composable
    fun red(): TagColor = TagColor(
        iconEnable = colors.elementError,
        iconDisable = colors.elementError.withAlpha(),
        iconPressed = colors.elementErrorPressed,
        backgroundEnable = colors.backgroundError,
        backgroundDisable = colors.backgroundError,
        backgroundPressed = colors.backgroundError,
        textEnable = colors.textPrimary,
        textDisable = colors.textPrimary.withAlpha(),
        textPressed = colors.textPrimary,
    )
}