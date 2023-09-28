package com.admiral.notification.action

/**
 * [ActionNotification] close type
 */
enum class ActionNotificationCloseType {
    /**
     * Icon type
     */
    ICON,

    /**
     * Text type
     */
    TEXT
}

// TODO: It will be better to refactor this code.
//  Sealed class can simplify ActionNotification.Builder since we can reduce set functions