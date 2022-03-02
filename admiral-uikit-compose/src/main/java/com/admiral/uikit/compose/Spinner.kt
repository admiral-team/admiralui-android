package com.admiral.uikit.compose

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState

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
    spinnerColorState: ColorState? = null,
) {
    val theme = ThemeManagerCompose.theme.value

    val spinnerColor =
        if (isEnabled) spinnerColorState?.normalEnabled ?: theme.palette.backgroundAccent
        else spinnerColorState?.normalDisabled ?: theme.palette.backgroundAccent.withAlpha()

    val sizeDp = when (size) {
        SpinnerSize.BIG -> SPINNER_BIG_SIZE.dp
        SpinnerSize.MEDIUM -> SPINNER_MEDIUM_SIZE.dp
        SpinnerSize.SMALL -> SPINNER_SMALL_SIZE.dp
    }

    CircularProgressIndicator(
        color = Color(spinnerColor),
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