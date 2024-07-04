package com.admiral.uikit.compose.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
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
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.tag.AdmiralTagColor.gray
import com.admiral.uikit.compose.tag.AdmiralTagColor.green
import com.admiral.uikit.compose.tag.AdmiralTagColor.orange
import com.admiral.uikit.compose.tag.AdmiralTagColor.red
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X25
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4

@Composable
fun Tag(
    modifier: Modifier = Modifier,
    text: String? = null,
    icon: Painter? = null,
    isIconColored: Boolean = false,
    color: TagColor = AdmiralTagColor.blue(),
    size: TagSize = AdmiralTagSize.large(),
    iconPosition: TagIconPosition = TagIconPosition.LEFT,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    val shape = RoundedCornerShape(DIMEN_X25)
    val textColor = color.getTextColor(isEnabled = isEnabled).value
    val backgroundColor = color.getBackgroundColor(isEnabled = isEnabled).value
    var iconColor = color.getIconColor(isEnabled = isEnabled).value
    iconColor = if (isIconColored) iconColor else Color.Unspecified
    val alpha = if (!isEnabled && !isIconColored) ALFA_DISABLE else ALFA_ENABLE

    Row(
        modifier = modifier
            .padding(vertical = size.paddingVertical)
            .height(size.tagHeight)
            .clip(shape)
            .background(backgroundColor)
            .clickable(
                onClick = onClick,
                enabled = isEnabled,
                indication = rememberRipple(),
                interactionSource = remember { MutableInteractionSource() }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(size.paddingHorizontal))

        if (icon != null && iconPosition == TagIconPosition.LEFT) {
            Icon(
                modifier = Modifier
                    .alpha(alpha)
                    .size(size.iconSize),
                painter = icon,
                tint = iconColor,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(DIMEN_X2))
        }

        text?.let {
            Text(
                modifier = Modifier,
                text = text,
                color = textColor,
                style = size.textStyle,
            )
        }

        if (icon != null && iconPosition == TagIconPosition.RIGHT) {
            Spacer(modifier = Modifier.width(DIMEN_X2))

            Icon(
                modifier = Modifier
                    .size(size.iconSize),
                painter = icon,
                tint = iconColor,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.width(size.paddingHorizontal))
    }
}

@Immutable
data class TagItem(
    val modifier: Modifier = Modifier,
    val text: String? = null,
    val icon: Painter? = null,
    val isIconColored: Boolean = false,
    val color: TagColor? = null,
    val size: TagSize? = null,
    val iconPosition: TagIconPosition = TagIconPosition.LEFT,
    val isEnabled: Boolean = true,
    val onClick: () -> Unit = {}
)

@Composable
fun TagGroup(
    modifier: Modifier = Modifier,
    tags: List<TagItem> = emptyList(),
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(DIMEN_X3)
    ) {
        tags.forEach {
            item {
                Tag(
                    modifier = it.modifier,
                    text = it.text,
                    icon = it.icon,
                    isIconColored = it.isIconColored,
                    color = it.color ?: AdmiralTagColor.blue(),
                    size = it.size ?: AdmiralTagSize.large(),
                    iconPosition = it.iconPosition,
                    isEnabled = it.isEnabled,
                    onClick = it.onClick
                )
            }
        }
    }
}

private const val ALFA_ENABLE = 1f
private const val ALFA_DISABLE = 0.6f

@Preview
@Composable
private fun TagPreview() {
    val emoji = "\uD83D\uDC36"
    val carIcon = painterResource(id = R.drawable.admiral_ic_car_solid)
    AdmiralTheme {
        Surface(
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = DIMEN_X4)
                    .fillMaxSize()
            ) {
                TagGroup(
                    tags = listOf(
                        TagItem(
                            text = "$emoji Sample",
                            icon = carIcon,
                            iconPosition = TagIconPosition.LEFT,
                            color = gray(),
                            isEnabled = false,
                            isIconColored = true,
                        ),
                        TagItem(text = "Sample"),
                        TagItem(
                            text = "Sample",
                            icon = carIcon,
                            iconPosition = TagIconPosition.LEFT,
                            color = orange(),
                            isIconColored = false,
                        ),
                        TagItem(text = "Sample", color = gray(), isEnabled = false),
                        TagItem(
                            text = "Sample",
                            icon = carIcon,
                            iconPosition = TagIconPosition.LEFT,
                            color = red(),
                            isEnabled = false,
                            isIconColored = false,
                        ),
                        TagItem(text = "Sample", color = red()),
                        TagItem(
                            text = "Sample $emoji",
                            icon = carIcon,
                            iconPosition = TagIconPosition.LEFT,
                            isEnabled = true,
                            isIconColored = false,
                        ),
                        TagItem(text = "Sample", color = orange()),
                        TagItem(
                            text = "Sample",
                            icon = carIcon,
                            iconPosition = TagIconPosition.LEFT,
                            color = green(),
                            isIconColored = true,
                        ),
                        TagItem(text = "Sample", color = green(), isEnabled = false),
                    )
                )

                TagGroup(
                    tags = listOf(
                        TagItem(text = "Sample", size = AdmiralTagSize.medium()),
                        TagItem(
                            text = "$emoji Sample",
                            icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                            iconPosition = TagIconPosition.LEFT,
                            size = AdmiralTagSize.medium(),
                            color = gray(),
                            isEnabled = false,
                            isIconColored = true,
                        ),
                        TagItem(
                            text = "Sample",
                            icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                            iconPosition = TagIconPosition.LEFT,
                            color = orange(),
                            isIconColored = false,
                            size = AdmiralTagSize.medium(),
                        ),
                        TagItem(
                            text = "Sample", color = gray(),
                            size = AdmiralTagSize.medium(),
                            isEnabled = false
                        ),
                        TagItem(
                            text = "Sample",
                            icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                            iconPosition = TagIconPosition.LEFT,
                            color = red(),
                            isEnabled = false,
                            isIconColored = false,
                            size = AdmiralTagSize.medium(),
                        ),
                        TagItem(
                            text = "Sample", color = red(),
                            size = AdmiralTagSize.medium(),
                        ),
                        TagItem(
                            text = "Sample $emoji",
                            icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                            iconPosition = TagIconPosition.LEFT,
                            isEnabled = true,
                            isIconColored = false,
                            size = AdmiralTagSize.medium(),
                        ),
                        TagItem(
                            text = "Sample",
                            size = AdmiralTagSize.medium(),
                            color = orange()
                        ),
                        TagItem(
                            text = "Sample",
                            icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                            iconPosition = TagIconPosition.LEFT,
                            color = green(),
                            size = AdmiralTagSize.medium(),
                            isIconColored = true,
                        ),
                        TagItem(
                            text = "Sample",
                            color = green(),
                            size = AdmiralTagSize.medium(),
                            isEnabled = false
                        ),
                    )
                )

                TagGroup(
                    tags = listOf(
                        TagItem(text = "Sample", size = AdmiralTagSize.small()),
                        TagItem(
                            text = "$emoji Sample",
                            icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                            iconPosition = TagIconPosition.LEFT,
                            size = AdmiralTagSize.small(),
                            color = gray(),
                            isEnabled = false,
                            isIconColored = true,
                        ),
                        TagItem(
                            text = "Sample",
                            icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                            iconPosition = TagIconPosition.LEFT,
                            color = orange(),
                            isIconColored = false,
                            size = AdmiralTagSize.small(),
                        ),
                        TagItem(
                            text = "Sample", color = gray(),
                            size = AdmiralTagSize.small(),
                            isEnabled = false
                        ),
                        TagItem(
                            text = "Sample",
                            icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                            iconPosition = TagIconPosition.LEFT,
                            color = red(),
                            isEnabled = false,
                            isIconColored = false,
                            size = AdmiralTagSize.small(),
                        ),
                        TagItem(
                            text = "Sample", color = red(),
                            size = AdmiralTagSize.small(),
                        ),
                        TagItem(
                            text = "Sample $emoji",
                            icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                            iconPosition = TagIconPosition.LEFT,
                            isEnabled = true,
                            isIconColored = false,
                            size = AdmiralTagSize.small(),
                        ),
                        TagItem(
                            text = "Sample",
                            size = AdmiralTagSize.small(),
                            color = orange()
                        ),
                        TagItem(
                            text = "Sample",
                            icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                            iconPosition = TagIconPosition.LEFT,
                            color = green(),
                            size = AdmiralTagSize.small(),
                            isIconColored = true,
                        ),
                        TagItem(
                            text = "Sample",
                            color = green(),
                            size = AdmiralTagSize.small(),
                            isEnabled = false
                        ),
                    )
                )
            }
        }
    }
}