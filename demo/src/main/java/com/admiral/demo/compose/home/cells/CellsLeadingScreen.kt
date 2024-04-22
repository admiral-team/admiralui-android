package com.admiral.demo.compose.home.cells

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.demo.R
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.cell.BaseCell
import com.admiral.uikit.compose.cell.unit.CardCellUnit
import com.admiral.uikit.compose.cell.unit.IconBackgroundCellUnit
import com.admiral.uikit.compose.cell.unit.IconCellUnit
import com.admiral.uikit.compose.cell.unit.LabelCellUnit
import com.admiral.uikit.compose.cell.unit.TextLabelCellUnit
import com.admiral.uikit.compose.cell.unit.TitleCellUnit
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.core.components.cell.base.CellUnitType

@Composable
@Suppress("LongMethod")
fun CellsLeadingScreen(
    onBackClick: () -> Unit = {}
) {
    val (isEnabled, setValue) = remember { mutableStateOf(true) }
    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
                title = stringResource(id = R.string.cells_leading_title),
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(state = rememberScrollState())
        ) {
            StandardTab(
                modifier = Modifier.padding(horizontal = DIMEN_X4),
                items = mutableListOf(
                    stringResource(id = R.string.tabs_default),
                    stringResource(id = R.string.tabs_disabled),
                ),
                selectedIndex = 0,
                onCheckedChange = {
                    setValue(it == 0)
                }
            )
            BaseCell(
                children = listOf(
                    CardCellUnit(
                        icon = painterResource(id = R.drawable.test_ic_card_start),
                        isEnabled = isEnabled,
                        unitType = CellUnitType.LEADING
                    ),
                    TitleCellUnit(
                        text = stringResource(R.string.cells_card_place),
                        isEnabled = isEnabled,
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                children = listOf(
                    LabelCellUnit(
                        icon = painterResource(id = R.drawable.ic_rnb_one),
                        unitType = CellUnitType.LEADING,
                        isEnabled = isEnabled
                    ),
                    TitleCellUnit(
                        text = stringResource(R.string.cells_card_label),
                        isEnabled = isEnabled,
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                children = listOf(
                    TextLabelCellUnit(
                        text = "IN",
                        unitType = CellUnitType.LEADING,
                        isEnabled = isEnabled
                    ),
                    TitleCellUnit(
                        text = stringResource(R.string.cells_icon),
                        isEnabled = isEnabled,
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                children = listOf(
                    IconBackgroundCellUnit(
                        icon = painterResource(id = R.drawable.admiral_ic_diamond_solid),
                        unitType = CellUnitType.LEADING,
                        isEnabled = isEnabled
                    ),
                    TitleCellUnit(
                        text = stringResource(R.string.cells_icon_vs_background),
                        isEnabled = isEnabled,
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                children = listOf(
                    IconCellUnit(
                        icon = painterResource(id = R.drawable.admiral_ic_diamond_solid),
                        iconColorEnable = AdmiralTheme.colors.elementAccent,
                        iconColorDisable = AdmiralTheme.colors.elementAccent.withAlpha(),
                        unitType = CellUnitType.LEADING,
                        isEnabled = isEnabled
                    ),
                    TitleCellUnit(
                        text = stringResource(R.string.cells_icon_place),
                        isEnabled = isEnabled,
                        unitType = CellUnitType.LEADING_TEXT
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
fun CellsLeadingScreenPreview() {
    AdmiralTheme {
        CellsLeadingScreen()
    }
}