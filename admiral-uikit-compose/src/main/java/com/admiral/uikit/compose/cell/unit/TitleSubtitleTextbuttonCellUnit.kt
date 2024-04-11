package com.admiral.uikit.compose.cell.unit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.compose.cell.base.CellUnit
import com.admiral.uikit.compose.links.Link
import com.admiral.uikit.compose.text.TextColor
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.core.components.cell.base.CellUnitType

data class TitleSubtitleTextbuttonCellUnit(
    override var unitType: CellUnitType,
    private val titleText: String,
    private val titleTopography: TextStyle? = null,
    private val titleTextColor: TextColor? = null,

    private val subtitleText: String,
    private val subtitleTopography: TextStyle? = null,
    private val subtitleTextColor: TextColor? = null,

    private val percentText: String,
    private val percentTopography: TextStyle? = null,
    private val percentTextColor: TextColor? = null,
    private val percentBackgroundColorEnabled: Color? = null,
    private val percentBackgroundColorDisabled: Color? = null,

    private val subtitleSecondText: String,
    private val subtitleSecondTopography: TextStyle? = null,
    private val subtitleSecondTextColor: TextColor? = null,

    private val buttonText: String,
    private val buttonTopography: TextStyle? = null,
    private val buttonTextColor: TextColor? = null,

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
                ?: AdmiralTheme.colors.textPrimary
            else subtitleTextColor?.textColorDisabled
                ?: AdmiralTheme.colors.textPrimary.withAlpha()

        val percentTextColor =
            if (isEnabled) percentTextColor?.textColorNormal
                ?: AdmiralTheme.colors.textSecondary
            else percentTextColor?.textColorDisabled
                ?: AdmiralTheme.colors.textSecondary.withAlpha()

        val percentBackgroundColor =
            if (isEnabled) percentBackgroundColorEnabled
                ?: AdmiralTheme.colors.backgroundAdditionalOne
            else percentBackgroundColorDisabled
                ?: AdmiralTheme.colors.backgroundAdditionalOne.withAlpha()

        val subtitleSecondTextColor =
            if (isEnabled) subtitleSecondTextColor?.textColorNormal
                ?: AdmiralTheme.colors.textSecondary
            else subtitleSecondTextColor?.textColorDisabled
                ?: AdmiralTheme.colors.textSecondary.withAlpha()

        if (isEnabled) buttonTextColor?.textColorNormal
            ?: AdmiralTheme.colors.textSecondary
        else buttonTextColor?.textColorDisabled
            ?: AdmiralTheme.colors.textSecondary.withAlpha()

        Column(modifier.padding(paddingValues)) {
            Text(
                text = titleText,
                color = titleTextColor,
                style = titleTopography ?: AdmiralTheme.typography.body1
            )
            Row(
                modifier = Modifier.padding(top = DIMEN_X1),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = subtitleText,
                    color = subtitleTextColor,
                    style = subtitleTopography ?: AdmiralTheme.typography.subhead3
                )
                Text(
                    modifier = Modifier
                        .padding(start = DIMEN_X1)
                        .background(
                            color = percentBackgroundColor,
                            shape = RoundedCornerShape(DIMEN_X1)
                        )
                        .padding(horizontal = DIMEN_X1, vertical = percentTextVerticalPadding.dp),
                    text = percentText,
                    color = percentTextColor,
                    style = percentTopography ?: AdmiralTheme.typography.caption2
                )
            }
            Text(
                modifier = Modifier.padding(top = DIMEN_X1),
                text = subtitleSecondText,
                color = subtitleSecondTextColor,
                style = subtitleSecondTopography ?: AdmiralTheme.typography.subhead2
            )
            Link(
                modifier = Modifier.padding(top = DIMEN_X2),
                linkTextStyle = AdmiralTheme.typography.body1,
                linkText = buttonText,
                isEnable = isEnabled
            )
        }
    }

    private companion object {
        const val percentTextVerticalPadding = 2
    }
}
