package com.admiral.uikit.components.input

enum class IconBackgroundType {
    OVAL,
    RECTANGLE;

    companion object {
        fun from(index: Int) = values()[index]
    }
}