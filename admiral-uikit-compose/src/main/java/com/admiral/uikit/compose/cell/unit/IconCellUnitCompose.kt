package com.admiral.uikit.compose.cell.unit

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.compose.cell.base.CellUnitCompose

data class IconCellUnitCompose(
    override var unitType: CellUnitType,
    private val icon: Painter,
    private val contentDescription: String = "",
    private val iconColorState: ColorState? = null,
    private val isEnabled: Boolean = true
) : CellUnitCompose {

    @Composable
    override fun Create(modifier: Modifier) {
        val theme = ThemeManagerCompose.theme.value

        val iconColor =
            if (isEnabled) iconColorState?.normalEnabled ?: theme.palette.elementPrimary
            else iconColorState?.normalDisabled ?: theme.palette.elementPrimary.withAlpha()

        Icon(modifier = modifier, painter = icon, contentDescription = contentDescription, tint = Color(iconColor))
    }
}