package com.admiral.uikit.compose.pincode

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.AdmiralTheme.colors
import com.admiral.themes.compose.ThemeManagerCompose

@Immutable
data class PinCodeColors(
    val defaultColor: Color,
    val activeColor: Color,
    val successColor: Color,
    val errorColor: Color,
)

@Composable
fun pinCodeColors(
    defaultColor: Color = colors.elementAdditional,
    activeColor: Color = colors.elementAccent,
    successColor: Color = colors.elementSuccess,
    errorColor: Color = colors.elementError,
): PinCodeColors = PinCodeColors(
    defaultColor = defaultColor,
    activeColor = activeColor,
    successColor = successColor,
    errorColor = errorColor,
)