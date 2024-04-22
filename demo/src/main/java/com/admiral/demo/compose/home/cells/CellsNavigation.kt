package com.admiral.demo.compose.home.cells

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val CELLS_MAIN_SCREEN_ROUTE = "cellsMainScreen"
internal const val CELLS_BASE_SCREEN_ROUTE = "cellsBaseScreen"
internal const val CELLS_LEADING_SCREEN_ROUTE = "cellsLeadingScreen"
internal const val CELLS_CENTER_SCREEN_ROUTE = "cellsCenterScreen"

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
) {
    composable(route = CELLS_BASE_SCREEN_ROUTE) {
        CellsBaseScreen(
            onBackClick = onBackClick,
            onCellsLeadingClick = onCellsLeadingClick,
            onCellsCenterClick = onCellsCenterClick,
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
