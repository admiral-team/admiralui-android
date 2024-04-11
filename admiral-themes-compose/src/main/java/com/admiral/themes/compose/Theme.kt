package com.admiral.themes.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun AdmiralTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val palette = if (darkTheme) admiralDarkColorPalette else admiralLightColorPalette
    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.Transparent,
        backgroundColor = Color.Transparent
    )
    ProvideAdmiralResources(colors = palette, typography = defaultAdmiralTypography()) {
        MaterialTheme(
            colors = debugColors(darkTheme),
            typography = debugTypography,
            shapes = MaterialTheme.shapes,
        ) {
            CompositionLocalProvider(
                LocalTextSelectionColors provides customTextSelectionColors,
                content = content,
            )
        }
    }
}

@Composable
fun ProvideAdmiralResources(
    colors: AdmiralColors,
    typography: AdmiralTypography,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        colors.copy()
    }.apply { updateColorsFrom(colors) }

    CompositionLocalProvider(
        LocalAdmiralColors provides colorPalette,
        LocalAdmiralTypographies provides typography,
        content = content
    )
}

object AdmiralTheme {
    val colors: AdmiralColors
        @Composable
        get() = LocalAdmiralColors.current

    val typography: AdmiralTypography
        @Composable
        get() = LocalAdmiralTypographies.current
}