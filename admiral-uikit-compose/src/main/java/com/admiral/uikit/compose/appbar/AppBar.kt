package com.admiral.uikit.compose.appbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.button.AdmiralButtonColor
import com.admiral.uikit.compose.button.AdmiralButtonSize
import com.admiral.uikit.compose.button.Button
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X6
import com.admiral.uikit.compose.util.DIMEN_X7
import com.admiral.uikit.compose.util.DIMEN_X8
import com.admiral.uikit.core.components.appbar.AppbarSize
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState

@Suppress("LongParameterList")
@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    navIcon: Painter? = null,
    onNavIconClick: () -> Unit = {},
    isNavigationIconVisible: Boolean = true,
    title: String? = null,
    titleAlign: TextAlign = TextAlign.Center,
    titleArrangement: Arrangement.Horizontal = Arrangement.Start,
    titleMaxLines: Int = Int.MAX_VALUE,
    actionText: String? = null,
    onActionTextClickListener: () -> Unit = {},
    actions: List<ActionItem> = emptyList(),
    overFlowActionsIcon: Painter? = null,
    maxActions: Int = MaxActions,
    onOverFlowActionsIconClick: () -> Unit = {},
    size: AppbarSize = AppbarSize.SMALL,
    backgroundColor: Color? = null,
    navIconTintColor: Color? = null,
    titleColor: Color? = null,
    actionTextColor: ColorState? = null,
) {
    val palette = ThemeManagerCompose.theme.value.palette
    val typography = ThemeManagerCompose.typography
    val textStyle = when (size) {
        AppbarSize.SMALL -> typography.subtitle2
        AppbarSize.BIG -> typography.largetitle1
    }
    val showAsActionItems = actions.take(maxActions)
    val overflowItems = actions.subtract(showAsActionItems.toSet()).toList()

    Surface(
        color = backgroundColor ?: Color(palette.backgroundBasic),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = AppBarHeight)
    ) {
        ConstraintLayout {
            val (
                navIconId,
                titleTextId,
                actionTextId,
                actionsContainerId,
            ) = createRefs()

            if (isNavigationIconVisible) {
                navIcon?.let { icon ->
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = navIconTintColor ?: Color(palette.elementPrimary),
                        modifier = Modifier
                            .size(DIMEN_X7)
                            .constrainAs(navIconId) {
                                start.linkTo(anchor = parent.start, margin = DIMEN_X4)
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            }
                            .clickable(
                                onClick = onNavIconClick,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = false)
                            ),
                    )
                }
            }

            Row(
                modifier = Modifier
                    .constrainAs(titleTextId) {
                        start.linkTo(
                            anchor = if (navIcon != null && isNavigationIconVisible) navIconId.end else parent.start,
                            margin = if (navIcon != null && isNavigationIconVisible) DIMEN_X8 else DIMEN_X4
                        )
                        end.linkTo(
                            anchor = when {
                                actionText.isNullOrEmpty().not() -> actionTextId.start
                                actions.isNotEmpty() && actionText.isNullOrEmpty() -> actionsContainerId.start
                                else -> parent.end
                            },
                            margin = if (actions.isEmpty()) DIMEN_X4 else DIMEN_X3
                        )
                        top.linkTo(
                            anchor = parent.top,
                            margin = if (size == AppbarSize.BIG) DIMEN_X8 else AppBarTopMarginZero
                        )
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    },
                horizontalArrangement = titleArrangement,
                verticalAlignment = Alignment.CenterVertically
            ) {
                title?.let {
                    Text(
                        modifier = Modifier,
                        text = title,
                        textAlign = titleAlign,
                        color = titleColor ?: Color(palette.textPrimary),
                        style = textStyle,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = titleMaxLines
                    )
                }
            }

            actionText?.let {
                Button(
                    modifier = Modifier
                        .wrapContentHeight()
                        .constrainAs(actionTextId) {
                            end.linkTo(actionsContainerId.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                    actionText = actionText,
                    color = AdmiralButtonColor.ghost(
                        textColorEnable = actionTextColor?.normalEnabled?.let { Color(it) }
                            ?: Color(palette.textAccent),
                        textColorDisable = actionTextColor?.normalDisabled?.let { Color(it) }
                            ?: Color(palette.textAccent.withAlpha())
                    ),
                    size = AdmiralButtonSize.small(),
                    onClick = onActionTextClickListener,
                )
            }

            Row(
                modifier = Modifier
                    .wrapContentHeight()
                    .constrainAs(actionsContainerId) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(anchor = parent.end, margin = DIMEN_X4)
                        width = Dimension.fillToConstraints
                    },
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                showAsActionItems.forEach { action ->
                    if (action != showAsActionItems.first()) Spacer(
                        modifier = Modifier.size(
                            DIMEN_X6
                        )
                    )
                    Icon(
                        painter = action.icon,
                        contentDescription = action.contentDescription,
                        tint = action.color ?: Color(palette.elementAccent),
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
                        tint = Color(palette.elementAccent),
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
    }
}

private val AppBarHeight = 56.dp
private val AppBarTopMarginZero = 0.dp
private const val MaxActions = 2

@Preview
@Composable
private fun AppBarPreview() {
    val titleTextMaxLineOne = 1
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
    Scaffold(modifier = Modifier) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            AppBar(title = "Дизайн-система «Адмирал»")
            AppBar(
                title = "Дизайн-система «Адмирал»",
                navIcon = painterResource(id = R.drawable.admiral_ic_arrow_left_outline)
            )
            AppBar(
                title = "Дизайн-система «Адмирал»",
                size = AppbarSize.BIG
            )
            AppBar(
                title = "Дизайн-система «Адмирал»",
                navIcon = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
                actions = actionsList.take(1)
            )
            AppBar(
                title = "Дизайн-система «Адмирал»",
                navIcon = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
                actions = actionsList.take(2)
            )
            AppBar(
                title = "Дизайн-система «Адмирал»",
                isNavigationIconVisible = false,
                navIcon = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
                actions = actionsList.take(3)
            )
            AppBar(
                title = "Дизайн-система «Адмирал»",
                navIcon = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
                overFlowActionsIcon = painterResource(id = R.drawable.admiral_ic_more_outline),
                actions = actionsList.take(3)
            )
            AppBar(
                title = "Дизайн-система «Адмирал»",
                navIcon = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
                overFlowActionsIcon = painterResource(id = R.drawable.admiral_ic_more_outline),
                titleMaxLines = titleTextMaxLineOne,
                actions = actionsList.take(3)
            )
            AppBar(
                title = "Дизайн-система «Адмирал»",
                navIcon = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
                overFlowActionsIcon = painterResource(id = R.drawable.admiral_ic_more_outline),
                actionText = "Cancel",
                titleMaxLines = titleTextMaxLineOne,
                actions = actionsList.take(3)
            )
            AppBar(
                title = "Дизайн-система «Адмирал»",
                navIcon = painterResource(id = R.drawable.admiral_ic_arrow_left_outline),
                overFlowActionsIcon = painterResource(id = R.drawable.admiral_ic_more_outline),
                actionText = "Cancel",
                titleMaxLines = titleTextMaxLineOne,
            )
        }
    }
}