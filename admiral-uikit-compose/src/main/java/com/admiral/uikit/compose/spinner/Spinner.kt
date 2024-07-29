package com.admiral.uikit.compose.spinner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.util.DIMEN_X12
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X6
import com.admiral.uikit.core.components.spinner.SpinnerSize

@Composable
fun Spinner(
    modifier: Modifier = Modifier,
    color: SpinnerColor = AdmiralSpinnerColor.default(),
    size: SpinnerSize = SpinnerSize.MEDIUM,
) {
    val sizeDp = when (size) {
        SpinnerSize.SMALL -> DIMEN_X4
        SpinnerSize.MEDIUM -> DIMEN_X6
        SpinnerSize.BIG -> DIMEN_X12
    }
    val strokeWidth = when (size) {
        SpinnerSize.SMALL -> StrokeSmall.dp
        SpinnerSize.MEDIUM -> StrokeMedium.dp
        SpinnerSize.BIG -> StrokeBig.dp
    }

    CircularProgressIndicator(
        modifier = modifier
            .size(sizeDp),
        color = color.spinnerColor,
        strokeWidth = strokeWidth,
    )
}

private const val StrokeSmall = 2
private const val StrokeMedium = 3
private const val StrokeBig = 6

@Preview
@Composable
fun SpinnerPreview() {
    AdmiralTheme {
        Surface(
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            Column {
                Spinner()
                Spinner(size = SpinnerSize.BIG)
                Spinner(size = SpinnerSize.SMALL)
            }
        }
    }
}

@Preview
@Composable
fun SpinnerContrastPreview() {
    AdmiralTheme {
        Surface(
            color = AdmiralTheme.colors.backgroundSecondary
        ) {
            Column {
                Spinner(color = AdmiralSpinnerColor.contrast())
                Spinner(
                    color = AdmiralSpinnerColor.contrast(),
                    size = SpinnerSize.BIG
                )
                Spinner(
                    color = AdmiralSpinnerColor.contrast(),
                    size = SpinnerSize.SMALL
                )
            }
        }
    }
}