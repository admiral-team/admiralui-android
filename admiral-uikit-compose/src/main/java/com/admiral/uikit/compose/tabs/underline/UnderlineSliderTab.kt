package com.admiral.uikit.compose.tabs.underline

import android.annotation.SuppressLint
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
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
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.badge.AdmiralBadgeColor
import com.admiral.uikit.compose.badge.AdmiralBadgePosition
import com.admiral.uikit.compose.badge.BadgedBox
import com.admiral.uikit.compose.tabs.TabItem
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X8

@Composable
fun UnderlineSliderTab(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    tabText: String,
    color: UnderlineTabColor = AdmiralUnderlineTabColorColor.normal(),
    activeTextStyle: TextStyle = AdmiralTheme.typography.subhead2,
    inactiveTextStyle: TextStyle = AdmiralTheme.typography.subhead3,
    isBadgeVisible: Boolean = false,
    isBadgeEnabled: Boolean = true,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    val textColor = color.getTextColor(isEnabled = isEnabled).value
    val textStyle = if (isSelected) activeTextStyle else inactiveTextStyle

    val bottomIndicatorColor =
        if (isSelected) color.getSelectBottomIndicatorColor(isEnabled = isEnabled).value
        else color.unSelectBottomIndicator

    Tab(
        modifier = modifier
            .fillMaxWidth()
            .height(DIMEN_X8)
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
            color = AdmiralBadgeColor.default(),
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

@Composable
fun UnderlineSliderTabList(
    modifier: Modifier = Modifier,
    items: List<String>,
    onCheckedChange: (Int) -> Unit = {},
    isEnabled: Boolean = true,
    selectedIndex: Int
) {
    TabsListSlider(
        selectedIndex,
        modifier,
        items.map { text ->
            TabItem(text = text)
        },
        onCheckedChange,
        isEnabled
    )
}

@Composable
fun UnderlineSliderTabListBadged(
    modifier: Modifier = Modifier,
    items: List<TabItem>,
    onCheckedChange: (Int) -> Unit = {},
    isEnabled: Boolean = true,
    selectedIndex: Int
) {
    TabsListSlider(selectedIndex, modifier, items, onCheckedChange, isEnabled)
}

@Composable
private fun TabsListSlider(
    selectedIndex: Int,
    modifier: Modifier,
    items: List<TabItem>,
    onCheckedChange: (Int) -> Unit,
    isEnabled: Boolean
) {
    val (currentSelectedIndex, setSelectedIndex) = remember {
        mutableIntStateOf(selectedIndex)
    }

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(items) { index, tabItem ->
            UnderlineSliderTab(
                isSelected = index == currentSelectedIndex,
                tabText = tabItem.text,
                isBadgeEnabled = tabItem.isBadgeEnabled,
                isBadgeVisible = tabItem.isBadgeVisible,
                onClick = {
                    setSelectedIndex(index)
                    onCheckedChange.invoke(index)
                },
                isEnabled = isEnabled
            )
        }
    }
}

@Composable
fun UnderlineTabList(
    modifier: Modifier = Modifier,
    items: List<String>,
    onCheckedChange: (Int) -> Unit = {},
    isEnabled: Boolean = true,
    selectedIndex: Int
) {
    TabsListFullWidth(
        selectedIndex,
        modifier,
        items.map { text ->
            TabItem(text = text)
        },
        onCheckedChange,
        isEnabled
    )
}

@Composable
fun UnderlineTabListBadged(
    modifier: Modifier = Modifier,
    items: List<TabItem>,
    onCheckedChange: (Int) -> Unit = {},
    isEnabled: Boolean = true,
    selectedIndex: Int
) {
    TabsListFullWidth(selectedIndex, modifier, items, onCheckedChange, isEnabled)
}

@Composable
private fun TabsListFullWidth(
    selectedIndex: Int,
    modifier: Modifier,
    items: List<TabItem>,
    onCheckedChange: (Int) -> Unit,
    isEnabled: Boolean
) {
    val (currentSelectedIndex, setSelectedIndex) = remember {
        mutableIntStateOf(selectedIndex)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, tabItem ->
            UnderlineSliderTab(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                isSelected = index == currentSelectedIndex,
                tabText = tabItem.text,
                isBadgeVisible = tabItem.isBadgeVisible,
                isBadgeEnabled = tabItem.isBadgeEnabled,
                onClick = {
                    setSelectedIndex(index)
                    onCheckedChange.invoke(index)
                },
                isEnabled = isEnabled
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
private const val MAX_LINES = 1
private const val BADGE_VERTICAL_OFFSET = -6
private const val BADGE_HORIZONTAL_OFFSET = 2
private const val BADGE_TEXT_PADDING_VERTICAL = 6

@Preview
@Composable
private fun UnderlineSliderTabPreview() {
    val tabList = remember {
        (1..22).map {
            "1".repeat(it)
        }
    }
    val tabListWithBadge = remember {
        (1..22).map {
            TabItem(
                text = "1".repeat(it),
                isSelected = it == 1,
                isBadgeVisible = true,
                isBadgeEnabled = true
            )
        }
    }
    val tabListWithBadgeDisabled = remember {
        (1..22).map {
            TabItem(
                text = "1".repeat(it),
                isSelected = it == 1,
                isBadgeVisible = true,
                isBadgeEnabled = false,
            )
        }
    }

    AdmiralTheme {
        Surface(
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = DIMEN_X4)
            ) {
                UnderlineSliderTabList(items = tabList, selectedIndex = 0)
                Spacer(modifier = Modifier.size(DIMEN_X4))
                UnderlineSliderTabList(items = tabList, selectedIndex = 0, isEnabled = false)
                Spacer(modifier = Modifier.size(DIMEN_X4))
                UnderlineSliderTabListBadged(items = tabListWithBadge, selectedIndex = 0)
                Spacer(modifier = Modifier.size(DIMEN_X4))
                UnderlineSliderTabListBadged(
                    items = tabListWithBadge,
                    selectedIndex = 0,
                    isEnabled = false
                )
                Spacer(modifier = Modifier.size(DIMEN_X4))
                UnderlineSliderTabListBadged(
                    items = tabListWithBadgeDisabled,
                    selectedIndex = 0,
                )
                Spacer(modifier = Modifier.size(DIMEN_X4))
                UnderlineSliderTabListBadged(
                    items = tabListWithBadgeDisabled,
                    selectedIndex = 0,
                    isEnabled = false
                )
            }
        }
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
    val sixListBadged = remember {
        (1..6).map {
            TabItem(
                text = "1".repeat(it),
                isSelected = it == 1,
                isBadgeVisible = true,
                isBadgeEnabled = true,
            )
        }.toMutableStateList()
    }

    AdmiralTheme {
        Surface(
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = DIMEN_X4)
            ) {
                UnderlineTabListBadged(items = twoList, selectedIndex = 0)
                Spacer(modifier = Modifier.size(DIMEN_X4))
                UnderlineTabListBadged(items = threeList, isEnabled = false, selectedIndex = 0)
                Spacer(modifier = Modifier.size(DIMEN_X4))
                UnderlineTabListBadged(items = fourList, selectedIndex = 0)
                Spacer(modifier = Modifier.size(DIMEN_X4))
                UnderlineTabListBadged(items = fiveList, selectedIndex = 0, isEnabled = false)
                Spacer(modifier = Modifier.size(DIMEN_X4))
                UnderlineTabListBadged(items = sixList, selectedIndex = 0)
                UnderlineTabListBadged(items = sixListBadged, selectedIndex = 0)
            }
        }
    }
}
