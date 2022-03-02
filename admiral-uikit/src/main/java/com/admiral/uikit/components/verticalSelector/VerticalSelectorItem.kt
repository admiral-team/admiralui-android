package com.admiral.uikit.components.verticalSelector

internal sealed class VerticalSelectorItem(val viewType: Int) {
    data class Fake(val heightInPixels: Int): VerticalSelectorItem(FAKE_VIEW_TYPE)

    data class Selectable(
        val id: String,
        val title: String,
        val payload: Any? = null
    ) : VerticalSelectorItem(SELECTABLE_VIEW_TYPE)

    companion object {
        const val FAKE_VIEW_TYPE = 1
        const val SELECTABLE_VIEW_TYPE = 2
    }
}

