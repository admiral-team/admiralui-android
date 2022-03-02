package com.admiral.themes

fun lightTheme() = Theme(
    name = THEME_LIGHT,
    palette = lightPalette(),
    typography = Typography(),
    false
)

fun darkTheme() = Theme(
    name = THEME_DARK,
    palette = darkPalette(),
    typography = Typography(),
    false
)

fun darkSMETheme() = Theme(
    name = THEME_DARK_SME,
    palette = sMEDarkPalette(),
    typography = Typography(),
    false
)

fun lightSMETheme() = Theme(
    name = THEME_LIGHT_SME,
    palette = sMELightPalette(),
    typography = Typography(),
    false
)