package com.admiral.uikit.compose.toolbar

import com.admiral.themes.compose.ThemeManagerCompose

data class ToolbarItem(
    val icon: Int,
    val title: String,
    val selectedContentColor: Int = ThemeManagerCompose.theme.value.palette.elementAccent,
    val unselectedContentColor: Int = ThemeManagerCompose.theme.value.palette.elementStaticWhite
)