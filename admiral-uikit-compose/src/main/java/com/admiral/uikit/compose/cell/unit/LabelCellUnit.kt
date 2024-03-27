package com.admiral.uikit.compose.cell.unit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.cell.base.CellUnit
import com.admiral.uikit.compose.util.DIMEN_X11
import com.admiral.uikit.core.components.cell.base.CellUnitType

data class LabelCellUnit(
    override var unitType: CellUnitType,
    private val icon: Painter,
    private val contentDescription: String = "",
    private val isEnabled: Boolean = true
) : CellUnit {

    @Composable
    override fun Create(modifier: Modifier) {
        Image(
            modifier = modifier
                .width(DIMEN_X11)
                .height(DIMEN_X11)
                .clip(CircleShape),
            painter = icon,
            contentDescription = contentDescription,
            alpha = if (isEnabled) AlphaEnabled else AlphaDisabled
        )
    }

    private companion object {
        const val AlphaDisabled = 0.6F
        const val AlphaEnabled = 1.0F
    }
}


@Composable
@Preview()
fun LabelCellUnitUnitPreview() {
    LabelCellUnit(
        unitType = CellUnitType.LEADING,
        icon = painterResource(id = R.drawable.admiral_ic_google_pay)
    ).Create(modifier = Modifier)
}

@Composable
@Preview()
fun LabelCellUnitDisabledPreview() {
    LabelCellUnit(
        unitType = CellUnitType.LEADING,
        icon = painterResource(id = R.drawable.admiral_ic_google_pay),
        isEnabled = false
    ).Create(modifier = Modifier)
}