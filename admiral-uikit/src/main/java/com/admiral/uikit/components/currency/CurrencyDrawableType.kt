package com.admiral.uikit.components.currency

enum class CurrencyDrawableType {
    NONE,
    UP,
    DOWN;

    companion object {
        fun from(index: Int) = values()[index]
    }
}