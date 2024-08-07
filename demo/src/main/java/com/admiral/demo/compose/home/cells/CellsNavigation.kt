package com.admiral.demo.compose.home.cells

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val CELLS_MAIN_SCREEN_ROUTE = "cellsMainScreen"
internal const val CELLS_BASE_SCREEN_ROUTE = "cellsBaseScreen"
internal const val CELLS_LEADING_SCREEN_ROUTE = "cellsLeadingScreen"
internal const val CELLS_CENTER_SCREEN_ROUTE = "cellsCenterScreen"
internal const val TRAILING_CELLS_SCREEN_ROUTE = "cellsTrailingScreen"
internal const val CELLS_ACTION_BAR_SCREEN_ROUTE = "cellsActionBarScreen"

internal fun NavController.navigateToCellsScreen() {
    navigate(CELLS_MAIN_SCREEN_ROUTE)
}

internal fun NavController.navigateToCellsBaseScreen() {
    navigate(CELLS_BASE_SCREEN_ROUTE)
}

internal fun NavController.navigateToCellsLeadingScreen() {
    navigate(CELLS_LEADING_SCREEN_ROUTE)
}

internal fun NavController.navigateToCellsCenterScreen() {
    navigate(CELLS_CENTER_SCREEN_ROUTE)
}

internal fun NavController.navigateToTrailingCellsScreen() {
    navigate(TRAILING_CELLS_SCREEN_ROUTE)
}

internal fun NavController.navigateToCellsActionBarScreen() {
    navigate(CELLS_ACTION_BAR_SCREEN_ROUTE)
}

internal fun NavGraphBuilder.cellsMainScreen(
    onBackClick: () -> Unit = {},
    onBaseCellsClick: () -> Unit = {},
    onActionbarClick: () -> Unit = {},
) {
    composable(route = CELLS_MAIN_SCREEN_ROUTE) {
        CellsMainScreen(
            onBackClick = onBackClick,
            onBaseCellsClick = onBaseCellsClick,
            onActionbarClick = onActionbarClick,
        )
    }
}

internal fun NavGraphBuilder.cellsBaseScreen(
    onBackClick: () -> Unit = {},
    onCellsLeadingClick: () -> Unit = {},
    onCellsCenterClick: () -> Unit = {},
    onCellsTrailingClick: () -> Unit = {},
) {
    composable(route = CELLS_BASE_SCREEN_ROUTE) {
        CellsBaseScreen(
            onBackClick = onBackClick,
            onCellsLeadingClick = onCellsLeadingClick,
            onCellsCenterClick = onCellsCenterClick,
            onCellsTrailingClick = onCellsTrailingClick
        )
    }
}

internal fun NavGraphBuilder.cellsLeadingScreen(onBackClick: () -> Unit) {
    composable(route = CELLS_LEADING_SCREEN_ROUTE) {
        CellsLeadingScreen(
            onBackClick = onBackClick
        )
    }
}

internal fun NavGraphBuilder.cellsCenterScreen(onBackClick: () -> Unit) {
    composable(route = CELLS_CENTER_SCREEN_ROUTE) {
        CellsCenterScreen(
            onBackClick = onBackClick
        )
    }
}

internal fun NavGraphBuilder.cellsActionBarScreen(onBackClick: () -> Unit) {
    composable(route = CELLS_ACTION_BAR_SCREEN_ROUTE) {
        CellsActionBarScreen(
            onBackClick = onBackClick
        )
    }
}

internal fun NavGraphBuilder.cellsTrailingScreen(onBackClick: () -> Unit) {
    composable(route = TRAILING_CELLS_SCREEN_ROUTE) {
        CellsTrailingScreen(
            onBackClick = onBackClick
        )
    }
}
