package com.admiral.uikit.compose.cell.unit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.compose.cell.base.CellUnit
import com.admiral.uikit.compose.text.TextColor
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.core.components.cell.base.CellUnitType

data class TextMessageCellUnit(
    override var unitType: CellUnitType,
    private val titleText: String,
    private val titleTopography: TextStyle? = null,
    private val titleTextColor: TextColor? = null,

    private val titleMoreText: String,
    private val titleMoreTopography: TextStyle? = null,
    private val titleMoreTextColor: TextColor? = null,

    private val sumText: String,
    private val sumTopography: TextStyle? = null,
    private val sumTextColor: TextColor? = null,

    private val sumMoreText: String,
    private val sumMoreTopography: TextStyle? = null,
    private val sumMoreTextColor: TextColor? = null,

    private val subtitleText: String,
    private val subtitleTopography: TextStyle? = null,
    private val subtitleTextColor: TextColor? = null,

    private val percentText: String,
    private val percentTopography: TextStyle? = null,
    private val percentTextColor: TextColor? = null,
    private val percentBackgroundColorEnabled: Color? = null,
    private val percentBackgroundColorDisabled: Color? = null,

    private val messageText: String,
    private val messageTopography: TextStyle? = null,
    private val messageTextColor: TextColor? = null,
    private val messageBackgroundColorEnabled: Color? = null,
    private val messageBackgroundColorDisabled: Color? = null,

    private val icon: Painter,
    private val iconContentDescription: String = "",
    private val iconColorEnabled: Color? = null,
    private val iconColorDisabled: Color? = null,

    private val paddingValues: PaddingValues = PaddingValues(0.dp),
    private val isEnabled: Boolean = true
) : CellUnit {

    @Composable
    override fun Create(modifier: Modifier) {
        val titleTextColor =
            if (isEnabled) titleTextColor?.textColorNormal ?: AdmiralTheme.colors.textPrimary
            else titleTextColor?.textColorDisabled
                ?: AdmiralTheme.colors.textPrimary.withAlpha()

        val titleMoreTextColor =
            if (isEnabled) titleMoreTextColor?.textColorNormal ?: AdmiralTheme.colors.textSecondary
            else titleMoreTextColor?.textColorDisabled
                ?: AdmiralTheme.colors.textSecondary.withAlpha()

        val sumTextColor =
            if (isEnabled) sumTextColor?.textColorNormal
                ?: AdmiralTheme.colors.textAccent
            else sumTextColor?.textColorDisabled
                ?: AdmiralTheme.colors.textAccent.withAlpha()

        val sumMoreTextColor =
            if (isEnabled) sumMoreTextColor?.textColorNormal
                ?: AdmiralTheme.colors.textAccentAdditional
            else sumMoreTextColor?.textColorDisabled
                ?: AdmiralTheme.colors.textAccentAdditional.withAlpha()

        val subtitleTextColor =
            if (isEnabled) subtitleTextColor?.textColorNormal
                ?: AdmiralTheme.colors.textSecondary
            else subtitleTextColor?.textColorDisabled
                ?: AdmiralTheme.colors.textSecondary.withAlpha()

        val percentTextColor =
            if (isEnabled) percentTextColor?.textColorNormal
                ?: AdmiralTheme.colors.textSecondary
            else percentTextColor?.textColorDisabled
                ?: AdmiralTheme.colors.textSecondary.withAlpha()

        val percentBackgroundColor =
            if (isEnabled) percentBackgroundColorEnabled ?: AdmiralTheme.colors.backgroundAdditionalOne
            else percentBackgroundColorDisabled ?: AdmiralTheme.colors.backgroundAdditionalOne.withAlpha()

        val messageTextColor =
            if (isEnabled) messageTextColor?.textColorNormal
                ?: AdmiralTheme.colors.textPrimary
            else messageTextColor?.textColorDisabled
                ?: AdmiralTheme.colors.textPrimary.withAlpha()

        val messageBackgroundColor =
            if (isEnabled) messageBackgroundColorEnabled ?: AdmiralTheme.colors.backgroundAdditionalOne
            else messageBackgroundColorDisabled ?: AdmiralTheme.colors.backgroundAdditionalOne.withAlpha()

        val iconColor =
            if (isEnabled) iconColorEnabled ?: AdmiralTheme.colors.elementPrimary
            else iconColorDisabled ?: AdmiralTheme.colors.elementPrimary.withAlpha()

        Column(modifier.padding(paddingValues)) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = titleText,
                    color = titleTextColor,
                    style = titleTopography ?: AdmiralTheme.typography.body1
                )
                Text(
                    modifier = Modifier.padding(start = DIMEN_X1),
                    text = titleMoreText,
                    color = titleMoreTextColor,
                    style = titleMoreTopography ?: AdmiralTheme.typography.subhead3
                )
            }
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = sumText,
                    color = sumTextColor,
                    style = sumTopography ?: AdmiralTheme.typography.subtitle2
                )
                Text(
                    modifier = Modifier.padding(start = DIMEN_X1),
                    text = sumMoreText,
                    color = sumMoreTextColor,
                    style = titleMoreTopography ?: AdmiralTheme.typography.subhead3
                )
                Icon(
                    modifier = Modifier
                        .padding(start = DIMEN_X1)
                        .size(iconSize.dp),
                    painter = icon,
                    contentDescription = iconContentDescription,
                    tint = iconColor
                )
            }
            Row(modifier = Modifier.padding(top = DIMEN_X1), verticalAlignment = Alignment.Bottom) {
                Text(
                    text = subtitleText,
                    color = subtitleTextColor,
                    style = subtitleTopography ?: AdmiralTheme.typography.subhead2
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
                modifier = Modifier
                    .padding(top = DIMEN_X2)
                    .background(
                        color = messageBackgroundColor,
                        shape = RoundedCornerShape(0.dp, DIMEN_X4, DIMEN_X4, DIMEN_X2)
                    )
                    .padding(horizontal = DIMEN_X4, vertical = DIMEN_X1),
                text = messageText,
                color = messageTextColor,
                style = messageTopography ?: AdmiralTheme.typography.subhead3
            )
        }
    }

    private companion object {
        const val percentTextVerticalPadding = 2
        const val iconSize = 14
    }
}
