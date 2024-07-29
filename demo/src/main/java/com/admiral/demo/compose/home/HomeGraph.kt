package com.admiral.demo.compose.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.admiral.demo.compose.home.alerts.alertScreen
import com.admiral.demo.compose.home.alerts.alertsOnboardingScreen
import com.admiral.demo.compose.home.alerts.errorViewScreen
import com.admiral.demo.compose.home.alerts.navigateErrorViewScreenRoute
import com.admiral.demo.compose.home.alerts.navigateToAlertScreenRoute
import com.admiral.demo.compose.home.alerts.navigateToAlertsOnboardingScreenRoute
import com.admiral.demo.compose.home.alerts.navigateToOnboardingScreenRoute
import com.admiral.demo.compose.home.alerts.navigateToZeroScreen
import com.admiral.demo.compose.home.alerts.onboardingScreen
import com.admiral.demo.compose.home.alerts.zeroScreen
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
import com.admiral.demo.compose.home.cells.navigateToCellsActionBarScreen
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
import com.admiral.demo.compose.home.informers.bigInformersScreen
import com.admiral.demo.compose.home.informers.informersAndNotificationsScreen
import com.admiral.demo.compose.home.informers.informersScreen
import com.admiral.demo.compose.home.informers.navigateToBigInformersScreen
import com.admiral.demo.compose.home.informers.navigateToInformersAndNotificationsScreen
import com.admiral.demo.compose.home.informers.navigateToInformersScreen
import com.admiral.demo.compose.home.informers.navigateToSmallInformersScreen
import com.admiral.demo.compose.home.informers.smallInformersScreen
import com.admiral.demo.compose.home.links.linkScreen
import com.admiral.demo.compose.home.links.navigateToLinkScreen
import com.admiral.demo.compose.home.radiobutton.navigateToRadioButtonScreen
import com.admiral.demo.compose.home.radiobutton.radioButtonScreen
import com.admiral.demo.compose.home.notifications.actionNotificationsScreen
import com.admiral.demo.compose.home.notifications.navigateToActionNotificationsScreen
import com.admiral.demo.compose.home.notifications.navigateToNotificationsScreen
import com.admiral.demo.compose.home.notifications.navigateToStaticNotificationsScreen
import com.admiral.demo.compose.home.notifications.navigateToToastNotificationsScreen
import com.admiral.demo.compose.home.notifications.notificationsScreen
import com.admiral.demo.compose.home.notifications.staticNotificationsScreen
import com.admiral.demo.compose.home.notifications.toastNotificationsScreen
import com.admiral.demo.compose.home.tabs.iconTabsScreen
import com.admiral.demo.compose.home.tabs.informerTabsScreen
import com.admiral.demo.compose.home.tabs.logoTabsScreen
import com.admiral.demo.compose.home.tabs.navigateToIconTabsScreen
import com.admiral.demo.compose.home.tabs.navigateToInformerTabsScreen
import com.admiral.demo.compose.home.tabs.navigateToLogoTabsScreen
import com.admiral.demo.compose.home.tabs.navigateToOutlineTabsScreen
import com.admiral.demo.compose.home.tabs.navigateToStandardTabsScreen
import com.admiral.demo.compose.home.tabs.navigateToTabsScreen
import com.admiral.demo.compose.home.tabs.navigateToUnderlineCenterTabsScreen
import com.admiral.demo.compose.home.tabs.navigateToUnderlineSliderTabsScreen
import com.admiral.demo.compose.home.tabs.navigateToUnderlineTabsScreen
import com.admiral.demo.compose.home.tabs.outlineTabsScreen
import com.admiral.demo.compose.home.tabs.standardTabsScreen
import com.admiral.demo.compose.home.tabs.tabsMainScreen
import com.admiral.demo.compose.home.tabs.underlineCenterTabsScreen
import com.admiral.demo.compose.home.tabs.underlineSliderTabsScreen
import com.admiral.demo.compose.home.tabs.underlineTabsScreen
import com.admiral.demo.compose.home.tags.navigateToTagsScreen
import com.admiral.demo.compose.home.tags.tagsScreen
import com.admiral.demo.compose.home.textblocks.navigateToAccordionTextBlocksScreen
import com.admiral.demo.compose.home.textblocks.navigateToHeaderTextBlocksScreen
import com.admiral.demo.compose.home.textblocks.navigateToLinkTextBlocksScreen
import com.admiral.demo.compose.home.textblocks.navigateToPaddingTextBlocksScreen
import com.admiral.demo.compose.home.textblocks.navigateToParagraphTextBlocksScreen
import com.admiral.demo.compose.home.textblocks.navigateToTextBlocksScreen
import com.admiral.demo.compose.home.textblocks.textBlocksAccordionScreen
import com.admiral.demo.compose.home.textblocks.textBlocksHeaderScreen
import com.admiral.demo.compose.home.textblocks.textBlocksLinkScreen
import com.admiral.demo.compose.home.textblocks.textBlocksPaddingScreen
import com.admiral.demo.compose.home.textblocks.textBlocksParagraphScreen
import com.admiral.demo.compose.home.textblocks.textBlocksScreen
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
    val onBackClick = { navController.navigateBack() }
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
            onRadioButtonClick = { navController.navigateToRadioButtonScreen() },
            onPageControlClick = { navController.navigateToPageControlScreen() },
            onLinkClick = { navController.navigateToLinkScreen() },
            onInformersAndNotificationsClick = { navController.navigateToInformersAndNotificationsScreen() },
            onTagsClick = { navController.navigateToTagsScreen() },
            onAlertsClick = { navController.navigateToAlertsOnboardingScreenRoute() },
            onTextBlocksClick = { navController.navigateToTextBlocksScreen() },
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
            onBackClick = onBackClick
        )
        standardTextFieldScreen(onBackClick = onBackClick)
        doubleTextFieldScreen(onBackClick = onBackClick)
        sliderTextFieldScreen(onBackClick = onBackClick)
        cardNumberTextFieldScreen(onBackClick = onBackClick)
        smsCodeTextFieldScreen(onBackClick = onBackClick)
        numbersTextFieldsScreen(
            onDefaultClick = { navController.navigateToNumberDefaultTextFieldScreen() },
            onSecondaryClick = { navController.navigateToNumberSecondaryTextFieldScreen() },
            onBackClick = onBackClick,
        )
        feedbackTextFieldScreen(onBackClick = onBackClick)
        pinCodeTextFieldScreen(onBackClick = onBackClick)
        numberDefaultTextFieldScreen(onBackClick = onBackClick)
        numberSecondaryTextFieldScreen(onBackClick = onBackClick)
        cellsMainScreen(
            onBackClick = onBackClick,
            onBaseCellsClick = { navController.navigateToCellsBaseScreen() },
            onActionbarClick = { navController.navigateToCellsActionBarScreen() },
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
        textBlocksScreen(
            onBackClick = onBackClick,
            onHeaderClick = { navController.navigateToHeaderTextBlocksScreen() },
            onAccordionClick = { navController.navigateToAccordionTextBlocksScreen() },
            onParagraphClick = { navController.navigateToParagraphTextBlocksScreen() },
            onLinkClick = { navController.navigateToLinkTextBlocksScreen() },
            onPaddingClick = { navController.navigateToPaddingTextBlocksScreen() }
        )
        textBlocksHeaderScreen(onBackClick = onBackClick)
        textBlocksAccordionScreen(onBackClick = onBackClick)
        textBlocksParagraphScreen(onBackClick = onBackClick)
        textBlocksLinkScreen(onBackClick = onBackClick)
        textBlocksPaddingScreen(onBackClick = onBackClick)
        tabsMainScreen(
            onBackClick = onBackClick,
            onStandardTabsClicked = { navController.navigateToStandardTabsScreen() },
            onLogoTabsClicked = { navController.navigateToLogoTabsScreen() },
            onInformerTabsClicked = { navController.navigateToInformerTabsScreen() },
            onOutlineTabsClicked = { navController.navigateToOutlineTabsScreen() },
            onUnderlineTabsClicked = { navController.navigateToUnderlineTabsScreen() },
            onIconTabsClicked = { navController.navigateToIconTabsScreen() },
        )
        standardTabsScreen(onBackClick = onBackClick)
        logoTabsScreen(onBackClick = onBackClick)
        informerTabsScreen(onBackClick = onBackClick)
        outlineTabsScreen(onBackClick = onBackClick)
        underlineTabsScreen(onBackClick = onBackClick)
        iconTabsScreen(onBackClick = onBackClick)
        checkBoxScreen(onBackClick = onBackClick)
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
            onBackClick = { navController.navigateBack() },
            onSliderTabsClicked = { navController.navigateToUnderlineSliderTabsScreen() },
            onCenterTabsClicked = { navController.navigateToUnderlineCenterTabsScreen() }
        )
        underlineSliderTabsScreen(onBackClick = { navController.navigateBack() })
        underlineCenterTabsScreen(onBackClick = { navController.navigateBack() })
        iconTabsScreen(
            onBackClick = { navController.navigateBack() }
        )
        informersAndNotificationsScreen(
            onBackClick = { navController.navigateBack() },
            onInformersClick = { navController.navigateToInformersScreen() },
            onNotificationsClick = { navController.navigateToNotificationsScreen() },
        )
        informersScreen(
            onBackClick = { navController.navigateBack() },
            onInformersBigClick = { navController.navigateToBigInformersScreen() },
            onInformersSmallClick = { navController.navigateToSmallInformersScreen() },
        )
        bigInformersScreen(onBackClick = { navController.navigateBack() })
        smallInformersScreen(onBackClick = { navController.navigateBack() })
        notificationsScreen(
            onBackClick = { navController.navigateBack() },
            onToastClick = { navController.navigateToToastNotificationsScreen() },
            onStaticClick = { navController.navigateToStaticNotificationsScreen() },
            onActionClick = { navController.navigateToActionNotificationsScreen() }
        )
        toastNotificationsScreen(onBackClick = onBackClick)
        actionNotificationsScreen(onBackClick = onBackClick)
        staticNotificationsScreen(onBackClick = onBackClick)
        checkBoxScreen(onBackClick = { navController.navigateBack() })
        pageControlScreen(
            onBackClick = onBackClick,
            onLinerClick = { navController.navigateToPageControlLinerScreen() },
            onCircleClick = { navController.navigateToPageControlCircleScreen() },
        )
        pageControlLinerScreen(onBackClick = onBackClick)
        pageControlCircleScreen(onBackClick = onBackClick)
        linkScreen(onBackClick = onBackClick)
        badgesScreen(
            onBackClick = onBackClick,
            onNormalClick = { navController.navigateToNormalBadgesScreen() },
            onSmallClick = { navController.navigateToSmallBadgesScreen() }
        )
        normalBadgesScreen(onBackClick = onBackClick)
        smallBadgesScreen(onBackClick = onBackClick)
        normalBadgesScreen(onBackClick = { navController.navigateBack() })
        smallBadgesScreen(onBackClick = { navController.navigateBack() })
        radioButtonScreen(onBackClick = onBackClick)
        alertsOnboardingScreen(
            onAlertClicked = { navController.navigateToAlertScreenRoute() },
            onOnboardingClick = { navController.navigateToOnboardingScreenRoute() },
            onZeroScreenClick = { navController.navigateToZeroScreen() },
            onErrorViewClick = { navController.navigateErrorViewScreenRoute() },
            onBackClick = onBackClick,
        )
        errorViewScreen {
            navController.navigateBack()
        }
        zeroScreen {
            navController.navigateBack()
        }
        alertScreen { navController.navigateBack() }
        onboardingScreen { navController.navigateBack() }
        tagsScreen(onBackClick = { navController.navigateBack() })
    }
}

internal fun NavController.navigateBack() {
    popBackStack()
}
