package com.admiral.demo.compose.home.textblocks

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
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.cell.BaseCell
import com.admiral.uikit.compose.cell.unit.IconCellUnit
import com.admiral.uikit.compose.cell.unit.TitleCellUnit
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.demo.R as DemoRes

@Suppress("LongParameterList", "LongMethod")
@Composable
fun TextBlocksScreen(
    onBackClick: () -> Unit = {},
    onHeaderClick: () -> Unit = {},
    onAccordionClick: () -> Unit = {},
    onParagraphClick: () -> Unit = {},
    onLinkClick: () -> Unit = {},
    onPaddingClick: () -> Unit = {},
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
                    .padding(vertical = DIMEN_X5, horizontal = DIMEN_X4)
                    .fillMaxWidth(),
                text = stringResource(id = DemoRes.string.text_blocks_title),
                style = AdmiralTheme.typography.title1,
                color = AdmiralTheme.colors.textPrimary,
            )
            BaseCell(
                onClick = onHeaderClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(DemoRes.string.text_blocks_header_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = DemoRes.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onAccordionClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(DemoRes.string.text_blocks_accordion_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = DemoRes.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onParagraphClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(DemoRes.string.text_blocks_paragraph_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = DemoRes.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onLinkClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(DemoRes.string.text_blocks_link_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = DemoRes.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onPaddingClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(DemoRes.string.text_blocks_padding_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = DemoRes.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
        }
    }
}

@Preview
@Composable
private fun TextBlocksScreenPreview() {
    AdmiralTheme {
        TextBlocksScreen()
    }
}