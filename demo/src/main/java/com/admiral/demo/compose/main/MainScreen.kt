package com.admiral.demo.compose.main

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
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.cell.BaseCell
import com.admiral.uikit.compose.cell.unit.IconBackgroundCellUnit
import com.admiral.uikit.compose.cell.unit.IconCellUnit
import com.admiral.uikit.compose.cell.unit.TitleSubtitleCellUnit
import com.admiral.uikit.core.components.cell.base.CellUnitType

internal typealias homeItemClick = () -> Unit

@Suppress("LongMethod", "LongParameterList")
@Composable
internal fun MainScreen(
    onTabsClick: homeItemClick = {},
    onButtonsClick: homeItemClick = {},
    onTextFieldsClick: homeItemClick = {},
    onCellsClick: homeItemClick = {},
    onCheckBoxClick: homeItemClick = {},
    onPageControlClick: homeItemClick = {},
    onLinkClick: homeItemClick = {},
    onBadgesClick: homeItemClick = {},
    onTagsClick: homeItemClick = {},
    onAlertsClick: homeItemClick = {},
    onTextBlocksClick: homeItemClick = {},
) {
    Scaffold { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .verticalScroll(state = rememberScrollState())
        ) {
            BaseCell(
                children = listOf(
                    IconBackgroundCellUnit(
                        icon = painterResource(id = R.drawable.admiral_ic_eyedropper_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_themes_title),
                        subtitleText = stringResource(R.string.home_section_themes_subtitle),
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
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_icons_title),
                        subtitleText = stringResource(R.string.home_section_icons_subtitle),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onButtonsClick,
                children = listOf(
                    IconBackgroundCellUnit(
                        icon = painterResource(id = R.drawable.admiral_ic_grid_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_buttons_title),
                        subtitleText = stringResource(R.string.home_section_buttons_subtitle),
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
                        icon = painterResource(id = R.drawable.admiral_ic_eyedropper_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_themes_title),
                        subtitleText = stringResource(R.string.home_section_themes_subtitle),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onTextFieldsClick,
                children = listOf(
                    IconBackgroundCellUnit(
                        icon = painterResource(id = R.drawable.admiral_ic_sign_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_text_fields_title),
                        subtitleText = stringResource(R.string.home_section_text_fields_subtitle),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onCellsClick,
                children = listOf(
                    IconBackgroundCellUnit(
                        icon = painterResource(id = R.drawable.admiral_ic_menu_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_cells_title),
                        subtitleText = stringResource(R.string.home_section_cells_subtitle),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onTextBlocksClick,
                children = listOf(
                    IconBackgroundCellUnit(
                        icon = painterResource(id = R.drawable.admiral_ic_typography_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_text_blocks_title),
                        subtitleText = stringResource(R.string.home_section_text_blocks_subtitle),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onTabsClick,
                children = listOf(
                    IconBackgroundCellUnit(
                        icon = painterResource(id = R.drawable.admiral_ic_diamond_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_tabs_title),
                        subtitleText = stringResource(R.string.home_section_tabs_subtitle),
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
                        icon = painterResource(id = R.drawable.admiral_ic_chat_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_chat_title),
                        subtitleText = stringResource(R.string.home_section_chat_subtitle),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onTagsClick,
                children = listOf(
                    IconBackgroundCellUnit(
                        icon = painterResource(id = R.drawable.admiral_ic_pin_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_tags_title),
                        subtitleText = stringResource(R.string.home_section_tags_subtitle),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onBadgesClick,
                children = listOf(
                    IconBackgroundCellUnit(
                        icon = painterResource(id = R.drawable.admiral_ic_mobile_outline),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_badges_title),
                        subtitleText = stringResource(R.string.home_section_badges_subtitle),
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
                        icon = painterResource(id = R.drawable.admiral_ic_tune_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_radiobutton_title),
                        subtitleText = stringResource(R.string.home_section_radiobutton_subtitle),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onCheckBoxClick,
                children = listOf(
                    IconBackgroundCellUnit(
                        icon = painterResource(id = R.drawable.admiral_ic_tasks_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_checkbox_title),
                        subtitleText = stringResource(R.string.home_section_checkbox_subtitle),
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
                        icon = painterResource(id = R.drawable.admiral_ic_tune_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_switcher_title),
                        subtitleText = stringResource(R.string.home_section_switcher_subtitle),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onLinkClick,
                children = listOf(
                    IconBackgroundCellUnit(
                        icon = painterResource(id = R.drawable.admiral_ic_link_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_links_title),
                        subtitleText = stringResource(R.string.home_section_links_subtitle),
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
                        icon = painterResource(id = R.drawable.admiral_ic_loader_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_spinner_title),
                        subtitleText = stringResource(R.string.home_section_spinner_subtitle),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onPageControlClick,
                children = listOf(
                    IconBackgroundCellUnit(
                        icon = painterResource(id = R.drawable.admiral_ic_more_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_page_control_title),
                        subtitleText = stringResource(R.string.home_section_page_control_subtitle),
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
                        icon = painterResource(id = R.drawable.admiral_ic_update_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_shimmer_title),
                        subtitleText = stringResource(R.string.home_section_shimmer_subtitle),
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
                        icon = painterResource(id = R.drawable.admiral_ic_calendar_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_calendar_title),
                        subtitleText = stringResource(R.string.home_section_calendar_subtitle),
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
                        icon = painterResource(id = R.drawable.admiral_ic_time_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_time_picker_title),
                        subtitleText = stringResource(R.string.home_section_timer_picker_subtitle),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onAlertsClick,
                children = listOf(
                    IconBackgroundCellUnit(
                        icon = painterResource(id = R.drawable.admiral_ic_email_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_alert_title),
                        subtitleText = stringResource(R.string.home_section_alert_subtitle),
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
                        icon = painterResource(id = R.drawable.admiral_ic_chat_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_toolbar_title),
                        subtitleText = stringResource(R.string.home_section_toolbar_subtitle),
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
                        icon = painterResource(id = R.drawable.admiral_ic_receipt_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_bottom_sheet_title),
                        subtitleText = stringResource(R.string.home_section_bottom_sheet_subtitle),
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
                        icon = painterResource(id = R.drawable.admiral_ic_treasury_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_currency_title),
                        subtitleText = stringResource(R.string.home_section_currency_subtitle),
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
                        icon = painterResource(id = R.drawable.admiral_ic_chat_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_chat_title),
                        subtitleText = stringResource(R.string.home_section_chat_subtitle),
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
                        icon = painterResource(id = R.drawable.admiral_ic_location_solid),
                        unitType = CellUnitType.LEADING
                    ),
                    TitleSubtitleCellUnit(
                        titleText = stringResource(R.string.home_section_map_title),
                        subtitleText = stringResource(R.string.home_section_map_subtitle),
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
private fun LoginScreenPreview() {
    AdmiralTheme {
        MainScreen()
    }
}
