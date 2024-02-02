package com.admiral.uikit.compose.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState

@Composable
@Suppress("LongParameterList")
fun StandardTab(
    items: List<String>,
    textColorState: ColorState? = null,
    borderColorState: ColorState? = null,
    selectedBorderColorState: ColorState? = null,
    isEnabled: Boolean = true,
    onCheckedChange: (String) -> Unit = {}
) {
    val (selectedId, setSelectedId) = remember {
        mutableStateOf(-1)
    }

    val theme = ThemeManagerCompose.theme.value

    val textColor = if (isEnabled) textColorState?.normalEnabled ?: theme.palette.textPrimary
    else textColorState?.normalDisabled ?: theme.palette.textPrimary.withAlpha()

    val borderColor = borderColorState?.normalEnabled ?: theme.palette.elementAdditional

    val selectedBorderColor =
        if (isEnabled) selectedBorderColorState?.normalEnabled ?: theme.palette.backgroundAccent
        else selectedBorderColorState?.normalDisabled ?: theme.palette.backgroundAccent.withAlpha()

    Surface(
        modifier = Modifier
            .height(SURFACE_HEIGHT.dp),
        color = Color(theme.palette.backgroundBasic)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(borderColor),
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
                        Color(borderColor)
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
                            color = if (index == selectedId) Color(selectedBorderColor) else Color.Transparent,
                            shape = RoundedCornerShape(ITEM_BORDER_RADIUS.dp)
                        )
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(textColor),
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