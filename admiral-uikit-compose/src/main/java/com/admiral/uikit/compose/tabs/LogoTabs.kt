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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.compose.util.DIMEN_X12
import com.admiral.uikit.compose.util.DIMEN_X2

@Composable
@Suppress("LongParameterList", "LongMethod")
fun LogoTabs(
    items: List<Painter>,
    borderColorState: ColorState? = null,
    selectedBorderColorState: ColorState? = null,
    isEnabled: Boolean = true,
    onCheckedChange: (Int) -> Unit = {}
) {
    val (selectedId, setSelectedId) = remember {
        mutableStateOf(-1)
    }

    val theme = ThemeManagerCompose.theme.value

    val borderColor = borderColorState?.normalEnabled ?: theme.palette.elementAdditional

    val selectedBorderColor =
        if (isEnabled) selectedBorderColorState?.normalEnabled ?: theme.palette.backgroundAccent
        else selectedBorderColorState?.normalDisabled ?: theme.palette.backgroundAccent.withAlpha()

    Surface(
        modifier = Modifier
            .height(DIMEN_X12),
        color = Color(theme.palette.backgroundBasic),
        shape = RoundedCornerShape(DIMEN_X2)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(borderColor),
                    shape = RoundedCornerShape(DIMEN_X2)
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
                        .fillMaxHeight()
                        .weight(1F)
                        .clickable(
                            onClick = {
                                setSelectedId(index)
                                onCheckedChange.invoke(index)
                            })
                        .border(
                            width = 2.dp,
                            color = if (index == selectedId) Color(selectedBorderColor) else Color.Transparent,
                            shape = RoundedCornerShape(DIMEN_X2)
                        )
                ) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center),
                        painter = item,
                        contentDescription = null
                    )
                }
            }
        }
    }
}