package com.admiral.uikit.compose.tag

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X25

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
    val textColor = if (isEnabled) color.textEnable else color.textDisable
    val backgroundColor = if (isEnabled) color.backgroundEnable else color.backgroundDisable
    var iconColor = if (isEnabled) color.iconEnable else color.iconDisable
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
                indication = rememberRipple(color = color.backgroundPressed),
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

private const val ALFA_ENABLE = 1f
private const val ALFA_DISABLE = 0.6f

@Preview
@Composable
private fun TagPreview() {
    val emoji = "\uD83D\uDC36"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Row(
            modifier = Modifier
                .padding(horizontal = DIMEN_X2, vertical = DIMEN_X2)
                .horizontalScroll(rememberScrollState())
        ) {
            Tag(text = "Sample")
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "$emoji Sample",
                icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                iconPosition = TagIconPosition.LEFT,
                color = AdmiralTagColor.gray(),
                isEnabled = false,
                isIconColored = true,
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample",
                icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                iconPosition = TagIconPosition.LEFT,
                color = AdmiralTagColor.orange(),
                isIconColored = false,
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(text = "Sample", color = AdmiralTagColor.gray(), isEnabled = false)
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample",
                icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                iconPosition = TagIconPosition.LEFT,
                color = AdmiralTagColor.red(),
                isEnabled = false,
                isIconColored = false,
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(text = "Sample", color = AdmiralTagColor.red())
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample $emoji",
                icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                iconPosition = TagIconPosition.LEFT,
                isEnabled = true,
                isIconColored = false,
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(text = "Sample", color = AdmiralTagColor.orange())
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample",
                icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                iconPosition = TagIconPosition.LEFT,
                color = AdmiralTagColor.green(),
                isIconColored = true,
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(text = "Sample", color = AdmiralTagColor.green(), isEnabled = false)
        }
        Row(
            modifier = Modifier
                .padding(horizontal = DIMEN_X2, vertical = DIMEN_X2)
                .horizontalScroll(rememberScrollState())
        ) {
            Tag(text = "Sample", size = AdmiralTagSize.medium())
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "$emoji Sample",
                icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                iconPosition = TagIconPosition.LEFT,
                size = AdmiralTagSize.medium(),
                color = AdmiralTagColor.gray(),
                isEnabled = false,
                isIconColored = true,
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample",
                icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                iconPosition = TagIconPosition.LEFT,
                color = AdmiralTagColor.orange(),
                isIconColored = false,
                size = AdmiralTagSize.medium(),
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample", color = AdmiralTagColor.gray(),
                size = AdmiralTagSize.medium(),
                isEnabled = false
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample",
                icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                iconPosition = TagIconPosition.LEFT,
                color = AdmiralTagColor.red(),
                isEnabled = false,
                isIconColored = false,
                size = AdmiralTagSize.medium(),
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample", color = AdmiralTagColor.red(),
                size = AdmiralTagSize.medium(),
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample $emoji",
                icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                iconPosition = TagIconPosition.LEFT,
                isEnabled = true,
                isIconColored = false,
                size = AdmiralTagSize.medium(),
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample",
                size = AdmiralTagSize.medium(),
                color = AdmiralTagColor.orange()
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample",
                icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                iconPosition = TagIconPosition.LEFT,
                color = AdmiralTagColor.green(),
                size = AdmiralTagSize.medium(),
                isIconColored = true,
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample",
                color = AdmiralTagColor.green(),
                size = AdmiralTagSize.medium(),
                isEnabled = false
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = DIMEN_X2, vertical = DIMEN_X2)
                .horizontalScroll(rememberScrollState())
        ) {
            Tag(text = "Sample", size = AdmiralTagSize.small())
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "$emoji Sample",
                icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                iconPosition = TagIconPosition.LEFT,
                size = AdmiralTagSize.small(),
                color = AdmiralTagColor.gray(),
                isEnabled = false,
                isIconColored = true,
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample",
                icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                iconPosition = TagIconPosition.LEFT,
                color = AdmiralTagColor.orange(),
                isIconColored = false,
                size = AdmiralTagSize.small(),
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample", color = AdmiralTagColor.gray(),
                size = AdmiralTagSize.small(),
                isEnabled = false
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample",
                icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                iconPosition = TagIconPosition.LEFT,
                color = AdmiralTagColor.red(),
                isEnabled = false,
                isIconColored = false,
                size = AdmiralTagSize.small(),
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample", color = AdmiralTagColor.red(),
                size = AdmiralTagSize.small(),
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample $emoji",
                icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                iconPosition = TagIconPosition.LEFT,
                isEnabled = true,
                isIconColored = false,
                size = AdmiralTagSize.small(),
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample",
                size = AdmiralTagSize.small(),
                color = AdmiralTagColor.orange()
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample",
                icon = painterResource(id = R.drawable.admiral_ic_car_solid),
                iconPosition = TagIconPosition.LEFT,
                color = AdmiralTagColor.green(),
                size = AdmiralTagSize.small(),
                isIconColored = true,
            )
            Spacer(modifier = Modifier.size(DIMEN_X2))
            Tag(
                text = "Sample",
                color = AdmiralTagColor.green(),
                size = AdmiralTagSize.small(),
                isEnabled = false
            )
        }
    }
}