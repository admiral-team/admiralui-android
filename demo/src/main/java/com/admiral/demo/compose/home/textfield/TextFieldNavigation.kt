package com.admiral.demo.compose.home.textfield

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val TEXT_FIELDS_SCREEN_ROUTE = "textFieldsScreen"
internal const val STANDARD_TEXT_FIELD_SCREEN_ROUTE = "standardTextFieldScreen"
internal const val CARD_NUMBER_TEXT_FIELD_SCREEN_ROUTE = "cardNumberTextFieldScreen"
internal const val SMS_CODE_TEXT_FIELD_SCREEN_ROUTE = "smsCodeTextFieldScreen"
internal const val DOUBLE_TEXT_FIELD_SCREEN_ROUTE = "doubleTextFieldScreen"
internal const val SLIDER_TEXT_FIELD_SCREEN_ROUTE = "sliderTextFieldScreen"
internal const val NUMBERS_TEXT_FIELDS_SCREEN_ROUTE = "numbersTextFieldsScreen"
internal const val NUMBER_DEFAULT_TEXT_FIELD_SCREEN_ROUTE = "numberDefaultTextFieldScreen"
internal const val NUMBER_SECONDARY_TEXT_FIELD_SCREEN_ROUTE = "numberSecondaryTextFieldScreen"
internal const val FEEDBACK_TEXT_FIELD_SCREEN_ROUTE = "feedbackTextFieldScreen"
internal const val PIN_CODE_TEXT_FIELD_SCREEN_ROUTE = "pinCodeTextFieldScreen"

internal fun NavController.navigateToTextFieldsScreen() {
    navigate(TEXT_FIELDS_SCREEN_ROUTE)
}

internal fun NavController.navigateToStandardTextFieldScreen() {
    navigate(STANDARD_TEXT_FIELD_SCREEN_ROUTE)
}

internal fun NavController.navigateToCardNumberTextFieldScreen() {
    navigate(CARD_NUMBER_TEXT_FIELD_SCREEN_ROUTE)
}

internal fun NavController.navigateToSmsCodeTextFieldScreen() {
    navigate(SMS_CODE_TEXT_FIELD_SCREEN_ROUTE)
}

internal fun NavController.navigateToDoubleTextFieldScreen() {
    navigate(DOUBLE_TEXT_FIELD_SCREEN_ROUTE)
}

internal fun NavController.navigateToSliderTextFieldScreen() {
    navigate(SLIDER_TEXT_FIELD_SCREEN_ROUTE)
}

internal fun NavController.navigateToNumbersTextFieldsScreen() {
    navigate(NUMBERS_TEXT_FIELDS_SCREEN_ROUTE)
}

internal fun NavController.navigateToNumberDefaultTextFieldScreen() {
    navigate(NUMBER_DEFAULT_TEXT_FIELD_SCREEN_ROUTE)
}

internal fun NavController.navigateToNumberSecondaryTextFieldScreen() {
    navigate(NUMBER_SECONDARY_TEXT_FIELD_SCREEN_ROUTE)
}

internal fun NavController.navigateToFeedbackTextFieldScreen() {
    navigate(FEEDBACK_TEXT_FIELD_SCREEN_ROUTE)
}

internal fun NavController.navigateToPinCodeTextFieldScreen() {
    navigate(PIN_CODE_TEXT_FIELD_SCREEN_ROUTE)
}

@Suppress("LongMethod", "LongParameterList")
internal fun NavGraphBuilder.textFieldsScreen(
    onStandardClick: () -> Unit = {},
    onDoubleClick: () -> Unit = {},
    onSliderClick: () -> Unit = {},
    onCardNumberClick: () -> Unit = {},
    onSmsCodeClick: () -> Unit = {},
    onNumberClick: () -> Unit = {},
    onFeedbackClick: () -> Unit = {},
    onPinCodeClick: () -> Unit = {},
) {
    composable(route = TEXT_FIELDS_SCREEN_ROUTE) {
        TextFieldsScreen(
            onStandardClick = onStandardClick,
            onDoubleClick = onDoubleClick,
            onSliderClick = onSliderClick,
            onCardNumberClick = onCardNumberClick,
            onSmsCodeClick = onSmsCodeClick,
            onNumberClick = onNumberClick,
            onFeedbackClick = onFeedbackClick,
            onPinCodeClick = onPinCodeClick,
        )
    }
}

internal fun NavGraphBuilder.standardTextFieldScreen() {
    composable(route = STANDARD_TEXT_FIELD_SCREEN_ROUTE) {
        StandardTextFieldScreen()
    }
}

internal fun NavGraphBuilder.cardNumberTextFieldScreen() {
    composable(route = CARD_NUMBER_TEXT_FIELD_SCREEN_ROUTE) {
        CardNumberTextFieldScreen()
    }
}

internal fun NavGraphBuilder.smsCodeTextFieldScreen() {
    composable(route = SMS_CODE_TEXT_FIELD_SCREEN_ROUTE) {
        SmsTextFieldScreen()
    }
}

internal fun NavGraphBuilder.doubleTextFieldScreen() {
    composable(route = DOUBLE_TEXT_FIELD_SCREEN_ROUTE) {
        DoubleTextFieldScreen()
    }
}

internal fun NavGraphBuilder.sliderTextFieldScreen() {
    composable(route = SLIDER_TEXT_FIELD_SCREEN_ROUTE) {

    }
}

internal fun NavGraphBuilder.numbersTextFieldsScreen(
    onDefaultClick: () -> Unit,
    onSecondaryClick: () -> Unit,
    onBackClick: () -> Unit
) {
    composable(route = NUMBERS_TEXT_FIELDS_SCREEN_ROUTE) {
        NumbersTextFieldsScreen(
            onDefaultClick = onDefaultClick,
            onSecondaryClick = onSecondaryClick,
            onBackClick = onBackClick,
        )
    }
}

internal fun NavGraphBuilder.numberDefaultTextFieldScreen(onBackClick: () -> Unit) {
    composable(route = NUMBER_DEFAULT_TEXT_FIELD_SCREEN_ROUTE) {
        NumberDefaultTextFieldsScreen(onBackClick = onBackClick)
    }
}

internal fun NavGraphBuilder.numberSecondaryTextFieldScreen(onBackClick: () -> Unit) {
    composable(route = NUMBER_SECONDARY_TEXT_FIELD_SCREEN_ROUTE) {
        NumberSecondaryTextFieldsScreen(onBackClick = onBackClick)
    }
}

internal fun NavGraphBuilder.feedbackTextFieldScreen(onBackClick: () -> Unit) {
    composable(route = FEEDBACK_TEXT_FIELD_SCREEN_ROUTE) {
        FeedbackTextFieldScreen(onBackClick = onBackClick)
    }
}

internal fun NavGraphBuilder.pinCodeTextFieldScreen() {
    composable(route = PIN_CODE_TEXT_FIELD_SCREEN_ROUTE) {

    }
}