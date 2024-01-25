package com.admiral.uikit.compose.links

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.core.components.link.LinkSize
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState

@Composable
@Suppress("LongParameterList", "LongMethod")
fun Link(
    modifier: Modifier = Modifier,
    linkSize: LinkSize = LinkSize.SMALL,
    isEnable: Boolean = true,
    linkText: String? = null,
    textColorState: ColorState? = null,
    drawableColorTint: ColorState? = null,
    iconStart: Painter? = null,
    iconEnd: Painter? = null,
    onClick: () -> Unit = {},
) {
    val theme = ThemeManagerCompose.theme.value
    val typography = ThemeManagerCompose.typography

    val textStyle =
        if (linkSize != LinkSize.SMALL) typography.body2 else typography.subhead3
    val iconSize = if (linkSize != LinkSize.SMALL) ICON_BIG_SIZE.dp else ICON_SMALL_SIZE.dp

    val iconColor = if (isEnable) drawableColorTint?.normalEnabled ?: theme.palette.elementAccent
    else drawableColorTint?.normalDisabled ?: theme.palette.elementAccent.withAlpha()

    val textColor = if (isEnable) textColorState?.normalEnabled ?: theme.palette.textAccent
    else textColorState?.normalDisabled ?: theme.palette.textAccent.withAlpha()

    ConstraintLayout(
        modifier = modifier
            .clickable(
                onClick = onClick,
                enabled = isEnable,
                indication = null,
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
                tint = Color(iconColor)
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
                color = Color(textColor),
                style = textStyle,
            )
        }

        iconEnd?.let {
            Icon(
                modifier = Modifier
                    .padding(DIMEN_X2)
                    .size(iconSize)
                    .constrainAs(iconEndId) {
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        top.linkTo(parent.top)
                    },
                painter = it,
                contentDescription = null,
                tint = Color(iconColor)
            )
        }
    }
}

private const val ICON_BIG_SIZE = 24
private const val ICON_SMALL_SIZE = 18

@Composable
@Preview(showBackground = false)
fun BigLinkPreview() {
    Link(
        linkText = "Link",
        iconStart = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
        iconEnd = painterResource(id = R.drawable.admiral_ic_arrow_right_outline),
        isEnable = true,
        linkSize = LinkSize.BIG
    )
}

@Composable
@Preview(showBackground = false)
fun SmallLinkPreview() {
    Link(
        linkText = "Link",
        iconStart = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
        iconEnd = painterResource(id = R.drawable.admiral_ic_arrow_right_outline),
        isEnable = true,
    )
}

@Composable
@Preview(showBackground = false)
fun LinkWithoutIconSmallPreview() {
    Link(
        linkText = "Link",
        isEnable = true,
    )
}

@Composable
@Preview(showBackground = false)
fun LinkWithoutIconBigPreview() {
    Link(
        linkText = "Link",
        isEnable = true,
        linkSize = LinkSize.BIG
    )
}

@Composable
@Preview(showBackground = false)
fun BigLinkDisablePreview() {
    Link(
        linkText = "Link",
        iconStart = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
        iconEnd = painterResource(id = R.drawable.admiral_ic_arrow_right_outline),
        isEnable = false,
        linkSize = LinkSize.BIG
    )
}

@Composable
@Preview(showBackground = false)
fun SmallLinkDisablePreview() {
    Link(
        linkText = "Link",
        iconStart = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
        iconEnd = painterResource(id = R.drawable.admiral_ic_arrow_right_outline),
        isEnable = false,
    )
}

@Composable
@Preview(showBackground = false)
fun LinkWithoutIconSmallDisablePreview() {
    Link(
        linkText = "Link",
        isEnable = false,
    )
}

@Composable
@Preview(showBackground = false)
fun LinkWithoutIconBigDisablePreview() {
    Link(
        linkText = "Link",
        isEnable = false,
        linkSize = LinkSize.BIG
    )
}

@Composable
@Preview(showBackground = false)
fun LinkLargeTextWithIconsPreview() {
    Link(
        iconStart = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
        iconEnd = painterResource(id = R.drawable.admiral_ic_arrow_right_outline),
        linkText = "LinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLinkLink",
        linkSize = LinkSize.BIG
    )
}
