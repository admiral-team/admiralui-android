package com.admiral.uikit.compose.cell.unit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.common.components.cell.base.CellUnitType
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.compose.cell.base.CellUnitCompose

data class TitleCellUnitCompose(
    override var unitType: CellUnitType,
    private val text: String,
    private val typography: TextStyle = ThemeManagerCompose.typography.body1,
    private val isEnabled: Boolean = true,
    private val paddingValues: PaddingValues = PaddingValues(0.dp),
    private val textColorState: ColorState? = null
) : CellUnitCompose {

    @Composable
    override fun Create(modifier: Modifier) {
        val theme = ThemeManagerCompose.theme.value

        val textColor =
            if (isEnabled) textColorState?.normalEnabled ?: theme.palette.textPrimary
            else textColorState?.normalDisabled ?: theme.palette.textPrimary.withAlpha()

        Text(modifier = modifier.padding(paddingValues), text = text, color = Color(textColor), style = typography)
    }
}
