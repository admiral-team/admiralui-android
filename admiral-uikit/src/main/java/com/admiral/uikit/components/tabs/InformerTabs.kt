package com.admiral.uikit.components.tabs

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import com.admiral.uikit.R
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.view.checkable.CheckableGroup
import com.admiral.uikit.core.R as core

class InformerTabs @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CheckableGroup(context, attrs, defStyleAttr) {

    init {
        updatePadding(
            left = context.pixels(core.dimen.module_x4),
            top = context.pixels(core.dimen.module_x2),
            right = context.pixels(core.dimen.module_x4),
            bottom = context.pixels(core.dimen.module_x2)
        )

        orientation = HORIZONTAL
        showDividers = SHOW_DIVIDER_MIDDLE
        dividerDrawable =
            ContextCompat.getDrawable(context, R.drawable.admiral_devider_space_horizontal_8dp)
    }

    override fun addView(child: View) {
        super.addView(child)
        onFinishInflate()
    }

    override fun onFinishInflate() {
        children.forEach {
            it.updateLayoutParams<LayoutParams> {
                weight = 1.0f
            }
        }
        super.onFinishInflate()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        children.forEach {
            it.isEnabled = enabled
        }
    }
}