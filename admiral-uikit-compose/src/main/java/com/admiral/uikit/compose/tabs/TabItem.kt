package com.admiral.uikit.compose.tabs

import androidx.compose.runtime.Immutable

@Immutable
data class TabItem(
    val text: String,
    val isSelected: Boolean = false,
)