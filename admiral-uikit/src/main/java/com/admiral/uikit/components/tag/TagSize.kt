package com.admiral.uikit.components.tag

/**
 * Define size for [Tag]
 */
sealed class TagSize(val index: Int) {
    object Large : TagSize(LARGE_INDEX)
    object Medium : TagSize(MEDIUM_INDEX)
    object Small : TagSize(SMALL_INDEX)

    companion object {
        private const val LARGE_INDEX = 0
        private const val MEDIUM_INDEX = 1
        private const val SMALL_INDEX = 2

        fun fromIndex(index: Int): TagSize = when (index) {
            LARGE_INDEX -> Large
            MEDIUM_INDEX -> Medium
            SMALL_INDEX -> Small
            else -> throw IllegalStateException("Unknown index: $index")
        }
    }
}