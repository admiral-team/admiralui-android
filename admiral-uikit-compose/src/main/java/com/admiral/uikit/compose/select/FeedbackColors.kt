package com.admiral.uikit.compose.select

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.ThemeManagerCompose

@Immutable
data class FeedbackColors(
    val iconTintNormalEnabled: Color,
    val iconTintNormalDisabled: Color,
    val iconTintSelectedEnabled: Color,
    val iconTintSelectedDisabled: Color
) {
    @Composable
    fun iconTintNormalColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) iconTintNormalEnabled else iconTintNormalDisabled)
    }

    @Composable
    fun iconTintSelectedColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) iconTintSelectedEnabled else iconTintSelectedDisabled)
    }
}

@Suppress("MagicNumber")
@Composable
fun feedbackColors(
    iconTintNormalEnabled: Color = Color(ThemeManagerCompose.theme.value.palette.elementAdditional),
    iconTintNormalDisabled: Color = Color(ThemeManagerCompose.theme.value.palette.elementAdditional),
    iconTintSelectedEnabled: Color = Color(ThemeManagerCompose.theme.value.palette.elementAccent),
    iconTintSelectedDisabled: Color = Color(ThemeManagerCompose.theme.value.palette.elementAccent)
        .copy(0.6f)
): FeedbackColors = FeedbackColors(
    iconTintNormalEnabled = iconTintNormalEnabled,
    iconTintNormalDisabled = iconTintNormalDisabled,
    iconTintSelectedEnabled = iconTintSelectedEnabled,
    iconTintSelectedDisabled = iconTintSelectedDisabled
)