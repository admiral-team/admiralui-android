package com.admiral.demo.compose.home.informers

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val INFORMERS_AND_NOTIFICATIONS_SCREEN = "informersAndNotificationsScreen"
internal const val NOTIFICATIONS_SCREEN = "notificationsScreen"
internal const val INFORMERS_SCREEN = "informersScreen"
internal const val BIG_INFORMERS_SCREEN = "bigInformersScreen"
internal const val SMALL_INFORMERS_SCREEN = "smallInformersScreen"

internal fun NavController.navigateToInformersAndNotificationsScreen() {
    navigate(INFORMERS_AND_NOTIFICATIONS_SCREEN)
}

internal fun NavController.navigateToNotificationsScreen() {
    navigate(NOTIFICATIONS_SCREEN)
}

internal fun NavController.navigateToInformersScreen() {
    navigate(INFORMERS_SCREEN)
}

internal fun NavController.navigateToBigInformersScreen() {
    navigate(BIG_INFORMERS_SCREEN)
}

internal fun NavController.navigateToSmallInformersScreen() {
    navigate(SMALL_INFORMERS_SCREEN)
}

internal fun NavGraphBuilder.informersAndNotificationsScreen(
    onBackClick: () -> Unit = {},
    onInformersClick: () -> Unit = {},
    onNotificationsClick: () -> Unit = {},
) {
    composable(route = INFORMERS_AND_NOTIFICATIONS_SCREEN) {
        InformersAndNotificationsScreen(
            onBackClick = onBackClick,
            onInformersClick = onInformersClick,
            onNotificationsClick = onNotificationsClick,
        )
    }
}

internal fun NavGraphBuilder.informersScreen(
    onBackClick: () -> Unit = {},
    onInformersBigClick: () -> Unit = {},
    onInformersSmallClick: () -> Unit = {},
) {
    composable(route = INFORMERS_SCREEN) {
        InformersScreen(
            onBackClick = onBackClick,
            onInformersBigClick = onInformersBigClick,
            onInformersSmallClick = onInformersSmallClick
        )
    }
}

internal fun NavGraphBuilder.notificationsScreen(onBackClick: () -> Unit) {
    composable(route = NOTIFICATIONS_SCREEN) {

    }
}

internal fun NavGraphBuilder.bigInformersScreen(onBackClick: () -> Unit) {
    composable(route = BIG_INFORMERS_SCREEN) {
        BigInformersScreen(onBackClick = onBackClick)
    }
}

internal fun NavGraphBuilder.smallInformersScreen(onBackClick: () -> Unit) {
    composable(route = SMALL_INFORMERS_SCREEN) {

    }
}