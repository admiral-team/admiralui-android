package com.admiral.uikit.compose.textfield

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.resources.R as res

@Preview(showBackground = true)
@Composable
fun PreviewDoubleTextField() {
    Box(
        modifier = Modifier
            .padding(EXAMPLE_TEXT_FIELD_PADDING.dp)
            .width(EXAMPLE_TEXT_FIELD_SIZE.dp)
    ) {
        DoubleTextField(
            startTextFieldParams = TextFieldParams(
                inputText = "First input",
                optionalText = "Optional label",
                placeholderText = "Placeholder text"
            ),
            endTextFieldParams = TextFieldParams(
                optionalText = "Optional label",
                placeholderText = "Placeholder text",
                icon = painterResource(id = res.drawable.admiral_ic_heart_solid)
            ),
            additionalText = "Additional Text"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDoubleTextFieldStartBig() {
    Box(
        modifier = Modifier
            .padding(EXAMPLE_TEXT_FIELD_PADDING.dp)
            .width(EXAMPLE_TEXT_FIELD_SIZE.dp)
    ) {
        DoubleTextField(
            startTextFieldParams = TextFieldParams(
                optionalText = "Optional label",
                placeholderText = "Placeholder text"
            ),
            endTextFieldParams = TextFieldParams(
                optionalText = "Optional label",
                placeholderText = "Placeholder text"
            ),
            additionalText = "Additional Text",
            ratio = DoubleTextFieldRatio.BIG_START
        )
    }
}

enum class DoubleTextFieldRatio(
    val startWeight: Float,
    val endWeight: Float
) {
    EQUAL(1.0f, 1.0f),
    BIG_START(2.0f, 1.0f),
    BIG_END(1.0f, 2.0f)
}

@Composable
@Suppress("LongParameterList")
fun DoubleTextField(
    modifier: Modifier = Modifier,
    startTextFieldParams: TextFieldParams,
    endTextFieldParams: TextFieldParams,
    additionalText: String? = null,
    isError: Boolean = false,
    ratio: DoubleTextFieldRatio = DoubleTextFieldRatio.EQUAL
) {
    val theme = ThemeManagerCompose.theme.value
    val typography = ThemeManagerCompose.typography

    Column(
        modifier = modifier
    ) {
        Row {
            TextField(
                modifier = Modifier
                    .weight(ratio.startWeight)
                    .padding(end = TEXT_FIELD_PADDING.dp),
                inputText = startTextFieldParams.inputText,
                optionalText = startTextFieldParams.optionalText,
                placeholderText = startTextFieldParams.placeholderText,
                additionalText = null,
                textColorState = startTextFieldParams.textColorState,
                inputTextColorState = startTextFieldParams.inputTextColorState,
                errorColor = startTextFieldParams.errorColor,
                iconBackgroundColorState = startTextFieldParams.iconBackgroundColorState,
                iconColorState = startTextFieldParams.iconColorState,
                icon = startTextFieldParams.icon,
                onIconClick = startTextFieldParams.onIconClick,
                isError = isError,
                isReadOnly = startTextFieldParams.isReadOnly,
                isEnabled = startTextFieldParams.isEnabled,
                onValueChange = startTextFieldParams.onValueChange,
                keyboardOptions = startTextFieldParams.keyboardOptions,
                keyboardActions = startTextFieldParams.keyboardActions,
                singleLine = startTextFieldParams.singleLine,
                maxLines = startTextFieldParams.maxLines,
                visualTransformation = startTextFieldParams.visualTransformation
            )
            TextField(
                modifier = Modifier
                    .weight(ratio.endWeight)
                    .padding(start = TEXT_FIELD_PADDING.dp),
                inputText = endTextFieldParams.inputText,
                optionalText = endTextFieldParams.optionalText,
                placeholderText = endTextFieldParams.placeholderText,
                additionalText = null,
                textColorState = endTextFieldParams.textColorState,
                inputTextColorState = endTextFieldParams.inputTextColorState,
                errorColor = endTextFieldParams.errorColor,
                iconBackgroundColorState = endTextFieldParams.iconBackgroundColorState,
                iconColorState = endTextFieldParams.iconColorState,
                icon = endTextFieldParams.icon,
                onIconClick = endTextFieldParams.onIconClick,
                isError = isError,
                isReadOnly = endTextFieldParams.isReadOnly,
                isEnabled = endTextFieldParams.isEnabled,
                onValueChange = endTextFieldParams.onValueChange,
                keyboardOptions = endTextFieldParams.keyboardOptions,
                keyboardActions = endTextFieldParams.keyboardActions,
                singleLine = endTextFieldParams.singleLine,
                maxLines = endTextFieldParams.maxLines,
                visualTransformation = endTextFieldParams.visualTransformation
            )
        }

        val additionalTextColor: Int = when {
            isError -> startTextFieldParams.errorColor ?: theme.palette.textError
            !startTextFieldParams.isEnabled -> startTextFieldParams.textColorState?.normalDisabled
                ?: theme.palette.textSecondary.withAlpha()

            else -> startTextFieldParams.textColorState?.normalEnabled
                ?: theme.palette.textSecondary
        }

        additionalText?.let {
            Text(
                modifier = Modifier.padding(bottom = ADDITIONAL_TEXT_PADDING.dp),
                text = it,
                style = typography.subhead2,
                color = Color(additionalTextColor),
                fontSize = ADDITIONAL_TEXT_FONT_SIZE.sp
            )
        }
    }
}

private const val ADDITIONAL_TEXT_FONT_SIZE = 14
private const val ADDITIONAL_TEXT_PADDING = 4

private const val EXAMPLE_TEXT_FIELD_SIZE = 320
private const val EXAMPLE_TEXT_FIELD_PADDING = 16

private const val TEXT_FIELD_PADDING = 8