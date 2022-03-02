package com.admiral.uikit.components.calendar.vertical

import com.admiral.uikit.components.calendar.common.CalendarVm

internal class VerticalCalendarVm : CalendarVm() {
    override val initialGenerationItemCount: Int = INITIAL_GENERATION_ITEM_COUNT
    override val additionalItemCount: Int = ADDITIONAL_ITEM_COUNT

    private companion object {
        const val INITIAL_GENERATION_ITEM_COUNT = 24
        const val ADDITIONAL_ITEM_COUNT = 5
    }
}