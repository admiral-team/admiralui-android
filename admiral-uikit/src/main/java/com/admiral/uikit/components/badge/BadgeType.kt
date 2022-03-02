package com.admiral.uikit.components.badge

enum class BadgeType {
    NORMAL,
    ERROR,
    ATTENTION,
    SUCCESS,
    NEUTRAL,
    ADDITIONAL;

    companion object {
        fun from(index: Int) = values()[index]
    }
}