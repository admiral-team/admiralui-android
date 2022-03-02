package com.admiral.uikit.components.chat

/**
 * Gravity enum for [TextMessage]
 */
enum class TextMessageGravity {

    /**
     * Push object to the start of its container, not changing its size.
     */
    START,

    /**
     * Place the object in the center of its container, not changing its size.
     */
    CENTER,

    /**
     * Push object to the end of its container, not changing its size.
     */
    END;

    companion object {
        fun from(index: Int) = values()[index]
    }
}