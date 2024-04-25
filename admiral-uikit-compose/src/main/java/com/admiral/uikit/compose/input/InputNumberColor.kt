package com.admiral.uikit.compose.input

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme.colors
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
    fun getTextColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) textEnable else textDisable)

    @Composable
    fun getTextFieldBackgroundColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) textFieldBackgroundEnable else textFieldBackgroundDisable)

    @Composable
    fun getTextFieldCursorColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) textFieldCursorEnabled else textFieldCursorDisable)

    @Composable
    fun getIconTintColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) iconTintEnable else iconTintDisable)

    @Composable
    fun getIconBorderColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) iconBorderEnable else iconBorderDisable)

    @Composable
    fun getBackgroundColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) iconBackgroundEnable else iconBackgroundDisable)

    @Composable
    fun getRippleColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) rippleEnable else rippleDisable)
}

private const val RippleAlpha = 0.1f

object AdmiralInputNumberColors {
    @Composable
    fun oval(
        textEnable: Color = colors.textPrimary,
        textDisable: Color = colors.textPrimary.withAlpha(),
        textFieldBackgroundEnable: Color = Color.Transparent,
        textFieldBackgroundDisable: Color = Color.Transparent,
        textFieldCursorEnabled: Color = colors.textAccent,
        textFieldCursorDisable: Color = colors.textAccent.withAlpha(),
        iconTintEnable: Color = colors.elementPrimary,
        iconTintDisable: Color = colors.elementPrimary.withAlpha(),
        iconBackgroundEnable: Color = colors.backgroundAdditionalOne,
        iconBackgroundDisable: Color = colors.backgroundAdditionalOne.withAlpha(),
        iconBorderEnable: Color = Color.Transparent,
        iconBorderDisable: Color = Color.Transparent,
        rippleEnable: Color = colors.textPrimary.withAlpha(RippleAlpha),
        rippleDisable: Color = colors.textPrimary.withAlpha(RippleAlpha),
    ) = InputNumberColors(
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

    @Composable
    fun rectangle(
        textEnable: Color = colors.textPrimary,
        textDisable: Color = colors.textPrimary.withAlpha(),
        textFieldBackgroundEnable: Color = Color.Transparent,
        textFieldBackgroundDisable: Color = Color.Transparent,
        textFieldCursorEnabled: Color = colors.textAccent,
        textFieldCursorDisable: Color = colors.textAccent.withAlpha(),
        iconTintEnable: Color = colors.elementAccent,
        iconTintDisable: Color = colors.elementAccent.withAlpha(),
        iconBackgroundEnable: Color = Color.Transparent,
        iconBackgroundDisable: Color = Color.Transparent,
        iconBorderEnable: Color = colors.elementAccent,
        iconBorderDisable: Color = colors.elementAccent.withAlpha(),
        rippleEnable: Color = colors.textPrimary.withAlpha(RippleAlpha),
        rippleDisable: Color = colors.textPrimary.withAlpha(RippleAlpha),
    ) = InputNumberColors(
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

    @Composable
    fun textField(
        textEnable: Color = colors.textPrimary,
        textDisable: Color = colors.textPrimary.withAlpha(),
        textFieldBackgroundEnable: Color = colors.backgroundAdditionalOne,
        textFieldBackgroundDisable: Color = colors.backgroundAdditionalOne.withAlpha(),
        textFieldCursorEnabled: Color = colors.textAccent,
        textFieldCursorDisable: Color = colors.textAccent.withAlpha(),
        iconTintEnable: Color = colors.elementPrimary,
        iconTintDisable: Color = colors.elementPrimary.withAlpha(),
        iconBackgroundEnable: Color = colors.backgroundAdditionalOne,
        iconBackgroundDisable: Color = colors.backgroundAdditionalOne.withAlpha(),
        iconBorderEnable: Color = Color.Transparent,
        iconBorderDisable: Color = Color.Transparent,
        rippleEnable: Color = colors.textPrimary.withAlpha(RippleAlpha),
        rippleDisable: Color = colors.textPrimary.withAlpha(RippleAlpha),
    ) = InputNumberColors(
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
