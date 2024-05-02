package com.admiral.uikit.compose.tabs

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.compose.util.ALPHA_DISABLED
import com.admiral.uikit.compose.util.ALPHA_ENABLED
import com.admiral.uikit.compose.util.DIMEN_X12
import com.admiral.uikit.compose.util.DIMEN_X2

@Composable
@Suppress("LongParameterList", "LongMethod")
fun LogoTabs(
    modifier: Modifier = Modifier,
    items: List<Painter>,
    selectedIndex: Int? = null,
    borderColor: Color = AdmiralTheme.colors.elementAdditional,
    selectedBorderEnableColor: Color = AdmiralTheme.colors.backgroundAccent,
    selectedBorderDisableColor: Color = AdmiralTheme.colors.backgroundAccent.withAlpha(),
    isEnabled: Boolean = true,
    onCheckedChange: (Int) -> Unit = {}
) {
    val (selectedId, setSelectedId) = remember {
        mutableIntStateOf(selectedIndex ?: -1)
    }

    val selectedBorderColor =
        if (isEnabled) selectedBorderEnableColor
        else selectedBorderDisableColor

    val roundedCornerShape = RoundedCornerShape(DIMEN_X2)

    Surface(
        modifier = modifier
            .height(DIMEN_X12),
        color = AdmiralTheme.colors.backgroundBasic,
        shape = roundedCornerShape
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = roundedCornerShape
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
                    } else {
                        Color.Transparent
                    },

                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = DIMEN_X2, bottom = DIMEN_X2)
                        .width(1.dp)
                )

                Box(
                    Modifier
                        .clip(roundedCornerShape)
                        .fillMaxHeight()
                        .weight(1F)
                        .clickable(
                            onClick = {
                                setSelectedId(index)
                                onCheckedChange.invoke(index)
                            },
                            enabled = isEnabled
                        )
                        .border(
                            width = 2.dp,
                            color = if (index == selectedId) selectedBorderColor else Color.Transparent,
                            shape = roundedCornerShape
                        )
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center),
                        painter = item,
                        contentDescription = null,
                        alpha = if (isEnabled) ALPHA_ENABLED else ALPHA_DISABLED
                    )
                }
            }
        }
    }
}