package com.admiral.uikit.compose.badge

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.ThemeManagerCompose

@Composable
fun BadgedBox(
    modifier: Modifier = Modifier,
    content: Int? = null,
    color: BadgeColor = normal(),
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
                if (hasContent) BadgeWithContentHorizontalOffset else BadgeHorizontalOffset

            anchorPlaceable.placeRelative(0, 0)
            val badgeX = anchorPlaceable.width + badgeHorizontalOffset.roundToPx()
            val badgeY = -badgePlaceable.height / 2
            badgePlaceable.placeRelative(badgeX, badgeY)
        }
    }
}

@Composable
private fun Badge(
    modifier: Modifier = Modifier,
    content: Int?,
    color: BadgeColor,
    isEnable: Boolean,
) {
    val radius = if (content != null) BadgeWithContentRadius else BadgeRadius
    val shape = RoundedCornerShape(radius)
    val backgroundColor =
        if (isEnable) color.backgroundColorNormal else color.backgroundColorDisable
    val contentColor = if (isEnable) color.contentColorEnable else color.contentColorDisable
    val textStyle = ThemeManagerCompose.typography.caption2

    Row(
        modifier = modifier
            .defaultMinSize(minWidth = radius * 2, minHeight = radius * 2)
            .border(borderWidth, color = color.borderColor, shape = shape)
            .padding(spaceBetweenBorder)
            .background(color = backgroundColor, shape = shape),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (content != null) {
            Text(
                modifier = Modifier.padding(horizontal = BadgeWithContentHorizontalPadding - spaceBetweenBorder),
                text = content.toString(),
                color = contentColor,
                style = textStyle,
                maxLines = 1,
                textAlign = TextAlign.Center,
            )
        }
    }
}

private const val BADGE_LAYOUT_ID = "badge"
private const val ANCHOR_LAYOUT_ID = "anchor"
private val borderWidth = 2.dp
private val spaceBetweenBorder = 1.dp
private val BadgeRadius = 4.dp
private val BadgeWithContentRadius = 8.dp
// Leading and trailing text padding when a badge is displaying text that is too long to fit in
// a circular badge, e.g. if badge number is greater than 9.
private val BadgeWithContentHorizontalPadding = 4.dp
// Horizontally align start/end of text badge 6dp from the end/start edge of its anchor
private val BadgeWithContentHorizontalOffset = (-6).dp
// Horizontally align start/end of icon only badge 4dp from the end/start edge of anchor
private val BadgeHorizontalOffset = (-4).dp

@Preview(showBackground = true)
@Composable
fun BoxBadgeSmallPreview() {
    Box(
        modifier = Modifier
            .size(50.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        val radius = 4.dp
        BadgedBox {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(radius))
                    .background(Color.Green)
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorite"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoxBadgeMedium9Preview() {
    Box(
        modifier = Modifier
            .size(50.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        val radius = 4.dp
        BadgedBox(
            content = 9
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(radius))
                    .background(Color.Green)
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorite"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoxBadgeMedium111Preview() {
    Box(
        modifier = Modifier
            .size(50.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        val radius = 4.dp
        BadgedBox(
            content = 111
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(radius))
                    .background(Color.Green)
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorite"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoxBadgeMedium123Preview() {
    Box(
        modifier = Modifier
            .size(50.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        val radius = 6.dp
        BadgedBox(
            content = 123
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(radius))
                    .background(Color.Green)
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorite"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BoxBadgeMedium123DisablePreview() {
    Box(
        modifier = Modifier
            .size(50.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        val radius = 6.dp
        BadgedBox(
            content = 123,
            isEnable = false,
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(radius))
                    .background(Color.Green)
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Favorite"
                )
            }
        }
    }
}