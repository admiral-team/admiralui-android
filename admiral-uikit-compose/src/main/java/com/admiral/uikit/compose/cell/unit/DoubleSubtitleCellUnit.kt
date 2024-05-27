package com.admiral.uikit.compose.cell.unit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.cell.base.CellUnit
import com.admiral.uikit.compose.text.AdmiralTextColor
import com.admiral.uikit.compose.text.Text
import com.admiral.uikit.compose.text.TextColor
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.core.components.cell.base.CellUnitType

@Immutable
data class DoubleSubtitleCellUnit(
    override var unitType: CellUnitType,
    val subtitleTop: String = "",
    val subtitleBottom: String = "",
    val subtitleTopColor: TextColor? = null,
    val subtitleBottomColor: TextColor? = null,
    val topTextStyle: TextStyle? = null,
    val bottomTextStyle: TextStyle? = null,
    val isEnabled: Boolean = true,
) : CellUnit {
    @Composable
    override fun Create(modifier: Modifier) {

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = subtitleTop,
                style = topTextStyle ?: AdmiralTheme.typography.subtitle3,
                color = subtitleTopColor ?: AdmiralTextColor.primary()
            )
            Spacer(
                modifier = Modifier
                    .size(DIMEN_X1)
                    .height(DIMEN_X1)
            )
            Text(
                text = subtitleBottom,
                style = bottomTextStyle ?: AdmiralTheme.typography.subtitle3,
                color = subtitleBottomColor ?: AdmiralTextColor.primary()
            )
        }
    }
}

@Preview
@Composable
private fun DoubleSubtitleCellUnitPreview() {
    AdmiralTheme {
        Column {
            DoubleSubtitleCellUnit(
                unitType = CellUnitType.LEADING,
                subtitleTop = "Date",
                subtitleBottom = "Percent",
            ).Create(modifier = Modifier)
            DoubleSubtitleCellUnit(
                unitType = CellUnitType.LEADING,
                subtitleTop = "Date",
                subtitleBottom = "Percent",
                isEnabled = false,
            ).Create(modifier = Modifier)
        }
    }
}
