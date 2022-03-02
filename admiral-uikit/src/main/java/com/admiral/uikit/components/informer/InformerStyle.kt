package com.admiral.uikit.components.informer

/**
 * Define styles for [InformerBig] and [InformerSmall]
 */
sealed class InformerStyle {
    object Info : InformerStyle()
    object Attention : InformerStyle()
    object Success : InformerStyle()
    object Error : InformerStyle()

    companion object {
        private const val INFO = 0
        private const val ATTENTION = 1
        private const val SUCCESS = 2
        private const val ERROR = 3

        fun fromIndex(index: Int): InformerStyle {

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