package com.admiral.uikit.compose.calendar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.compose.util.DIMEN_X9
import java.time.DateTimeException
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Suppress("LongParameterList")
fun Calendar(
    modifier: Modifier = Modifier,
    firstMonth: LocalDate = LocalDate.now(),
    lastMonth: LocalDate = LocalDate.now().plusMonths(0),
    isEnabled: Boolean = true,
    monthTextColorState: ColorState? = null,
    weekDayLegendTextColorState: ColorState? = null,
    daySelectedTextColorState: ColorState? = null,
    dayDefaultTextColorState: ColorState? = null,
    dayBackgroundChosenColorState: ColorState? = null,
    dayBackgroundRangeColorState: ColorState? = null,
    dayBackgroundDefaultColorState: ColorState? = null,
    dividerColorState: ColorState? = null,
) {
    val theme = ThemeManagerCompose.theme.value

    val monthColor =
        if (isEnabled) monthTextColorState?.normalEnabled ?: theme.palette.textPrimary
        else monthTextColorState?.normalDisabled ?: theme.palette.textPrimary.withAlpha()

    val weekDayLegendColor =
        if (isEnabled) weekDayLegendTextColorState?.normalEnabled ?: theme.palette.textSecondary
        else weekDayLegendTextColorState?.normalDisabled ?: theme.palette.textSecondary.withAlpha()

    val daySelectedTextColor =
        if (isEnabled) daySelectedTextColorState?.normalEnabled ?: theme.palette.textStaticWhite
        else daySelectedTextColorState?.normalDisabled ?: theme.palette.textStaticWhite.withAlpha()

    val dayDefaultTextColor =
        if (isEnabled) dayDefaultTextColorState?.normalEnabled ?: theme.palette.textPrimary
        else dayDefaultTextColorState?.normalDisabled ?: theme.palette.textPrimary.withAlpha()

    val dayBackgroundChosenColor =
        if (isEnabled) dayBackgroundChosenColorState?.normalEnabled
            ?: theme.palette.backgroundAccent
        else dayBackgroundChosenColorState?.normalDisabled
            ?: theme.palette.backgroundAccent.withAlpha()

    val dayBackgroundRangeColor =
        if (isEnabled) dayBackgroundRangeColorState?.normalEnabled
            ?: theme.palette.backgroundSelected
        else dayBackgroundRangeColorState?.normalDisabled
            ?: theme.palette.backgroundSelected.withAlpha()

    val dayBackgroundDefaultColor =
        if (isEnabled) dayBackgroundDefaultColorState?.normalEnabled
            ?: theme.palette.backgroundBasic
        else dayBackgroundDefaultColorState?.normalDisabled
            ?: theme.palette.backgroundBasic.withAlpha()

    val dividerColor =
        if (isEnabled) dividerColorState?.normalEnabled ?: theme.palette.elementAdditional
        else dividerColorState?.normalDisabled ?: theme.palette.elementAdditional.withAlpha()

    var firstDateChosen: SelectedDay? = null
    var secondDateChosen: SelectedDay? = null

    val curDate = LocalDate.now()

    val months = mutableListOf<LocalDate>()
    var monthsCount = 0L
    while (true) {
        val monthToAdd = firstMonth.plusMonths(monthsCount)
        months.add(monthToAdd)
        monthsCount += 1

        if (monthToAdd == lastMonth) {
            break
        }
    }

    val dates = remember {
        mutableStateListOf<CalendarDay>()
    }
    months.forEach {
        val spaces = when (it.withDayOfMonth(1).dayOfWeek) {
            DayOfWeek.MONDAY -> SPACE_0
            DayOfWeek.TUESDAY -> SPACE_1
            DayOfWeek.WEDNESDAY -> SPACE_2
            DayOfWeek.THURSDAY -> SPACE_3
            DayOfWeek.FRIDAY -> SPACE_4
            DayOfWeek.SATURDAY -> SPACE_5
            DayOfWeek.SUNDAY -> SPACE_6
            else -> 0
        }

        for (i in 1..spaces) {
            dates.add(CalendarDay(0, 0, 0, false))
        }

        for (i in 1..TOTAL_SPACE_IN_A_MONTH - spaces) {
            val date = try {
                it.withDayOfMonth(i)
            } catch (e: DateTimeException) {
                null
            }

            dates.add(CalendarDay(date?.year, date?.monthValue, date?.dayOfMonth, false))
        }
    }

    LazyVerticalGrid(
        modifier = Modifier.padding(start = DIMEN_X4, end = DIMEN_X4),
        columns = GridCells.Fixed(ITEM_COUNT)
    ) {
        months.forEachIndexed { monthIndex, month ->

            // Month Title
            items(ITEM_COUNT) {
                if (it == 0) {
                    val monthTitle =
                        month.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.getDefault())

                    Text(
                        modifier = modifier
                            .padding(top = DIMEN_X2)
                            .fillMaxWidth()
                            .fillMaxSize(), // .fillParentMaxWidth(), todo method doesn't exist after updating the compose version
                        text = monthTitle.replaceFirstChar {
                            if (it.isLowerCase()) {
                                it.titlecase(Locale.getDefault())
                            } else {
                                it.toString()
                            }
                        },
                        color = Color(monthColor),
                        style = ThemeManagerCompose.typography.subtitle1
                    )
                }
            }

            // Week legend
            items(DayOfWeek.values().size) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        modifier = Modifier.padding(top = DIMEN_X5, bottom = DIMEN_X5),
                        text = DayOfWeek.of(it + 1)
                            .getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                        color = Color(weekDayLegendColor),
                        style = ThemeManagerCompose.typography.subhead2
                    )
                }
            }

            // Days
            val items = dates.subList(
                monthIndex * TOTAL_SPACE_IN_A_MONTH,
                (monthIndex + 1) * TOTAL_SPACE_IN_A_MONTH
            )
            itemsIndexed(items) { index, currentDateItem ->
                val indexWithMonth = monthIndex * TOTAL_SPACE_IN_A_MONTH + index

                // Check if it's a space of previous month
                val dayText = try {
                    curDate.withDayOfMonth(currentDateItem.day ?: 0).dayOfMonth
                } catch (e: DateTimeException) {
                    null
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Text of a day
                    val annotatedString = buildAnnotatedString {
                        withStyle(
                            style = ThemeManagerCompose.typography.body1.copy(
                                color = if (currentDateItem == firstDateChosen?.day ||
                                    currentDateItem == secondDateChosen?.day
                                ) {
                                    Color(daySelectedTextColor)
                                } else {
                                    Color(dayDefaultTextColor)
                                }

                            ).toSpanStyle()
                        ) { append("  $dayText  ") }
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(DIMEN_X1))
                            .padding(top = DAY_BOX_PADDINGS.dp, bottom = DAY_BOX_PADDINGS.dp)
                            .width(DIMEN_X9)
                            .height(DIMEN_X9)
                            .background(
                                color =
                                if (dayText == null) {
                                    Color.Transparent
                                } else if (currentDateItem.isChosen == true) {
                                    if (currentDateItem == firstDateChosen?.day ||
                                        currentDateItem == secondDateChosen?.day
                                    ) {
                                        Color(dayBackgroundChosenColor)
                                    } else {
                                        Color(dayBackgroundRangeColor)
                                    }
                                } else {
                                    Color(dayBackgroundDefaultColor)
                                },
                                shape = RoundedCornerShape(DIMEN_X1)
                            ),
                    ) {
                        if (dayText != null) {
                            ClickableText(
                                modifier = Modifier,
                                text = annotatedString,
                                onClick = {
                                    // first date chosen
                                    if (firstDateChosen == null) {
                                        firstDateChosen =
                                            SelectedDay(dates[indexWithMonth], indexWithMonth)
                                        dates[indexWithMonth] = dates[indexWithMonth].apply {
                                            isChosen = true
                                        }

                                        // clicked second date that is after the first choice
                                    } else if (secondDateChosen == null && currentDateItem.isAfter(
                                            firstDateChosen?.day
                                        )
                                    ) {
                                        secondDateChosen =
                                            SelectedDay(dates[indexWithMonth], indexWithMonth)

                                        for (i in (firstDateChosen?.position
                                            ?: 0)..(secondDateChosen?.position ?: 0)) {
                                            dates[i] = dates[i].apply {
                                                isChosen = true
                                            }
                                        }

                                        // clicked second date that is before the first choice while second isn't chosen
                                    } else if (secondDateChosen == null && currentDateItem.isBefore(
                                            firstDateChosen?.day
                                        )
                                    ) {
                                        dates[firstDateChosen?.position ?: 0] =
                                            dates[firstDateChosen?.position ?: 0].apply {
                                                isChosen = false
                                            }
                                        dates[indexWithMonth] = dates[indexWithMonth].apply {
                                            isChosen = true
                                        }

                                        firstDateChosen =
                                            SelectedDay(dates[indexWithMonth], indexWithMonth)

                                        // both dates are chosen clicked at the date that is before the chosen range
                                    } else if (firstDateChosen != null &&
                                        secondDateChosen != null &&
                                        currentDateItem.isBefore(firstDateChosen?.day)
                                    ) {
                                        for (i in indexWithMonth..(secondDateChosen?.position
                                            ?: 0)) {
                                            dates[i] = dates[i].apply {
                                                isChosen = true
                                            }
                                        }

                                        firstDateChosen =
                                            SelectedDay(dates[indexWithMonth], indexWithMonth)

                                        // both dates are chosen clicked at the date that is after the chosen range
                                    } else if (firstDateChosen != null && secondDateChosen != null &&
                                        currentDateItem.isAfter(secondDateChosen?.day)
                                    ) {
                                        for (i in (secondDateChosen?.position
                                            ?: 0)..indexWithMonth) {
                                            dates[i] = dates[i].apply {
                                                isChosen = true
                                            }
                                        }

                                        secondDateChosen =
                                            SelectedDay(dates[indexWithMonth], indexWithMonth)

                                        // both dates are chosen clicked at the date that is in the middle of the chosen range
                                    } else if (firstDateChosen != null &&
                                        secondDateChosen != null &&
                                        currentDateItem.isAfter(firstDateChosen?.day) &&
                                        currentDateItem.isBefore(secondDateChosen?.day)
                                    ) {
                                        for (i in (firstDateChosen?.position
                                            ?: 0)..(secondDateChosen?.position ?: 0)) {
                                            dates[i] = dates[i].apply {
                                                isChosen = false
                                            }
                                        }

                                        firstDateChosen =
                                            SelectedDay(dates[indexWithMonth], indexWithMonth)
                                        dates[indexWithMonth] = dates[indexWithMonth].apply {
                                            isChosen = true
                                        }
                                        secondDateChosen = null
                                    }
                                }
                            )
                        }
                    }
                }
            }

            // Separator
            items(ITEM_COUNT) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Divider(
                        modifier = Modifier.padding(top = DIMEN_X2, bottom = DIMEN_X2),
                        color = Color(dividerColor),
                        thickness = 1.dp
                    )
                }
            }
        }
    }
}

const val SPACE_0 = 0
const val SPACE_1 = 1
const val SPACE_2 = 2
const val SPACE_3 = 3
const val SPACE_4 = 4
const val SPACE_5 = 5
const val SPACE_6 = 6

const val TOTAL_SPACE_IN_A_MONTH = 35

const val ITEM_COUNT = 7
const val DAY_BOX_PADDINGS = 5
