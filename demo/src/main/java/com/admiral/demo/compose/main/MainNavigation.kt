package com.admiral.demo.compose.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val MAIN_ROUTE = "main"

internal fun NavGraphBuilder.mainScreen(
    onTabsClick: () -> Unit,
) {
    composable(route = MAIN_ROUTE) {
        MainScreen(
            onTabsClick = onTabsClick,
        )
    }
}