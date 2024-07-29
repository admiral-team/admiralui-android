package com.admiral.uikit.compose.switcher

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.AdmiralTheme.colors
import com.admiral.themes.compose.withAlpha

@Immutable
data class SwitcherColor(
    val checkedThumb: Color,
    val checkedTrack: Color,
    val uncheckedThumb: Color,
    val uncheckedTrack: Color,
    val disabledCheckedThumb: Color,
    val disabledCheckedTrack: Color,
    val disabledUncheckedThumb: Color,
    val disabledUncheckedTrack: Color,
    val textEnabled: Color,
    val textDisabled: Color,
    val textPressed: Color,
) {
    @Composable
    fun getThumbColor(isEnabled: Boolean, isChecked: Boolean) =
        rememberUpdatedState(
            newValue = when {
                isEnabled && isChecked -> checkedThumb
                !isEnabled && isChecked -> disabledCheckedThumb
                isEnabled && !isChecked -> uncheckedThumb
                else -> disabledUncheckedThumb
            }
        )

    @Composable
    fun getTrackColor(isEnabled: Boolean, isChecked: Boolean) =
        rememberUpdatedState(
            newValue = when {
                isEnabled && isChecked -> checkedTrack
                !isEnabled && isChecked -> disabledCheckedTrack
                isEnabled && !isChecked -> uncheckedTrack
                else -> disabledUncheckedTrack
            }
        )

    @Composable
    fun getTextColor(isEnabled: Boolean) =
        rememberUpdatedState(newValue = if (isEnabled) textEnabled else textDisabled)
}

object AdmiralSwithcerColors {
    @Composable
    fun default(
        background: Color = colors.backgroundBasic,
        checkedThumbColor: Color = colors.elementStaticWhite,
        checkedTrackColor: Color = colors.elementAccent,
        uncheckedThumbColor: Color = colors.elementStaticWhite,
        uncheckedTrackColor: Color = colors.elementPrimary,
        disabledCheckedThumbColor: Color = colors.elementStaticWhite
            .withAlpha()
            .compositeOver(background),
        disabledCheckedTrackColor: Color = colors.elementAccent
            .withAlpha()
            .compositeOver(background),
        disabledUncheckedThumbColor: Color = colors.elementStaticWhite
            .withAlpha()
            .compositeOver(background),
        disabledUncheckedTrackColor: Color = colors.elementPrimary
            .withAlpha()
            .compositeOver(background),
        textEnabled: Color = colors.textPrimary,
        textDisabled: Color = colors.textPrimary.withAlpha(),
        textPressed: Color = colors.textPrimary,
    ): SwitcherColor = SwitcherColor(
        checkedThumb = checkedThumbColor,
        checkedTrack = checkedTrackColor,
        uncheckedThumb = uncheckedThumbColor,
        uncheckedTrack = uncheckedTrackColor,
        disabledCheckedThumb = disabledCheckedThumbColor,
        disabledCheckedTrack = disabledCheckedTrackColor,
        disabledUncheckedThumb = disabledUncheckedThumbColor,
        disabledUncheckedTrack = disabledUncheckedTrackColor,
        textEnabled = textEnabled,
        textDisabled = textDisabled,
        textPressed = textPressed,
    )
}