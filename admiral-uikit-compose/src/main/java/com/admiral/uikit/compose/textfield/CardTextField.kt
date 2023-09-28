package com.admiral.uikit.compose.textfield

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.admiral.themes.ThemeManager
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.compose.R

@Preview(showBackground = true)
@Composable
fun FirstCardTextField() {
    Box(
        modifier = Modifier
            .padding(EXAMPLE_TEXT_FIELD_PADDING.dp)
            .width(EXAMPLE_TEXT_FIELD_SIZE.dp)
    ) {
        CardTextField(
            inputText = "Text in text field",
            placeholderText = "Placeholder text",
            additionalText = "Additional text",
            icon = painterResource(id = R.drawable.admiral_ic_heart_solid)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SecondCardTextField() {
    Box(
        modifier = Modifier
            .padding(EXAMPLE_TEXT_FIELD_PADDING.dp)
            .width(EXAMPLE_TEXT_FIELD_SIZE.dp)
    ) {
        CardTextField(
            placeholderText = "Placeholder text",
            additionalText = "Additional text",
            isReadOnly = true
        )
    }
}

@Composable
@Suppress("LongParameterList")
fun CardTextField(
    modifier: Modifier = Modifier,
    inputText: String = "",
    placeholderText: String? = null,
    additionalText: String? = null,
    textColorState: ColorState? = null,
    inputTextColorState: ColorState? = null,
    errorColor: Int? = null,
    iconBackgroundColorState: ColorState? = null,
    iconColorState: ColorState? = null,
    icon: Painter? = null,
    onIconClick: () -> Unit = {},
    isError: Boolean = false,
    isReadOnly: Boolean = false,
    isEnabled: Boolean = true,
    onValueChange: (TextFieldValue) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val theme = ThemeManagerCompose.theme.value
    val typography = ThemeManagerCompose.typography

    val textState = remember { mutableStateOf(TextFieldValue(inputText)) }
    val focusState = remember { mutableStateOf(false) }
    val localFocusManager = LocalFocusManager.current
    val focusRequester = FocusRequester()

    Column(
        modifier = modifier.fakeClickable(focusRequester)
    ) {
        val defaultColor: Int = when {
            isError -> errorColor ?: theme.palette.textError
            focusState.value -> textColorState?.focused ?: theme.palette.textAccent
            !isEnabled -> textColorState?.normalDisabled ?: theme.palette.textSecondary.withAlpha()
            else -> textColorState?.normalEnabled ?: theme.palette.textSecondary
        }

        val inputTextColor =
            if (isEnabled) inputTextColorState?.normalEnabled ?: theme.palette.textPrimary
            else inputTextColorState?.normalDisabled ?: theme.palette.textPrimary.withAlpha()

        val additionalTextColor: Int = when {
            isError -> errorColor ?: theme.palette.textError
            !isEnabled -> textColorState?.normalDisabled ?: theme.palette.textSecondary.withAlpha()
            else -> textColorState?.normalEnabled ?: theme.palette.textSecondary
        }

        val placeholderTextColor: Int = when {
            !isEnabled -> textColorState?.normalDisabled ?: theme.palette.textSecondary.withAlpha()
            else -> textColorState?.normalEnabled ?: theme.palette.textSecondary
        }

        Box {
            BasicTextField(
                modifier = Modifier
                    .onFocusChanged {
                        focusState.value = it.isFocused
                    }
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(top = TEXT_FIELD_PADDING_TOP.dp),
                enabled = isEnabled,
                readOnly = isReadOnly,
                value = textState.value,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                singleLine = singleLine,
                maxLines = maxLines,
                visualTransformation = visualTransformation,
                onValueChange = {
                    textState.value = it
                    onValueChange.invoke(it)
                },
                cursorBrush = SolidColor(Color(defaultColor)),
                textStyle = typography.title2.copy(color = Color(inputTextColor)),
                decorationBox = { innerTextField ->
                    Box {
                        if (textState.value.text.isEmpty()) {
                            val color = if (focusState.value) placeholderTextColor else defaultColor
                            Text(
                                text = placeholderText ?: "",
                                style = typography.title2,
                                color = Color(color)
                            )
                        }
                        innerTextField()
                    }
                }
            )

            val iconColor =
                if (isEnabled) textColorState?.normalEnabled ?: ThemeManager.theme.palette.elementPrimary
                else textColorState?.normalDisabled ?: ThemeManager.theme.palette.elementPrimary.withAlpha()

            icon?.let {
                Icon(
                    modifier = Modifier
                        .size(ICON_SIZE.dp)
                        .align(
                            BiasAlignment(
                                ICON_HORIZONTAL_BIAS,
                                ICON_VERTICAL_BIAS
                            )
                        )
                        .padding(ICON_PADDING.dp),
                    painter = it,
                    contentDescription = null,
                    tint = Color(iconColor)
                )
            }
        }

        Box(
            Modifier
                .height(LINE_HEIGHT.dp)
                .fillMaxWidth()
                .padding(bottom = LINE_PADDING_BOTTOM.dp, top = LINE_PADDING_TOP.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                val pathEffect = if (isReadOnly) PathEffect.dashPathEffect(
                    floatArrayOf(
                        LINE_PATH_EFFECT_INTERVAL,
                        LINE_PATH_EFFECT_INTERVAL
                    ), 0f
                ) else null

                drawLine(
                    start = Offset(x = 0f, y = canvasHeight / LINE_OFFSET_CENTERED),
                    end = Offset(x = canvasWidth, y = canvasHeight / LINE_OFFSET_CENTERED),
                    color = Color(defaultColor),
                    pathEffect = pathEffect
                )
            }
        }

        additionalText?.let {
            Text(
                modifier = Modifier
                    .padding(bottom = ADDITIONAL_TEXT_PADDING.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = focusRequester::requestFocus
                    )
                    .fillMaxWidth(),
                text = it,
                style = typography.subhead2,
                color = Color(additionalTextColor),
                fontSize = ADDITIONAL_TEXT_FONT_SIZE.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

private const val ADDITIONAL_TEXT_FONT_SIZE = 14
private const val ADDITIONAL_TEXT_PADDING = 4

private const val LINE_PATH_EFFECT_INTERVAL = 10F
private const val LINE_OFFSET_CENTERED = 2
private const val LINE_PADDING_BOTTOM = 6
private const val LINE_PADDING_TOP = 2
private const val LINE_HEIGHT = 4

private const val ICON_SIZE = 24
private const val ICON_PADDING = 4
private const val ICON_HORIZONTAL_BIAS = 1.0f
private const val ICON_VERTICAL_BIAS = 0.5f

private const val EXAMPLE_TEXT_FIELD_SIZE = 240
private const val EXAMPLE_TEXT_FIELD_PADDING = 16

private const val TEXT_FIELD_PADDING_TOP = 4