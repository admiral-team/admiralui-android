package com.admiral.demo.compose.home.tabs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val TABS_MAIN_SCREEN_ROUTE = "tabsMainScreen"
internal const val TABS_STANDARD_SCREEN_ROUTE = "tabsStandardScreen"
internal const val TABS_LOGO_SCREEN_ROUTE = "tabsLogoScreen"
internal const val TABS_INFORMER_SCREEN_ROUTE = "tabsInformerScreen"
internal const val TABS_OUTLINE_SCREEN_ROUTE = "tabsOutlineScreen"
internal const val TABS_UNDERLINE_SCREEN_ROUTE = "tabsUnderlineScreen"
internal const val TABS_ICON_SCREEN_ROUTE = "tabsIconScreen"

internal fun NavController.navigateToTabsScreen() {
    navigate(TABS_MAIN_SCREEN_ROUTE)
}

internal fun NavController.navigateToStandardTabsScreen() {
    navigate(TABS_STANDARD_SCREEN_ROUTE)
}

internal fun NavController.navigateToLogoTabsScreen() {
    navigate(TABS_LOGO_SCREEN_ROUTE)
}

internal fun NavController.navigateToInformerTabsScreen() {
    navigate(TABS_INFORMER_SCREEN_ROUTE)
}

internal fun NavController.navigateToOutlineTabsScreen() {
    navigate(TABS_OUTLINE_SCREEN_ROUTE)
}

internal fun NavController.navigateToUnderlineTabsScreen() {
    navigate(TABS_UNDERLINE_SCREEN_ROUTE)
}

internal fun NavController.navigateToIconTabsScreen() {
    navigate(TABS_ICON_SCREEN_ROUTE)
}

@Suppress("LongParameterList")
internal fun NavGraphBuilder.tabsMainScreen(
    onBackClick: () -> Unit = {},
    onStandardTabsClicked: () -> Unit = {},
    onLogoTabsClicked: () -> Unit = {},
    onInformerTabsClicked: () -> Unit = {},
    onOutlineTabsClicked: () -> Unit = {},
    onUnderlineTabsClicked: () -> Unit = {},
    onIconTabsClicked: () -> Unit = {},
) {
    composable(route = TABS_MAIN_SCREEN_ROUTE) {
        TabsMainScreen(
            onBackClick = onBackClick,
            onStandardTabsClicked = onStandardTabsClicked,
            onLogoTabsClicked = onLogoTabsClicked,
            onInformerTabsClicked = onInformerTabsClicked,
            onOutlineTabsClicked = onOutlineTabsClicked,
            onUnderlineTabsClicked = onUnderlineTabsClicked,
            onIconTabsClicked = onIconTabsClicked,
        )
    }
}

internal fun NavGraphBuilder.standardTabsScreen(
    onBackClick: () -> Unit = {},
) {
    composable(route = TABS_STANDARD_SCREEN_ROUTE) {
        StandardTabsScreen(
            onBackClick = onBackClick,
        )
    }
}

internal fun NavGraphBuilder.logoTabsScreen(
    onBackClick: () -> Unit = {},
) {
    composable(route = TABS_LOGO_SCREEN_ROUTE) {
        LogoTabsScreen(
            onBackClick = onBackClick,
        )
    }
}

internal fun NavGraphBuilder.informerTabsScreen(
    onBackClick: () -> Unit = {},
) {
    composable(route = TABS_INFORMER_SCREEN_ROUTE) {
        LogoTabsScreen(
            onBackClick = onBackClick,
        )
    }
}

internal fun NavGraphBuilder.outlineTabsScreen(
    onBackClick: () -> Unit = {},
) {
    composable(route = TABS_OUTLINE_SCREEN_ROUTE) {
        OutlineTabsScreen(
            onBackClick = onBackClick,
        )
    }
}

internal fun NavGraphBuilder.underlineTabsScreen(
    onBackClick: () -> Unit = {},
) {
    composable(route = TABS_UNDERLINE_SCREEN_ROUTE) {
        UnderlineSliderTabsScreen(
            onBackClick = onBackClick,
        )
    }
}

internal fun NavGraphBuilder.iconTabsScreen(
    onBackClick: () -> Unit = {},
) {
    composable(route = TABS_ICON_SCREEN_ROUTE) {
        LogoTabsScreen(
            onBackClick = onBackClick,
        )
    }
}
