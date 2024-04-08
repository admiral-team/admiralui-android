package com.admiral.uikit.compose.cell.unit

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.compose.cell.base.CellUnit
import com.admiral.uikit.core.components.cell.base.CellUnitType

data class TitleCellUnit(
    override var unitType: CellUnitType,
    private val text: String,
    private val typography: TextStyle? = null,
    private val isEnabled: Boolean = true,
    private val paddingValues: PaddingValues = PaddingValues(0.dp),
    private val textColorEnable: Color? = null,
    private val textColorDisable: Color? = null
) : CellUnit {

    @Composable
    override fun Create(modifier: Modifier) {
        val textColor =
            if (isEnabled) textColorEnable ?: AdmiralTheme.colors.textPrimary
            else textColorDisable ?: AdmiralTheme.colors.textPrimary.withAlpha()

        Text(
            modifier = modifier.padding(paddingValues),
            text = text,
            color = textColor,
            style = typography ?: AdmiralTheme.typography.body1
        )
    }
}
