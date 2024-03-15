package com.admiral.uikit.compose.tabs.outline

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.badge.AdmiralBadgeColor
import com.admiral.uikit.compose.badge.AdmiralBadgePosition
import com.admiral.uikit.compose.badge.BadgedBox
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X4

private const val MAX_LINES = 1
private val BorderActiveWidth = 2.dp
private val BorderInactiveWidth = 1.dp
private val BadgeVerticalOffset = (-9).dp
private val BadgeHorizontalOffset = (-1).dp

@Composable
fun OutlineSliderTab(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    tabText: String,
    color: OutlineSliderTabColor = AdmiralOutlineSliderTabColor.normal(),
    activeTextStyle: TextStyle = ThemeManagerCompose.typography.subhead2,
    inactiveTextStyle: TextStyle = ThemeManagerCompose.typography.subhead3,
    isBadgeVisible: Boolean = false,
    isBadgeEnabled: Boolean = true,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {

    val borderColor = if (isEnabled) {
        if (isSelected) color.selectStrokeEnabled
        else color.unSelectStrokeEnable
    } else {
        if (isSelected) color.selectStrokeDisable
        else color.unSelectStrokeDisable
    }
    val textColor = if (isEnabled) color.textEnable else color.textDisable
    val textStyle = if (isSelected) activeTextStyle else inactiveTextStyle
    val shape = RoundedCornerShape(DIMEN_X2)
    val borderWidth = if (isSelected) BorderActiveWidth else BorderInactiveWidth

    Tab(
        modifier = modifier
            .fillMaxWidth()
            .border(borderWidth, borderColor, shape)
            .clip(shape),
        selected = isSelected,
        enabled = isEnabled,
        onClick = { onClick() },
        content = {
            when {
                isBadgeVisible -> OutlineSliderTabWithBadge(
                    text = tabText,
                    textColor = textColor,
                    textStyle = textStyle,
                    isBadgeEnabled = isBadgeEnabled,
                )

                else -> OutlineSliderTab(
                    text = tabText,
                    textColor = textColor,
                    textStyle = textStyle,
                )
            }
        }
    )
}

@Composable
private fun OutlineSliderTab(
    text: String,
    textColor: Color,
    textStyle: TextStyle,
) {
    Text(
        modifier = Modifier
            .padding(DIMEN_X2)
            .wrapContentHeight(align = Alignment.CenterVertically),
        text = text,
        style = textStyle,
        color = textColor,
        maxLines = MAX_LINES,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun OutlineSliderTabWithBadge(
    text: String,
    textColor: Color,
    textStyle: TextStyle,
    isBadgeEnabled: Boolean,
) {
    Row(
        modifier = Modifier
            .padding(
                end = DIMEN_X4,
                start = DIMEN_X2,
                top = DIMEN_X2,
                bottom = DIMEN_X2
            )
    ) {
        BadgedBox(
            color = AdmiralBadgeColor.normal(),
            isEnable = isBadgeEnabled,
            content = null,
            position = AdmiralBadgePosition.default(
                badgeVerticalOffset = BadgeVerticalOffset,
                badgeHorizontalOffset = BadgeHorizontalOffset
            )
        ) {
            Text(
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .padding(horizontal = DIMEN_X2),
                text = text,
                color = textColor,
                style = textStyle,
                maxLines = MAX_LINES,
            )
        }
    }
}

@Immutable
data class TabItem(
    val text: String,
    val isSelected: Boolean = false,
)

@Composable
private fun OutlineSliderTabList(
    list: MutableList<TabItem>,
    isBadgeEnabled: Boolean = true,
    isBadgeVisible: Boolean = false,
    isEnabled: Boolean = true,
) {
    LazyRow(
        modifier = Modifier
            .padding(horizontal = DIMEN_X4),
        horizontalArrangement = Arrangement.spacedBy(DIMEN_X2),
    ) {
        itemsIndexed(list) { index, tabItem ->
            OutlineSliderTab(
                isSelected = tabItem.isSelected,
                tabText = tabItem.text,
                onClick = { selectNewTab(list, list[index]) },
                isEnabled = isEnabled,
                isBadgeVisible = isBadgeVisible,
                isBadgeEnabled = isBadgeEnabled,
            )
        }
    }
}

@Preview
@Composable
private fun OutlineSliderTabPreview() {
    val tabList = remember {
        (0..20).map {
            TabItem(
                text = "Tab $it",
                isSelected = it == 0,
            )
        }.toMutableStateList()
    }

    Column(
        modifier = Modifier
            .background(color = Color(ThemeManagerCompose.theme.value.palette.backgroundBasic))
            .padding(vertical = DIMEN_X4)
    ) {
        OutlineSliderTabList(tabList)
        Spacer(modifier = Modifier.size(DIMEN_X4))
        OutlineSliderTabList(tabList, isEnabled = false)
        Spacer(modifier = Modifier.size(DIMEN_X4))
        OutlineSliderTabList(tabList, isBadgeVisible = true)
        Spacer(modifier = Modifier.size(DIMEN_X4))
        OutlineSliderTabList(tabList, isBadgeVisible = true, isEnabled = false)
        Spacer(modifier = Modifier.size(DIMEN_X4))
        OutlineSliderTabList(tabList, isBadgeVisible = true, isBadgeEnabled = false)
        Spacer(modifier = Modifier.size(DIMEN_X4))
        OutlineSliderTabList(tabList, isBadgeVisible = true, isBadgeEnabled = false, isEnabled = false)
    }
}

fun selectNewTab(list: MutableList<TabItem>, tabItemSelected: TabItem) {
    val indexOfLastSelected = list.indexOf(list.find { it.isSelected })
    val indexOfItem = list.indexOf(tabItemSelected)

    if (indexOfItem != indexOfLastSelected) {
        if (indexOfLastSelected != -1) {
            list[indexOfLastSelected] = list[indexOfLastSelected].copy(isSelected = false)
        }

        if (indexOfItem != -1) {
            list[indexOfItem] = list[indexOfItem].copy(isSelected = true)
        }
    }
}