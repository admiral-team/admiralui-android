package com.admiral.uikit.compose.tabs.underline

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.badge.AdmiralBadgeColor
import com.admiral.uikit.compose.badge.AdmiralBadgePosition
import com.admiral.uikit.compose.badge.BadgedBox
import com.admiral.uikit.compose.tabs.outline.TabItem
import com.admiral.uikit.compose.tabs.outline.selectNewTab

@Composable
fun UnderlineSliderTab(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    tabText: String,
    color: UnderlineTabColor = AdmiralUnderlineTabColorColor.normal(),
    activeTextStyle: TextStyle = ThemeManagerCompose.typography.subhead2,
    inactiveTextStyle: TextStyle = ThemeManagerCompose.typography.subhead3,
    isBadgeVisible: Boolean = false,
    isBadgeEnabled: Boolean = true,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    val textColor = if (isEnabled) color.textEnable else color.textDisable
    val textStyle = if (isSelected) activeTextStyle else inactiveTextStyle
    val bottomIndicatorColor = if (isEnabled) {
        if (isSelected) color.selectBottomIndicatorEnabled
        else color.unSelectBottomIndicator
    } else {
        if (isSelected) color.selectBottomIndicatorEnabled
        else color.unSelectBottomIndicator
    }
    Tab(
        modifier = modifier
            .fillMaxWidth()
            .height(TAB_HEIGHT.dp)
            .bottomBorder(
                BOTTOM_INDICATOR_WIDTH.dp,
                color = bottomIndicatorColor
            ),
        selected = isSelected,
        enabled = isEnabled,
        onClick = {
            onClick()
        },
        text = {
            when {
                isBadgeVisible -> UnderlineSliderTabWithBadge(
                    text = tabText,
                    textColor = textColor,
                    textStyle = textStyle,
                    isBadgeEnabled = isBadgeEnabled,
                )

                else -> UnderlineSliderTab(
                    text = tabText,
                    textColor = textColor,
                    textStyle = textStyle,
                )
            }
        }
    )
}

@Composable
private fun UnderlineSliderTab(
    text: String,
    textColor: Color,
    textStyle: TextStyle,
) {
    Text(
        text = text,
        style = textStyle,
        color = textColor,
        maxLines = MAX_LINES,
    )
}

@Composable
private fun UnderlineSliderTabWithBadge(
    text: String,
    textColor: Color,
    textStyle: TextStyle,
    isBadgeEnabled: Boolean,
) {
    Box(
        modifier = Modifier
            .padding(
                vertical = BADGE_TEXT_PADDING_VERTICAL.dp
            )

    ) {
        BadgedBox(
            color = AdmiralBadgeColor.normal(),
            isEnable = isBadgeEnabled,
            content = null,
            position = AdmiralBadgePosition.default(
                badgeVerticalOffset = BADGE_VERTICAL_OFFSET.dp,
                badgeHorizontalOffset = BADGE_HORIZONTAL_OFFSET.dp
            )
        ) {
            Text(
                modifier = Modifier,
                text = text,
                color = textColor,
                style = textStyle,
                maxLines = MAX_LINES,
            )
        }
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
private fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

private const val BOTTOM_INDICATOR_WIDTH = 2
private const val TAB_HEIGHT = 32
private const val MAX_LINES = 1
private const val BADGE_VERTICAL_OFFSET = -6
private const val BADGE_HORIZONTAL_OFFSET = 2
private const val BADGE_TEXT_PADDING_VERTICAL = 6


@Composable
private fun UnderlineSliderTabList(
    list: MutableList<TabItem>,
    isBadgeEnabled: Boolean = true,
    isBadgeVisible: Boolean = false,
    isEnabled: Boolean = true,
) {
    LazyRow(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(list) { index, tabItem ->
            UnderlineSliderTab(
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

@Composable
private fun UnderlineCenterTabList(
    list: MutableList<TabItem>,
    isBadgeEnabled: Boolean = true,
    isBadgeVisible: Boolean = false,
    isEnabled: Boolean = true,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        list.forEachIndexed { index, tabItem ->
            UnderlineSliderTab(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
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
private fun UnderlineSliderTabPreview() {
    val tabList = remember {
        (1..22).map {
            TabItem(
                text = "1".repeat(it),
                isSelected = it == 1,
            )
        }.toMutableStateList()
    }

    Column(
        modifier = Modifier
            .background(color = Color(ThemeManagerCompose.theme.value.palette.backgroundBasic))
            .padding(vertical = 16.dp)
    ) {
        UnderlineSliderTabList(tabList)
        Spacer(modifier = Modifier.size(16.dp))
        UnderlineSliderTabList(tabList, isEnabled = false)
        Spacer(modifier = Modifier.size(16.dp))
        UnderlineSliderTabList(tabList, isBadgeVisible = true)
        Spacer(modifier = Modifier.size(16.dp))
        UnderlineSliderTabList(tabList, isBadgeVisible = true, isEnabled = false)
        Spacer(modifier = Modifier.size(16.dp))
        UnderlineSliderTabList(tabList, isBadgeVisible = true, isBadgeEnabled = false)
        Spacer(modifier = Modifier.size(16.dp))
        UnderlineSliderTabList(tabList, isBadgeVisible = true, isBadgeEnabled = false, isEnabled = false)
    }
}

@Preview
@Composable
private fun UnderlineCenterTabPreview() {
    val twoList = remember {
        (1..2).map {
            TabItem(
                text = "1".repeat(it),
                isSelected = it == 1,
            )
        }.toMutableStateList()
    }
    val threeList = remember {
        (1..3).map {
            TabItem(
                text = "1".repeat(it),
                isSelected = it == 1,
            )
        }.toMutableStateList()
    }
    val fourList = remember {
        (1..4).map {
            TabItem(
                text = "1".repeat(it),
                isSelected = it == 1,
            )
        }.toMutableStateList()
    }
    val fiveList = remember {
        (1..5).map {
            TabItem(
                text = "1".repeat(it),
                isSelected = it == 1,
            )
        }.toMutableStateList()
    }
    val sixList = remember {
        (1..6).map {
            TabItem(
                text = "1".repeat(it),
                isSelected = it == 1,
            )
        }.toMutableStateList()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(ThemeManagerCompose.theme.value.palette.backgroundBasic))
            .padding(vertical = 16.dp)
    ) {
        UnderlineCenterTabList(twoList)
        Spacer(modifier = Modifier.size(16.dp))
        UnderlineCenterTabList(threeList, isEnabled = false)
        Spacer(modifier = Modifier.size(16.dp))
        UnderlineCenterTabList(fourList, isBadgeVisible = true)
        Spacer(modifier = Modifier.size(16.dp))
        UnderlineCenterTabList(fiveList, isBadgeVisible = true, isEnabled = false)
        Spacer(modifier = Modifier.size(16.dp))
        UnderlineCenterTabList(sixList, isBadgeVisible = true, isBadgeEnabled = false)
        UnderlineCenterTabList(sixList)
    }
}