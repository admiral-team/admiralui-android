package com.admiral.demo.compose.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.admiral.demo.compose.home.button.buttonsScreen
import com.admiral.demo.compose.home.button.ghostButtonsScreen
import com.admiral.demo.compose.home.button.navigateToButtonsScreen
import com.admiral.demo.compose.home.button.navigateToGhostButtonsScreen
import com.admiral.demo.compose.home.button.navigateToPrimaryButtonsScreen
import com.admiral.demo.compose.home.button.navigateToRuleButtonsScreen
import com.admiral.demo.compose.home.button.navigateToSecondaryButtonsScreen
import com.admiral.demo.compose.home.button.primaryButtonsScreen
import com.admiral.demo.compose.home.button.ruleButtonsScreen
import com.admiral.demo.compose.home.button.secondaryButtonsScreen
import com.admiral.demo.compose.home.cells.cellsBaseScreen
import com.admiral.demo.compose.home.cells.cellsCenterScreen
import com.admiral.demo.compose.home.cells.cellsLeadingScreen
import com.admiral.demo.compose.home.cells.cellsMainScreen
import com.admiral.demo.compose.home.cells.navigateToCellsBaseScreen
import com.admiral.demo.compose.home.cells.navigateToCellsCenterScreen
import com.admiral.demo.compose.home.cells.navigateToCellsLeadingScreen
import com.admiral.demo.compose.home.cells.navigateToCellsScreen
import com.admiral.demo.compose.home.checkbox.checkBoxScreen
import com.admiral.demo.compose.home.checkbox.navigateToCheckBoxScreen
import com.admiral.demo.compose.home.textfield.cardNumberTextFieldScreen
import com.admiral.demo.compose.home.textfield.doubleTextFieldScreen
import com.admiral.demo.compose.home.textfield.feedbackTextFieldScreen
import com.admiral.demo.compose.home.textfield.navigateToCardNumberTextFieldScreen
import com.admiral.demo.compose.home.textfield.navigateToDoubleTextFieldScreen
import com.admiral.demo.compose.home.textfield.navigateToFeedbackTextFieldScreen
import com.admiral.demo.compose.home.textfield.navigateToNumberDefaultTextFieldScreen
import com.admiral.demo.compose.home.textfield.navigateToNumberSecondaryTextFieldScreen
import com.admiral.demo.compose.home.textfield.navigateToNumbersTextFieldsScreen
import com.admiral.demo.compose.home.textfield.navigateToPinCodeTextFieldScreen
import com.admiral.demo.compose.home.textfield.navigateToSliderTextFieldScreen
import com.admiral.demo.compose.home.textfield.navigateToSmsCodeTextFieldScreen
import com.admiral.demo.compose.home.textfield.navigateToStandardTextFieldScreen
import com.admiral.demo.compose.home.textfield.navigateToTextFieldsScreen
import com.admiral.demo.compose.home.textfield.numberDefaultTextFieldScreen
import com.admiral.demo.compose.home.textfield.numberSecondaryTextFieldScreen
import com.admiral.demo.compose.home.textfield.numbersTextFieldsScreen
import com.admiral.demo.compose.home.textfield.pinCodeTextFieldScreen
import com.admiral.demo.compose.home.textfield.sliderTextFieldScreen
import com.admiral.demo.compose.home.textfield.smsCodeTextFieldScreen
import com.admiral.demo.compose.home.textfield.standardTextFieldScreen
import com.admiral.demo.compose.home.textfield.textFieldsScreen
import com.admiral.demo.compose.main.MAIN_ROUTE
import com.admiral.demo.compose.main.mainScreen
import com.admiral.demo.compose.tabs.navigateToTabs
import com.admiral.demo.compose.tabs.tabsScreen

internal const val HOME_GRAPH_ROUTE = "home_graph"

@Suppress("LongMethod")
fun NavGraphBuilder.homeGraph(
    navController: NavController,
) {
    navigation(
        startDestination = MAIN_ROUTE,
        route = HOME_GRAPH_ROUTE,
    ) {
        mainScreen(
            onTabsClick = { navController.navigateToTabs() },
            onButtonsClick = { navController.navigateToButtonsScreen() },
            onTextFieldsClick = { navController.navigateToTextFieldsScreen() },
            onCellsClick = { navController.navigateToCellsScreen() },
            onCheckBoxClick = {navController.navigateToCheckBoxScreen()}
        )
        buttonsScreen(
            onPrimaryClick = { navController.navigateToPrimaryButtonsScreen() },
            onSecondaryClick = { navController.navigateToSecondaryButtonsScreen() },
            onGhostClick = { navController.navigateToGhostButtonsScreen() },
            onRulesClick = { navController.navigateToRuleButtonsScreen() },
            onOtherClick = { },
        )
        primaryButtonsScreen()
        secondaryButtonsScreen()
        ghostButtonsScreen()
        ruleButtonsScreen(
            onBackClicked = { navController.popBackStack() }
        )
        textFieldsScreen(
            onStandardClick = { navController.navigateToStandardTextFieldScreen() },
            onDoubleClick = { navController.navigateToDoubleTextFieldScreen() },
            onSliderClick = { navController.navigateToSliderTextFieldScreen() },
            onCardNumberClick = { navController.navigateToCardNumberTextFieldScreen() },
            onSmsCodeClick = { navController.navigateToSmsCodeTextFieldScreen() },
            onNumberClick = { navController.navigateToNumbersTextFieldsScreen() },
            onFeedbackClick = { navController.navigateToFeedbackTextFieldScreen() },
            onPinCodeClick = { navController.navigateToPinCodeTextFieldScreen() },
        )
        standardTextFieldScreen()
        doubleTextFieldScreen()
        sliderTextFieldScreen()
        cardNumberTextFieldScreen()
        smsCodeTextFieldScreen()
        numbersTextFieldsScreen(
            onDefaultClick = { navController.navigateToNumberDefaultTextFieldScreen() },
            onSecondaryClick = { navController.navigateToNumberSecondaryTextFieldScreen() },
            onBackClick = { navController.navigateBack() },
        )
        feedbackTextFieldScreen(onBackClick = { navController.navigateBack() })
        pinCodeTextFieldScreen()
        numberDefaultTextFieldScreen(onBackClick = { navController.navigateBack() })
        numberSecondaryTextFieldScreen(onBackClick = { navController.navigateBack() })
        cellsMainScreen(
            onBackClick = { navController.navigateBack() },
            onBaseCellsClick = { navController.navigateToCellsBaseScreen() },
            onActionbarClick = {},
        )
        cellsBaseScreen(
            onBackClick = { navController.navigateBack() },
            onCellsLeadingClick = {
                navController.navigateToCellsLeadingScreen()
            },
            onCellsCenterClick = {
                navController.navigateToCellsCenterScreen()
            }
        )
        cellsLeadingScreen(
            onBackClick = { navController.navigateBack() },
        )
        cellsCenterScreen(
            onBackClick = { navController.navigateBack() },
        )
        tabsScreen()
        checkBoxScreen(onBackClick = { navController.navigateBack() })
    }
}

internal fun NavController.navigateBack() {
    popBackStack()
}
