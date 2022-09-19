package com.admiral.uikit.components.input

enum class InputType {
    OVAL,
    RECTANGLE,
    TEXT_FIELD;

    companion object {
        fun from(index: Int) = values()[index]
    }
}