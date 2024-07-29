package com.admiral.uikit.compose.spinner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme

@Immutable
data class SpinnerColor(
    val spinnerColor: Color,
)

object AdmiralSpinnerColor {
    @Composable
    fun default(
        spinnerColor: Color = AdmiralTheme.colors.elementAccent,
    ) = SpinnerColor(
        spinnerColor = spinnerColor,
    )

    @Composable
    fun contrast(
        spinnerColor: Color = AdmiralTheme.colors.elementStaticWhite,
    ) = SpinnerColor(
        spinnerColor = spinnerColor,
    )
}