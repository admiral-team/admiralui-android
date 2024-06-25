package com.admiral.demo.compose.home.alerts

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val AlertsOnboardingScreenRoute = "alertsOnboardingScreenRoute"
internal const val AlertScreenRoute = "alertScreenRoute"
internal const val OnboardingScreenRoute = "onboardingScreenRoute"
internal const val ZeroScreenRoute = "zeroScreenRoute"
internal const val ErrorViewScreenRoute = "errorViewScreenRoute"

internal fun NavController.navigateToAlertsOnboardingScreenRoute() {
    navigate(AlertsOnboardingScreenRoute)
}

internal fun NavController.navigateToAlertScreenRoute() {
    navigate(AlertScreenRoute)
}

internal fun NavController.navigateToOnboardingScreenRoute() {
    navigate(OnboardingScreenRoute)
}

internal fun NavController.navigateErrorViewScreenRoute() {
    navigate(ErrorViewScreenRoute)
}

internal fun NavController.navigateToZeroScreen() {
    navigate(ZeroScreenRoute)
}

internal fun NavGraphBuilder.alertsOnboardingScreen(
    onAlertClicked: () -> Unit,
    onOnboardingClick: () -> Unit,
    onZeroScreenClick: () -> Unit,
    onErrorViewClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    composable(route = AlertsOnboardingScreenRoute) {
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
    composable(route = ErrorViewScreenRoute) {
        ErrorViewScreen(
            onBackClick = onBackClick,
        )
    }
}

internal fun NavGraphBuilder.zeroScreen(
    onBackClick: () -> Unit,
) {
    composable(route = ZeroScreenRoute) {
        ZeroScreen(
            onBackClick = onBackClick,
        )
    }
}