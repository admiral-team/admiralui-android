package com.admiral.uikit.components.chat.textOperation

/**
 * TextOperationType enum for [TextOperation]
 */
enum class TextOperationType {

    /**
     * Determines default view type.
     */
    DEFAULT,

    /**
     * Determines success view type.
     */
    SUCCESS,

    /**
     * Determines error view type.
     */
    ERROR;

    companion object {
        fun from(index: Int) = values()[index]
    }
}