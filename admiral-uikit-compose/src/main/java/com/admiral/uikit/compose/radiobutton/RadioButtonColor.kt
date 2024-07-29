package com.admiral.uikit.compose.radiobutton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme.colors
import com.admiral.themes.compose.withAlpha

@Immutable
data class RadioButtonColor(
    val normalEnabled: Color,
    val normalDisabled: Color,
    val checkedEnabled: Color,
    val checkedDisabled: Color,
    val normalEnabledError: Color,
    val normalDisabledError: Color,
    val checkedEnabledError: Color,
    val checkedDisabledError: Color,
    val textEnabled: Color,
    val textDisabled: Color,
    val textCheckedEnabled: Color,
    val textCheckedDisabled: Color,
    val textEnabledError: Color,
    val textDisabledError: Color,
    val textCheckedEnabledError: Color,
    val textCheckedDisabledError: Color,
) {
    @Composable
    fun getButtonColor(isEnabled: Boolean, isError: Boolean, isSelected: Boolean) =
        rememberUpdatedState(
            when {
                isError && isSelected -> if (isEnabled) checkedEnabledError else checkedDisabledError
                isError && !isSelected -> if (isEnabled) normalEnabledError else normalDisabledError
                !isError && isSelected -> if (isEnabled) checkedEnabled else checkedDisabled
                else -> if (isEnabled) normalEnabled else normalDisabled
            }
        )

    @Composable
    fun getTextColor(isEnabled: Boolean, isError: Boolean, isSelected: Boolean) =
        rememberUpdatedState(
            when {
                isError && isSelected -> if (isEnabled) textCheckedEnabledError else textCheckedDisabledError
                isError && !isSelected -> if (isEnabled) textEnabledError else textDisabledError
                !isError && isSelected -> if (isEnabled) textCheckedEnabled else textCheckedDisabled
                else -> if (isEnabled) textEnabled else textDisabled
            }
        )
}

object AdmiralRadioButtonColor {
    @Composable
    fun default(
        normalEnabled: Color = colors.elementAccent,
        normalDisabled: Color = colors.elementAccent.withAlpha(),
        checkedEnabled: Color = colors.elementAccent,
        checkedDisabled: Color = colors.elementAccent.withAlpha(),
        normalEnabledError: Color = colors.elementError,
        normalDisabledError: Color = colors.elementError.withAlpha(),
        checkedEnabledError: Color = colors.elementError,
        checkedDisabledError: Color = colors.elementError.withAlpha(),
        textEnabled: Color = colors.textPrimary,
        textDisabled: Color = colors.textPrimary.withAlpha(),
        textCheckedEnabled: Color = colors.textPrimary,
        textCheckedDisabled: Color = colors.textPrimary.withAlpha(),
        textEnabledError: Color = colors.textPrimary,
        textDisabledError: Color = colors.textPrimary.withAlpha(),
        textCheckedEnabledError: Color = colors.textPrimary,
        textCheckedDisabledError: Color = colors.textPrimary.withAlpha(),
    ) = RadioButtonColor(
        normalEnabled = normalEnabled,
        normalDisabled = normalDisabled,
        checkedEnabled = checkedEnabled,
        checkedDisabled = checkedDisabled,
        normalEnabledError = normalEnabledError,
        normalDisabledError = normalDisabledError,
        checkedEnabledError = checkedEnabledError,
        checkedDisabledError = checkedDisabledError,
        textEnabled = textEnabled,
        textDisabled = textDisabled,
        textCheckedEnabled = textCheckedEnabled,
        textCheckedDisabled = textCheckedDisabled,
        textEnabledError = textEnabledError,
        textDisabledError = textDisabledError,
        textCheckedEnabledError = textCheckedEnabledError,
        textCheckedDisabledError = textCheckedDisabledError,
    )
}
