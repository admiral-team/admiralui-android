package com.admiral.uikit.compose.select

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme.colors
import com.admiral.themes.compose.withAlpha

@Immutable
data class FeedbackColors(
    val iconTintNormalEnabled: Color,
    val iconTintNormalDisabled: Color,
    val iconTintSelectedEnabled: Color,
    val iconTintSelectedDisabled: Color
) {
    @Composable
    fun getIconTintNormalColor(enabled: Boolean): State<Color> =
        rememberUpdatedState(if (enabled) iconTintNormalEnabled else iconTintNormalDisabled)

    @Composable
    fun getIconTintSelectedColor(enabled: Boolean): State<Color> =
        rememberUpdatedState(if (enabled) iconTintSelectedEnabled else iconTintSelectedDisabled)
}

object AdmiralFeedbackColors {
    @Suppress("MagicNumber")
    @Composable
    fun default(
        iconTintNormalEnabled: Color = colors.elementAdditional,
        iconTintNormalDisabled: Color = colors.elementAdditional,
        iconTintSelectedEnabled: Color = colors.elementAccent,
        iconTintSelectedDisabled: Color = colors.elementAccent.withAlpha()
    ): FeedbackColors = FeedbackColors(
        iconTintNormalEnabled = iconTintNormalEnabled,
        iconTintNormalDisabled = iconTintNormalDisabled,
        iconTintSelectedEnabled = iconTintSelectedEnabled,
        iconTintSelectedDisabled = iconTintSelectedDisabled
    )
}
