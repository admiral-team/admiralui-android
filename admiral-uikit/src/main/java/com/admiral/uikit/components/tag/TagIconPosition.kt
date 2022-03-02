package com.admiral.uikit.components.tag

import android.util.LayoutDirection

sealed class TagIconPosition(val direction: Int) {

    object Left : TagIconPosition(LayoutDirection.LTR)
    object Right : TagIconPosition(LayoutDirection.RTL)

    companion object {

        fun from(direction: Int): TagIconPosition {
            return when (direction) {
                LayoutDirection.LTR -> Left
                LayoutDirection.RTL -> Right
                else -> Left
            }
        }
    }
}