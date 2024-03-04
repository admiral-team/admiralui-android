package com.admiral.demo.compose.home.button

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.demo.R
import com.admiral.uikit.compose.cell.BaseCell
import com.admiral.uikit.compose.cell.unit.IconCellUnit
import com.admiral.uikit.compose.cell.unit.TitleCellUnit
import com.admiral.uikit.compose.util.DIMEN_X8
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.resources.R as res

@Composable
@Suppress("LongMethod")
fun ComposeButtonsScreen(
    onPrimaryClick: () -> Unit = {},
    onSecondaryClick: () -> Unit = {},
    onGhostClick: () -> Unit = {},
    onRulesClick: () -> Unit = {},
    onOtherClick: () -> Unit = {},
) {
    Scaffold { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .padding(top = DIMEN_X8)
                .verticalScroll(state = rememberScrollState())
        ) {
            BaseCell(
                onClick = onPrimaryClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.buttons_primary_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = res.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onSecondaryClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.buttons_secondary_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = res.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onGhostClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.buttons_ghost_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = res.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onRulesClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.buttons_rules),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = res.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onOtherClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.buttons_other_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = res.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
        }
    }
}

@Preview
@Composable
fun ComposeButtonsViewPreview() {
    ComposeButtonsScreen()
}