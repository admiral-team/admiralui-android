package com.admiral.demo.compose.home.cells

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.demo.R
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.cell.BaseCell
import com.admiral.uikit.compose.cell.unit.CardCellUnit
import com.admiral.uikit.compose.cell.unit.CheckBoxCellUnit
import com.admiral.uikit.compose.cell.unit.DoubleSubtitleCellUnit
import com.admiral.uikit.compose.cell.unit.IconCellUnit
import com.admiral.uikit.compose.cell.unit.LabelCellUnit
import com.admiral.uikit.compose.cell.unit.SubtitlePaymentCellUnit
import com.admiral.uikit.compose.cell.unit.TitleCellUnit
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X6
import com.admiral.uikit.core.components.cell.base.CellUnitType

@Composable
@Suppress("LongMethod")
fun CellsTrailingScreen(
    onBackClick: () -> Unit = {}
) {

    var isEnabled by remember { mutableStateOf(true) }

    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
                title = stringResource(id = R.string.cells_trailing_title),
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
                    isEnabled = it == 0
                }
            )

            BaseCell(
                modifier = Modifier
                    .padding(top = DIMEN_X6),
                isEnabled = isEnabled,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.cell_title),
                        isEnabled = isEnabled,
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline),
                        isEnabled = isEnabled,
                    )
                )
            )

            BaseCell(
                modifier = Modifier,
                isEnabled = isEnabled,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.cell_title),
                        isEnabled = isEnabled,
                        unitType = CellUnitType.LEADING_TEXT
                    ),

                )
            )

            BaseCell(
                modifier = Modifier,
                isEnabled = isEnabled,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.cell_title),
                        isEnabled = isEnabled,
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    CheckBoxCellUnit(
                        unitType = CellUnitType.TRAILING,
                        isEnabled = isEnabled,
                    )
                )
            )

            BaseCell(
                modifier = Modifier,
                isEnabled = isEnabled,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.cell_title),
                        unitType = CellUnitType.LEADING_TEXT,
                        isEnabled = isEnabled,
                    ),

                )
            )

            BaseCell(
                modifier = Modifier,
                isEnabled = isEnabled,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.cell_title),
                        isEnabled = isEnabled,
                        unitType = CellUnitType.LEADING_TEXT,
                    ),
                    LabelCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.ic_rnb_one),
                        isEnabled = isEnabled,
                    )
                )
            )

            BaseCell(
                modifier = Modifier,
                isEnabled = isEnabled,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.cell_title),
                        unitType = CellUnitType.LEADING_TEXT,
                        isEnabled = isEnabled,
                    ),
                    CardCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.test_ic_card),
                        isEnabled = isEnabled,
                    )
                )
            )

            BaseCell(
                modifier = Modifier,
                isEnabled = isEnabled,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.cell_title),
                        unitType = CellUnitType.LEADING_TEXT,
                        isEnabled = isEnabled,
                    ),
                    DoubleSubtitleCellUnit(
                        unitType = CellUnitType.TRAILING,
                        subtitleTop = stringResource(id = R.string.cell_date),
                        subtitleBottom = stringResource(id = R.string.cell_percent),
                    )
                )
            )

            BaseCell(
                modifier = Modifier,
                isEnabled = isEnabled,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.cell_title),
                        unitType = CellUnitType.LEADING_TEXT,
                        isEnabled = isEnabled,
                    ),
                    SubtitlePaymentCellUnit(
                        unitType = CellUnitType.TRAILING,
                        paymentIcon = painterResource(id = R.drawable.test_ic_mir),
                        subtitle = stringResource(id = R.string.cell_subtitle),
                        isEnabled = isEnabled,
                    )
                )
            )
        }
    }
}

@Preview
@Composable
private fun CellsTrailingScreenPreview() {
    AdmiralTheme {
        CellsTrailingScreen()
    }
}