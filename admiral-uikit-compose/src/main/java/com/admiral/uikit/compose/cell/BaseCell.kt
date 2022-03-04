package com.admiral.uikit.compose.cell

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.common.components.cell.base.CellUnitType
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.compose.cell.base.CellUnitCompose
import com.admiral.uikit.compose.util.DIMEN_X16
import com.admiral.uikit.compose.util.DIMEN_X4

@Composable
@Suppress("LongParameterList")
fun BaseCell(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    onClick: (() -> Unit)? = null,
    isEnabled: Boolean = true,
    children: List<CellUnitCompose>,
    backgroundColorState: ColorState? = null
) {
    val theme = ThemeManagerCompose.theme.value

    val backgroundColor =
        if (isEnabled) backgroundColorState?.normalEnabled ?: theme.palette.backgroundBasic
        else backgroundColorState?.normalDisabled ?: theme.palette.backgroundBasic.withAlpha()

    Row(verticalAlignment = verticalAlignment,
        modifier = modifier
            .wrapContentHeight()
            .defaultMinSize(minHeight = DIMEN_X16)
            .clickable(
                onClick = { onClick?.invoke() },
                indication = rememberRipple(
                    bounded = true,
                    color = Color(theme.palette.backgroundAccentPressed)
                ),
                interactionSource = remember {
                    MutableInteractionSource()
                }
            )
            .background(
                color = Color(backgroundColor)
            )
    ) {
        val leadingTextElement = children.find { it.unitType == CellUnitType.LEADING_TEXT }
        if (leadingTextElement == null) {
            val leadingElement = children.find { it.unitType == CellUnitType.LEADING }
            leadingElement?.Create(
                Modifier
                    .weight(1f)
                    .padding(start = DIMEN_X4)
            )
        } else {
            val leadingElement = children.find { it.unitType == CellUnitType.LEADING }
            leadingElement?.Create(
                Modifier
                    .padding(start = DIMEN_X4)
            )

            leadingTextElement.Create(
                Modifier
                    .weight(1f)
                    .padding(start = DIMEN_X4)
            )
        }

        val trailingElement = children.find { it.unitType == CellUnitType.TRAILING }
        trailingElement?.Create(
            Modifier
                .padding(end = DIMEN_X4)
        )
    }
}