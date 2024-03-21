package com.admiral.uikit.compose.pincode

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
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
    defaultColor: Color = Color(ThemeManagerCompose.theme.value.palette.elementAdditional),
    activeColor: Color = Color(ThemeManagerCompose.theme.value.palette.elementAccent),
    successColor: Color = Color(ThemeManagerCompose.theme.value.palette.elementSuccess),
    errorColor: Color = Color(ThemeManagerCompose.theme.value.palette.elementError),
): PinCodeColors = PinCodeColors(
    defaultColor = defaultColor,
    activeColor = activeColor,
    successColor = successColor,
    errorColor = errorColor,
)