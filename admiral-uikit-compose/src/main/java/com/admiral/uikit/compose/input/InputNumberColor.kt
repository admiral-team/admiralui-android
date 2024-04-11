package com.admiral.uikit.compose.input

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha

@Immutable
data class InputNumberColors(
    val textEnable: Color,
    val textDisable: Color,
    val textFieldBackgroundEnable: Color,
    val textFieldBackgroundDisable: Color,
    val textFieldCursorEnabled: Color,
    val textFieldCursorDisable: Color,
    val iconTintEnable: Color,
    val iconTintDisable: Color,
    val iconBackgroundEnable: Color,
    val iconBackgroundDisable: Color,
    val iconBorderEnable: Color,
    val iconBorderDisable: Color,
    val rippleEnable: Color,
    val rippleDisable: Color,
) {

    @Composable
    fun getTextColor(isEnabled: Boolean) = if (isEnabled) textEnable else textDisable

    @Composable
    fun getTextFieldBackgroundColor(isEnabled: Boolean) =
        if (isEnabled) textFieldBackgroundEnable else textFieldBackgroundDisable

    @Composable
    fun getTextFieldCursorColor(isEnabled: Boolean) =
        if (isEnabled) textFieldCursorEnabled else textFieldCursorDisable

    @Composable
    fun getIconTintColor(isEnabled: Boolean) =
        if (isEnabled) iconTintEnable else iconTintDisable

    @Composable
    fun getIconBorderColor(isEnabled: Boolean) =
        if (isEnabled) iconBorderEnable else iconBorderDisable

    @Composable
    fun getBackgroundColor(isEnabled: Boolean) =
        if (isEnabled) iconBackgroundEnable else iconBackgroundDisable

    @Composable
    fun getRippleColor(isEnabled: Boolean) =
        if (isEnabled) rippleEnable else rippleDisable
}

private const val RippleAlpha = 0.1f

object AdmiralInputNumberColors {
    @Composable
    fun oval(
        textEnable: Color = AdmiralTheme.colors.textPrimary,
        textDisable: Color = AdmiralTheme.colors.textPrimary.withAlpha(),
        textFieldBackgroundEnable: Color = Color.Transparent,
        textFieldBackgroundDisable: Color = Color.Transparent,
        textFieldCursorEnabled: Color = AdmiralTheme.colors.textAccent,
        textFieldCursorDisable: Color = AdmiralTheme.colors.textAccent.withAlpha(),
        iconTintEnable: Color = AdmiralTheme.colors.elementPrimary,
        iconTintDisable: Color = AdmiralTheme.colors.elementPrimary.withAlpha(),
        iconBackgroundEnable: Color = AdmiralTheme.colors.backgroundAdditionalOne,
        iconBackgroundDisable: Color = AdmiralTheme.colors.backgroundAdditionalOne.withAlpha(),
        iconBorderEnable: Color = Color.Transparent,
        iconBorderDisable: Color = Color.Transparent,
        rippleEnable: Color = AdmiralTheme.colors.textPrimary.withAlpha(RippleAlpha),
        rippleDisable: Color = AdmiralTheme.colors.textPrimary.withAlpha(RippleAlpha),
    ) = remember {
        InputNumberColors(
            textEnable = textEnable,
            textDisable = textDisable,
            textFieldBackgroundEnable = textFieldBackgroundEnable,
            textFieldBackgroundDisable = textFieldBackgroundDisable,
            textFieldCursorEnabled = textFieldCursorEnabled,
            textFieldCursorDisable = textFieldCursorDisable,
            iconTintEnable = iconTintEnable,
            iconTintDisable = iconTintDisable,
            iconBackgroundEnable = iconBackgroundEnable,
            iconBackgroundDisable = iconBackgroundDisable,
            iconBorderEnable = iconBorderEnable,
            iconBorderDisable = iconBorderDisable,
            rippleEnable = rippleEnable,
            rippleDisable = rippleDisable
        )
    }

    @Composable
    fun rectangle(
        textEnable: Color = AdmiralTheme.colors.textPrimary,
        textDisable: Color = AdmiralTheme.colors.textPrimary.withAlpha(),
        textFieldBackgroundEnable: Color = Color.Transparent,
        textFieldBackgroundDisable: Color = Color.Transparent,
        textFieldCursorEnabled: Color = AdmiralTheme.colors.textAccent,
        textFieldCursorDisable: Color = AdmiralTheme.colors.textAccent.withAlpha(),
        iconTintEnable: Color = AdmiralTheme.colors.elementAccent,
        iconTintDisable: Color = AdmiralTheme.colors.elementAccent.withAlpha(),
        iconBackgroundEnable: Color = Color.Transparent,
        iconBackgroundDisable: Color = Color.Transparent,
        iconBorderEnable: Color = AdmiralTheme.colors.elementAccent,
        iconBorderDisable: Color = AdmiralTheme.colors.elementAccent.withAlpha(),
        rippleEnable: Color = AdmiralTheme.colors.textPrimary.withAlpha(RippleAlpha),
        rippleDisable: Color = AdmiralTheme.colors.textPrimary.withAlpha(RippleAlpha),
    ) = remember {
        InputNumberColors(
            textEnable = textEnable,
            textDisable = textDisable,
            textFieldBackgroundEnable = textFieldBackgroundEnable,
            textFieldBackgroundDisable = textFieldBackgroundDisable,
            textFieldCursorEnabled = textFieldCursorEnabled,
            textFieldCursorDisable = textFieldCursorDisable,
            iconTintEnable = iconTintEnable,
            iconTintDisable = iconTintDisable,
            iconBackgroundEnable = iconBackgroundEnable,
            iconBackgroundDisable = iconBackgroundDisable,
            iconBorderEnable = iconBorderEnable,
            iconBorderDisable = iconBorderDisable,
            rippleEnable = rippleEnable,
            rippleDisable = rippleDisable
        )
    }

    @Composable
    fun textField(
        textEnable: Color = AdmiralTheme.colors.textPrimary,
        textDisable: Color = AdmiralTheme.colors.textPrimary.withAlpha(),
        textFieldBackgroundEnable: Color = AdmiralTheme.colors.backgroundAdditionalOne,
        textFieldBackgroundDisable: Color = AdmiralTheme.colors.backgroundAdditionalOne.withAlpha(),
        textFieldCursorEnabled: Color = AdmiralTheme.colors.textAccent,
        textFieldCursorDisable: Color = AdmiralTheme.colors.textAccent.withAlpha(),
        iconTintEnable: Color = AdmiralTheme.colors.elementPrimary,
        iconTintDisable: Color = AdmiralTheme.colors.elementPrimary.withAlpha(),
        iconBackgroundEnable: Color = AdmiralTheme.colors.backgroundAdditionalOne,
        iconBackgroundDisable: Color = AdmiralTheme.colors.backgroundAdditionalOne.withAlpha(),
        iconBorderEnable: Color = Color.Transparent,
        iconBorderDisable: Color = Color.Transparent,
        rippleEnable: Color = AdmiralTheme.colors.textPrimary.withAlpha(RippleAlpha),
        rippleDisable: Color = AdmiralTheme.colors.textPrimary.withAlpha(RippleAlpha),
    ) = remember {
        InputNumberColors(
            textEnable = textEnable,
            textDisable = textDisable,
            textFieldBackgroundEnable = textFieldBackgroundEnable,
            textFieldBackgroundDisable = textFieldBackgroundDisable,
            textFieldCursorEnabled = textFieldCursorEnabled,
            textFieldCursorDisable = textFieldCursorDisable,
            iconTintEnable = iconTintEnable,
            iconTintDisable = iconTintDisable,
            iconBackgroundEnable = iconBackgroundEnable,
            iconBackgroundDisable = iconBackgroundDisable,
            iconBorderEnable = iconBorderEnable,
            iconBorderDisable = iconBorderDisable,
            rippleEnable = rippleEnable,
            rippleDisable = rippleDisable
        )
    }
}