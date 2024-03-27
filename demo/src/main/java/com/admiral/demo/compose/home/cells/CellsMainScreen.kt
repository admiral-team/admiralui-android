package com.admiral.demo.compose.home.cells

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.demo.R
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.appbar.AppBar
import com.admiral.uikit.compose.cell.BaseCell
import com.admiral.uikit.compose.cell.unit.IconCellUnit
import com.admiral.uikit.compose.cell.unit.TitleCellUnit
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.core.components.cell.base.CellUnitType

@Composable
@Suppress("LongMethod")
fun CellsMainScreen(
    onBaseCellsClick: () -> Unit = {},
    onActionbarClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            AppBar(
                navIcon = painterResource(id = com.admiral.uikit.compose.R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick
            )
        }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .verticalScroll(state = rememberScrollState())
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(top = DIMEN_X5, bottom = DIMEN_X4, start = DIMEN_X4, end = DIMEN_X4),
                text = stringResource(id = R.string.cells_title),
                style = ThemeManagerCompose.typography.title1,
            )
            BaseCell(
                onClick = onBaseCellsClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.cells_base_cells_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onActionbarClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.actionbar_title),
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
fun CellsMainScreenPreview() {
    CellsMainScreen()
}