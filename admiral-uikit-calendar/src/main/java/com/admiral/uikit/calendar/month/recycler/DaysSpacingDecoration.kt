package com.admiral.uikit.calendar.month.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

internal class DaysSpacingDecoration(
    private val span: Int,
    private val dayVerticalSpacingPx: Int,
    private val dayHorizontalSpacingPx: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        val column = position % span

        outRect.apply {
            left = column * dayHorizontalSpacingPx / span
            right = dayHorizontalSpacingPx - (column + 1) * dayHorizontalSpacingPx / span
            top = if (position >= span) dayVerticalSpacingPx else top
        }
    }
}