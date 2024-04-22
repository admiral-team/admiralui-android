package com.admiral.uikit.compose.tabs

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.themes.compose.withAlpha

@Composable
@Suppress("LongParameterList")
fun StandardTab(
    modifier: Modifier = Modifier,
    items: List<String>,
    textEnableColor: Color = AdmiralTheme.colors.textPrimary,
    textDisableColor: Color = AdmiralTheme.colors.textPrimary.withAlpha(),
    borderColor: Color = AdmiralTheme.colors.elementAdditional,
    selectedBorderEnableColor: Color = AdmiralTheme.colors.backgroundAccent,
    selectedBorderDisableColor: Color = AdmiralTheme.colors.backgroundAccent.withAlpha(),
    isEnabled: Boolean = true,
    onCheckedChange: (String) -> Unit = {}
) {
    val (selectedId, setSelectedId) = remember {
        mutableStateOf(-1)
    }
    val textColor = if (isEnabled) textEnableColor else textDisableColor
    val selectedBorderColor =
        if (isEnabled) selectedBorderEnableColor else selectedBorderDisableColor

    Surface(
        modifier = modifier
            .height(SURFACE_HEIGHT.dp),
        color = AdmiralTheme.colors.backgroundBasic
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(OUTER_BORDER_RADIUS.dp)
                )
        )

        Row(
            modifier = Modifier,
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            items.forEachIndexed { index, item ->
                Divider(
                    color = if (index != 0 && index != selectedId && index != selectedId + 1) {
                        borderColor
                    } else Color.Transparent,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = DIVIDER_PADDINGS.dp, bottom = DIVIDER_PADDINGS.dp)
                        .width(1.dp)
                )

                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(1F)
                        .clickable(
                            onClick = {
                                setSelectedId(index)
                                onCheckedChange.invoke(item)
                            })
                        .border(
                            width = ITEM_BORDER_WIDTH.dp,
                            color = if (index == selectedId) selectedBorderColor else Color.Transparent,
                            shape = RoundedCornerShape(ITEM_BORDER_RADIUS.dp)
                        )
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        color = textColor,
                        text = item,
                        style = (if (index == selectedId) {
                            ThemeManagerCompose.typography.subhead2
                        } else {
                            ThemeManagerCompose.typography.subhead3
                        })
                    )
                }
            }
        }
    }
}

private const val OUTER_BORDER_RADIUS = 8

private const val ITEM_BORDER_RADIUS = 8
private const val ITEM_BORDER_WIDTH = 2

private const val DIVIDER_PADDINGS = 8

private const val SURFACE_HEIGHT = 32

@Preview
@Composable
fun StandardTabPreview() {
    AdmiralTheme {
        StandardTab(
            items = listOf(
                "tab1",
                "tab2",
                "tab3",
                "tab4",
                "tab5",
                "tab6",
                "tab1",
                "tab2",
                "tab3",
                "tab4",
                "tab5",
                "tab6"
            )
        )
    }
}