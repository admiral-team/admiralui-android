package com.admiral.uikit.compose.cell.unit

import androidx.annotation.Size
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.cell.base.CellUnit
import com.admiral.uikit.compose.util.DIMEN_X11
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState

data class TextLabelCellUnit(
    override var unitType: CellUnitType,
    @Size(min = TextMinLength, max = TextMaxLength)
    private val text: String,
    private val textColorState: ColorState? = null,
    private val backgroundColorState: ColorState? = null,
    private val isEnabled: Boolean = true
) : CellUnit {

    @OptIn(ExperimentalTextApi::class)
    @Composable
    override fun Create(modifier: Modifier) {
        val theme = ThemeManagerCompose.theme.value
        val textStyle = ThemeManagerCompose.typography.subhead2

        val textColor =
            if (isEnabled) textColorState?.normalEnabled ?: theme.palette.textStaticWhite
            else textColorState?.normalDisabled ?: theme.palette.textStaticWhite.withAlpha()
        val backgroundColor =
            if (isEnabled) backgroundColorState?.normalEnabled ?: theme.palette.backgroundSecondary
            else backgroundColorState?.normalDisabled ?: theme.palette.backgroundSecondary.withAlpha()

        Box(
            modifier = modifier
                .width(DIMEN_X11)
                .height(DIMEN_X11)
                .clip(CircleShape)
                .background(color = Color(backgroundColor), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = text,
                color = Color(textColor),
                style = textStyle.copy(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
            )
        }
    }

    private companion object {
        const val TextMinLength = 1L
        const val TextMaxLength = 2L
    }
}

@Composable
@Preview()
fun TextLabelCellUnitPreview() {
    TextLabelCellUnit(
        unitType = CellUnitType.LEADING,
        text = "AH"
    ).Create(modifier = Modifier)
}

@Composable
@Preview
fun TextLabelCellUnitDisabledPreview() {
    TextLabelCellUnit(
        unitType = CellUnitType.LEADING,
        isEnabled = false,
        text = "AH"
    ).Create(modifier = Modifier)
}

@Composable
@Preview
fun TextLabelCellUnitSinglePreview() {
    TextLabelCellUnit(
        unitType = CellUnitType.LEADING,
        text = "A"
    ).Create(modifier = Modifier)
}

@Composable
@Preview
fun TextLabelCellUnitCustomTextColorPreview() {
    TextLabelCellUnit(
        unitType = CellUnitType.LEADING,
        text = "AH",
        textColorState = ColorState(ThemeManagerCompose.theme.value.palette.textError)
    ).Create(modifier = Modifier)
}

@Composable
@Preview
fun TextLabelCellUnitCustomTextColorDisabledPreview() {
    TextLabelCellUnit(
        unitType = CellUnitType.LEADING,
        isEnabled = false,
        text = "AH",
        textColorState = ColorState(ThemeManagerCompose.theme.value.palette.textError)
    ).Create(modifier = Modifier)
}

@Composable
@Preview
fun TextLabelCellUnitCustomBackgroundColorPreview() {
    TextLabelCellUnit(
        unitType = CellUnitType.LEADING,
        text = "AH",
        backgroundColorState = ColorState(ThemeManagerCompose.theme.value.palette.backgroundAccentTwo)
    ).Create(modifier = Modifier)
}
