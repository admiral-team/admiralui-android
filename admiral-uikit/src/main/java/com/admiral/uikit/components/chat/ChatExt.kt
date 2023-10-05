package com.admiral.uikit.components.chat

import androidx.core.view.isVisible
import com.admiral.themes.ThemeManager
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.imageview.ImageView
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.drawable
import com.admiral.resources.R as res

@Suppress("MagicNumber")
internal fun ImageView.setMessageStatusIcon(
    status: MessageStatus,
    isOutgoing: Boolean
) {
    val color = if (isOutgoing) {
        when (status) {
            MessageStatus.ERROR -> {
                ThemeManager.theme.palette.elementErrorDefault
            }

            MessageStatus.SENDING -> {
                ThemeManager.theme.palette.elementStaticWhite.withAlpha(0.5f)
            }

            else -> {
                ThemeManager.theme.palette.elementStaticWhite
            }
        }
    } else {
        when (status) {
            MessageStatus.ERROR -> {
                ThemeManager.theme.palette.elementErrorDefault
            }

            MessageStatus.SENDING -> {
                ThemeManager.theme.palette.textSecondary.withAlpha(0.5f)
            }

            else -> {
                ThemeManager.theme.palette.elementPrimary
            }
        }
    }

    val drawable = when (status) {
        MessageStatus.NONE -> null
        MessageStatus.LOAD -> drawable(res.drawable.admiral_ic_time_outline)
        MessageStatus.SENDING -> drawable(res.drawable.admiral_ic_status_one_outline)
        MessageStatus.SEND -> drawable(res.drawable.admiral_ic_status_one_outline)
        MessageStatus.READ -> drawable(res.drawable.admiral_ic_status_two_outline)
        MessageStatus.ERROR -> drawable(res.drawable.admiral_ic_error_outline)
        MessageStatus.LOADING -> drawable(res.drawable.admiral_ic_time_outline)
        MessageStatus.DONE -> drawable(res.drawable.admiral_ic_status_one_outline)
    }

    if (drawable == null) {
        this.isVisible = false
    } else {
        this.isVisible = true
        this.setImageDrawable(drawable)
    }

    this.imageTintColorState = ColorState(color)
}

internal fun TextView.setTimeTextColors(isOutgoing: Boolean) {
    val colorState = if (isOutgoing) {
        ColorState(
            normalEnabled = ThemeManager.theme.palette.textAccentAdditional,
            normalDisabled = ThemeManager.theme.palette.textAccentAdditional.withAlpha(),
            pressed = ThemeManager.theme.palette.textAccentAdditional
        )
    } else {
        ColorState(
            normalEnabled = ThemeManager.theme.palette.textSecondary,
            normalDisabled = ThemeManager.theme.palette.textSecondary.withAlpha(),
            pressed = ThemeManager.theme.palette.textSecondary
        )
    }

    this.textColor = colorState
}