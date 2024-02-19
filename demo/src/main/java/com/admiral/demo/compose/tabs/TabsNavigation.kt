package com.admiral.demo.compose.tabs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val TABS_ROUTE = "tabs"

internal fun NavController.navigateToTabs() {
    navigate(TABS_ROUTE)
}

internal fun NavGraphBuilder.tabsScreen() {
    composable(route = TABS_ROUTE) {
        TabsScreen()
    }
}
