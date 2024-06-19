package com.admiral.uikit.compose.cell.unit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.cell.base.CellUnit
import com.admiral.uikit.compose.checkbox.AdmiralCheckBoxColor
import com.admiral.uikit.compose.checkbox.CheckBox
import com.admiral.uikit.compose.checkbox.checkBoxColor
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.core.components.cell.base.CellUnitType

@Immutable
data class CheckBoxCellUnit(
    override var unitType: CellUnitType,
    val text: String = "",
    val colors: AdmiralCheckBoxColor? = null,
    val isChecked: Boolean = false,
    val isError: Boolean = false,
    val isEnabled: Boolean = true,
    val onCheckedChange: (Boolean) -> Unit = {},
) : CellUnit {
    @Composable
    override fun Create(modifier: Modifier) {
        val checkBoxColor = colors ?: checkBoxColor()

        CheckBox(
            modifier = modifier,
            isChecked = isChecked,
            text = text,
            colors = checkBoxColor,
            isEnabled = isEnabled,
            isError = isError,
            onCheckedChange = onCheckedChange
        )
    }
}

@Preview
@Composable
private fun CheckBoxCellUnitPreview() {
    AdmiralTheme {
        var isChecked by remember { mutableStateOf(true) }

        Surface(
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(DIMEN_X2),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
            ) {
                CheckBoxCellUnit(
                    unitType = CellUnitType.LEADING,
                    isChecked = isChecked,
                    onCheckedChange = { isCheckedNew ->
                        println("isChecked $isChecked")
                        isChecked = isCheckedNew
                    }
                ).Create(modifier = Modifier)
                Spacer(modifier = Modifier.padding(DIMEN_X2))
                CheckBoxCellUnit(
                    unitType = CellUnitType.LEADING,
                    isEnabled = false,
                ).Create(modifier = Modifier)
            }
        }
    }
}