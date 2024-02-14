package com.admiral.uikit.compose.badge

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class BadgePosition(
    val badgeVerticalOffset: Dp,
    val badgeVerticalWithContentOffset: Dp,
    val badgeHorizontalOffset: Dp,
    val badgeWithContentHorizontalOffset: Dp,
)

object AdmiralBadgePosition {
    @Composable
    fun standard(
        badgeVerticalOffset: Dp = VERTICAL_OFFSET.dp,
        badgeVerticalWithContentOffset: Dp = VERTICAL_OFFSET_WITH_CONTENT.dp,
        badgeHorizontalOffset: Dp = HORIZONTAL_OFFSET.dp,
        badgeWithContentHorizontalOffset: Dp = HORIZONTAL_OFFSET_WITH_CONTENT.dp,
    ): BadgePosition = remember(
        badgeVerticalOffset,
        badgeVerticalWithContentOffset,
        badgeHorizontalOffset,
        badgeWithContentHorizontalOffset
    ) {
        BadgePosition(
            badgeVerticalOffset = badgeVerticalOffset,
            badgeVerticalWithContentOffset = badgeVerticalWithContentOffset,
            badgeHorizontalOffset = badgeHorizontalOffset,
            badgeWithContentHorizontalOffset = badgeWithContentHorizontalOffset,
        )
    }
}

private const val VERTICAL_OFFSET = -4
private const val VERTICAL_OFFSET_WITH_CONTENT = -6
private const val HORIZONTAL_OFFSET = -4
private const val HORIZONTAL_OFFSET_WITH_CONTENT = -6