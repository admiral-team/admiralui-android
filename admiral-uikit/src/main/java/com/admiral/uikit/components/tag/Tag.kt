package com.admiral.uikit.components.tag

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.res.use
import com.google.android.material.chip.Chip
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.themes.Typography
import com.admiral.uikit.R
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.ext.applyStyle
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels

class Tag @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.chipStyle
) : Chip(
    ContextThemeWrapper(context, R.style.AdmiralThemeOverlay_Chip),
    attrs,
    defStyleAttr
), ThemeObserver {

    private var chipBackgroundColorEnabled = ThemeManager.theme.palette.backgroundAdditionalOne
    private var chipBackgroundColorDisabled = ThemeManager.theme.palette.backgroundAdditionalOne
    private var chipBackgroundColorPressed = ThemeManager.theme.palette.backgroundAdditionalOne

    private var chipIconColorEnabled = ThemeManager.theme.palette.elementPrimary
    private var chipIconColorDisabled = ThemeManager.theme.palette.elementPrimary.withAlpha()
    private var chipIconColorPressed = ThemeManager.theme.palette.elementPrimary

    /**
     * If true, icon will be colored, otherwise it will be in original colors
     */
    var isIconColored = false
        set(value) {
            field = value
            invalidateIconColor()
        }

    /**
     * Color of the tag
     */
    var tagColor: TagColor = TagColor.Blue
        set(value) {
            field = value
            invalidateColors()
        }

    /**
     * Position for the icon
     */
    var iconPosition: TagIconPosition = TagIconPosition.Left
        set(value) {
            field = value
            layoutDirection = value.direction
        }

    /**
     * Size of the tag
     */
    var tagSize: TagSize = TagSize.Large
        set(value) {
            field = value

            when (value) {
                TagSize.Large -> {
                    val tagHeight = pixels(R.dimen.admiral_tag_height_large)
                    chipMinHeight = tagHeight.toFloat()
                    height = tagHeight
                    chipIconSize = pixels(R.dimen.admiral_tag_icon_size_large).toFloat()
                    applyStyle(Typography.getStyle(ThemeManager.theme.typography.body1))
                }
                TagSize.Medium -> {
                    val tagHeight = pixels(R.dimen.admiral_tag_height_medium)
                    chipMinHeight = tagHeight.toFloat()
                    height = tagHeight
                    chipIconSize = pixels(R.dimen.admiral_tag_icon_size_medium).toFloat()
                    applyStyle(Typography.getStyle(ThemeManager.theme.typography.body1))
                }
                TagSize.Small -> {
                    val tagHeight = pixels(R.dimen.admiral_tag_height_small)
                    chipMinHeight = tagHeight.toFloat()
                    height = tagHeight
                    chipIconSize = pixels(R.dimen.admiral_tag_icon_size_small).toFloat()
                    applyStyle(Typography.getStyle(ThemeManager.theme.typography.subhead2))
                }
            }
        }

    init {
        parseAttrs(attrs, R.styleable.Tag).use {
            text = it.getString(R.styleable.Tag_admiralText)
            chipIcon = it.getDrawable(R.styleable.Tag_chipIcon)
            isIconColored = it.getBoolean(R.styleable.Tag_isIconColored, false)
            iconPosition = TagIconPosition.from(it.getInt(R.styleable.Tag_admiralIconPosition, 0))
            tagColor = TagColor.fromIndex(it.getInt(R.styleable.Tag_admiralTagColor, 0))
            tagSize = TagSize.fromIndex(
                it.getInt(R.styleable.Tag_admiralTagSize, TagSize.Large.index)
            )
        }

        invalidateColors()
        invalidateBackgroundColor()
        invalidateIconColor()
        invalidateTextColor()
        invalidatePadding()

        ellipsize = TextUtils.TruncateAt.END
        maxLines = 1

        // set to null since we don't want to show shadows
        stateListAnimator = null

        isClickable = true
        isFocusable = true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.subscribe(this)
    }

    override fun onDetachedFromWindow() {
        ThemeManager.unsubscribe(this)
        super.onDetachedFromWindow()
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateColors()
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)

        if (!isIconColored) {
            chipIcon?.alpha = if (enabled) {
                ALPHA_ENABLED
            } else {
                ALPHA_DISABLED
            }
        }
    }

    private fun invalidatePadding() {
        setEnsureMinTouchTargetSize(false)

        chipStartPadding = pixels(R.dimen.module_x4).toFloat()
        chipEndPadding = pixels(R.dimen.module_x4).toFloat()

        iconEndPadding = pixels(R.dimen.module_x2).toFloat()
        textEndPadding = 0f
        textStartPadding = 0f
    }

    private fun invalidateColors() {
        when (tagColor) {
            TagColor.Blue -> {
                chipIconColorEnabled = ThemeManager.theme.palette.elementAccent
                chipIconColorDisabled = ThemeManager.theme.palette.elementAccent.withAlpha()
                chipIconColorPressed = ThemeManager.theme.palette.elementAccentPressed

                chipBackgroundColorEnabled = ThemeManager.theme.palette.backgroundSelected
                chipBackgroundColorDisabled = ThemeManager.theme.palette.backgroundSelected
                chipBackgroundColorPressed = ThemeManager.theme.palette.backgroundSelectedPressed
            }
            TagColor.Gray -> {
                chipIconColorEnabled = ThemeManager.theme.palette.elementPrimary
                chipIconColorDisabled = ThemeManager.theme.palette.elementPrimary.withAlpha()
                chipIconColorPressed = ThemeManager.theme.palette.elementPrimary

                chipBackgroundColorEnabled = ThemeManager.theme.palette.backgroundAdditionalOne
                chipBackgroundColorDisabled = ThemeManager.theme.palette.backgroundAdditionalOne
                chipBackgroundColorPressed =
                    ThemeManager.theme.palette.backgroundAdditionalOnePressed
            }
            TagColor.Green -> {
                chipIconColorEnabled = ThemeManager.theme.palette.elementSuccess
                chipIconColorDisabled = ThemeManager.theme.palette.elementSuccess.withAlpha()
                chipIconColorPressed = ThemeManager.theme.palette.elementSuccessPressed

                chipBackgroundColorEnabled = ThemeManager.theme.palette.backgroundSuccess
                chipBackgroundColorDisabled = ThemeManager.theme.palette.backgroundSuccess
                chipBackgroundColorPressed = ThemeManager.theme.palette.backgroundSuccess
            }
            TagColor.Orange -> {
                chipIconColorEnabled = ThemeManager.theme.palette.elementAttention
                chipIconColorDisabled = ThemeManager.theme.palette.elementAttention.withAlpha()
                chipIconColorPressed = ThemeManager.theme.palette.elementAttentionPressed

                chipBackgroundColorEnabled = ThemeManager.theme.palette.backgroundAttention
                chipBackgroundColorDisabled = ThemeManager.theme.palette.backgroundAttention
                chipBackgroundColorPressed = ThemeManager.theme.palette.backgroundAttention
            }
            TagColor.Red -> {
                chipIconColorEnabled = ThemeManager.theme.palette.elementError
                chipIconColorDisabled = ThemeManager.theme.palette.elementError.withAlpha()
                chipIconColorPressed = ThemeManager.theme.palette.elementErrorPressed

                chipBackgroundColorEnabled = ThemeManager.theme.palette.backgroundError
                chipBackgroundColorDisabled = ThemeManager.theme.palette.backgroundError
                chipBackgroundColorPressed = ThemeManager.theme.palette.backgroundError
            }
        }
        invalidateBackgroundColor()
        invalidateIconColor()
        invalidateTextColor()
    }

    private fun invalidateIconColor() {
        chipIconTint = if (isIconColored) {
            colorStateList(
                enabled = chipIconColorEnabled,
                disabled = chipIconColorDisabled,
                pressed = chipIconColorPressed
            )
        } else null
    }

    private fun invalidateBackgroundColor() {
        chipBackgroundColor = colorStateList(
            enabled = chipBackgroundColorEnabled,
            disabled = chipBackgroundColorDisabled,
            pressed = chipBackgroundColorPressed
        )
    }

    private fun invalidateTextColor() {
        setTextColor(
            colorStateList(
                enabled = ThemeManager.theme.palette.textPrimary,
                disabled = ThemeManager.theme.palette.textPrimary.withAlpha(),
                pressed = ThemeManager.theme.palette.textPrimary,
            )
        )
    }

    private companion object {
        const val ALPHA_ENABLED = 255
        const val ALPHA_DISABLED = 130
    }
}