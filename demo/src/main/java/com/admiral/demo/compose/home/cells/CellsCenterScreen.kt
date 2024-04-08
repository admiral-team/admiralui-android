package com.admiral.demo.compose.home.cells

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.demo.R
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.appbar.AppBar
import com.admiral.uikit.compose.cell.BaseCell
import com.admiral.uikit.compose.cell.unit.IconCellUnit
import com.admiral.uikit.compose.cell.unit.SubtitleTitleCellUnit
import com.admiral.uikit.compose.cell.unit.TextMessageCellUnit
import com.admiral.uikit.compose.cell.unit.TitleCellUnit
import com.admiral.uikit.compose.cell.unit.TitleSubtitleCellUnit
import com.admiral.uikit.compose.cell.unit.TitleSubtitleTextbuttonCellUnit
import com.admiral.uikit.compose.tabs.outline.OutlineSliderTabList
import com.admiral.uikit.compose.tabs.outline.TabItem
import com.admiral.uikit.core.components.cell.base.CellUnitType

@Composable
@Suppress("LongMethod")
fun CellsCenterScreen(
    onBackClick: () -> Unit = {}
) {
    val (isEnabled, setValue) = remember { mutableStateOf(true) }
    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AppBar(
                navIcon = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
                title = stringResource(id = R.string.cells_center_title),
                titleArrangement = Arrangement.Center
            )
        }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .verticalScroll(state = rememberScrollState())
        ) {
            OutlineSliderTabList(
                mutableListOf(
                    TabItem("Default", true),
                    TabItem("Disabled", false),
                ),
                onClick = {
                    setValue(it == 0)
                }
            )
            BaseCell(
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(id = R.string.cell_title),
                        isEnabled = isEnabled,
                        unitType = CellUnitType.LEADING
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                children = listOf(
                    TitleSubtitleCellUnit(
                        titleText = stringResource(id = R.string.cell_title),
                        subtitleText = stringResource(id = R.string.cell_subtitle),
                        isEnabled = isEnabled,
                        unitType = CellUnitType.LEADING
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                children = listOf(
                    SubtitleTitleCellUnit(
                        titleText = stringResource(id = R.string.cell_title),
                        subtitleText = stringResource(id = R.string.cell_subtitle),
                        isEnabled = isEnabled,
                        unitType = CellUnitType.LEADING
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                children = listOf(
                    TextMessageCellUnit(
                        titleText = stringResource(R.string.cell_title),
                        titleMoreText = stringResource(R.string.cell_more),
                        sumText = stringResource(R.string.cell_summ),
                        sumMoreText = stringResource(R.string.cell_more),
                        subtitleText = stringResource(R.string.cell_subtitle),
                        percentText = stringResource(R.string.cell_percent),
                        messageText = stringResource(R.string.cell_message),
                        icon = painterResource(id = R.drawable.admiral_ic_info_outline),
                        isEnabled = isEnabled,
                        unitType = CellUnitType.LEADING
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                children = listOf(
                    TitleSubtitleTextbuttonCellUnit(
                        titleText = stringResource(R.string.cell_title),
                        subtitleText = stringResource(R.string.cell_subtitle),
                        percentText = stringResource(R.string.cell_percent),
                        subtitleSecondText = stringResource(R.string.cell_subtitle_second),
                        buttonText = stringResource(R.string.cell_button),
                        isEnabled = isEnabled,
                        unitType = CellUnitType.LEADING
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
        }
    }
}

@Preview
@Composable
fun CellsCenterScreenPreview() {
    CellsCenterScreen()
}