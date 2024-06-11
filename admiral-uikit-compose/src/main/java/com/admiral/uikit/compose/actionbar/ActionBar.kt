package com.admiral.uikit.compose.actionbar

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.text.Text
import com.admiral.uikit.compose.util.DIMEN_X25
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X8

/**
 * A composable function that renders an action bar with customizable actions and content.
 *
 * @param modifier Modifier to be applied to the ActionBar.
 * @param colors Colors for the ActionBar. Defaults to [ActionBarDefault].actionBarColors().
 * @param moreItem An optional ActionBarItem for the [moreActionItem] action. Defaults to null.
 * @param upItem An optional ActionBarItem for the [upActionItem] action. Defaults to null.
 * @param downItem An optional ActionBarItem for the [downActionItem] action. Defaults to null.
 * @param editItem An optional ActionBarItem for the [editActionItem] action. Defaults to null.
 * @param deleteItem An optional ActionBarItem for the [deleteActionItem] action. Defaults to null.
 * @param isEnabled Boolean indicating whether the ActionBar is enabled. Defaults to true.
 * @param hiddenContent A composable lambda to define additional hidden content within the ActionBar.
 * @param content A composable lambda that takes a Shape and defines the main content of the ActionBar.
 */
@Composable
fun ActionBar(
    modifier: Modifier = Modifier,
    colors: ActionBarColor = ActionBarDefault.actionBarColors(),
    moreItem: ActionBarItem? = null,
    upItem: ActionBarItem? = null,
    downItem: ActionBarItem? = null,
    editItem: ActionBarItem? = null,
    deleteItem: ActionBarItem? = null,
    isEnabled: Boolean = true,
    hiddenContent: @Composable RowScope.() -> Unit = {},
    content: @Composable (Shape) -> Unit,
) {
    BaseRevealSwipe(
        modifier = modifier,
        colors = colors,
        actionSize = DefaultActionSize,
        isEnabled = isEnabled,
        hiddenContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.size(DIMEN_X4))
                moreItem?.let { ActionBarImage(moreItem, isEnabled) }
                Spacer(modifier = Modifier.size(DIMEN_X3))
                upItem?.let { ActionBarImage(upItem, isEnabled) }
                Spacer(modifier = Modifier.size(DIMEN_X3))
                downItem?.let { ActionBarImage(downItem, isEnabled) }
                Spacer(modifier = Modifier.size(DIMEN_X3))
                editItem?.let { ActionBarImage(editItem, isEnabled) }
                Spacer(modifier = Modifier.size(DIMEN_X3))
                deleteItem?.let { ActionBarImage(deleteItem, isEnabled) }
                Spacer(modifier = Modifier.size(DIMEN_X4))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = hiddenContent
            )
        }
    ) {
        content(it)
    }
}

@Composable
fun moreActionItem(
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    colors: ActionBarColor = ActionBarDefault.actionBarColors(),
    onClickListener: () -> Unit = {},
    isEnabled: Boolean = true,
) = ActionBarItem(
    modifier = modifier,
    image = painterResource(id = R.drawable.admiral_ic_more_horizontal_outline),
    contentDescription = contentDescription,
    rippleColor = colors.moreIconTintPressed,
    tintColor = colors.getMoreIconTintColor(isEnabled = isEnabled).value,
    backgroundColor = AdmiralTheme.colors.backgroundBasic,
    onClickListener = onClickListener,
    isEnable = isEnabled
)

@Composable
fun upActionItem(
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    colors: ActionBarColor = ActionBarDefault.actionBarColors(),
    onClickListener: () -> Unit = {},
    isEnabled: Boolean = true,
) = ActionBarItem(
    modifier = modifier,
    image = painterResource(id = R.drawable.admiral_ic_arrow_up_outline),
    contentDescription = contentDescription,
    rippleColor = colors.upIconTintPressed,
    tintColor = colors.getUpIconTintColor(isEnabled = isEnabled).value,
    backgroundColor = AdmiralTheme.colors.backgroundBasic,
    onClickListener = onClickListener,
    isEnable = isEnabled
)

@Composable
fun downActionItem(
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    colors: ActionBarColor = ActionBarDefault.actionBarColors(),
    onClickListener: () -> Unit = {},
    isEnabled: Boolean = true,
) = ActionBarItem(
    modifier = modifier,
    image = painterResource(id = R.drawable.admiral_ic_arrow_down_outline),
    contentDescription = contentDescription,
    rippleColor = colors.downIconTintPressed,
    tintColor = colors.getDownIconTintColor(isEnabled = isEnabled).value,
    backgroundColor = AdmiralTheme.colors.backgroundBasic,
    onClickListener = onClickListener,
    isEnable = isEnabled
)

@Composable
fun editActionItem(
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    colors: ActionBarColor = ActionBarDefault.actionBarColors(),
    onClickListener: () -> Unit = {},
    isEnabled: Boolean = true,
) = ActionBarItem(
    modifier = modifier,
    image = painterResource(id = R.drawable.admiral_ic_edit_outline),
    contentDescription = contentDescription,
    rippleColor = colors.editIconTintPressed,
    tintColor = colors.getEditIconTintColor(isEnabled = isEnabled).value,
    backgroundColor = AdmiralTheme.colors.backgroundBasic,
    onClickListener = onClickListener,
    isEnable = isEnabled
)

@Composable
fun deleteActionItem(
    modifier: Modifier = Modifier,
    contentDescription: String = "",
    colors: ActionBarColor = ActionBarDefault.actionBarColors(),
    onClickListener: () -> Unit = {},
    isEnabled: Boolean = true,
) = ActionBarItem(
    modifier = modifier,
    image = painterResource(id = R.drawable.admiral_ic_close_outline),
    contentDescription = contentDescription,
    rippleColor = colors.deleteIconTintPressed,
    tintColor = colors.getDeleteIconTintColor(isEnabled = isEnabled).value,
    backgroundColor = AdmiralTheme.colors.backgroundBasic,
    onClickListener = onClickListener,
    isEnable = isEnabled
)

@Composable
fun ActionBarImage(
    item: ActionBarItem,
    isEnabled: Boolean
) {
    Box(
        modifier = item.modifier
            .background(color = item.backgroundColor, shape = CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier
                .size(DIMEN_X8)
                .clickable(
                    enabled = isEnabled,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = false,
                        color = item.rippleColor
                    ),
                    onClick = item.onClickListener,
                )
                .padding(ActionItemPadding),
            painter = item.image,
            contentDescription = item.contentDescription,
            tint = if (isEnabled) item.tintColor else item.tintColor.withAlpha()
        )
    }
}

private val ActionItemPadding = 2.dp

@Preview
@Composable
private fun ActionBarPreview() {
    AdmiralTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = AdmiralTheme.colors.backgroundBasic,
        ) {
            Column {
                ActionBar(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Surface(
                        modifier = Modifier
                            .height(DIMEN_X25)
                            .fillMaxWidth(),
                        color = AdmiralTheme.colors.backgroundBasic,
                        shape = it
                    ) {
                        Box(
                            modifier = Modifier
                                .background(AdmiralTheme.colors.backgroundBasic, shape = it),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Hello World!!")
                        }
                    }
                }
            }
        }
    }
}