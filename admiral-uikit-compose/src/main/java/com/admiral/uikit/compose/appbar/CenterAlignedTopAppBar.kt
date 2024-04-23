package com.admiral.uikit.compose.appbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.button.AdmiralButtonColor
import com.admiral.uikit.compose.button.AdmiralButtonSize
import com.admiral.uikit.compose.button.Button
import com.admiral.uikit.compose.util.DIMEN_X6
import com.admiral.uikit.compose.util.DIMEN_X7
import com.admiral.uikit.core.components.appbar.AppbarSize

@Suppress("LongParameterList", "LongMethod", "MagicNumber")
@Composable
fun AdmiralCenterAlignedTopAppBar(
    modifier: Modifier = Modifier,
    navIcon: Painter? = null,
    onNavIconClick: () -> Unit = {},
    isNavigationIconVisible: Boolean = true,
    title: String? = null,
    titleAlign: TextAlign = TextAlign.Center,
    isTitleCentered: Boolean = true,
    titleMaxLines: Int = Int.MAX_VALUE,
    actionText: String? = null,
    onActionTextClickListener: () -> Unit = {},
    actions: List<ActionItem> = emptyList(),
    overFlowActionsIcon: Painter? = null,
    maxActions: Int = 2,
    onOverFlowActionsIconClick: () -> Unit = {},
    size: AppbarSize = AppbarSize.SMALL,
    backgroundColor: Color = AdmiralTheme.colors.backgroundBasic,
    navIconTintColor: Color = AdmiralTheme.colors.elementPrimary,
    titleColor: Color = AdmiralTheme.colors.textPrimary,
    actionTextColor: Color = AdmiralTheme.colors.textAccent,
) {
    val textStyle = when (size) {
        AppbarSize.SMALL -> AdmiralTheme.typography.subtitle2
        AppbarSize.BIG -> AdmiralTheme.typography.largetitle1
    }

    val showAsActionItems = actions.take(maxActions)
    val overflowItems = actions.subtract(showAsActionItems.toSet()).toList()

    Surface(
        color = backgroundColor,
        modifier = modifier
            .fillMaxWidth()
    ) {
        SingleRowTopAppBar(
            modifier = modifier,
            centeredTitle = isTitleCentered,
            navigationIcon = {
                if (isNavigationIconVisible) {
                    navIcon?.let { icon ->
                        Icon(
                            painter = icon,
                            contentDescription = null,
                            tint = navIconTintColor,
                            modifier = Modifier
                                .size(DIMEN_X7)
                                .clickable(
                                    onClick = onNavIconClick,
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(bounded = false)
                                ),
                        )
                    }
                }
            },
            title = {
                title?.let {
                    Text(
                        text = title,
                        textAlign = titleAlign,
                        color = titleColor,
                        style = textStyle,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = titleMaxLines
                    )
                }
            },
            actions = {
                actionText?.let {
                    Button(
                        modifier = Modifier
                            .wrapContentHeight(),
                        actionText = actionText,
                        color = AdmiralButtonColor.ghost(
                            textColorEnable = actionTextColor,
                            textColorDisable = actionTextColor.withAlpha()
                        ),
                        size = AdmiralButtonSize.small(),
                        onClick = onActionTextClickListener,
                    )
                }

                Row(
                    modifier = Modifier
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    showAsActionItems.forEach { action ->
                        if (action != showAsActionItems.first())
                            Spacer(modifier = Modifier.size(DIMEN_X6))

                        Icon(
                            painter = action.icon,
                            contentDescription = action.contentDescription,
                            tint = action.color ?: AdmiralTheme.colors.elementAccent,
                            modifier = Modifier
                                .size(DIMEN_X7)
                                .clickable(
                                    onClick = { action.onClick() },
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(bounded = false),
                                ),
                        )
                    }

                    if (overflowItems.isNotEmpty() && overFlowActionsIcon != null) {
                        Spacer(modifier = Modifier.size(DIMEN_X6))

                        Icon(
                            painter = overFlowActionsIcon,
                            contentDescription = null,
                            tint = AdmiralTheme.colors.elementAccent,
                            modifier = Modifier
                                .size(DIMEN_X7)
                                .clickable(
                                    onClick = onOverFlowActionsIconClick,
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(bounded = false),
                                ),
                        )
                    }
                }
            }
        )
    }
}

@Composable
internal fun SingleRowTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    centeredTitle: Boolean,
) {
    val actionsRow = @Composable {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            content = actions
        )
    }

    val height = LocalDensity.current.run { ContainerHeight.toPx() }

    AdmiralTopAppBarLayout(
        modifier = modifier,
        heightPx = height,
        navigationIconContentColor = AdmiralTheme.colors.elementPrimary,
        actionIconContentColor = AdmiralTheme.colors.backgroundBasic,
        title = title,
        titleVerticalArrangement = Arrangement.Center,
        titleHorizontalArrangement =
        if (centeredTitle) Arrangement.Center else Arrangement.Start,
        titleBottomPadding = 0,
        navigationIcon = navigationIcon,
        actions = actionsRow,
    )
}

private val ContainerHeight = 56.0.dp

@Preview
@Composable
private fun TopAppBarCenterPreview() {
    val actionsList = arrayListOf(
        ActionItem(
            icon = painterResource(R.drawable.admiral_ic_download_outline),
            contentDescription = null,
            onClick = { }
        ),
        ActionItem(
            icon = painterResource(R.drawable.admiral_ic_search_outline),
            contentDescription = null,
            onClick = { }
        ),
        ActionItem(
            icon = painterResource(R.drawable.admiral_ic_heart_solid),
            contentDescription = null,
            onClick = { }
        ),
        ActionItem(
            icon = painterResource(R.drawable.admiral_ic_account_detail_outline),
            contentDescription = null,
            onClick = { }
        ),
        ActionItem(
            icon = painterResource(R.drawable.admiral_ic_accept_outline),
            contentDescription = null,
            onClick = { }
        )
    )

    AdmiralTheme {
        Surface(color = AdmiralTheme.colors.backgroundBasic) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box {
                    AdmiralCenterAlignedTopAppBar(
                        title = "Admiral Design System",
                        navIcon = painterResource(id = R.drawable.admiral_ic_arrow_left_outline)
                    )
                }

                AdmiralCenterAlignedTopAppBar(
                    title = "Admiral",
                    navIcon = painterResource(id = R.drawable.admiral_ic_arrow_left_outline)
                )

                AdmiralCenterAlignedTopAppBar(
                    title = "Admiral",
                    navIcon = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
                    isTitleCentered = false
                )

                AdmiralCenterAlignedTopAppBar(
                    title = "Admiral",
                    navIcon = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
                    actions = actionsList,
                    overFlowActionsIcon = painterResource(id = R.drawable.admiral_ic_more_outline)
                )
            }
        }
    }
}