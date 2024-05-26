package com.admiral.uikit.compose.cell.unit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.compose.cell.base.CellUnit
import com.admiral.uikit.compose.util.DIMEN_X11
import com.admiral.uikit.core.components.cell.base.CellUnitType

data class IconBackgroundCellUnit(
    override var unitType: CellUnitType,
    private val icon: Painter,
    private val contentDescription: String = "",
    private val iconColorEnable: Color? = null,
    private val iconColorDisable: Color? = null,
    private val backgroundColorEnable: Color? = null,
    private val backgroundColorDisable: Color? = null,
    private val isEnabled: Boolean = true
) : CellUnit {

    @Composable
    override fun Create(modifier: Modifier) {
        val iconColor =
            if (isEnabled) iconColorEnable ?: AdmiralTheme.colors.elementAccent
            else iconColorDisable ?: AdmiralTheme.colors.elementAccent.withAlpha()

        val backgroundColor =
            if (isEnabled) backgroundColorEnable ?: AdmiralTheme.colors.backgroundAdditionalOne
            else backgroundColorDisable ?: AdmiralTheme.colors.backgroundAdditionalOne.withAlpha()

        Icon(
            modifier = modifier
                .size(DIMEN_X11)
                .clip(CircleShape)
                .background(color = backgroundColor, shape = CircleShape)
                .padding(IconPadding),
            painter = icon,
            contentDescription = contentDescription,
            tint = iconColor,
        )
    }

    private companion object {
        val IconPadding = 10.dp
    }
}