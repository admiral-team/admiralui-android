package com.admiral.uikit.components.badge

enum class BadgeSize(val size: Int) {
    NORMAL(BadgeSize.SIZE_NORMAL),
    SMALL(BadgeSize.SIZE_SMALL);

    companion object {
        private const val SIZE_NORMAL = 20
        private const val SIZE_SMALL = 12

        fun from(index: Int) = values()[index]
    }
}