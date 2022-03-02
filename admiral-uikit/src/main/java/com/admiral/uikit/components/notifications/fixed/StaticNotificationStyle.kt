package com.admiral.uikit.components.notifications.fixed

/**
 * Define styles for [InformerBig] and [InformerSmall]
 */
sealed class StaticNotificationStyle {
    object Info : StaticNotificationStyle()
    object Attention : StaticNotificationStyle()
    object Success : StaticNotificationStyle()
    object Error : StaticNotificationStyle()

    companion object {
        private const val INFO = 0
        private const val ATTENTION = 1
        private const val SUCCESS = 2
        private const val ERROR = 3

        fun fromIndex(index: Int): StaticNotificationStyle {

            return when (index) {
                INFO -> Info
                ATTENTION -> Attention
                SUCCESS -> Success
                ERROR -> Error
                else -> Info
            }
        }
    }
}