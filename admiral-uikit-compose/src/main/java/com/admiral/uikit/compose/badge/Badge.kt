package com.admiral.uikit.compose.badge

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X4

private const val BADGE_LAYOUT_ID = "BADGE_LAYOUT_ID"
private const val ANCHOR_LAYOUT_ID = "ANCHOR_LAYOUT_ID"
private const val MAX_LINES = 1
private val BorderWidth = 2.dp
private val SpaceBetweenBorder = 1.dp
private val BadgeRadius = 5.dp

@Composable
fun BadgedBox(
    modifier: Modifier = Modifier,
    content: Int? = null,
    color: BadgeColor = AdmiralBadgeColor.normal(),
    position: BadgePosition = AdmiralBadgePosition.default(),
    isEnable: Boolean = true,
    anchor: @Composable BoxScope.() -> Unit,
) {
    Layout(
        {
            Box(
                modifier = Modifier.layoutId(ANCHOR_LAYOUT_ID),
                contentAlignment = Alignment.Center,
                content = anchor
            )
            Badge(
                modifier = Modifier.layoutId(BADGE_LAYOUT_ID),
                content = content,
                color = color,
                isEnable = isEnable
            )
        },
        modifier = modifier
    ) { measurables, constraints ->

        val badgePlaceable = measurables.first { it.layoutId == BADGE_LAYOUT_ID }.measure(
            // Measure with loose constraints for height as we don't want the text to take up more
            // space than it needs.
            constraints.copy(minHeight = 0)
        )

        val anchorPlaceable =
            measurables.first { it.layoutId == ANCHOR_LAYOUT_ID }.measure(constraints)

        val firstBaseline = anchorPlaceable[FirstBaseline]
        val lastBaseline = anchorPlaceable[LastBaseline]
        val totalWidth = anchorPlaceable.width
        val totalHeight = anchorPlaceable.height

        layout(
            totalWidth,
            totalHeight,
            // Provide custom baselines based only on the anchor content to avoid default baseline
            // calculations from including by any badge content.
            mapOf(
                FirstBaseline to firstBaseline,
                LastBaseline to lastBaseline
            )
        ) {
            // Use the width of the badge to infer whether it has any content (based on radius used
            // in [Badge]) and determine its horizontal offset.
            val hasContent = badgePlaceable.width > (2 * BadgeRadius.roundToPx())
            val badgeHorizontalOffset =
                if (hasContent) position.badgeWithContentHorizontalOffset else position.badgeHorizontalOffset
            val badgeVerticalOffset =
                if (hasContent) position.badgeVerticalWithContentOffset else position.badgeVerticalOffset

            anchorPlaceable.placeRelative(0, 0)
            val badgeX = anchorPlaceable.width + badgeHorizontalOffset.roundToPx()
            val badgeY = -badgePlaceable.height - badgeVerticalOffset.roundToPx()
            badgePlaceable.placeRelative(badgeX, badgeY)
        }
    }
}


@OptIn(ExperimentalTextApi::class)
@Composable
private fun Badge(
    modifier: Modifier = Modifier,
    content: Int?,
    color: BadgeColor,
    isEnable: Boolean,
) {
    val radius = if (content != null) DIMEN_X2 else BadgeRadius
    val shape = RoundedCornerShape(radius)
    val backgroundColor =
        if (isEnable) color.backgroundColorNormal else color.backgroundColorDisable
    val contentColor = if (isEnable) color.contentColorEnable else color.contentColorDisable
    val textStyle = ThemeManagerCompose.typography.caption2

    Row(
        modifier = modifier
            .defaultMinSize(minWidth = radius * 2, minHeight = radius * 2)
            .border(BorderWidth, color = color.borderColor, shape = shape)
            .padding(SpaceBetweenBorder)
            .background(color = backgroundColor, shape = shape),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (content != null) {
            Text(
                modifier = Modifier.padding(horizontal = DIMEN_X1 - SpaceBetweenBorder),
                text = content.toString(),
                color = contentColor,
                style = textStyle.copy(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                maxLines = MAX_LINES,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun IconWithBackground() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(DIMEN_X1))
            .background(Color.Gray)
    ) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = "Favorite"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BadgePreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(ThemeManagerCompose.theme.value.palette.backgroundBasic))
            .verticalScroll(ScrollState(0))
            .padding(vertical = DIMEN_X4, horizontal = DIMEN_X2),
    ) {
        Row {
            BadgedBox { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(color = AdmiralBadgeColor.error()) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(color = AdmiralBadgeColor.attention()) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(color = AdmiralBadgeColor.success()) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(color = AdmiralBadgeColor.neutral()) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(color = AdmiralBadgeColor.additional()) { IconWithBackground() }
        }
        Spacer(modifier = Modifier.size(DIMEN_X4))

        Row {
            BadgedBox(content = 9) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(content = 9, color = AdmiralBadgeColor.error()) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(content = 9, color = AdmiralBadgeColor.attention()) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(content = 9, color = AdmiralBadgeColor.success()) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(content = 9, color = AdmiralBadgeColor.neutral()) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(content = 9, color = AdmiralBadgeColor.additional()) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))
        }
        Spacer(modifier = Modifier.size(DIMEN_X4))

        Row {
            BadgedBox(content = 9, isEnable = false) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(
                content = 9,
                isEnable = false,
                color = AdmiralBadgeColor.error()
            ) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(
                content = 9,
                isEnable = false,
                color = AdmiralBadgeColor.attention()
            ) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(
                content = 9,
                isEnable = false,
                color = AdmiralBadgeColor.success()
            ) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(
                content = 9,
                isEnable = false,
                color = AdmiralBadgeColor.neutral()
            ) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(
                content = 9,
                isEnable = false,
                color = AdmiralBadgeColor.additional()
            ) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))
        }
        Spacer(modifier = Modifier.size(DIMEN_X4))

        Row {
            BadgedBox(content = 111) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(content = 111, color = AdmiralBadgeColor.error()) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(content = 111, color = AdmiralBadgeColor.attention()) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(content = 111, color = AdmiralBadgeColor.success()) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(content = 111, color = AdmiralBadgeColor.neutral()) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))

            BadgedBox(
                content = 111,
                color = AdmiralBadgeColor.additional()
            ) { IconWithBackground() }
            Spacer(modifier = Modifier.size(DIMEN_X4))
        }
        Spacer(modifier = Modifier.size(DIMEN_X4))
    }
}