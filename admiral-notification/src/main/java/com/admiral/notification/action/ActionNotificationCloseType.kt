package com.admiral.notification.action

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import com.admiral.themes.ThemeManager

/**
 * [ActionNotification] close type
 */
sealed class ActionNotificationCloseType {
    /**
     * Icon type
     */
    data class Icon(
        val isVisible: Boolean = true,
        val icon: Drawable? = null,
        @ColorInt val color: Int = ThemeManager.theme.palette.elementAccent
    ) : ActionNotificationCloseType()

    /**
     * Text type
     */
    data class Text(
        val isVisible: Boolean = true,
        val text: String = "Отмена",
        @ColorInt val color: Int = ThemeManager.theme.palette.elementAccent
    ) : ActionNotificationCloseType()
}