package com.admiral.uikit.compose.notifications.toast

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.AdmiralTheme.typography
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.button.AdmiralButtonSize
import com.admiral.uikit.compose.button.Button
import com.admiral.uikit.compose.links.Link
import com.admiral.uikit.compose.text.AdmiralTextColor
import com.admiral.uikit.compose.text.Text
import com.admiral.uikit.compose.util.DIMEN_X12
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X20
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X6
import com.admiral.uikit.compose.util.DIMEN_X7
import com.admiral.uikit.core.components.link.LinkSize
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("LongMethod", "MagicNumber")
@Composable
fun ToastNotification(
    modifier: Modifier = Modifier,
    text: String? = null,
    linkText: String? = null,
    colors: ToastNotificationColor = AdmiralToastNotificationColor.default(),
    icon: Painter? = null,
    isCloseIconVisible: Boolean = false,
    duration: Long = 0,
    isVisible: Boolean = false,
    onLinkClickListener: () -> Unit = {},
    onTimerFinish: () -> Unit = {},
) {
    var visibility by remember { mutableStateOf(isVisible) }
    var millisUntilFinished by remember { mutableLongStateOf(duration) }

    LaunchedEffect(key1 = isVisible) {
        visibility = isVisible
        millisUntilFinished = duration

        while (millisUntilFinished > 0) {
            delay(CountDownInterval)
            millisUntilFinished -= CountDownInterval
        }
    }

    if (visibility) {
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(DIMEN_X2))
                .background(color = colors.background, shape = RoundedCornerShape(DIMEN_X2))
                .padding(horizontal = DIMEN_X4, vertical = DIMEN_X3),
        ) {

            val (
                iconId,
                textId,
                linkId,
                closeIconId,
            ) = createRefs()

            if (icon != null && isCloseIconVisible) {
                Icon(
                    modifier = Modifier
                        .size(DIMEN_X7)
                        .constrainAs(iconId) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                    painter = icon,
                    tint = colors.iconTint,
                    contentDescription = null
                )
            }

            text?.let {
                Text(
                    modifier = Modifier
                        .constrainAs(textId) {
                            top.linkTo(parent.top)
                            start.linkTo(
                                if (icon != null) iconId.end else parent.start,
                                if (icon != null) DIMEN_X3 else 0.dp
                            )
                            end.linkTo(
                                if (isCloseIconVisible) closeIconId.start else parent.end,
                                if (isCloseIconVisible) DIMEN_X3 else 0.dp
                            )
                            width = Dimension.fillToConstraints
                        },
                    text = text,
                    style = typography.body2,
                    color = AdmiralTextColor.primary(
                        textColorNormal = colors.text,
                        textColorDisabled = colors.text
                    )
                )
            }

            linkText?.let {
                Link(
                    modifier = Modifier
                        .padding(vertical = LinkVerticalPadding)
                        .constrainAs(linkId) {
                            top.linkTo(textId.bottom, DIMEN_X2)
                            start.linkTo(
                                if (icon != null) iconId.end else parent.start,
                                if (icon != null) DIMEN_X3 else 0.dp
                            )
                            end.linkTo(
                                if (isCloseIconVisible) closeIconId.start else parent.end,
                                if (isCloseIconVisible) DIMEN_X3 else 0.dp
                            )
                            width = Dimension.fillToConstraints
                        },
                    linkText = linkText,
                    linkSize = LinkSize.BIG,
                    textEnableColor = colors.link,
                    onClick = onLinkClickListener,
                )
            }

            if (isCloseIconVisible) {
                Icon(
                    modifier = Modifier
                        .size(DIMEN_X6)
                        .constrainAs(closeIconId) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                        }
                        .clickable(
                            onClick = {
                                visibility = visibility.not()
                                println("clicked")
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ),
                    painter = painterResource(id = R.drawable.admiral_ic_close_outline),
                    contentDescription = null,
                    tint = colors.closeIconTint,
                )
            }
        }
    }
}

private const val CountDownInterval = 1000L
private val LinkVerticalPadding = 2.dp

@Preview
@Composable
private fun ToastNotificationPreview() {
    var isVisible by remember { mutableStateOf(false) }
    val messageText =
        "At breakpoint boundaries, mini units divide the screen into a fixed master grid."
    val linkText = "Link text"

    AdmiralTheme {
        Scaffold(
            modifier = Modifier,
            backgroundColor = AdmiralTheme.colors.backgroundBasic,
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                Box {
                    Button(
                        modifier = Modifier
                            .padding(DIMEN_X4)
                            .fillMaxWidth(),
                        actionText = "Show action notification",
                        size = AdmiralButtonSize.small(),
                        onClick = {
                            isVisible = isVisible.not()
                        },
                    )
                }

                Box {
                    ToastNotification(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = DIMEN_X20)
                            .padding(horizontal = DIMEN_X4),
                        text = messageText,
                        linkText = linkText,
                        isCloseIconVisible = true,
                        icon = painterResource(id = R.drawable.admiral_ic_info_solid),
                        duration = 3000,
                        isVisible = isVisible,
                        onTimerFinish = {
                            isVisible = isVisible.not()
                        }
                    )
                }
            }
        }
    }
}
