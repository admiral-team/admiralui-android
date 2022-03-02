package com.admiral.uikit.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.themes.ThemeManager
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.common.components.button.ButtonSize
import com.admiral.uikit.common.components.button.ButtonStyle
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState

@Preview(showBackground = false)
@Composable
fun Primary() {
    Button(
        actionText = "Выбрать",
        additionalText = "08.06.20 — 14.08.20"
    )
}

@Preview(showBackground = false)
@Composable
fun Secondary() {
    Button(
        actionText = "Выбрать",
        isEnabled = false,
        iconStart = painterResource(id = R.drawable.admiral_ic_heart_solid),
        buttonStyle = ButtonStyle.Secondary
    )
}

@Preview(showBackground = false)
@Composable
fun Ghost() {
    Button(
        actionText = "Выбрать",
        iconStart = painterResource(id = R.drawable.admiral_ic_heart_solid),
        buttonStyle = ButtonStyle.Ghost
    )
}

@Composable
@Suppress("LongParameterList")
fun Button(
    modifier: Modifier = Modifier,
    buttonStyle: ButtonStyle = ButtonStyle.Primary,
    buttonSize: ButtonSize = ButtonSize.Big,
    isEnabled: Boolean = true,
    actionText: String? = null,
    additionalText: String? = null,
    backgroundColorState: ColorState? = null,
    textColorState: ColorState? = null,
    iconColorState: ColorState? = null,
    iconStart: Painter? = null,
    iconEnd: Painter? = null,
    onClick: () -> Unit = {}
) {
    val theme = ThemeManagerCompose.theme.value

    val horizontalArrangement = remember {
        if (additionalText != null && actionText != null) Arrangement.SpaceBetween
        else Arrangement.Center
    }

    val iconColor = when (buttonStyle) {
        ButtonStyle.Primary ->
            if (isEnabled) iconColorState?.normalEnabled ?: ThemeManager.theme.palette.elementStaticWhite
            else iconColorState?.normalDisabled ?: ThemeManager.theme.palette.elementStaticWhite.withAlpha()
        ButtonStyle.Secondary, ButtonStyle.Ghost ->
            if (isEnabled) iconColorState?.normalEnabled ?: ThemeManager.theme.palette.elementAccent
            else iconColorState?.normalDisabled ?: ThemeManager.theme.palette.elementAccent.withAlpha()
    }

    val textColor = when (buttonStyle) {
        ButtonStyle.Primary ->
            if (isEnabled) textColorState?.normalEnabled ?: ThemeManager.theme.palette.textStaticWhite
            else textColorState?.normalDisabled ?: ThemeManager.theme.palette.textStaticWhite.withAlpha()
        ButtonStyle.Secondary, ButtonStyle.Ghost ->
            if (isEnabled) textColorState?.normalEnabled ?: ThemeManager.theme.palette.textAccent
            else textColorState?.normalDisabled ?: ThemeManager.theme.palette.textAccent.withAlpha()
    }

    val backgroundColor =
        if (isEnabled) backgroundColorState?.normalEnabled ?: ThemeManager.theme.palette.backgroundAccent
        else backgroundColorState?.normalDisabled ?: ThemeManager.theme.palette.backgroundAccent.withAlpha()

    val typography = ThemeManagerCompose.typography

    Surface(modifier = modifier) {
        Row(
            modifier = Modifier
                .height(BUTTON_HEIGHT.dp)
                .clip(RoundedCornerShape(BORDER_RADIUS.dp))
                .widthIn(min = dimensionResource(id = buttonSize.minWidth))
                .clickable(
                    onClick = onClick,
                    indication = rememberRipple(
                        bounded = true,
                        color = Color(theme.palette.backgroundAccentPressed)
                    ),
                    interactionSource = remember {
                        MutableInteractionSource()
                    }
                )
                .withBorder(buttonStyle, backgroundColor)
                .background(
                    color = if (buttonStyle == ButtonStyle.Primary) Color(backgroundColor) else Color.Transparent,
                    shape = RoundedCornerShape(BORDER_RADIUS.dp)
                ),
            horizontalArrangement = horizontalArrangement,
        ) {
            additionalText?.let {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = dimensionResource(id = buttonSize.padding)),
                    text = it,
                    textAlign = TextAlign.Start,
                    color = Color(textColor),
                    style = typography.body1
                )
            }

            iconStart?.let {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = ICONS_PADDING_END.dp),
                    painter = it,
                    contentDescription = null,
                    tint = Color(iconColor)
                )
            }

            actionText?.let {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = dimensionResource(id = buttonSize.padding)),
                    text = actionText,
                    textAlign = TextAlign.End,
                    color = Color(textColor),
                    style = typography.body1
                )
            }

            iconEnd?.let {
                Icon(
                    modifier = Modifier
                        .size(ICON_END_SIZE.dp)
                        .align(Alignment.CenterVertically)
                        .padding(end = ICONS_PADDING_END.dp),
                    painter = it,
                    contentDescription = null,
                    tint = Color(iconColor)
                )
            }
        }
    }
}

private fun Modifier.withBorder(
    buttonStyle: ButtonStyle,
    backgroundColor: Int
) = this.then(
    if (buttonStyle == ButtonStyle.Secondary) {
        this.border(
            width = BORDER_WIDTH.dp,
            color = Color(backgroundColor),
            shape = RoundedCornerShape(BORDER_RADIUS.dp)
        )
    } else {
        this
    }
)

private const val BORDER_RADIUS = 8
private const val BORDER_WIDTH = 2

private const val ICON_END_SIZE = 24
private const val ICONS_PADDING_END = 14

private const val BUTTON_HEIGHT = 48