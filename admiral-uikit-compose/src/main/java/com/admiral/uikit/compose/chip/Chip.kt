package com.admiral.uikit.compose.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X25
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X6
import com.admiral.uikit.compose.util.DIMEN_X7
import com.admiral.uikit.compose.util.DIMEN_X9

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    text: String? = null,
    emoji: String? = null,
    icon: Painter? = null,
    iconPosition: ChipIconPosition = ChipIconPosition.LEFT,
    emojiPosition: ChipEmojiPosition = ChipEmojiPosition.LEFT,
    color: ChipColor = AdmiralChipColor.primary(),
    isCloseIconVisible: Boolean = false,
    isIconColored: Boolean = true,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    val shape = RoundedCornerShape(DIMEN_X25)
    val textColor = color.getTextColor(isEnabled = isEnabled).value
    val backgroundColor = color.getBackgroundColor(isEnabled = isEnabled).value
    var iconColor = color.getIconColor(isEnabled = isEnabled).value
    iconColor = if (isIconColored) iconColor else Color.Unspecified
    val alpha = if (isEnabled) AlphaEnable else AlphaDisable

    Row(
        modifier = modifier
            .heightIn(min = DIMEN_X9)
            .clip(shape)
            .background(backgroundColor)
            .clickable(
                onClick = onClick,
                enabled = isEnabled,
                indication = rememberRipple(),
                interactionSource = remember { MutableInteractionSource() }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.padding(start = DIMEN_X3))

        if (icon != null && iconPosition == ChipIconPosition.LEFT) {
            Icon(
                modifier = Modifier
                    .padding(vertical = DIMEN_X1)
                    .size(DIMEN_X7)
                    .alpha(alpha),
                painter = icon,
                tint = iconColor,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(DIMEN_X2))
        }

        if (emoji != null && emojiPosition == ChipEmojiPosition.LEFT) {
            Text(
                modifier = Modifier
                    .padding(end = DIMEN_X2)
                    .padding(vertical = TextVerticalPadding),
                text = emoji,
                color = textColor,
                style = AdmiralTheme.typography.body1,
            )
        }

        text?.let {
            Text(
                modifier = Modifier
                    .padding(vertical = TextVerticalPadding),
                text = text,
                color = textColor,
                style = AdmiralTheme.typography.body1,
            )
        }

        if (emoji != null && emojiPosition == ChipEmojiPosition.RIGHT) {
            Text(
                modifier = Modifier
                    .padding(start = DIMEN_X2)
                    .padding(vertical = TextVerticalPadding),
                text = emoji,
                color = textColor,
                style = AdmiralTheme.typography.body1,
            )
        }

        if (icon != null && iconPosition == ChipIconPosition.RIGHT) {
            Spacer(modifier = Modifier.width(DIMEN_X2))

            Icon(
                modifier = Modifier
                    .padding(vertical = DIMEN_X1)
                    .size(DIMEN_X7)
                    .alpha(alpha),
                painter = icon,
                tint = iconColor,
                contentDescription = null
            )
        }

        if (isCloseIconVisible) {
            Icon(
                modifier = Modifier
                    .padding(DIMEN_X1)
                    .size(DIMEN_X7)
                    .alpha(alpha),
                painter = painterResource(id = R.drawable.admiral_ic_close_solid),
                tint = iconColor,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.padding(start = DIMEN_X3))
    }
}

@Immutable
data class ChipItem(
    val modifier: Modifier = Modifier,
    val text: String? = null,
    val emoji: String? = null,
    val icon: Painter? = null,
    val iconPosition: ChipIconPosition = ChipIconPosition.LEFT,
    val emojiPosition: ChipEmojiPosition = ChipEmojiPosition.LEFT,
    val color: ChipColor? = null,
    val isCloseIconVisible: Boolean = false,
    val isIconColored: Boolean = true,
    val isEnabled: Boolean = true,
    val onClick: () -> Unit = {}
)

@Composable
fun ChipGroup(
    modifier: Modifier = Modifier,
    chips: List<ChipItem> = emptyList(),
    isEnabled: Boolean = true
) {
    LazyRow(
        modifier = modifier,
        userScrollEnabled = isEnabled,
        horizontalArrangement = Arrangement.spacedBy(DIMEN_X3)
    ) {
        chips.forEach {
            item {
                Chip(
                    modifier = it.modifier,
                    text = it.text,
                    emoji = it.emoji,
                    icon = it.icon,
                    iconPosition = it.iconPosition,
                    emojiPosition = it.emojiPosition,
                    color = it.color ?: AdmiralChipColor.primary(),
                    isCloseIconVisible = it.isCloseIconVisible,
                    isIconColored = it.isIconColored,
                    isEnabled = isEnabled,
                    onClick = it.onClick
                )
            }
        }
    }
}

private const val AlphaEnable = 1f
private const val AlphaDisable = 0.6f
private val TextVerticalPadding = 6.dp

@Preview
@Composable
private fun ChipPreview() {
    AdmiralTheme {
        val gpsSolid = painterResource(id = R.drawable.admiral_ic_gps_solid)
        val heartEmoji = "\uD83D\uDC99"

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AdmiralTheme.colors.backgroundBasic)
                .padding(DIMEN_X4),
        ) {
            // ==================================== primary ====================================

            ChipGroup(
                chips = listOf(
                    ChipItem(text = "Text"),
                    ChipItem(text = "Text", icon = gpsSolid),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        iconPosition = ChipIconPosition.RIGHT,
                        isIconColored = true,
                    ),
                    ChipItem(
                        text = "Text",
                        isCloseIconVisible = true
                    ),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        isCloseIconVisible = true
                    ),
                )
            )

            Spacer(modifier = Modifier.padding(DIMEN_X1))

            ChipGroup(
                isEnabled = false,
                chips = listOf(
                    ChipItem(text = "Text"),
                    ChipItem(text = "Text", icon = gpsSolid),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        iconPosition = ChipIconPosition.RIGHT,
                        isIconColored = true,
                    ),
                    ChipItem(
                        text = "Text",
                        isCloseIconVisible = true
                    ),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        isCloseIconVisible = true
                    ),
                )
            )

            Spacer(modifier = Modifier.padding(DIMEN_X3))

            ChipGroup(
                chips = listOf(
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.RIGHT
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                        icon = gpsSolid,
                    ),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                        iconPosition = ChipIconPosition.RIGHT,
                        isIconColored = true,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        isCloseIconVisible = true,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        isCloseIconVisible = true,
                        iconPosition = ChipIconPosition.LEFT,
                        icon = gpsSolid,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                    ),
                )
            )

            Spacer(modifier = Modifier.padding(DIMEN_X1))

            ChipGroup(
                isEnabled = false,
                chips = listOf(
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.RIGHT
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                        icon = gpsSolid,
                    ),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                        iconPosition = ChipIconPosition.RIGHT,
                        isIconColored = true,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        isCloseIconVisible = true,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        isCloseIconVisible = true,
                        iconPosition = ChipIconPosition.LEFT,
                        icon = gpsSolid,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                    ),
                )
            )

            Spacer(modifier = Modifier.padding(DIMEN_X3))

            ChipGroup(
                chips = listOf(
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.LEFT
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.LEFT,
                        icon = gpsSolid,
                    ),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.LEFT,
                        iconPosition = ChipIconPosition.RIGHT,
                        isIconColored = true,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.LEFT,
                        isCloseIconVisible = true
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        isCloseIconVisible = true,
                        iconPosition = ChipIconPosition.LEFT,
                        icon = gpsSolid,
                        emojiPosition = ChipEmojiPosition.LEFT,
                    ),
                )
            )

            Spacer(modifier = Modifier.padding(DIMEN_X1))

            ChipGroup(
                isEnabled = false,
                chips = listOf(
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.LEFT
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.LEFT,
                        icon = gpsSolid,
                    ),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.LEFT,
                        iconPosition = ChipIconPosition.RIGHT,
                        isIconColored = true,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.LEFT,
                        isCloseIconVisible = true
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        isCloseIconVisible = true,
                        iconPosition = ChipIconPosition.LEFT,
                        icon = gpsSolid,
                        emojiPosition = ChipEmojiPosition.LEFT,
                    ),
                )
            )

            Spacer(modifier = Modifier.padding(DIMEN_X6))

            // ==================================== additional ====================================

            ChipGroup(
                chips = listOf(
                    ChipItem(
                        text = "Text",
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        iconPosition = ChipIconPosition.RIGHT,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        isCloseIconVisible = true,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        isCloseIconVisible = true,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                )
            )

            Spacer(modifier = Modifier.padding(DIMEN_X1))

            ChipGroup(
                isEnabled = false,
                chips = listOf(
                    ChipItem(
                        text = "Text",
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        iconPosition = ChipIconPosition.RIGHT,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        isCloseIconVisible = true,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        isCloseIconVisible = true,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                )
            )

            Spacer(modifier = Modifier.padding(DIMEN_X3))

            ChipGroup(
                chips = listOf(
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                        icon = gpsSolid,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                        iconPosition = ChipIconPosition.RIGHT,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        isCloseIconVisible = true,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        isCloseIconVisible = true,
                        iconPosition = ChipIconPosition.LEFT,
                        icon = gpsSolid,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                )
            )

            Spacer(modifier = Modifier.padding(DIMEN_X1))

            ChipGroup(
                isEnabled = false,
                chips = listOf(
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                        icon = gpsSolid,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                        iconPosition = ChipIconPosition.RIGHT,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        isCloseIconVisible = true,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        isCloseIconVisible = true,
                        iconPosition = ChipIconPosition.LEFT,
                        icon = gpsSolid,
                        emojiPosition = ChipEmojiPosition.RIGHT,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                )
            )

            Spacer(modifier = Modifier.padding(DIMEN_X3))

            ChipGroup(
                chips = listOf(
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.LEFT,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.LEFT,
                        icon = gpsSolid,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.LEFT,
                        iconPosition = ChipIconPosition.RIGHT,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.LEFT,
                        isCloseIconVisible = true,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        isCloseIconVisible = true,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                        iconPosition = ChipIconPosition.LEFT,
                        icon = gpsSolid,
                        emojiPosition = ChipEmojiPosition.LEFT,
                    ),
                )
            )

            Spacer(modifier = Modifier.padding(DIMEN_X1))

            ChipGroup(
                isEnabled = false,
                chips = listOf(
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.LEFT,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.LEFT,
                        icon = gpsSolid,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        icon = gpsSolid,
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.LEFT,
                        iconPosition = ChipIconPosition.RIGHT,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        emojiPosition = ChipEmojiPosition.LEFT,
                        isCloseIconVisible = true,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                    ),
                    ChipItem(
                        text = "Text",
                        emoji = heartEmoji,
                        isCloseIconVisible = true,
                        color = AdmiralChipColor.additional(),
                        isIconColored = false,
                        iconPosition = ChipIconPosition.LEFT,
                        icon = gpsSolid,
                        emojiPosition = ChipEmojiPosition.LEFT,
                    ),
                )
            )
        }
    }
}