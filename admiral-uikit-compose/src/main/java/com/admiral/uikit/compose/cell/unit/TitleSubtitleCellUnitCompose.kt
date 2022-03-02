package com.admiral.uikit.compose.cell.unit

import androidx.compose.foundation.layout.Column
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
import com.admiral.uikit.compose.util.DIMEN_X1

data class TitleSubtitleCellUnitCompose(
    override var unitType: CellUnitType,
    private val titleText: String,
    private val titleTopography: TextStyle = ThemeManagerCompose.typography.body1,
    private val titleTextColorState: ColorState? = null,
    private val subtitleText: String,
    private val subtitleTopography: TextStyle = ThemeManagerCompose.typography.subhead3,
    private val subtitleTextColorState: ColorState? = null,
    private val paddingValues: PaddingValues = PaddingValues(0.dp),
    private val isEnabled: Boolean = true
) : CellUnitCompose {

    @Composable
    override fun Create(modifier: Modifier) {
        val theme = ThemeManagerCompose.theme.value

        val titleTextColor =
            if (isEnabled) titleTextColorState?.normalEnabled ?: theme.palette.textPrimary
            else titleTextColorState?.normalDisabled ?: theme.palette.textPrimary.withAlpha()

        val subtitleTextColor =
            if (isEnabled) titleTextColorState?.normalEnabled ?: theme.palette.textSecondary
            else titleTextColorState?.normalDisabled ?: theme.palette.textSecondary.withAlpha()

        Column(modifier.padding(paddingValues)) {
            Text(modifier = Modifier, text = titleText, color = Color(titleTextColor), style = titleTopography)
            Text(
                modifier = Modifier.padding(top = DIMEN_X1),
                text = subtitleText,
                color = Color(subtitleTextColor),
                style = subtitleTopography
            )
        }
    }
}