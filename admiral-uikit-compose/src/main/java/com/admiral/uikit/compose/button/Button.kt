package com.admiral.uikit.compose.button

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.button.AdmiralButtonColor.primary
import com.admiral.uikit.compose.button.AdmiralButtonSize.big
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X12
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X4

@Composable
@Suppress("LongParameterList")
fun Button(
    modifier: Modifier = Modifier,
    actionText: String? = null,
    additionalText: String? = null,
    iconStart: Painter? = null,
    iconEnd: Painter? = null,
    color: ButtonColor = primary(),
    size: ButtonSize = big(),
    radius: Dp = DIMEN_X2,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit = {},
) {
    val typography = ThemeManagerCompose.typography
    val roundedCornerShape = RoundedCornerShape(radius)

    val horizontalArrangement = remember {
        if (additionalText != null && actionText != null) Arrangement.SpaceBetween
        else Arrangement.Center
    }

    val iconColor = if (isEnabled) color.iconTintEnable else color.iconTintDisable
    val textColor = if (isEnabled) color.textColorEnable else color.textColorDisable
    val backgroundColor = if (isEnabled) color.backgroundEnable else color.backgroundDisable
    val actionTextPadding = if (additionalText != null) DIMEN_X1 else size.padding

    Row(
        modifier = modifier
            .height(DIMEN_X12)
            .widthIn(min = size.defaultWidth)
            .border(
                width = BORDER_WIDTH.dp,
                color = color.borderColor,
                shape = roundedCornerShape
            )
            .padding(SPACE_BETWEEN_BORDER_WIDTH.dp)
            .background(
                color = backgroundColor,
                shape = roundedCornerShape
            )
            .clickable(
                onClick = onClick,
                indication = rememberRipple(
                    bounded = true,
                    color = color.backgroundPressed
                ),
                interactionSource = remember {
                    MutableInteractionSource()
                }
            )
            .padding(DIMEN_X2),
        horizontalArrangement = horizontalArrangement,
    ) {

        additionalText?.let {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = DIMEN_X1),
                text = it,
                textAlign = TextAlign.Start,
                color = textColor,
                style = typography.body1
            )
        }

        iconStart?.let {
            Icon(
                modifier = Modifier
                    .height(size.iconHeight)
                    .align(Alignment.CenterVertically)
                    .padding(start = size.padding),
                painter = it,
                contentDescription = null,
                tint = iconColor
            )
        }

        actionText?.let {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = size.padding, end = actionTextPadding),
                text = actionText,
                textAlign = TextAlign.End,
                color = textColor,
                style = typography.body1
            )
        }

        iconEnd?.let {
            Icon(
                modifier = Modifier
                    .height(size.iconHeight)
                    .align(Alignment.CenterVertically)
                    .padding(end = size.padding),
                painter = it,
                contentDescription = null,
                tint = iconColor
            )
        }
    }
}

private const val BORDER_WIDTH = 2
private const val SPACE_BETWEEN_BORDER_WIDTH = BORDER_WIDTH / 2

@Preview(showBackground = false)
@Composable
fun PrimaryButtonPreview() {
    var isLoading by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(ThemeManagerCompose.theme.value.palette.staticBlack))
            .verticalScroll(ScrollState(0))
            .padding(vertical = DIMEN_X4, horizontal = DIMEN_X2),
    ) {
        Button(actionText = "Выбрать",
            additionalText = "08.06.20 — 14.08.20",
            isLoading = isLoading,
            onClick = {
                isLoading = isLoading.not()
            })
        Spacer(modifier = Modifier.size(DIMEN_X4))
        Button(
            actionText = "Big Button",
            iconStart = painterResource(id = R.drawable.admiral_ic_heart_outline)
        )
        Spacer(modifier = Modifier.size(DIMEN_X4))
        Button(
            actionText = "Big Button",
            iconEnd = painterResource(id = R.drawable.admiral_ic_maintenance_solid)
        )
        Spacer(modifier = Modifier.size(DIMEN_X4))
        Button(actionText = "Big Button")
        Spacer(modifier = Modifier.size(DIMEN_X4))
        Button(
            size = AdmiralButtonSize.medium(),
            actionText = "Medium Button",
            iconStart = painterResource(id = R.drawable.admiral_ic_heart_outline)
        )
        Spacer(modifier = Modifier.size(DIMEN_X4))
        Button(
            size = AdmiralButtonSize.medium(),
            actionText = "Medium Button",
            iconEnd = painterResource(id = R.drawable.admiral_ic_maintenance_solid)
        )
        Spacer(modifier = Modifier.size(DIMEN_X4))
        Button(
            size = AdmiralButtonSize.medium(),
            actionText = "Medium Button",
        )
        Spacer(modifier = Modifier.size(DIMEN_X4))
        Button(
            size = AdmiralButtonSize.small(),
            actionText = "Small Button",
            iconStart = painterResource(id = R.drawable.admiral_ic_heart_outline)
        )
        Spacer(modifier = Modifier.size(DIMEN_X4))
        Button(
            size = AdmiralButtonSize.small(),
            actionText = "Small Button",
            iconEnd = painterResource(id = R.drawable.admiral_ic_maintenance_solid)
        )
        Spacer(modifier = Modifier.size(DIMEN_X4))
        Button(
            size = AdmiralButtonSize.small(),
            actionText = "Small Button",
        )
        Spacer(modifier = Modifier.size(DIMEN_X4))
    }
}