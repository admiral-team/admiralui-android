package com.admiral.uikit.core.components.button

/**
 * Define styles for Button
 */
sealed class ButtonStyle {

    /**
     * Button with full color background.
     */
    object Primary : ButtonStyle()

    /**
     * Button with transparent background and stroke.
     */
    object Secondary : ButtonStyle()

    /**
     * Button with transparent background.
     */
    object Ghost : ButtonStyle()
}
