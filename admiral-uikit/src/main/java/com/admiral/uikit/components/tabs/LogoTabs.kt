package com.admiral.uikit.components.tabs

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.children
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.view.checkable.CheckableGroup
import com.admiral.resources.R as res

class LogoTabs @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CheckableGroup(context, attrs, defStyleAttr), ThemeObserver {

    init {
        updatePadding(
            left = context.pixels(res.dimen.module_x4),
            top = context.pixels(res.dimen.module_x2),
            right = context.pixels(res.dimen.module_x4),
            bottom = context.pixels(res.dimen.module_x2)
        )

        orientation = HORIZONTAL

        onThemeChanged(ThemeManager.theme)
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

    /**
     * Subscribe for theme change.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.subscribe(this)
    }

    /**
     * Unsubscribe for theme change.
     */
    override fun onDetachedFromWindow() {
        ThemeManager.unsubscribe(this)
        super.onDetachedFromWindow()
    }

    override fun onThemeChanged(theme: Theme) {
        background =
            drawable(R.drawable.admiral_bg_checkable_group)?.colored(theme.palette.elementAdditional)
    }

    override fun setCheckedId(id: Int, isChecked: Boolean) {
        super.setCheckedId(id, isChecked)

        children.forEachIndexed { index, view ->
            // add divider to the every element
            (view as? LogoTab)?.isRightDividerVisible = true

            // remove divider at the current view
            if (view.id == id) {
                (view as? LogoTab)?.isRightDividerVisible = false
            }
            // remove divider at the previous view
            if (index != childCount - 1 && children.toList()[index + 1].id == id) {
                (children.toList()[index] as? LogoTab)?.isRightDividerVisible = false
            }

            // remove divider at the last view
            if (index == childCount - 1) {
                (children.toList()[index] as? LogoTab)?.isRightDividerVisible = false
            }
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        children.forEach { it.isEnabled = enabled }
    }
}