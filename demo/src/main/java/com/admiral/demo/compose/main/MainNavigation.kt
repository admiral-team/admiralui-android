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
    onSwitcherClick: () -> Unit,
    onBadgesClick: () -> Unit,
    onRadioButtonClick: () -> Unit,
    onLinkClick: () -> Unit,
    onPageControlClick: () -> Unit,
    onInformersAndNotificationsClick: () -> Unit,
    onTagsClick: () -> Unit,
    onTextBlocksClick: () -> Unit,
    onAlertsClick: () -> Unit,
    onSpinnerClick: () -> Unit,
) {
    composable(route = MAIN_ROUTE) {
        MainScreen(
            onTabsClick = onTabsClick,
            onButtonsClick = onButtonsClick,
            onTextFieldsClick = onTextFieldsClick,
            onCellsClick = onCellsClick,
            onCheckBoxClick = onCheckBoxClick,
            onSwitcherClick = onSwitcherClick,
            onBadgesClick = onBadgesClick,
            onRadioButtonClick = onRadioButtonClick,
            onLinkClick = onLinkClick,
            onPageControlClick = onPageControlClick,
            onInformersAndNotificationsClick = onInformersAndNotificationsClick,
            onAlertsClick = onAlertsClick,
            onTextBlocksClick = onTextBlocksClick,
            onTagsClick = onTagsClick,
            onSpinnerClick = onSpinnerClick
        )
    }
}
