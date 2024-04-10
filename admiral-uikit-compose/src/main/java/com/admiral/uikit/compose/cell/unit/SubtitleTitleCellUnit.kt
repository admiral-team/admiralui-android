package com.admiral.uikit.compose.cell.unit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.compose.cell.base.CellUnit
import com.admiral.uikit.compose.text.TextColor
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.core.components.cell.base.CellUnitType

data class SubtitleTitleCellUnit(
    override var unitType: CellUnitType,
    private val titleText: String,
    private val titleTopography: TextStyle? = null,
    private val titleTextColor: TextColor? = null,
    private val subtitleText: String,
    private val subtitleTopography: TextStyle? = null,
    private val subtitleTextColor: TextColor? = null,
    private val paddingValues: PaddingValues = PaddingValues(0.dp),
    private val isEnabled: Boolean = true
) : CellUnit {

    @Composable
    override fun Create(modifier: Modifier) {
        val titleTextColor =
            if (isEnabled) titleTextColor?.textColorNormal ?: AdmiralTheme.colors.textPrimary
            else titleTextColor?.textColorDisabled
                ?: AdmiralTheme.colors.textPrimary.withAlpha()

        val subtitleTextColor =
            if (isEnabled) subtitleTextColor?.textColorNormal
                ?: AdmiralTheme.colors.textSecondary
            else subtitleTextColor?.textColorDisabled
                ?: AdmiralTheme.colors.textSecondary.withAlpha()

        Column(modifier.padding(paddingValues)) {
            Text(
                modifier = Modifier.padding(top = DIMEN_X1),
                text = subtitleText,
                color = subtitleTextColor,
                style = subtitleTopography ?: AdmiralTheme.typography.subhead3
            )
            Text(
                modifier = Modifier,
                text = titleText,
                color = titleTextColor,
                style = titleTopography ?: AdmiralTheme.typography.body1
            )
        }
    }
}
