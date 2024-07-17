package com.admiral.demo.compose.home.notifications

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.admiral.demo.compose.home.informers.NotificationsScreen
import com.admiral.demo.compose.home.informers.StaticNotificationScreen

internal const val NOTIFICATIONS_SCREEN = "notificationsScreen"
internal const val TOAST_NOTIFICATIONS_SCREEN = "toastNotificationsScreen"
internal const val STATIC_NOTIFICATIONS_SCREEN = "staticNotificationsScreen"
internal const val ACTION_NOTIFICATIONS_SCREEN = "actionNotificationsScreen"

internal fun NavController.navigateToNotificationsScreen() {
    navigate(NOTIFICATIONS_SCREEN)
}

internal fun NavController.navigateToStaticNotificationsScreen() {
    navigate(STATIC_NOTIFICATIONS_SCREEN)
}

internal fun NavController.navigateToToastNotificationsScreen() {
    navigate(TOAST_NOTIFICATIONS_SCREEN)
}

internal fun NavController.navigateToActionNotificationsScreen() {
    navigate(ACTION_NOTIFICATIONS_SCREEN)
}

internal fun NavGraphBuilder.notificationsScreen(
    onBackClick: () -> Unit,
    onToastClick: () -> Unit,
    onStaticClick: () -> Unit,
    onActionClick: () -> Unit,
) {
    composable(route = NOTIFICATIONS_SCREEN) {
        NotificationsScreen(
            onBackClick = onBackClick,
            onToastClick = onToastClick,
            onStaticClick = onStaticClick,
            onActionClick = onActionClick,
        )
    }
}

internal fun NavGraphBuilder.toastNotificationsScreen(onBackClick: () -> Unit) {
    composable(route = TOAST_NOTIFICATIONS_SCREEN) {
        ToastNotificationsScreen(onBackClick = onBackClick)
    }
}

internal fun NavGraphBuilder.staticNotificationsScreen(onBackClick: () -> Unit) {
    composable(route = STATIC_NOTIFICATIONS_SCREEN) {
        StaticNotificationScreen(onBackClick = onBackClick)
    }
}

internal fun NavGraphBuilder.actionNotificationsScreen(onBackClick: () -> Unit) {
    composable(route = ACTION_NOTIFICATIONS_SCREEN) {
        ActionNotificationsScreen(onBackClick = onBackClick)
    }
}