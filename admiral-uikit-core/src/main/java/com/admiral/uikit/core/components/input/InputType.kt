package com.admiral.uikit.core.components.input

enum class InputType {
    OVAL,
    RECTANGLE,
    TEXT_FIELD;

    companion object {
        fun from(index: Int) = values()[index]
    }
}