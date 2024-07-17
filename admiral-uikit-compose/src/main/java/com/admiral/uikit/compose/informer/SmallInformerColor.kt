package com.admiral.uikit.compose.informer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha

@Immutable
data class SmallInformerColor(
    val background: Color,
    val textEnabled: Color,
    val textDisabled: Color,
) {
    @Composable
    fun getTextColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) textEnabled else textDisabled)
}

object AdmiralSmallInformerColor {
    @Composable
    fun info(
        background: Color = AdmiralTheme.colors.backgroundAdditionalOne,
        textEnable: Color = AdmiralTheme.colors.textPrimary,
        textDisable: Color = AdmiralTheme.colors.textPrimary.withAlpha(),
    ) = SmallInformerColor(
        background = background,
        textEnabled = textEnable,
        textDisabled = textDisable,
    )

    @Composable
    fun attention(
        background: Color = AdmiralTheme.colors.backgroundAttention,
        textEnable: Color = AdmiralTheme.colors.textPrimary,
        textDisable: Color = AdmiralTheme.colors.textPrimary.withAlpha(),
    ) = SmallInformerColor(
        background = background,
        textEnabled = textEnable,
        textDisabled = textDisable,
    )

    @Composable
    fun success(
        background: Color = AdmiralTheme.colors.backgroundSuccess,
        textEnable: Color = AdmiralTheme.colors.textPrimary,
        textDisable: Color = AdmiralTheme.colors.textPrimary.withAlpha(),
    ) = SmallInformerColor(
        background = background,
        textEnabled = textEnable,
        textDisabled = textDisable,
    )

    @Composable
    fun error(
        background: Color = AdmiralTheme.colors.backgroundError,
        textEnable: Color = AdmiralTheme.colors.textPrimary,
        textDisable: Color = AdmiralTheme.colors.textPrimary.withAlpha(),
    ) = SmallInformerColor(
        background = background,
        textEnabled = textEnable,
        textDisabled = textDisable,
    )
}