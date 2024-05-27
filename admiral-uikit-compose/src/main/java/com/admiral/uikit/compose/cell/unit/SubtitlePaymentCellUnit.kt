package com.admiral.uikit.compose.cell.unit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.cell.base.CellUnit
import com.admiral.uikit.compose.text.AdmiralTextColor
import com.admiral.uikit.compose.text.Text
import com.admiral.uikit.compose.text.TextColor
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.core.components.cell.base.CellUnitType

@Immutable
data class SubtitlePaymentCellUnit(
    override var unitType: CellUnitType = CellUnitType.LEADING,
    val paymentIcon: Painter? = null,
    val contentDescription: String = "",
    val subtitle: String = "",
    val textStyle: TextStyle? = null,
    val subtitleColor: TextColor? = null,
    val isEnabled: Boolean = true
) : CellUnit {

    @Composable
    override fun Create(modifier: Modifier) {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            paymentIcon?.let {
                Image(
                    painter = paymentIcon,
                    contentDescription = contentDescription,
                    alpha = if (isEnabled) ALPHA_ENABLE else ALPHA_DISABLE
                )
            }
            Spacer(
                modifier = Modifier
                    .size(DIMEN_X1)
                    .width(DIMEN_X1)
            )
            Text(
                text = subtitle,
                style = textStyle ?: AdmiralTheme.typography.subtitle3,
                color = subtitleColor ?: AdmiralTextColor.primary()
            )
        }
    }

    private companion object {
        const val ALPHA_DISABLE = 0.6F
        const val ALPHA_ENABLE = 1.0F
    }
}

@Preview
@Composable
private fun SubtitlePaymentCellUnitPreview() {
    AdmiralTheme {
        Surface(
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            Column {
                SubtitlePaymentCellUnit(
                    subtitle = "Subtitle",
                    paymentIcon = painterResource(id = R.drawable.admiral_ic_flag_place),
                ).Create(modifier = Modifier)
                SubtitlePaymentCellUnit(
                    subtitle = "Subtitle",
                    isEnabled = false,
                    paymentIcon = painterResource(id = R.drawable.admiral_ic_flag_place),
                ).Create(modifier = Modifier)
            }
        }
    }
}
