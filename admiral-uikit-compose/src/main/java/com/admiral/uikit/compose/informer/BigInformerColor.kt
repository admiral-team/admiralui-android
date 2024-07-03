package com.admiral.uikit.compose.informer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha

@Immutable
data class BigInformerColor(
    val background: Color,
    val textEnabled: Color,
    val textDisabled: Color,
    val linkEnabled: Color,
    val linkDisabled: Color,
    val linkPressed: Color,
) {
    @Composable
    fun getTextColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) textEnabled else textDisabled)

    @Composable
    fun getLinkColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) linkEnabled else linkDisabled)
}

object AdmiralBigInformerColor {
    @Composable
    fun info(
        background: Color = AdmiralTheme.colors.backgroundAdditionalOne,
        textEnable: Color = AdmiralTheme.colors.textPrimary,
        textDisable: Color = AdmiralTheme.colors.textPrimary.withAlpha(),
        linkEnable: Color = AdmiralTheme.colors.textAccent,
        linkDisable: Color = AdmiralTheme.colors.textAccent.withAlpha(),
        linkPressed: Color = AdmiralTheme.colors.textAccentPressed,
    ) = BigInformerColor(
        background = background,
        textEnabled = textEnable,
        textDisabled = textDisable,
        linkEnabled = linkEnable,
        linkDisabled = linkDisable,
        linkPressed = linkPressed,
    )

    @Composable
    fun attention(
        background: Color = AdmiralTheme.colors.backgroundAttention,
        textEnable: Color = AdmiralTheme.colors.textPrimary,
        textDisable: Color = AdmiralTheme.colors.textPrimary.withAlpha(),
        linkEnable: Color = AdmiralTheme.colors.textAccent,
        linkDisable: Color = AdmiralTheme.colors.textAccent.withAlpha(),
        linkPressed: Color = AdmiralTheme.colors.textAccentPressed,
    ) = BigInformerColor(
        background = background,
        textEnabled = textEnable,
        textDisabled = textDisable,
        linkEnabled = linkEnable,
        linkDisabled = linkDisable,
        linkPressed = linkPressed,
    )

    @Composable
    fun success(
        background: Color = AdmiralTheme.colors.backgroundSuccess,
        textEnable: Color = AdmiralTheme.colors.textPrimary,
        textDisable: Color = AdmiralTheme.colors.textPrimary.withAlpha(),
        linkEnable: Color = AdmiralTheme.colors.textAccent,
        linkDisable: Color = AdmiralTheme.colors.textAccent.withAlpha(),
        linkPressed: Color = AdmiralTheme.colors.textAccentPressed,
    ) = BigInformerColor(
        background = background,
        textEnabled = textEnable,
        textDisabled = textDisable,
        linkEnabled = linkEnable,
        linkDisabled = linkDisable,
        linkPressed = linkPressed,
    )

    @Composable
    fun error(
        background: Color = AdmiralTheme.colors.backgroundError,
        textEnable: Color = AdmiralTheme.colors.textPrimary,
        textDisable: Color = AdmiralTheme.colors.textPrimary.withAlpha(),
        linkEnable: Color = AdmiralTheme.colors.textAccent,
        linkDisable: Color = AdmiralTheme.colors.textAccent.withAlpha(),
        linkPressed: Color = AdmiralTheme.colors.textAccentPressed,
    ) = BigInformerColor(
        background = background,
        textEnabled = textEnable,
        textDisabled = textDisable,
        linkEnabled = linkEnable,
        linkDisabled = linkDisable,
        linkPressed = linkPressed,
    )
}