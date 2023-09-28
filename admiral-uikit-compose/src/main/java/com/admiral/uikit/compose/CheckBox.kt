package com.admiral.uikit.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.admiral.themes.ThemeManager
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X5

@Composable
@Suppress("LongParameterList")
fun checkBox(
    modifier: Modifier = Modifier,
    radioOptions: List<String> = listOf(),
    checkedItem: Int = 0,
    isVertical: Boolean = true,
    isEnabled: Boolean = true,
    radioColor: ColorState? = null,
    textColor: ColorState? = null
): String {
    return if (radioOptions.isNotEmpty()) {
        val (selectedOption, onOptionSelected) = remember {
            mutableStateOf(radioOptions[checkedItem])
        }

        if (isVertical) {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(DIMEN_X5)
            ) {
                CreateCheckBoxes(radioOptions, selectedOption, isEnabled, radioColor, textColor, onOptionSelected)
            }
        } else {
            Row(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(DIMEN_X5)
            ) {
                CreateCheckBoxes(radioOptions, selectedOption, isEnabled, radioColor, textColor, onOptionSelected)
            }
        }

        selectedOption
    } else {
        ""
    }
}

@Composable
@Suppress("LongParameterList")
private fun CreateCheckBoxes(
    radioOptions: List<String>,
    selectedOption: String,
    isEnabled: Boolean = true,
    radioColor: ColorState? = null,
    textColor: ColorState? = null,
    onOptionSelected: (String) -> Unit
) {
    val theme = ThemeManagerCompose.theme.value
    val typography = ThemeManagerCompose.typography

    radioOptions.forEach { item ->
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(radioColor?.normalEnabled ?: ThemeManager.theme.palette.elementAccent),
                    uncheckedColor = Color(radioColor?.normalEnabled ?: ThemeManager.theme.palette.elementAccent),
                    disabledColor = Color(
                        radioColor?.normalDisabled ?: ThemeManager.theme.palette.elementAccent.withAlpha()
                    )
                ),
                enabled = isEnabled,
                checked = (item == selectedOption),
                onCheckedChange = {
                    onOptionSelected(item)
                }
            )

            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = typography.subhead3.copy(
                        color = if (isEnabled) {
                            Color(
                                textColor?.normalEnabled ?: theme.palette.textPrimary
                            )
                        } else {
                            Color(
                                textColor?.normalDisabled ?: theme.palette.textPrimary.withAlpha()
                            )
                        }

                    ).toSpanStyle()
                ) { append("  $item  ") }
            }

            ClickableText(
                modifier = Modifier.padding(start = DIMEN_X1),
                text = annotatedString,
                onClick = {
                    if (isEnabled) onOptionSelected(item)
                }
            )
        }
    }
}