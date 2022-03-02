package com.admiral.uikit.compose.textfield

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.admiral.themes.ThemeManager
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.compose.R

@Preview(showBackground = true)
@Composable
fun First() {
    Box(
        modifier = Modifier
            .padding(PREVIEW_BOX_PADDING.dp)
            .width(PREVIEW_BOX_WIDTH.dp)
    ) {
        TextField(
            inputText = "Text in text field",
            optionalText = "Optional label",
            placeholderText = "Placeholder text",
            additionalText = "Additional text",
            icon = painterResource(id = R.drawable.admiral_ic_heart_solid)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Second() {
    Box(
        modifier = Modifier
            .padding(PREVIEW_BOX_PADDING.dp)
            .width(PREVIEW_BOX_WIDTH.dp)
    ) {
        TextField(
            optionalText = "Optional label",
            placeholderText = "Placeholder text",
            additionalText = "Additional text",
            isReadOnly = true
        )
    }
}

@Composable
@Suppress("LongParameterList")
fun TextField(
    modifier: Modifier = Modifier,
    inputText: String = "",
    optionalText: String? = null,
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
    focusRequester: FocusRequester = FocusRequester()
) {
    val theme = ThemeManagerCompose.theme.value
    val typography = ThemeManagerCompose.typography

    val textState = remember { mutableStateOf(TextFieldValue(inputText)) }
    val focusState = remember { mutableStateOf(false) }
    val localFocusManager = LocalFocusManager.current

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

        val labelAlpha = if (focusState.value || textState.value.text.isNotEmpty()) 1.0f else 0.0f

        val alpha: Float by animateFloatAsState(
            if (focusState.value || textState.value.text.isNotEmpty()) 1f else 0f,
            animationSpec = tween(ALPHA_ANIMATION_TIME)
        )

        Text(
            modifier = Modifier
                .alpha(alpha)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = focusRequester::requestFocus
                ),
            text = optionalText ?: "",
            style = typography.subhead2,
            color = Color(defaultColor),
            fontSize = TEXT_FONT_SIZE.sp,
            maxLines = if (singleLine) 1 else Int.MAX_VALUE
        )

        Box {
            BasicTextField(
                modifier = Modifier
                    .onFocusChanged {
                        focusState.value = it.isFocused
                    }
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(top = TEXT_FIELD_PADDING.dp),
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
                textStyle = typography.body1.copy(color = Color(inputTextColor)),
                decorationBox = { innerTextField ->
                    Box {
                        if (textState.value.text.isEmpty()) {
                            val text = if (focusState.value) placeholderText else optionalText
                            val color = if (focusState.value) placeholderTextColor else defaultColor
                            Text(
                                text = text ?: "",
                                style = typography.body1,
                                color = Color(color),
                                maxLines = if (singleLine) 1 else Int.MAX_VALUE
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
                        .align(BiasAlignment(ICON_HORIZONTAL_BIAS, ICON_VERTICAL_BIAS))
                        .padding(ICON_PADDING.dp),
                    painter = it,
                    contentDescription = null,
                    tint = Color(iconColor)
                )
            }
        }

        Box(
            Modifier
                .height(LINE_BOX_HEIGHT.dp)
                .fillMaxWidth()
                .padding(bottom = LINE_PADDING_BOTTOM.dp, top = LINE_PADDING_TOP.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                val pathEffect = if (isReadOnly) PathEffect.dashPathEffect(
                    floatArrayOf(LINE_PATH_EFFECT_INTERVAL, LINE_PATH_EFFECT_INTERVAL),
                    0f
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
                    .padding(bottom = PADDING.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = focusRequester::requestFocus
                    ),
                text = it,
                style = typography.subhead2,
                color = Color(additionalTextColor),
                fontSize = TEXT_FONT_SIZE.sp
            )
        }
    }
}

fun Modifier.fakeClickable(focusRequester: FocusRequester): Modifier = composed {
    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = focusRequester::requestFocus
    )
}

private const val PREVIEW_BOX_PADDING = 16
private const val PREVIEW_BOX_WIDTH = 240
private const val TEXT_FONT_SIZE = 14
private const val PADDING = 4
private const val LINE_BOX_HEIGHT = 4
private const val LINE_OFFSET_CENTERED = 2
private const val LINE_PADDING_BOTTOM = 6
private const val LINE_PADDING_TOP = 2
private const val LINE_PATH_EFFECT_INTERVAL = 10f
private const val ICON_SIZE = 24
private const val ICON_PADDING = 4
private const val ICON_HORIZONTAL_BIAS = 1f
private const val ICON_VERTICAL_BIAS = 0.5f
private const val TEXT_FIELD_PADDING = 4
private const val ALPHA_ANIMATION_TIME = 300