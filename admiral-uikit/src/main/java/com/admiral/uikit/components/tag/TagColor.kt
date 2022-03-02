package com.admiral.uikit.components.tag

/**
 * Define color for [Tag]
 */
sealed class TagColor {
    object Blue : TagColor()
    object Gray : TagColor()
    object Green : TagColor()
    object Orange : TagColor()
    object Red : TagColor()

    companion object {
        private const val BLUE = 0
        private const val GRAY = 1
        private const val GREEN = 2
        private const val ORANGE = 3
        private const val RED = 4

        fun fromIndex(index: Int): TagColor {

            return when (index) {
                BLUE -> Blue
                GRAY -> Gray
                GREEN -> Green
                ORANGE -> Orange
                RED -> Red
                else -> Blue
            }
        }
    }
}