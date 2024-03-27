package com.admiral.uikit.compose.cell.unit

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.cell.base.CellUnit
import com.admiral.uikit.core.components.cell.base.CellUnitType

data class CardCellUnit(
    override var unitType: CellUnitType,
    private val icon: Painter,
    private val contentDescription: String = "",
    private val isEnabled: Boolean = true
) : CellUnit {

    @Composable
    override fun Create(modifier: Modifier) {
        Image(
            modifier = modifier,
            painter = icon,
            contentDescription = contentDescription,
            alpha = if (isEnabled) ALPHA_ENABLED else ALPHA_DISABLED
        )
    }

    private companion object {
        const val ALPHA_DISABLED = 0.6F
        const val ALPHA_ENABLED = 1.0F
    }
}

@Composable
@Preview()
fun CardCellUnitPreview() {
    CardCellUnit(
        unitType = CellUnitType.LEADING,
        icon = painterResource(id = R.drawable.admiral_ic_google_pay)
    ).Create(modifier = Modifier)
}

@Composable
@Preview()
fun CardCellUnitDisabledPreview() {
    CardCellUnit(
        unitType = CellUnitType.LEADING,
        icon = painterResource(id = R.drawable.admiral_ic_google_pay),
        isEnabled = false
    ).Create(modifier = Modifier)
}