package com.admiral.uikit.components.appbar

enum class AppbarType {
    NORMAL,
    SEARCH;

    companion object {
        fun from(index: Int) = values()[index]
    }
}