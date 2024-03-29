package com.admiral.uikit.compose

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState

@Preview
@Composable
fun SpinnerPreview() {
    Spinner(
        size = SpinnerSize.SMALL
    )
}

@Composable
fun Spinner(
    modifier: Modifier = Modifier,
    size: SpinnerSize = SpinnerSize.MEDIUM,
    isEnabled: Boolean = true,
    enableColor: Color = AdmiralTheme.colors.backgroundAccent,
    disableColor: Color = AdmiralTheme.colors.backgroundAccent.withAlpha(),
) {

    val spinnerColor = if (isEnabled) enableColor else disableColor

    val sizeDp = when (size) {
        SpinnerSize.BIG -> SPINNER_BIG_SIZE.dp
        SpinnerSize.MEDIUM -> SPINNER_MEDIUM_SIZE.dp
        SpinnerSize.SMALL -> SPINNER_SMALL_SIZE.dp
    }

    CircularProgressIndicator(
        color = spinnerColor,
        strokeWidth = 2.dp,
        modifier = modifier
            .size(sizeDp)
    )
}

enum class SpinnerSize {
    BIG,
    MEDIUM,
    SMALL
}

private const val SPINNER_BIG_SIZE = 48
private const val SPINNER_MEDIUM_SIZE = 24
private const val SPINNER_SMALL_SIZE = 16