package com.admiral.uikit.compose.notifications.fixed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.links.Link
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.core.components.notification.StaticNotificationStyle


@Composable
fun StaticNotification(
    modifier: Modifier = Modifier,
    notificationText: String? = null,
    linkText: String? = null,
    icon: Painter? = null,
    notificationStyle: StaticNotificationStyle = StaticNotificationStyle.Info,
    ellipsize: TextOverflow = TextOverflow.Visible,
    maxLines: Int = Int.MAX_VALUE,
    isBackgroundColorDefault: Boolean = false,
    isIconVisible: Boolean = true,
    isCloseIconVisible: Boolean = true,
    isEnable: Boolean = true,
    colors: StaticNotificationColor = AdmiralStaticNotificationColors.info(),
    onLinkClick: () -> Unit = {},
    onCloseIconClick: () -> Unit = {},
) {
    val backgroundColor =
        if (isBackgroundColorDefault) colors.getBackgroundEnable(isEnable).value
        else AdmiralTheme.colors.backgroundAdditionalOne

    val iconCloseColor = if (isEnable) AdmiralTheme.colors.elementPrimary
    else AdmiralTheme.colors.elementPrimary.withAlpha()

    val closeIcon = painterResource(id = R.drawable.admiral_ic_close_outline)

    val iconDefault = painterResource(
        when (notificationStyle) {
            StaticNotificationStyle.Info -> (R.drawable.admiral_ic_info_solid)
            StaticNotificationStyle.Attention -> (R.drawable.admiral_ic_error_triangle_solid)
            StaticNotificationStyle.Success -> (R.drawable.admiral_ic_check_solid)
            StaticNotificationStyle.Error -> (R.drawable.admiral_ic_close_circle_solid)
        }
    )

    Card(
        modifier = modifier
            .clip(shape = RoundedCornerShape(DIMEN_X2)),
        backgroundColor = backgroundColor,
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(vertical = DIMEN_X3, horizontal = DIMEN_X4)
        ) {

            val (
                iconId,
                closeIconId,
                notificationContainerId,
            ) = createRefs()

            if (isIconVisible) {
                Icon(
                    painter = icon ?: iconDefault,
                    contentDescription = null,
                    tint = colors.getIconColor(isEnable).value,
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
                    .constrainAs(notificationContainerId) {
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
                        color = colors.getNotificationTextColor(isEnable).value,
                        style = AdmiralTheme.typography.body2,
                        modifier = Modifier.wrapContentWidth(),
                    )
                }

                linkText?.let {
                    Spacer(modifier = Modifier.height(DIMEN_X2))

                    Link(
                        linkText = it,
                        textEnableColor = colors.linkTextEnable,
                        textDisableColor = colors.linkTextDisable,
                        onClick = onLinkClick,
                        isEnabled = isEnable,
                    )
                }
            }

            if (isCloseIconVisible) {
                Icon(
                    painter = closeIcon,
                    contentDescription = null,
                    tint = iconCloseColor,
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
private fun StaticNotificationPreview() {
    AdmiralTheme {
        Surface(
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                StaticNotification(
                    notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
                    linkText = "Link",
                    notificationStyle = StaticNotificationStyle.Error,
                    colors = AdmiralStaticNotificationColors.error(),
                    isEnable = true
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                StaticNotification(
                    notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
                    linkText = "Link",
                    notificationStyle = StaticNotificationStyle.Error,
                    colors = AdmiralStaticNotificationColors.error(),
                    isEnable = false
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                StaticNotification(
                    notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
                    linkText = "Link",
                    notificationStyle = StaticNotificationStyle.Attention,
                    colors = AdmiralStaticNotificationColors.attention(),
                    isEnable = true
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                StaticNotification(
                    notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
                    linkText = "Link",
                    notificationStyle = StaticNotificationStyle.Attention,
                    colors = AdmiralStaticNotificationColors.attention(),
                    isEnable = false
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                StaticNotification(
                    notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
                    linkText = "Link",
                    notificationStyle = StaticNotificationStyle.Info,
                    isEnable = true
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                StaticNotification(
                    notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
                    linkText = "Link",
                    notificationStyle = StaticNotificationStyle.Info,
                    isEnable = false
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                StaticNotification(
                    notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
                    linkText = "Link",
                    notificationStyle = StaticNotificationStyle.Success,
                    colors = AdmiralStaticNotificationColors.success(),
                    isEnable = true
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                StaticNotification(
                    notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
                    linkText = "Link",
                    notificationStyle = StaticNotificationStyle.Success,
                    colors = AdmiralStaticNotificationColors.success(),
                    isEnable = true
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                StaticNotification(
                    notificationText = "At breakpoint boundaries, mini units divide the screen into a fixed master grid.",
                    icon = painterResource(id = R.drawable.admiral_ic_info_solid),
                    isEnable = true
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                StaticNotification(
                    linkText = "Link",
                    isEnable = true
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                StaticNotification(
                    linkText = "Link",
                    isEnable = true,
                    isIconVisible = false,
                )
                Spacer(modifier = Modifier.size(DIMEN_X1))
                StaticNotification(
                    linkText = "Link",
                    isEnable = true,
                    isIconVisible = false,
                    isCloseIconVisible = false,
                )
            }
        }
    }
}