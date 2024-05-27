package com.admiral.demo.compose.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.admiral.demo.compose.home.badges.badgesScreen
import com.admiral.demo.compose.home.badges.navigateToBadgesScreen
import com.admiral.demo.compose.home.badges.navigateToNormalBadgesScreen
import com.admiral.demo.compose.home.badges.navigateToSmallBadgesScreen
import com.admiral.demo.compose.home.badges.normalBadgesScreen
import com.admiral.demo.compose.home.badges.smallBadgesScreen
import com.admiral.demo.compose.home.button.buttonsScreen
import com.admiral.demo.compose.home.button.ghostButtonsScreen
import com.admiral.demo.compose.home.button.navigateToButtonsScreen
import com.admiral.demo.compose.home.button.navigateToGhostButtonsScreen
import com.admiral.demo.compose.home.button.navigateToOtherButtonsScreen
import com.admiral.demo.compose.home.button.navigateToPrimaryButtonsScreen
import com.admiral.demo.compose.home.button.navigateToRuleButtonsScreen
import com.admiral.demo.compose.home.button.navigateToSecondaryButtonsScreen
import com.admiral.demo.compose.home.button.otherButtonsScreen
import com.admiral.demo.compose.home.button.primaryButtonsScreen
import com.admiral.demo.compose.home.button.ruleButtonsScreen
import com.admiral.demo.compose.home.button.secondaryButtonsScreen
import com.admiral.demo.compose.home.cells.cellsBaseScreen
import com.admiral.demo.compose.home.cells.cellsCenterScreen
import com.admiral.demo.compose.home.cells.cellsLeadingScreen
import com.admiral.demo.compose.home.cells.cellsMainScreen
import com.admiral.demo.compose.home.cells.cellsTrailingScreen
import com.admiral.demo.compose.home.cells.navigateToCellsBaseScreen
import com.admiral.demo.compose.home.cells.navigateToCellsCenterScreen
import com.admiral.demo.compose.home.cells.navigateToCellsLeadingScreen
import com.admiral.demo.compose.home.cells.navigateToCellsScreen
import com.admiral.demo.compose.home.cells.navigateToTrailingCellsScreen
import com.admiral.demo.compose.home.checkbox.checkBoxScreen
import com.admiral.demo.compose.home.checkbox.navigateToCheckBoxScreen
import com.admiral.demo.compose.home.control.navigateToPageControlCircleScreen
import com.admiral.demo.compose.home.control.navigateToPageControlLinerScreen
import com.admiral.demo.compose.home.control.navigateToPageControlScreen
import com.admiral.demo.compose.home.control.pageControlCircleScreen
import com.admiral.demo.compose.home.control.pageControlLinerScreen
import com.admiral.demo.compose.home.control.pageControlScreen
import com.admiral.demo.compose.home.links.linkScreen
import com.admiral.demo.compose.home.links.navigateToLinkScreen
import com.admiral.demo.compose.home.tabs.iconTabsScreen
import com.admiral.demo.compose.home.tabs.informerTabsScreen
import com.admiral.demo.compose.home.tabs.logoTabsScreen
import com.admiral.demo.compose.home.tabs.navigateToIconTabsScreen
import com.admiral.demo.compose.home.tabs.navigateToInformerTabsScreen
import com.admiral.demo.compose.home.tabs.navigateToLogoTabsScreen
import com.admiral.demo.compose.home.tabs.navigateToOutlineTabsScreen
import com.admiral.demo.compose.home.tabs.navigateToStandardTabsScreen
import com.admiral.demo.compose.home.tabs.navigateToTabsScreen
import com.admiral.demo.compose.home.tabs.navigateToUnderlineTabsScreen
import com.admiral.demo.compose.home.tabs.outlineTabsScreen
import com.admiral.demo.compose.home.tabs.standardTabsScreen
import com.admiral.demo.compose.home.tabs.tabsMainScreen
import com.admiral.demo.compose.home.tabs.underlineTabsScreen
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
            onTabsClick = { navController.navigateToTabsScreen() },
            onButtonsClick = { navController.navigateToButtonsScreen() },
            onTextFieldsClick = { navController.navigateToTextFieldsScreen() },
            onCellsClick = { navController.navigateToCellsScreen() },
            onCheckBoxClick = { navController.navigateToCheckBoxScreen() },
            onBadgesClick = { navController.navigateToBadgesScreen() },
            onPageControlClick = { navController.navigateToPageControlScreen() },
            onLinkClick = { navController.navigateToLinkScreen() },
        )
        buttonsScreen(
            onPrimaryClick = { navController.navigateToPrimaryButtonsScreen() },
            onSecondaryClick = { navController.navigateToSecondaryButtonsScreen() },
            onGhostClick = { navController.navigateToGhostButtonsScreen() },
            onRulesClick = { navController.navigateToRuleButtonsScreen() },
            onOtherClick = { navController.navigateToOtherButtonsScreen() },
        )
        primaryButtonsScreen(onBackClicked = { navController.popBackStack() })
        secondaryButtonsScreen(onBackClicked = { navController.popBackStack() })
        ghostButtonsScreen(onBackClicked = { navController.popBackStack() })
        ruleButtonsScreen(onBackClicked = { navController.popBackStack() })
        otherButtonsScreen(onBackClicked = { navController.popBackStack() })
        textFieldsScreen(
            onStandardClick = { navController.navigateToStandardTextFieldScreen() },
            onDoubleClick = { navController.navigateToDoubleTextFieldScreen() },
            onSliderClick = { navController.navigateToSliderTextFieldScreen() },
            onCardNumberClick = { navController.navigateToCardNumberTextFieldScreen() },
            onSmsCodeClick = { navController.navigateToSmsCodeTextFieldScreen() },
            onNumberClick = { navController.navigateToNumbersTextFieldsScreen() },
            onFeedbackClick = { navController.navigateToFeedbackTextFieldScreen() },
            onPinCodeClick = { navController.navigateToPinCodeTextFieldScreen() },
            onBackClick = { navController.navigateBack() }
        )
        standardTextFieldScreen(onBackClick = { navController.navigateBack() })
        doubleTextFieldScreen(onBackClick = { navController.navigateBack() })
        sliderTextFieldScreen(onBackClick = { navController.navigateBack() })
        cardNumberTextFieldScreen(onBackClick = { navController.navigateBack() })
        smsCodeTextFieldScreen(onBackClick = { navController.navigateBack() })
        numbersTextFieldsScreen(
            onDefaultClick = { navController.navigateToNumberDefaultTextFieldScreen() },
            onSecondaryClick = { navController.navigateToNumberSecondaryTextFieldScreen() },
            onBackClick = { navController.navigateBack() },
        )
        feedbackTextFieldScreen(onBackClick = { navController.navigateBack() })
        pinCodeTextFieldScreen(onBackClick = { navController.navigateBack() })
        numberDefaultTextFieldScreen(onBackClick = { navController.navigateBack() })
        numberSecondaryTextFieldScreen(onBackClick = { navController.navigateBack() })
        cellsMainScreen(
            onBackClick = { navController.navigateBack() },
            onBaseCellsClick = { navController.navigateToCellsBaseScreen() },
            onActionbarClick = {},
        )
        cellsBaseScreen(
            onBackClick = { navController.navigateBack() },
            onCellsLeadingClick = { navController.navigateToCellsLeadingScreen() },
            onCellsCenterClick = { navController.navigateToCellsCenterScreen() },
            onCellsTrailingClick = { navController.navigateToTrailingCellsScreen() }
        )
        cellsLeadingScreen(onBackClick = { navController.navigateBack() })
        cellsCenterScreen(onBackClick = { navController.navigateBack() })
        cellsTrailingScreen(onBackClick = { navController.navigateBack() })
        tabsMainScreen(
            onBackClick = { navController.navigateBack() },
            onStandardTabsClicked = { navController.navigateToStandardTabsScreen() },
            onLogoTabsClicked = { navController.navigateToLogoTabsScreen() },
            onInformerTabsClicked = { navController.navigateToInformerTabsScreen() },
            onOutlineTabsClicked = { navController.navigateToOutlineTabsScreen() },
            onUnderlineTabsClicked = { navController.navigateToUnderlineTabsScreen() },
            onIconTabsClicked = { navController.navigateToIconTabsScreen() },
        )
        standardTabsScreen(
            onBackClick = { navController.navigateBack() }
        )
        logoTabsScreen(
            onBackClick = { navController.navigateBack() }
        )
        informerTabsScreen(
            onBackClick = { navController.navigateBack() }
        )
        outlineTabsScreen(
            onBackClick = { navController.navigateBack() }
        )
        underlineTabsScreen(
            onBackClick = { navController.navigateBack() }
        )
        iconTabsScreen(
            onBackClick = { navController.navigateBack() }
        )
        checkBoxScreen(onBackClick = { navController.navigateBack() })
        pageControlScreen(
            onBackClick = { navController.navigateBack() },
            onLinerClick = { navController.navigateToPageControlLinerScreen() },
            onCircleClick = { navController.navigateToPageControlCircleScreen() },
        )
        pageControlLinerScreen(onBackClick = { navController.navigateBack() })
        pageControlCircleScreen(onBackClick = { navController.navigateBack() })
        linkScreen(onBackClick = { navController.navigateBack() })
        badgesScreen(
            onBackClick = { navController.navigateBack() },
            onNormalClick = { navController.navigateToNormalBadgesScreen() },
            onSmallClick = { navController.navigateToSmallBadgesScreen() }
        )
        normalBadgesScreen(onBackClick = { navController.navigateBack() })
        smallBadgesScreen(onBackClick = { navController.navigateBack() })
    }
}

internal fun NavController.navigateBack() {
    popBackStack()
}
