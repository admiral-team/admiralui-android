package com.admiral.uikit.components.actionbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.annotation.DrawableRes
import androidx.core.view.children
import com.admiral.themes.ColorPaletteEnum
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.layout.LinearLayout

class ActionBarSecondary @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        children.forEach {
            it.isEnabled = enabled
        }
    }

    /**
     * Remove all actions
     */
    fun clearActions() {
        removeAllViews()
    }

    /**
     * Add new action
     * @param icon icon resource for the action
     * @param description description for the action
     * @param backgroundColorNormalEnabledPalette palette for normal state
     * @param backgroundColorPressedPalette palette for pressed state
     * @param action action callback
     */
    @Suppress("LongParameterList")
    fun addAction(
        @DrawableRes icon: Int?,
        description: String?,
        backgroundColorNormalEnabledPalette: ColorPaletteEnum,
        backgroundColorPressedPalette: ColorPaletteEnum,
        descriptionColorNormalEnabledPalette: ColorPaletteEnum,
        descriptionColorPressedPalette: ColorPaletteEnum,
        action: ((view: View) -> Unit)
    ) {
        val actionBarItem = createActionBarItem(
            icon = icon,
            description = description,
            backgroundColorNormalEnabledPalette = backgroundColorNormalEnabledPalette,
            backgroundColorPressedPalette = backgroundColorPressedPalette,
            descriptionColorNormalEnabledPalette = descriptionColorNormalEnabledPalette,
            descriptionColorPressedPalette = descriptionColorPressedPalette,
            action = action
        )

        addView(actionBarItem, ViewGroup.LayoutParams(WRAP_CONTENT, MATCH_PARENT))
    }

    @Suppress("LongParameterList")
    private fun createActionBarItem(
        @DrawableRes icon: Int?,
        description: String?,
        backgroundColorNormalEnabledPalette: ColorPaletteEnum,
        backgroundColorPressedPalette: ColorPaletteEnum,
        descriptionColorNormalEnabledPalette: ColorPaletteEnum,
        descriptionColorPressedPalette: ColorPaletteEnum,
        action: ((view: View) -> Unit)
    ): ActionBarSecondaryItem = ActionBarSecondaryItem(context).apply {
        this.backgroundColorNormalEnabledPalette = backgroundColorNormalEnabledPalette
        this.backgroundColorPressedPalette = backgroundColorPressedPalette
        this.descriptionColorNormalEnabledPalette = descriptionColorNormalEnabledPalette
        this.descriptionColorPressedPalette = descriptionColorPressedPalette
        this.icon = icon?.let { drawable(icon) }
        this.description = description
        setOnClickListener { action.invoke(it) }
    }
}