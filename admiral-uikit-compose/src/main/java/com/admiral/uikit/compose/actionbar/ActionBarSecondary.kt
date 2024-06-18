package com.admiral.uikit.compose.actionbar

import androidx.annotation.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X18
import com.admiral.uikit.compose.util.DIMEN_X7

@Composable
fun ActionBarSecondary(
    modifier: Modifier = Modifier,
    colors: ActionBarColor = ActionBarDefault.actionBarColors(),
    @Size(min = 1, max = 3)
    hiddenContentList: Array<ActionBarSecondaryItem> = arrayOf(),
    isEnabled: Boolean = true,
    content: @Composable (Shape) -> Unit,
) {
    BaseRevealSwipe(
        modifier = modifier,
        colors = colors,
        isEnabled = isEnabled,
        actionSize = SecondaryActionSize,
        hiddenContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                hiddenContentList.forEach {
                    ActionSecondaryItem(
                        item = it
                    )
                }
            }
        }
    ) {
        content(it)
    }
}

@Composable
fun ActionSecondaryItem(
    isEnabled: Boolean = true,
    item: ActionBarSecondaryItem
) {
    Column(
        modifier = Modifier
            .width(ActionSecondaryWidth)
            .height(DIMEN_X18)
            .clickable(
                enabled = isEnabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    color = item.backgroundColorPressed
                ),
                onClick = item.action,
            )
            .background(color = if (isEnabled) item.backgroundColorNormalEnabled else item.backgroundColorNormalEnabled.withAlpha()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            modifier = Modifier
                .size(DIMEN_X7),
            painter = item.icon,
            tint = if (isEnabled) AdmiralTheme.colors.elementStaticWhite else AdmiralTheme.colors.elementStaticWhite.withAlpha(),
            contentDescription = item.iconContentDescription
        )
        Spacer(modifier = Modifier.size(DIMEN_X1))
        item.description?.let {
            Text(
                modifier = Modifier,
                text = item.description,
                style = AdmiralTheme.typography.caption2,
                color = if (isEnabled) item.descriptionColorNormalEnabled else item.descriptionColorNormalEnabled.withAlpha()
            )
        }
    }
}

private val ActionSecondaryWidth = 90.dp

@Preview
@Composable
private fun ActionBarSecondaryPreview() {
    AdmiralTheme {
        Surface(
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val actionBarSecondaryItemList = arrayOf(
                    ActionBarSecondaryItem(
                        icon = painterResource(id = R.drawable.admiral_ic_email_outline),
                        description = "Text",
                        iconContentDescription = "",
                        backgroundColorNormalEnabled = AdmiralTheme.colors.elementAccent,
                        backgroundColorPressed = AdmiralTheme.colors.elementAccentPressed,
                        descriptionColorNormalEnabled = AdmiralTheme.colors.textStaticWhite,
                        descriptionColorPressed = AdmiralTheme.colors.textStaticWhite,
                        action = {
                            println("action!!!")
                        }
                    ),
                    ActionBarSecondaryItem(
                        icon = painterResource(id = R.drawable.admiral_ic_email_outline),
                        description = "Text",
                        iconContentDescription = "",
                        backgroundColorNormalEnabled = AdmiralTheme.colors.elementSecondary,
                        backgroundColorPressed = AdmiralTheme.colors.elementAccentPressed,
                        descriptionColorNormalEnabled = AdmiralTheme.colors.textStaticWhite,
                        descriptionColorPressed = AdmiralTheme.colors.textStaticWhite,
                        action = {
                            println("action!!!")
                        }
                    ),
                    ActionBarSecondaryItem(
                        icon = painterResource(id = R.drawable.admiral_ic_email_outline),
                        description = "Text",
                        iconContentDescription = "",
                        backgroundColorNormalEnabled = AdmiralTheme.colors.elementError,
                        backgroundColorPressed = AdmiralTheme.colors.elementAccentPressed,
                        descriptionColorNormalEnabled = AdmiralTheme.colors.textStaticWhite,
                        descriptionColorPressed = AdmiralTheme.colors.textStaticWhite,
                        action = {
                            println("action!!!")
                        }
                    )
                )

                ActionBarSecondary(
                    hiddenContentList = actionBarSecondaryItemList
                ) {
                    Surface(
                        modifier = Modifier
                            .height(DIMEN_X18)
                            .fillMaxWidth(),
                        color = AdmiralTheme.colors.backgroundBasic,
                        shape = it
                    ) {
                        Box(
                            modifier = Modifier
                                .background(AdmiralTheme.colors.backgroundBasic, shape = it),
                            contentAlignment = Alignment.Center
                        ) {
                            com.admiral.uikit.compose.text.Text(text = "Hello World!!")
                        }
                    }
                }
            }
        }
    }
}