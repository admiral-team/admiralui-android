package com.admiral.uikit.components.cell

enum class BaseCellRadius {
    NONE,
    RADIUS_4,
    RADIUS_8,
    RADIUS_10,
    RADIUS_12,
    RADIUS_16;

    companion object {
        fun from(index: Int) = values()[index]
    }
}