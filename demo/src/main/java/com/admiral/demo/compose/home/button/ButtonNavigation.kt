package com.admiral.demo.compose.home.button

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val BUTTONS_SCREEN_ROUTE = "buttonsScreen"
internal const val PRIMARY_BUTTONS_SCREEN_ROUTE = "primaryButtonsScreen"
internal const val SECONDARY_BUTTONS_SCREEN_ROUTE = "secondaryButtonsScreen"
internal const val GHOST_BUTTONS_SCREEN_ROUTE = "ghostButtonsScreen"
internal const val RULE_BUTTONS_SCREEN_ROUTE = "ruleButtonsScreen"

internal fun NavController.navigateToButtonsScreen() {
    navigate(BUTTONS_SCREEN_ROUTE)
}

internal fun NavController.navigateToPrimaryButtonsScreen() {
    navigate(PRIMARY_BUTTONS_SCREEN_ROUTE)
}

internal fun NavController.navigateToSecondaryButtonsScreen() {
    navigate(SECONDARY_BUTTONS_SCREEN_ROUTE)
}

internal fun NavController.navigateToGhostButtonsScreen() {
    navigate(GHOST_BUTTONS_SCREEN_ROUTE)
}

internal fun NavController.navigateToRuleButtonsScreen() {
    navigate(RULE_BUTTONS_SCREEN_ROUTE)
}

internal fun NavGraphBuilder.buttonsScreen(
    onPrimaryClick: () -> Unit = {},
    onSecondaryClick: () -> Unit = {},
    onGhostClick: () -> Unit = {},
    onRulesClick: () -> Unit = {},
    onOtherClick: () -> Unit = {},
) {
    composable(route = BUTTONS_SCREEN_ROUTE) {
        ButtonsScreen(
            onPrimaryClick = onPrimaryClick,
            onSecondaryClick = onSecondaryClick,
            onGhostClick = onGhostClick,
            onRulesClick = onRulesClick,
            onOtherClick = onOtherClick,
        )
    }
}

internal fun NavGraphBuilder.primaryButtonsScreen() {
    composable(route = PRIMARY_BUTTONS_SCREEN_ROUTE) {
        PrimaryButtonsScreen()
    }
}

internal fun NavGraphBuilder.secondaryButtonsScreen() {
    composable(route = SECONDARY_BUTTONS_SCREEN_ROUTE) {
        SecondaryButtonsScreen()
    }
}

internal fun NavGraphBuilder.ghostButtonsScreen() {
    composable(route = GHOST_BUTTONS_SCREEN_ROUTE) {
        GhostButtonsScreen()
    }
}

internal fun NavGraphBuilder.ruleButtonsScreen(onBackClicked: () -> Unit) {
    composable(route = RULE_BUTTONS_SCREEN_ROUTE) {
        RuleButtonsScreen(onBackClicked = onBackClicked)
    }
}