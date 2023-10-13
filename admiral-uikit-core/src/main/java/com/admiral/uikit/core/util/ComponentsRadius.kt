package com.admiral.uikit.core.util

enum class ComponentsRadius {
    NONE,
    RADIUS_4,
    RADIUS_8,
    RADIUS_12,
    RADIUS_16,
    RADIUS_20;

    companion object {
        fun from(index: Int) = values()[index]
    }
}