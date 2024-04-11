package com.admiral.uikit.compose.toolbar

import androidx.compose.ui.graphics.Color

data class ToolbarItem(
    val icon: Int,
    val title: String,
    val selectedContentColor: Color? = null,
    val unselectedContentColor: Color? = null
)