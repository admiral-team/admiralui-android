package com.admiral.uikit.compose.notifications.fixed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.links.Link
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.core.components.notification.StaticNotificationStyle
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.resources.R as res

@Composable
fun StaticNotification(
    modifier: Modifier = Modifier,
    backgroundColors: ColorState? = null,
    notificationTextColors: ColorState? = null,
    notificationText: String? = null,
    linkText: String? = null,
    linkColors: ColorState? = null,
    icon: Painter? = null,
    iconColors: ColorState? = null,
    notificationStyle: StaticNotificationStyle = StaticNotificationStyle.Info,
    ellipsize: TextOverflow = TextOverflow.Visible,
    maxLines: Int = Int.MAX_VALUE,
    isBackgroundColorDefault: Boolean = false,
    isIconVisible: Boolean = true,
    isCloseIconVisible: Boolean = true,
    isEnable: Boolean = true,
    onLinkClick: () -> Unit = {},
    onCloseIconClick: () -> Unit = {},
) {

    val theme = ThemeManagerCompose.theme.value
    val typography = ThemeManagerCompose.typography
    val palette = theme.palette

    var backgroundColor = when (notificationStyle) {
        StaticNotificationStyle.Info -> palette.backgroundSelected
        StaticNotificationStyle.Attention -> palette.backgroundAttention
        StaticNotificationStyle.Success -> palette.backgroundSuccess
        StaticNotificationStyle.Error -> palette.backgroundError
    }

    if (isBackgroundColorDefault) {
        backgroundColor = palette.backgroundAdditionalOne
    }

    backgroundColor =
        if (isEnable) backgroundColors?.normalEnabled ?: backgroundColor
        else backgroundColors?.normalDisabled ?: backgroundColor

    val notificationTextColor =
        if (isEnable) notificationTextColors?.normalEnabled ?: palette.textPrimary
        else notificationTextColors?.normalDisabled ?: palette.textPrimary.withAlpha()

    val linkTextColor = ColorState(
        normalEnabled = linkColors?.normalEnabled ?: palette.textAccent,
        normalDisabled = linkColors?.normalDisabled ?: palette.textAccent.withAlpha(),
        pressed = linkColors?.pressed ?: palette.textAccentPressed
    )

    val closeIcon = painterResource(id = res.drawable.admiral_ic_close_outline)

    val iconColorDefault =
        when (notificationStyle) {
            StaticNotificationStyle.Info -> palette.elementAccent
            StaticNotificationStyle.Attention -> palette.elementAttention
            StaticNotificationStyle.Success -> palette.elementSuccess
            StaticNotificationStyle.Error -> palette.elementError
        }

    val iconDefault = painterResource(
        when (notificationStyle) {
            StaticNotificationStyle.Info -> (res.drawable.admiral_ic_info_solid)
            StaticNotificationStyle.Attention -> (res.drawable.admiral_ic_error_triangle_solid)
            StaticNotificationStyle.Success -> (res.drawable.admiral_ic_check_solid)
            StaticNotificationStyle.Error -> (res.drawable.admiral_ic_close_circle_solid)
        }
    )

    val iconColor = if (isEnable) iconColors?.normalEnabled ?: iconColorDefault
    else iconColors?.normalDisabled ?: iconColorDefault.withAlpha()

    val iconCloseColor = if (isEnable) palette.elementPrimary
    else palette.elementPrimary.withAlpha()

    Card(
        modifier = modifier
            .clip(shape = RoundedCornerShape(DIMEN_X2)),
        backgroundColor = Color(backgroundColor),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(vertical = DIMEN_X3, horizontal = DIMEN_X4)
        ) {

            val (
                iconId,
                notificationTextId,
                linkTextId,
                closeIconId,
                columnId,
            ) = createRefs()

            if (isIconVisible) {
                Icon(
                    painter = icon ?: iconDefault,
                    contentDescription = null,
                    tint = Color(iconColor),
                    modifier = Modifier
                        .size(ICON_SIZE.dp)
                        .constrainAs(iconId) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(columnId) {
                        start.linkTo(
                            if (isIconVisible) iconId.end else parent.start,
                            margin = DIMEN_X3
                        )
                        end.linkTo(
                            if (isCloseIconVisible) closeIconId.start else parent.end,
                            margin = DIMEN_X3
                        )
                        width = Dimension.preferredWrapContent
                    },
            ) {
                notificationText?.let {
                    Text(
                        text = it,
                        maxLines = maxLines,
                        overflow = ellipsize,
                        color = Color(notificationTextColor),
                        style = typography.body2,
                        modifier = Modifier.wrapContentWidth(),
                    )
                }

                linkText?.let {
                    Spacer(modifier = Modifier.height(DIMEN_X2))

                    Link(
                        linkText = it,
                        textColorState = linkTextColor,
                        onClick = onLinkClick,
                        isEnable = isEnable,
                    )
                }
            }

            if (isCloseIconVisible) {
                Icon(
                    painter = closeIcon,
                    contentDescription = null,
                    tint = Color(iconCloseColor),
                    modifier = Modifier
                        .size(ICON_SIZE.dp)
                        .constrainAs(closeIconId) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        }
                        .clickable(
                            enabled = isEnable,
                            onClick = onCloseIconClick,
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                )
            }
        }
    }
}

private const val ICON_SIZE = 28

@Preview
@Composable
fun StaticNotificationErrorPreview() {
    StaticNotification(
        notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
        linkText = "Link",
        notificationStyle = StaticNotificationStyle.Error,
        isEnable = true
    )
}

@Preview
@Composable
fun StaticNotificationErrorDisablePreview() {
    StaticNotification(
        notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
        linkText = "Link",
        notificationStyle = StaticNotificationStyle.Error,
        isEnable = false
    )
}

@Preview
@Composable
fun StaticNotificationAttentionPreview() {
    StaticNotification(
        notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
        linkText = "Link",
        notificationStyle = StaticNotificationStyle.Attention,
        isEnable = true
    )
}

@Preview
@Composable
fun StaticNotificationAttentionDisablePreview() {
    StaticNotification(
        notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
        linkText = "Link",
        notificationStyle = StaticNotificationStyle.Attention,
        isEnable = false
    )
}

@Preview
@Composable
fun StaticNotificationInfoPreview() {
    StaticNotification(
        notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
        linkText = "Link",
        notificationStyle = StaticNotificationStyle.Info,
        isEnable = true
    )
}

@Preview
@Composable
fun StaticNotificationInfoDisablePreview() {
    StaticNotification(
        notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
        linkText = "Link",
        notificationStyle = StaticNotificationStyle.Info,
        isEnable = false
    )
}

@Preview
@Composable
fun StaticNotificationSuccessPreview() {
    StaticNotification(
        notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
        linkText = "Link",
        notificationStyle = StaticNotificationStyle.Success,
        isEnable = true
    )
}

@Preview
@Composable
fun StaticNotificationSuccessDisablePreview() {
    StaticNotification(
        notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
        linkText = "Link",
        notificationStyle = StaticNotificationStyle.Success,
        isEnable = true
    )
}

@Preview
@Composable
fun StaticNotificationEmptyLinkPreview() {
    StaticNotification(
        notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
        icon = painterResource(id = res.drawable.admiral_ic_info_solid),
        isEnable = true
    )
}

@Preview
@Composable
fun StaticNotificationEmptyNotificationTextPreview() {
    StaticNotification(
        linkText = "Link",
        isEnable = true
    )
}

@Preview
@Composable
fun StaticNotificationIconInvisibleTextPreview() {
    StaticNotification(
        linkText = "Link",
        isEnable = true,
        isIconVisible = false,
    )
}

@Preview
@Composable
fun StaticNotificationCloseInvisibleTextPreview() {
    StaticNotification(
        linkText = "Link",
        isEnable = true,
        isIconVisible = false,
        isCloseIconVisible = false,
    )
}