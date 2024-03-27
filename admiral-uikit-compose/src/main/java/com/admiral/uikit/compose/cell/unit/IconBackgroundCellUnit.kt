package com.admiral.uikit.compose.cell.unit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.cell.base.CellUnit
import com.admiral.uikit.compose.util.DIMEN_X11
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState

data class IconBackgroundCellUnit(
    override var unitType: CellUnitType,
    private val icon: Painter,
    private val contentDescription: String = "",
    private val iconColorState: ColorState? = null,
    private val backgroundColorState: ColorState? = null,
    private val isEnabled: Boolean = true
) : CellUnit {

    @Composable
    override fun Create(modifier: Modifier) {
        val theme = ThemeManagerCompose.theme.value

        val iconColor =
            if (isEnabled) iconColorState?.normalEnabled ?: theme.palette.elementAccent
            else iconColorState?.normalDisabled ?: theme.palette.elementAccent.withAlpha()

        val backgroundColor =
            if (isEnabled) backgroundColorState?.normalEnabled
                ?: theme.palette.backgroundAdditionalOne
            else backgroundColorState?.normalDisabled
                ?: theme.palette.backgroundAdditionalOne.withAlpha()

        Icon(
            modifier = modifier
                .width(DIMEN_X11)
                .height(DIMEN_X11)
                .clip(CircleShape)
                .background(color = Color(backgroundColor), shape = CircleShape)
                .padding(IconPadding.dp),
            painter = icon,
            contentDescription = contentDescription,
            tint = Color(iconColor),
        )
    }

    private companion object {
        const val IconPadding = 10
    }
}