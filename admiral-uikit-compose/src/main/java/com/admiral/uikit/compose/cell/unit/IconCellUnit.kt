package com.admiral.uikit.compose.cell.unit

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.compose.cell.base.CellUnit
import com.admiral.uikit.core.components.cell.base.CellUnitType

data class IconCellUnit(
    override var unitType: CellUnitType,
    private val icon: Painter,
    private val contentDescription: String = "",
    private val iconColorEnable: Color? = null,
    private val iconColorDisable: Color? = null,
    private val isEnabled: Boolean = true
) : CellUnit {

    @Composable
    override fun Create(modifier: Modifier) {
        val iconColor =
            if (isEnabled) iconColorEnable ?: AdmiralTheme.colors.elementPrimary
            else iconColorDisable ?: AdmiralTheme.colors.elementPrimary.withAlpha()

        Icon(modifier = modifier, painter = icon, contentDescription = contentDescription, tint = iconColor)
    }
}