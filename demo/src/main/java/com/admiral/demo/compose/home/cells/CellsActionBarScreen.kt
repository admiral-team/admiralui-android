package com.admiral.demo.compose.home.cells

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.actionbar.ActionBar
import com.admiral.uikit.compose.actionbar.ActionBarSecondary
import com.admiral.uikit.compose.actionbar.ActionBarSecondaryItem
import com.admiral.uikit.compose.appbar.AdmiralCenterAlignedTopAppBar
import com.admiral.uikit.compose.cell.BaseCell
import com.admiral.uikit.compose.cell.unit.CardCellUnit
import com.admiral.uikit.compose.cell.unit.IconCellUnit
import com.admiral.uikit.compose.cell.unit.TitleCellUnit
import com.admiral.uikit.compose.tabs.StandardTab
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X10
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.core.components.cell.base.CellUnitType
import com.admiral.demo.R as demoR

@Composable
@Suppress("LongMethod")
fun CellsActionBarScreen(
    onBackClick: () -> Unit = {},
) {
    var isEnabled by remember { mutableStateOf(true) }

    val list = arrayOf(
        ActionBarSecondaryItem(
            icon = painterResource(id = R.drawable.admiral_ic_email_outline),
            description = stringResource(id = com.admiral.demo.R.string.actionbar_email),
            iconContentDescription = "",
            backgroundColorNormalEnabled = AdmiralTheme.colors.elementAccent,
            backgroundColorPressed = AdmiralTheme.colors.elementAccentPressed,
            descriptionColorNormalEnabled = AdmiralTheme.colors.textStaticWhite,
            descriptionColorPressed = AdmiralTheme.colors.textStaticWhite,
            action = {
                println("Action email")
            }
        ),
        ActionBarSecondaryItem(
            icon = painterResource(id = R.drawable.admiral_ic_star_outline),
            description = stringResource(id = com.admiral.demo.R.string.actionbar_star),
            iconContentDescription = "",
            backgroundColorNormalEnabled = AdmiralTheme.colors.elementSuccess,
            backgroundColorPressed = AdmiralTheme.colors.elementSuccessPressed,
            descriptionColorNormalEnabled = AdmiralTheme.colors.textStaticWhite,
            descriptionColorPressed = AdmiralTheme.colors.textStaticWhite,
            action = {
                println("Action star")
            }
        ),
        ActionBarSecondaryItem(
            icon = painterResource(id = R.drawable.admiral_ic_edit_outline),
            description = stringResource(id = com.admiral.demo.R.string.actionbar_edit),
            iconContentDescription = "",
            backgroundColorNormalEnabled = AdmiralTheme.colors.elementAttention,
            backgroundColorPressed = AdmiralTheme.colors.elementAttentionPressed,
            descriptionColorNormalEnabled = AdmiralTheme.colors.textStaticWhite,
            descriptionColorPressed = AdmiralTheme.colors.textStaticWhite,
            action = {
                println("Action edit")
            }
        )
    )


    Scaffold(
        backgroundColor = AdmiralTheme.colors.backgroundBasic,
        topBar = {
            AdmiralCenterAlignedTopAppBar(
                navIcon = painterResource(id = R.drawable.admiral_ic_chevron_left_outline),
                onNavIconClick = onBackClick,
                title = stringResource(id = demoR.string.actionbar_title)
            )
        }
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .verticalScroll(state = rememberScrollState())
        ) {
            StandardTab(
                modifier = Modifier.padding(horizontal = DIMEN_X4),
                items = mutableListOf(
                    stringResource(id = com.admiral.demo.R.string.tabs_default),
                    stringResource(id = com.admiral.demo.R.string.tabs_disabled),
                ),
                selectedIndex = 0,
                onCheckedChange = {
                    isEnabled = it == 0
                }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(top = DIMEN_X10, bottom = DIMEN_X1, start = DIMEN_X4),
                text = stringResource(id = demoR.string.actionbar_default_title),
                color = AdmiralTheme.colors.textSecondary,
                style = ThemeManagerCompose.typography.body1,
            )
            ActionBar(
                modifier = Modifier
                    .fillMaxWidth(),
                isEnabled = isEnabled,
            ) {
                BaseCell(
                    modifier = Modifier.clip(it),
                    children = listOf(
                        CardCellUnit(
                            icon = painterResource(id = com.admiral.demo.R.drawable.test_ic_card_start),
                            isEnabled = isEnabled,
                            unitType = CellUnitType.LEADING
                        ),
                        TitleCellUnit(
                            text = stringResource(com.admiral.demo.R.string.cells_card_place),
                            isEnabled = isEnabled,
                            unitType = CellUnitType.LEADING_TEXT
                        ),
                        IconCellUnit(
                            unitType = CellUnitType.TRAILING,
                            icon = painterResource(id = com.admiral.demo.R.drawable.admiral_ic_chevron_right_outline)
                        )
                    )
                )
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(top = DIMEN_X10, bottom = DIMEN_X1, start = DIMEN_X4),
                text = stringResource(id = demoR.string.actionbar_secondary_title),
                color = AdmiralTheme.colors.textSecondary,
                style = ThemeManagerCompose.typography.body1,
            )
            ActionBarSecondary(
                modifier = Modifier
                    .fillMaxWidth(),
                hiddenContentList = list,
                isEnabled = isEnabled,
            ) {
                BaseCell(
                    modifier = Modifier.clip(it),
                    children = listOf(
                        CardCellUnit(
                            icon = painterResource(id = com.admiral.demo.R.drawable.test_ic_card_start),
                            isEnabled = isEnabled,
                            unitType = CellUnitType.LEADING
                        ),
                        TitleCellUnit(
                            text = stringResource(com.admiral.demo.R.string.cells_card_place),
                            isEnabled = isEnabled,
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
}

@Preview
@Composable
fun CellsActionBarPreview() {
    AdmiralTheme {
        CellsActionBarScreen()
    }
}