package com.admiral.demo.compose.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.admiral.demo.compose.home.button.buttonsScreen
import com.admiral.demo.compose.home.button.ghostButtonsScreen
import com.admiral.demo.compose.home.button.navigateToButtonsScreen
import com.admiral.demo.compose.home.button.navigateToGhostButtonsScreen
import com.admiral.demo.compose.home.button.navigateToPrimaryButtonsScreen
import com.admiral.demo.compose.home.button.navigateToSecondaryButtonsScreen
import com.admiral.demo.compose.home.button.primaryButtonsScreen
import com.admiral.demo.compose.home.button.secondaryButtonsScreen
import com.admiral.demo.compose.main.MAIN_ROUTE
import com.admiral.demo.compose.main.mainScreen
import com.admiral.demo.compose.tabs.navigateToTabs
import com.admiral.demo.compose.tabs.tabsScreen

internal const val HOME_GRAPH_ROUTE = "home_graph"

fun NavGraphBuilder.homeGraph(
    navController: NavController,
) {
    navigation(
        startDestination = MAIN_ROUTE,
        route = HOME_GRAPH_ROUTE,
    ) {
        mainScreen(
            onTabsClick = { navController.navigateToTabs() },
            onButtonsClick = { navController.navigateToButtonsScreen() }
        )
        buttonsScreen(
            onPrimaryClick = { navController.navigateToPrimaryButtonsScreen() },
            onSecondaryClick = { navController.navigateToSecondaryButtonsScreen() },
            onGhostClick = { navController.navigateToGhostButtonsScreen() },
            onRulesClick = { },
            onOtherClick = { },
        )
        primaryButtonsScreen()
        secondaryButtonsScreen()
        ghostButtonsScreen()
        tabsScreen()
    }
}
