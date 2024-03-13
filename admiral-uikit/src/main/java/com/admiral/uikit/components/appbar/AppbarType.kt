package com.admiral.uikit.components.appbar

@Deprecated("Use com.admiral.uikit.core.components.appbar.AppbarType instead")
enum class AppbarType {
    NORMAL,
    SEARCH;

    companion object {
        fun from(index: Int) = values()[index]
    }
}