package com.admiral.demo.compose.home.alerts

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val ALERTS_ONBOARDING_SCREEN_ROUTE = "alertsOnboardingScreenRoute"
internal const val ALERTS_SCREEN_ROUTE = "alertScreenRoute"
internal const val ONBOARDING_SCREEN_ROUTE = "onboardingScreenRoute"
internal const val ZERO_SCREEN_ROUTE = "zeroScreenRoute"
internal const val ERROR_VIEW_SCREEN_ROUTE = "errorViewScreenRoute"

internal fun NavController.navigateToAlertsOnboardingScreenRoute() {
    navigate(ALERTS_ONBOARDING_SCREEN_ROUTE)
}

internal fun NavController.navigateToAlertScreenRoute() {
    navigate(ALERTS_SCREEN_ROUTE)
}

internal fun NavController.navigateToOnboardingScreenRoute() {
    navigate(ONBOARDING_SCREEN_ROUTE)
}

internal fun NavController.navigateErrorViewScreenRoute() {
    navigate(ERROR_VIEW_SCREEN_ROUTE)
}

internal fun NavController.navigateToZeroScreen() {
    navigate(ZERO_SCREEN_ROUTE)
}

internal fun NavGraphBuilder.alertsOnboardingScreen(
    onAlertClicked: () -> Unit,
    onOnboardingClick: () -> Unit,
    onZeroScreenClick: () -> Unit,
    onErrorViewClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    composable(route = ALERTS_ONBOARDING_SCREEN_ROUTE) {
        AlertsOnboardingScreen(
            onAlertClicked = onAlertClicked,
            onOnboardingClick = onOnboardingClick,
            onZeroScreenClick = onZeroScreenClick,
            onErrorViewClick = onErrorViewClick,
            onBackClick = onBackClick,
        )
    }
}

internal fun NavGraphBuilder.errorViewScreen(
    onBackClick: () -> Unit,
) {
    composable(route = ERROR_VIEW_SCREEN_ROUTE) {
        ErrorViewScreen(
            onBackClick = onBackClick,
        )
    }
}

internal fun NavGraphBuilder.zeroScreen(
    onBackClick: () -> Unit,
) {
    composable(route = ZERO_SCREEN_ROUTE) {
        ZeroScreen(
            onBackClick = onBackClick,
        )
    }
}

internal fun NavGraphBuilder.alertScreen(
    onBackClick: () -> Unit,
) {
    composable(route = ALERTS_SCREEN_ROUTE) {

    }
}

internal fun NavGraphBuilder.onboardingScreen(
    onBackClick: () -> Unit,
) {
    composable(route = ONBOARDING_SCREEN_ROUTE) {

    }
}