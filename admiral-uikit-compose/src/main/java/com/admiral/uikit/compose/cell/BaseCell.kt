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
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.compose.cell.base.CellUnit
import com.admiral.uikit.compose.util.DIMEN_X16
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.core.components.cell.base.CellUnitType

@Composable
@Suppress("LongParameterList")
fun BaseCell(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    onClick: (() -> Unit)? = null,
    isEnabled: Boolean = true,
    children: List<CellUnit>,
    backgroundEnableColor: Color = AdmiralTheme.colors.backgroundBasic,
    backgroundDisableColor: Color = AdmiralTheme.colors.backgroundBasic.withAlpha()
) {
    val backgroundColor = if (isEnabled) backgroundEnableColor else backgroundDisableColor

    Row(verticalAlignment = verticalAlignment,
        modifier = modifier
            .wrapContentHeight()
            .defaultMinSize(minHeight = DIMEN_X16)
            .clickable(
                onClick = { onClick?.invoke() },
                indication = rememberRipple(
                    bounded = true,
                    color = AdmiralTheme.colors.backgroundAccentPressed
                ),
                interactionSource = remember {
                    MutableInteractionSource()
                }
            )
            .background(color = backgroundColor)
            .padding(vertical = DIMEN_X3)
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