package com.admiral.demo.compose.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val MAIN_ROUTE = "main"

@Suppress("LongParameterList")
internal fun NavGraphBuilder.mainScreen(
    onTabsClick: () -> Unit,
    onButtonsClick: () -> Unit,
    onTextFieldsClick: () -> Unit,
    onCellsClick: () -> Unit,
    onCheckBoxClick: () -> Unit,
    onBadgesClick: () -> Unit,
    onLinkClick: () -> Unit,
    onPageControlClick: () -> Unit,
    onAlertsClick: () -> Unit,
) {
    composable(route = MAIN_ROUTE) {
        MainScreen(
            onTabsClick = onTabsClick,
            onButtonsClick = onButtonsClick,
            onTextFieldsClick = onTextFieldsClick,
            onCellsClick = onCellsClick,
            onCheckBoxClick = onCheckBoxClick,
            onBadgesClick = onBadgesClick,
            onLinkClick = onLinkClick,
            onPageControlClick = onPageControlClick,
            onAlertsClick = onAlertsClick
        )
    }
}
