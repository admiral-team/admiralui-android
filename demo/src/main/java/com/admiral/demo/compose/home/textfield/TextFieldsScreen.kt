package com.admiral.demo.compose.home.textfield

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
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.compose.cell.BaseCell
import com.admiral.uikit.compose.cell.unit.IconCellUnit
import com.admiral.uikit.compose.cell.unit.TitleCellUnit
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.core.components.cell.base.CellUnitType

@Composable
@Suppress("LongMethod", "LongParameterList")
fun TextFieldsScreen(
    onStandardClick: () -> Unit = {},
    onDoubleClick: () -> Unit = {},
    onSliderClick: () -> Unit = {},
    onCardNumberClick: () -> Unit = {},
    onSmsCodeClick: () -> Unit = {},
    onNumberClick: () -> Unit = {},
    onFeedbackClick: () -> Unit = {},
    onPinCodeClick: () -> Unit = {},
) {
    Scaffold(backgroundColor = AdmiralTheme.colors.backgroundBasic) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .verticalScroll(state = rememberScrollState())
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = DIMEN_X5, bottom = DIMEN_X4, start = DIMEN_X4, end = DIMEN_X4),
                text = stringResource(id = R.string.text_fields_title),
                style = ThemeManagerCompose.typography.title1,
            )
            BaseCell(
                onClick = onStandardClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.text_fields_standard_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onDoubleClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.text_fields_double_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onSliderClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.text_fields_slider_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onCardNumberClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.text_fields_card_number_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onSmsCodeClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.text_fields_sms_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onNumberClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.text_fields_number_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onFeedbackClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.text_fields_feedback_title),
                        unitType = CellUnitType.LEADING_TEXT
                    ),
                    IconCellUnit(
                        unitType = CellUnitType.TRAILING,
                        icon = painterResource(id = R.drawable.admiral_ic_chevron_right_outline)
                    )
                )
            )
            BaseCell(
                onClick = onPinCodeClick,
                children = listOf(
                    TitleCellUnit(
                        text = stringResource(R.string.text_fields_pincode_title),
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
private fun TextFieldsScreenPreview() {
    TextFieldsScreen()
}