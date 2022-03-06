package com.admiral.demo.features.home.gun.presentation.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GunSpacingDecorator(
    private val spacingInPx: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.apply {
            bottom = spacingInPx
        }
    }
}