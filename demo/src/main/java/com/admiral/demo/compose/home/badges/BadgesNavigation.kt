package com.admiral.demo.compose.home.badges

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val BADGES_SCREEN_ROUTE = "badgesScreen"
internal const val NORMAL_BADGES_SCREEN_ROUTE = "normalBadgesScreen"
internal const val SMALL_BADGES_SCREEN_ROUTE = "smallBadgesScreen"

internal fun NavController.navigateToBadgesScreen() {
    navigate(BADGES_SCREEN_ROUTE)
}

internal fun NavController.navigateToNormalBadgesScreen() {
    navigate(NORMAL_BADGES_SCREEN_ROUTE)
}

internal fun NavController.navigateToSmallBadgesScreen() {
    navigate(SMALL_BADGES_SCREEN_ROUTE)
}

internal fun NavGraphBuilder.badgesScreen(
    onBackClick: () -> Unit = {},
    onNormalClick: () -> Unit = {},
    onSmallClick: () -> Unit = {},
) {
    composable(route = BADGES_SCREEN_ROUTE) {
        BadgesScreen(
            onBackClick = onBackClick,
            onNormalClick = onNormalClick,
            onSmallClick = onSmallClick,
        )
    }
}

internal fun NavGraphBuilder.normalBadgesScreen(
    onBackClick: () -> Unit = {},
) {
    composable(route = NORMAL_BADGES_SCREEN_ROUTE) {
        NormalBadgesScreen(onBackClick = onBackClick)
    }
}

internal fun NavGraphBuilder.smallBadgesScreen(
    onBackClick: () -> Unit = {},
) {
    composable(route = SMALL_BADGES_SCREEN_ROUTE) {
        SmallBadgesScreen(onBackClick = onBackClick)
    }
}