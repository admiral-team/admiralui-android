package com.admiral.uikit.core.components.appbar

enum class AppbarType {
    NORMAL,
    SEARCH;

    companion object {
        fun from(index: Int) = values()[index]
    }
}