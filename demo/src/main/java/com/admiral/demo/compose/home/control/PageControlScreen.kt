package com.admiral.demo.compose.home.control

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
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.cell.BaseCell
import com.admiral.uikit.compose.cell.unit.IconCellUnit
import com.admiral.uikit.compose.cell.unit.TitleCellUnit
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.core.components.cell.base.CellUnitType

@Composable
fun PageControlScreen(
    onBackClick: () -> Unit = {},
    onLinerClick: () -> Unit = {},
    onCircleClick: () -> Unit = {},
) {
    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
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
                text = stringResource(id = com.admiral.demo.R.string.page_control_title),
                style = ThemeManagerCompose.typography.title1,
                color = AdmiralTheme.colors.textPrimary
            )
            BaseCell(
                onClick = onLinerClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(com.admiral.demo.R.string.page_control_liner),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = com.admiral.demo.R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onCircleClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(com.admiral.demo.R.string.page_control_circle),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = com.admiral.demo.R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
        }
    }
}

@Preview
@Composable
private fun PageScreenPreview() {
    AdmiralTheme {
        PageControlScreen()
    }
}