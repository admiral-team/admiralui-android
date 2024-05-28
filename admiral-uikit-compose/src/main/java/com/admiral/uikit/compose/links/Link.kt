package com.admiral.uikit.compose.links

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.core.components.link.LinkSize

@Composable
@Suppress("LongParameterList", "LongMethod")
fun Link(
    modifier: Modifier = Modifier,
    linkSize: LinkSize = LinkSize.SMALL,
    isEnable: Boolean = true,
    linkText: String? = null,
    textEnableColor: Color = AdmiralTheme.colors.textAccent,
    textDisableColor: Color = AdmiralTheme.colors.textAccent.withAlpha(),
    drawableEnableColorTint: Color = AdmiralTheme.colors.elementAccent,
    drawableDisableColorTint: Color = AdmiralTheme.colors.elementAccent.withAlpha(),
    pressedColor: Color = AdmiralTheme.colors.elementAccentPressed,
    linkTextStyle: TextStyle? = null,
    iconStart: Painter? = null,
    iconEnd: Painter? = null,
    onClick: () -> Unit = {},
) {
    val textStyle =
        linkTextStyle
            ?: if (linkSize != LinkSize.SMALL) AdmiralTheme.typography.body2 else AdmiralTheme.typography.subhead3
    val iconSize = if (linkSize != LinkSize.SMALL) ICON_BIG_SIZE.dp else ICON_SMALL_SIZE.dp
    val textColor = if (isEnable) textEnableColor else textDisableColor
    val iconColor = if (isEnable) drawableEnableColorTint else drawableDisableColorTint

    ConstraintLayout(
        modifier = modifier
            .clickable(
                onClick = onClick,
                enabled = isEnable,
                indication = rememberRipple(color = pressedColor),
                interactionSource = remember { MutableInteractionSource() }
            )
    ) {

        val (
            iconStartId,
            linkTextId,
            iconEndId,
        ) = createRefs()

        iconStart?.let {
            Icon(
                modifier = Modifier
                    .padding(end = DIMEN_X2)
                    .size(iconSize)
                    .constrainAs(iconStartId) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                painter = it,
                contentDescription = null,
                tint = iconColor
            )
        }

        linkText?.let {
            Text(
                modifier = Modifier
                    .constrainAs(linkTextId) {
                        start.linkTo(iconStartId.end)
                        end.linkTo(iconEndId.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.preferredWrapContent
                        height = Dimension.wrapContent
                    },
                text = it,
                color = textColor,
                style = textStyle,
            )
        }

        iconEnd?.let {
            Icon(
                modifier = Modifier
                    .padding(start = DIMEN_X2)
                    .size(iconSize)
                    .constrainAs(iconEndId) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                    },
                painter = it,
                contentDescription = null,
                tint = iconColor
            )
        }
    }
}

private const val ICON_BIG_SIZE = 24
private const val ICON_SMALL_SIZE = 18

@Preview
@Composable
private fun LinkPreview() {
    AdmiralTheme {
        Surface(
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            Column {
                Link(
                    linkText = "Link",
                    iconStart = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
                    iconEnd = painterResource(id = R.drawable.admiral_ic_arrow_right_outline),
                    isEnable = true,
                    linkSize = LinkSize.BIG
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                Link(
                    linkText = "Link",
                    iconStart = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
                    iconEnd = painterResource(id = R.drawable.admiral_ic_arrow_right_outline),
                    isEnable = true,
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                Link(
                    linkText = "Link",
                    isEnable = true,
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                Link(
                    linkText = "Link",
                    isEnable = true,
                    linkSize = LinkSize.BIG
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                Link(
                    linkText = "Link",
                    iconStart = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
                    iconEnd = painterResource(id = R.drawable.admiral_ic_arrow_right_outline),
                    isEnable = false,
                    linkSize = LinkSize.BIG
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                Link(
                    linkText = "Link",
                    iconStart = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
                    iconEnd = painterResource(id = R.drawable.admiral_ic_arrow_right_outline),
                    isEnable = false,
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                Link(
                    linkText = "Link",
                    isEnable = false,
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                Link(
                    linkText = "Link",
                    isEnable = false,
                    linkSize = LinkSize.BIG
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                Link(
                    iconStart = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
                    iconEnd = painterResource(id = R.drawable.admiral_ic_arrow_right_outline),
                    linkText = "LinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLink",
                    linkSize = LinkSize.BIG
                )
            }
        }
    }
}
