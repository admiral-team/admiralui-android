package com.admiral.uikit.components.calendar.month.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

internal class DaysSpacingDecoration(
    private val span: Int,
    private val horizontalSpacingInPx: Int,
    private val verticalSpacingInPx: Int
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
            left = column * verticalSpacingInPx / span
            right = verticalSpacingInPx - (column + 1) * verticalSpacingInPx / span
            top = if (position >= span) horizontalSpacingInPx else top
        }
    }
}