package com.admiral.uikit.compose.radiobutton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.AdmiralTheme.typography
import com.admiral.uikit.compose.text.AdmiralTextColor
import com.admiral.uikit.compose.text.Text
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X4

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RadioButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    text: String? = null,
    isError: Boolean = false,
    isEnabled: Boolean = true,
    colors: RadioButtonColor = AdmiralRadioButtonColor.default(),
    textStyle: TextStyle = typography.subhead3,
    onClick: () -> Unit = {},
) {
    val radioBtnColor =
        colors.getButtonColor(
            isEnabled = isEnabled,
            isError = isError,
            isSelected = isSelected
        ).value

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
            androidx.compose.material3.RadioButton(
                modifier = Modifier,
                selected = isSelected,
                enabled = isEnabled,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = radioBtnColor,
                    unselectedColor = radioBtnColor,
                    disabledSelectedColor = radioBtnColor,
                    disabledUnselectedColor = radioBtnColor
                )
            )
        }

        text?.let {
            Text(
                modifier = Modifier
                    .padding(start = DIMEN_X2),
                style = textStyle,
                color = AdmiralTextColor.primary(
                    colors.getTextColor(
                        isEnabled = isEnabled,
                        isError = isError,
                        isSelected = isSelected
                    ).value
                ),
                text = text
            )
        }

    }
}

@Composable
fun RadioButtonGroup(
    modifier: Modifier = Modifier,
    radioOptions: List<String> = listOf(),
    selectedItemPosition: Int = 0,
    spaceBetweenRadio: Dp = DIMEN_X2,
    isEnabled: Boolean = true,
    isError: Boolean = false,
    isVertical: Boolean = false,
    colors: RadioButtonColor = AdmiralRadioButtonColor.default(),
) {
    var onOptionSelected by remember {
        mutableStateOf(radioOptions[selectedItemPosition])
    }

    if (isVertical) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(spaceBetweenRadio)
        ) {
            CreateRadioButtons(
                radioOptions = radioOptions,
                isEnabled = isEnabled,
                isError = isError,
                colors = colors,
                selectedOption = onOptionSelected,
                onOptionSelected = { newOptionSelected ->
                    onOptionSelected = newOptionSelected
                }
            )
        }
    } else {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spaceBetweenRadio)
        ) {
            CreateRadioButtons(
                radioOptions = radioOptions,
                isEnabled = isEnabled,
                isError = isError,
                colors = colors,
                selectedOption = onOptionSelected,
                onOptionSelected = { newOptionSelected ->
                    onOptionSelected = newOptionSelected
                }
            )
        }
    }
}

@Composable
private fun CreateRadioButtons(
    radioOptions: List<String>,
    selectedOption: String,
    isEnabled: Boolean = true,
    isError: Boolean = false,
    colors: RadioButtonColor = AdmiralRadioButtonColor.default(),
    onOptionSelected: (String) -> Unit = {}
) {
    radioOptions.forEach { item ->
        RadioButton(
            text = item,
            isError = isError,
            isEnabled = isEnabled,
            colors = colors,
            isSelected = item == selectedOption,
            onClick = { onOptionSelected(item) }
        )
    }
}

@Preview
@Composable
private fun RadioButtonPreview() {
    AdmiralTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = AdmiralTheme.colors.backgroundBasic,
        ) {
            Column(
                modifier = Modifier
                    .padding(DIMEN_X4),
                verticalArrangement = Arrangement.spacedBy(DIMEN_X2)
            ) {
                RadioButtonGroup(
                    modifier = Modifier,
                    radioOptions = listOf("option 1", "option 2", "option 3"),
                    isVertical = true
                )

                Spacer(modifier = Modifier.size(DIMEN_X4))

                RadioButton(text = "Text")
                RadioButton(isEnabled = false, text = "Text")
                RadioButton(isSelected = true, text = "Text")
                RadioButton(isSelected = true, isEnabled = false, text = "Text")
                RadioButton(isError = true, isSelected = false, text = "Text")
                RadioButton(isError = true, isSelected = false, isEnabled = false, text = "Text")
                RadioButton(isError = true, isSelected = true, text = "Text")
                RadioButton(isError = true, isSelected = true, isEnabled = false, text = "Text")
            }
        }
    }
}