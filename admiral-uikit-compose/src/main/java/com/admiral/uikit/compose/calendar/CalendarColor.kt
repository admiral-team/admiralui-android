package com.admiral.uikit.compose.calendar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha

@Immutable
data class CalendarColors(
    val monthTextEnable: Color,
    val monthTextDisable: Color,
    val weekDayLegendTextEnable: Color,
    val weekDayLegendTextDisable: Color,
    val daySelectedTextEnable: Color,
    val daySelectedTextDisable: Color,
    val dayDefaultTextEnable: Color,
    val dayDefaultTextDisable: Color,
    val dayBackgroundChosenEnable: Color,
    val dayBackgroundChosenDisable: Color,
    val dayBackgroundRangeEnable: Color,
    val dayBackgroundRangeDisable: Color,
    val dayBackgroundDefaultEnable: Color,
    val dayBackgroundDefaultDisable: Color,
    val dividerEnable: Color,
    val dividerDisable: Color,
) {
    @Composable
    fun getMonthTextColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) monthTextEnable else monthTextDisable)

    @Composable
    fun getWeekDayLegendTextColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) weekDayLegendTextEnable else weekDayLegendTextDisable)

    @Composable
    fun getDaySelectedTextColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) daySelectedTextEnable else daySelectedTextDisable)

    @Composable
    fun getDayDefaultTextColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) dayDefaultTextEnable else dayDefaultTextDisable)

    @Composable
    fun getDayBackgroundChosenColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) dayBackgroundChosenEnable else dayBackgroundChosenDisable)

    @Composable
    fun getDayBackgroundRangeColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) dayBackgroundRangeEnable else dayBackgroundRangeDisable)

    @Composable
    fun getDayBackgroundDefaultColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) dayBackgroundDefaultEnable else dayBackgroundDefaultDisable)

    @Composable
    fun getDividerColor(isEnabled: Boolean): State<Color> =
        rememberUpdatedState(if (isEnabled) dividerEnable else dividerDisable)
}

object AdmiralCalendarColor {
    @Composable
    fun calendarColors(
        monthTextEnable: Color = AdmiralTheme.colors.textPrimary,
        monthTextDisable: Color = AdmiralTheme.colors.textPrimary.withAlpha(),
        weekDayLegendTextEnable: Color = AdmiralTheme.colors.textSecondary,
        weekDayLegendTextDisable: Color = AdmiralTheme.colors.textSecondary.withAlpha(),
        daySelectedTextEnable: Color = AdmiralTheme.colors.textStaticWhite,
        daySelectedTextDisable: Color = AdmiralTheme.colors.textStaticWhite.withAlpha(),
        dayDefaultTextEnable: Color = AdmiralTheme.colors.textPrimary,
        dayDefaultTextDisable: Color = AdmiralTheme.colors.textPrimary.withAlpha(),
        dayBackgroundChosenEnable: Color = AdmiralTheme.colors.backgroundAccent,
        dayBackgroundChosenDisable: Color = AdmiralTheme.colors.backgroundAccent.withAlpha(),
        dayBackgroundRangeEnable: Color = AdmiralTheme.colors.backgroundSelected,
        dayBackgroundRangeDisable: Color = AdmiralTheme.colors.backgroundSelected.withAlpha(),
        dayBackgroundDefaultEnable: Color = AdmiralTheme.colors.backgroundBasic,
        dayBackgroundDefaultDisable: Color = AdmiralTheme.colors.backgroundBasic.withAlpha(),
        dividerEnable: Color = AdmiralTheme.colors.elementAdditional,
        dividerDisable: Color = AdmiralTheme.colors.elementAdditional.withAlpha(),
    ) = CalendarColors(
        monthTextEnable = monthTextEnable,
        monthTextDisable = monthTextDisable,
        weekDayLegendTextEnable = weekDayLegendTextEnable,
        weekDayLegendTextDisable = weekDayLegendTextDisable,
        daySelectedTextEnable = daySelectedTextEnable,
        daySelectedTextDisable = daySelectedTextDisable,
        dayDefaultTextEnable = dayDefaultTextEnable,
        dayDefaultTextDisable = dayDefaultTextDisable,
        dayBackgroundChosenEnable = dayBackgroundChosenEnable,
        dayBackgroundChosenDisable = dayBackgroundChosenDisable,
        dayBackgroundRangeEnable = dayBackgroundRangeEnable,
        dayBackgroundRangeDisable = dayBackgroundRangeDisable,
        dayBackgroundDefaultEnable = dayBackgroundDefaultEnable,
        dayBackgroundDefaultDisable = dayBackgroundDefaultDisable,
        dividerEnable = dividerEnable,
        dividerDisable = dividerDisable,
    )
}