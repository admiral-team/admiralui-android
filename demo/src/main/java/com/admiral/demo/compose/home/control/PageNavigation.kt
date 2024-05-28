package com.admiral.demo.compose.home.control

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val PAGE_CONTROL_SCREEN_ROUTE = "pageControlScreen"
internal const val PAGE_CONTROL_LINER_SCREEN_ROUTE = "pageControlLinerScreen"
internal const val PAGE_CONTROL_CIRCLE_SCREEN_ROUTE = "pageControlCircleScreen"

internal fun NavController.navigateToPageControlScreen() {
    navigate(PAGE_CONTROL_SCREEN_ROUTE)
}

internal fun NavController.navigateToPageControlLinerScreen() {
    navigate(PAGE_CONTROL_LINER_SCREEN_ROUTE)
}

internal fun NavController.navigateToPageControlCircleScreen() {
    navigate(PAGE_CONTROL_CIRCLE_SCREEN_ROUTE)
}

internal fun NavGraphBuilder.pageControlScreen(
    onBackClick: () -> Unit = {},
    onLinerClick: () -> Unit = {},
    onCircleClick: () -> Unit = {},
) {
    composable(route = PAGE_CONTROL_SCREEN_ROUTE) {
        PageControlScreen(
            onBackClick = onBackClick,
            onLinerClick = onLinerClick,
            onCircleClick = onCircleClick,
        )
    }
}

internal fun NavGraphBuilder.pageControlLinerScreen(
    onBackClick: () -> Unit = {},
) {
    composable(route = PAGE_CONTROL_LINER_SCREEN_ROUTE) {
        PageControlLinerScreen(
            onBackClick = onBackClick,
        )
    }
}

internal fun NavGraphBuilder.pageControlCircleScreen(onBackClick: () -> Unit) {
    composable(route = PAGE_CONTROL_CIRCLE_SCREEN_ROUTE) {
        PageControlCircleScreen(
            onBackClick = onBackClick
        )
    }
}