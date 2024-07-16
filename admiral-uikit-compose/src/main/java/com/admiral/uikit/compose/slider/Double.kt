package com.admiral.uikit.compose.slider

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.textfield.fakeClickable
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState

@Preview(showBackground = true)
@Composable
fun DoubleSliderPreview() {
    Box(
        modifier = Modifier
            .padding(SliderBoxPadding.dp)
            .width(SliderBoxWidth.dp)
    ) {
        DoubleSlider(
            optionalText = "Optional label 2",
            additionalText = "Additional text",
            valueLeft = SliderPreviewValue,
            valueRight = SliderPreviewRightValue,
            valueRange = SliderPreviewRangeStart..SliderPreviewRangeEnd
        )
    }
}

@Composable
@Suppress("LongParameterList")
fun DoubleSlider(
    modifier: Modifier = Modifier,
    optionalText: String? = null,
    additionalText: String? = null,
    isError: Boolean = false,
    isReadOnly: Boolean = false,
    isEnabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    focusRequester: FocusRequester = FocusRequester(),
    trackColor: Color = AdmiralTheme.colors.elementAccent,
    thumbColor: Color = AdmiralTheme.colors.elementAccent,
    textColorState: ColorState? = null,
    inputTextColorState: ColorState? = null,
    errorColor: Int? = null,
    valueLeft: Float,
    valueRight: Float,
    valueRange: IntRange,
    onValueChange: (Pair<Int, Int>) -> Unit = {}
) {
    val theme = ThemeManagerCompose.theme.value

    val textStateLeft = remember { mutableStateOf(TextFieldValue(valueLeft.toInt().toString())) }
    val textStateRight = remember { mutableStateOf(TextFieldValue(valueRight.toInt().toString())) }
    val focusState = remember { mutableStateOf(false) }

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

        val alpha: Float by animateFloatAsState(
            if (focusState.value || textStateLeft.value.text.isNotEmpty()) 1f else 0f,
            animationSpec = tween(AlphaAnimationTime)
        )

        OptionalText(
            alpha = alpha,
            focusRequester = focusRequester,
            optionalText = optionalText,
            defaultColor = defaultColor
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextField(
                focusState = focusState,
                focusRequester = focusRequester,
                isEnabled = isEnabled,
                isReadOnly = isReadOnly,
                textState = textStateLeft,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                onValueChange = {
                    val rightValue = textStateRight.value.text

                    val value = if (it != "") {
                        it.toInt()
                    } else {
                        0
                    }

                    if (isInRange(
                            valueRange.first,
                            rightValue.toInt(),
                            value
                        ).not()
                    ) {
                        if (value < valueRange.first) {
                            textStateLeft.value = TextFieldValue(valueRange.first.toString())
                            onValueChange.invoke(valueRange.first to rightValue.toInt())
                        } else {
                            textStateLeft.value = TextFieldValue(rightValue)
                            onValueChange.invoke(rightValue.toInt() to rightValue.toInt())
                        }
                    }
                },
                defaultColor = defaultColor,
                inputTextColor = inputTextColor,
                textAlign = TextAlign.Start,
                prefixText = stringResource(id = R.string.admiral_slider_from_compose),
                suffixText = stringResource(id = R.string.admiral_slider_suffix)
            )

            TextField(
                focusState = focusState,
                focusRequester = focusRequester,
                isEnabled = isEnabled,
                isReadOnly = isReadOnly,
                textState = textStateRight,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                onValueChange = {
                    val leftValue = textStateLeft.value.text

                    val value = if (it != "") {
                        it.toInt()
                    } else {
                        0
                    }

                    if (isInRange(leftValue.toInt(), valueRange.last, value).not()) {
                        if (value > valueRange.last) {
                            textStateRight.value = TextFieldValue(valueRange.last.toString())
                            onValueChange.invoke(leftValue.toInt() to valueRange.last)
                        } else {
                            textStateRight.value = TextFieldValue(leftValue)
                            onValueChange.invoke(leftValue.toInt() to leftValue.toInt())
                        }
                    }
                },
                defaultColor = defaultColor,
                inputTextColor = inputTextColor,
                textAlign = TextAlign.End,
                prefixText = stringResource(id = R.string.admiral_slider_to_compose),
                suffixText = stringResource(id = R.string.admiral_slider_suffix)
            )
        }

        RangeSlider(
            valueRange = valueRange,
            thumbColor = thumbColor,
            trackColor = trackColor,
            textStateLeft = textStateLeft,
            textStateRight = textStateRight,
            isEnabled = isEnabled,
            isReadOnly = isReadOnly
        )

        RangeTexts(
            focusRequester = focusRequester,
            valueRange = valueRange,
            additionalTextColor = additionalTextColor
        )

        AdditionalText(
            additionalText = additionalText,
            focusRequester = focusRequester,
            additionalTextColor = additionalTextColor
        )
    }
}

@Composable
private fun AdditionalText(
    additionalText: String?,
    focusRequester: FocusRequester,
    additionalTextColor: Int
) {
    additionalText?.let {
        Text(
            modifier = Modifier
                .padding(bottom = AdditionalTextPadding.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = focusRequester::requestFocus
                ),
            text = it,
            style = AdmiralTheme.typography.subhead2,
            color = Color(additionalTextColor),
            fontSize = TextFontSize.sp
        )
    }
}

@Composable
private fun RangeTexts(
    focusRequester: FocusRequester,
    valueRange: IntRange,
    additionalTextColor: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = AdditionalTextPadding.dp)
                .align(Alignment.CenterVertically)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = focusRequester::requestFocus
                ),
            text = valueRange.first.toString(),
            style = AdmiralTheme.typography.subhead2,
            color = Color(additionalTextColor),
            fontSize = TextFontSize.sp
        )

        Text(
            modifier = Modifier
                .padding(bottom = AdditionalTextPadding.dp)
                .align(Alignment.CenterVertically)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = focusRequester::requestFocus
                ),
            text = valueRange.last.toString(),
            style = AdmiralTheme.typography.subhead2,
            color = Color(additionalTextColor),
            textAlign = TextAlign.End,
            fontSize = TextFontSize.sp
        )
    }
}

@Composable
private fun RangeSlider(
    valueRange: IntRange,
    thumbColor: Color = AdmiralTheme.colors.elementAccent,
    trackColor: Color = AdmiralTheme.colors.elementAccent,
    textStateLeft: MutableState<TextFieldValue>,
    textStateRight: MutableState<TextFieldValue>,
    isEnabled: Boolean,
    isReadOnly: Boolean
) {
    val trackInactive = AdmiralTheme.colors.elementPrimary
    val sliderColors = SliderColors(
        thumbColor = thumbColor,
        disabledThumbColor = thumbColor.withAlpha(value = 0.6f),
        activeTrackColor = trackColor,
        disabledActiveTrackColor = trackColor.withAlpha(value = 0.6f),
        inactiveTrackColor = trackInactive,
        disabledInactiveTrackColor = trackInactive.withAlpha(value = 0.6f),
    )
    HackedDoubleSlider(
        value = textStateLeft.value.text.toFloat()..textStateRight.value.text.toFloat(),
        enabled = isEnabled && !isReadOnly,
        valueRange = valueRange.first.toFloat()..valueRange.last.toFloat(),
        onValueChange = {
            textStateLeft.value = TextFieldValue(it.start.toInt().toString())
            textStateRight.value = TextFieldValue(it.endInclusive.toInt().toString())
        },
        colors = sliderColors
    )
}

@Composable
private fun TextField(
    focusState: MutableState<Boolean>,
    focusRequester: FocusRequester,
    isEnabled: Boolean,
    isReadOnly: Boolean,
    textState: MutableState<TextFieldValue>,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    onValueChange: (String) -> Unit,
    defaultColor: Int,
    inputTextColor: Int,
    textAlign: TextAlign,
    prefixText: String,
    suffixText: String
) {
    BasicTextField(
        modifier = Modifier
            .onFocusChanged {
                focusState.value = it.isFocused
            }
            .widthIn(1.dp, Dp.Infinity)
            .focusRequester(focusRequester),
        enabled = isEnabled,
        readOnly = isReadOnly,
        value = textState.value,
        keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Number),
        keyboardActions = keyboardActions,
        singleLine = true,
        maxLines = 1,
        visualTransformation = PrefixTransformation(
            prefix = prefixText,
            color = AdmiralTheme.colors.textSecondary,
            textStyle = AdmiralTheme.typography.body1,
            suffix = suffixText
        ),
        onValueChange = {
            textState.value = if (it.text != "") it else TextFieldValue("0")
            onValueChange(it.text)
        },
        cursorBrush = SolidColor(Color(defaultColor)),
        textStyle = AdmiralTheme.typography.body1.copy(
            color = Color(inputTextColor),
            textAlign = textAlign
        ),
    )
}

@Composable
private fun OptionalText(
    alpha: Float,
    focusRequester: FocusRequester,
    optionalText: String?,
    defaultColor: Int
) {
    Text(
        modifier = Modifier
            .alpha(alpha)
            .padding(bottom = DIMEN_X2)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = focusRequester::requestFocus
            ),
        text = optionalText ?: "",
        style = AdmiralTheme.typography.subhead2,
        color = Color(defaultColor),
        fontSize = TextFontSize.sp,
        maxLines = 1
    )
}

class PrefixTransformation(
    private val prefix: String,
    private val suffix: String,
    private val color: Color,
    private val textStyle: TextStyle
) :
    VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        return prefixFilter(
            number = text,
            prefix = prefix,
            suffix = suffix,
            colorPrefix = color,
            style = textStyle
        )
    }
}

fun prefixFilter(
    number: AnnotatedString,
    prefix: String,
    suffix: String,
    colorPrefix: Color,
    style: TextStyle
): TransformedText {
    val prefixOffset = prefix.length

    val numberOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return offset + prefixOffset
        }

        override fun transformedToOriginal(offset: Int): Int {
            return when (offset) {
                in 0..prefixOffset -> {
                    0
                }

                in prefixOffset + number.length..Int.MAX_VALUE -> {
                    prefixOffset + number.length - prefixOffset
                }

                else -> {
                    offset - prefixOffset
                }
            }
        }
    }

    val builder = AnnotatedString.Builder()
    builder.withStyle(
        style = SpanStyle(
            color = colorPrefix,
            fontStyle = style.fontStyle,
            fontSize = style.fontSize,
            fontSynthesis = style.fontSynthesis,
            fontFamily = style.fontFamily,
            fontWeight = style.fontWeight,
        )
    ) {
        append(prefix)
    }
    builder.append(number.text + suffix)

    return TransformedText(
        text = builder.toAnnotatedString(),
        offsetMapping = numberOffsetTranslator
    )
}

private const val SliderBoxPadding = 16
private const val SliderBoxWidth = 240
private const val SliderPreviewValue = 200.0f
private const val SliderPreviewRightValue = 20000.0f
private const val SliderPreviewRangeStart = 200
private const val SliderPreviewRangeEnd = 10000
private const val AdditionalTextPadding = 4
private const val TextFontSize = 14
private const val AlphaAnimationTime = 300
