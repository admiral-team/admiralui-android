package com.admiral.demo.compose.home.textblocks

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

internal const val TEXT_BLOCKS_SCREEN_ROUTE = "textBlocksScreenRoute"
internal const val TEXT_BLOCKS_HEADER_SCREEN_ROUTE = "textBlockHeaderScreenRoute"
internal const val TEXT_BLOCKS_ACCORDION_SCREEN_ROUTE = "textBlockAccordionScreenRoute"
internal const val TEXT_BLOCKS_PARAGRAPH_SCREEN_ROUTE = "textBlockParagraphScreenRoute"
internal const val TEXT_BLOCKS_LINK_SCREEN_ROUTE = "textBlockLinkScreenRoute"
internal const val TEXT_BLOCKS_PADDING_SCREEN_ROUTE = "textBlockPaddingScreenRoute"

internal fun NavController.navigateToTextBlocksScreen() {
    navigate(TEXT_BLOCKS_SCREEN_ROUTE)
}

internal fun NavController.navigateToHeaderTextBlocksScreen() {
    navigate(TEXT_BLOCKS_HEADER_SCREEN_ROUTE)
}

internal fun NavController.navigateToAccordionTextBlocksScreen() {
    navigate(TEXT_BLOCKS_ACCORDION_SCREEN_ROUTE)
}

internal fun NavController.navigateToParagraphTextBlocksScreen() {
    navigate(TEXT_BLOCKS_PARAGRAPH_SCREEN_ROUTE)
}

internal fun NavController.navigateToLinkTextBlocksScreen() {
    navigate(TEXT_BLOCKS_LINK_SCREEN_ROUTE)
}

internal fun NavController.navigateToPaddingTextBlocksScreen() {
    navigate(TEXT_BLOCKS_PADDING_SCREEN_ROUTE)
}

@Suppress("LongParameterList")
internal fun NavGraphBuilder.textBlocksScreen(
    onBackClick: () -> Unit = {},
    onHeaderClick: () -> Unit = {},
    onAccordionClick: () -> Unit = {},
    onParagraphClick: () -> Unit = {},
    onLinkClick: () -> Unit = {},
    onPaddingClick: () -> Unit = {},
) {
    composable(route = TEXT_BLOCKS_SCREEN_ROUTE) {
        TextBlocksScreen(
            onBackClick = onBackClick,
            onHeaderClick = onHeaderClick,
            onAccordionClick = onAccordionClick,
            onParagraphClick = onParagraphClick,
            onLinkClick = onLinkClick,
            onPaddingClick = onPaddingClick,
        )
    }
}

internal fun NavGraphBuilder.textBlocksHeaderScreen(
    onBackClick: () -> Unit = {},
) {
    composable(route = TEXT_BLOCKS_HEADER_SCREEN_ROUTE) {
        HeaderTextBlocksScreen(onBackClick = onBackClick)
    }
}

internal fun NavGraphBuilder.textBlocksAccordionScreen(
    onBackClick: () -> Unit = {},
) {
    composable(route = TEXT_BLOCKS_ACCORDION_SCREEN_ROUTE) {
        AccordionTextBlocksScreen(onBackClick = onBackClick)
    }
}

internal fun NavGraphBuilder.textBlocksParagraphScreen(
    onBackClick: () -> Unit = {},
) {
    composable(route = TEXT_BLOCKS_PARAGRAPH_SCREEN_ROUTE) {
        ParagraphTextBlocksScreen(onBackClick = onBackClick)
    }
}

internal fun NavGraphBuilder.textBlocksLinkScreen(
    onBackClick: () -> Unit = {},
) {
    composable(route = TEXT_BLOCKS_LINK_SCREEN_ROUTE) {
        LinkTextBlocksScreen(onBackClick = onBackClick)
    }
}

internal fun NavGraphBuilder.textBlocksPaddingScreen(
    onBackClick: () -> Unit = {},
) {
    composable(route = TEXT_BLOCKS_PADDING_SCREEN_ROUTE) {
        PaddingTextBlocksScreen(onBackClick = onBackClick)
    }
}
