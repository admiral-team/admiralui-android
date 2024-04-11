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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.SliderColors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.admiral.themes.ThemeManager
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.textfield.fakeClickable

@Preview(showBackground = true)
@Composable
fun SliderPreview() {
    Box(
        modifier = Modifier
            .padding(SLIDER_BOX_PADDING.dp)
            .width(SLIDER_BOX_WIDTH.dp)
    ) {
        Slider(
            optionalText = "Optional label 2",
            placeholderText = "Placeholder text",
            additionalText = "Additional text",
            icon = painterResource(id = R.drawable.admiral_ic_heart_solid),
            value = SLIDER_PREVIEW_VALUE,
            valueRange = SLIDER_PREVIEW_VALUE_RANGE_START..SLIDER_PREVIEW_VALUE_RANGE_END
        )
    }
}

@Composable
@Suppress("LongParameterList")
fun Slider(
    modifier: Modifier = Modifier,
    optionalText: String? = null,
    placeholderText: String? = null,
    additionalText: String? = null,
    icon: Painter? = null,
    onIconClick: () -> Unit = {},
    isError: Boolean = false,
    isReadOnly: Boolean = false,
    isEnabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    focusRequester: FocusRequester = FocusRequester(),
    trackColor: Color? = null,
    thumbColor: Color? = null,
    textColorState: ColorState? = null,
    inputTextColorState: ColorState? = null,
    errorColor: Int? = null,
    iconBackgroundColorState: ColorState? = null,
    iconColorState: ColorState? = null,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit = {}
) {
    val theme = ThemeManagerCompose.theme.value
    val typography = ThemeManagerCompose.typography

    val textState = remember { mutableStateOf(TextFieldValue(value.toInt().toString())) }
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
            maxLines = 1
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
                keyboardOptions = keyboardOptions.copy(keyboardType = KeyboardType.Number),
                keyboardActions = keyboardActions,
                singleLine = true,
                maxLines = 1,
                visualTransformation = visualTransformation,
                onValueChange = {
                    if (it.text.isDigitsOnly()) {
                        val v = it.text.toInt()
                        if (isInRange(valueRange.start.toInt(), valueRange.endInclusive.toInt(), v)) {
                            textState.value = it
                            onValueChange.invoke(it.text.toFloat())
                        }
                    }
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
                                maxLines = 1
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

        val steps = valueRange.endInclusive - valueRange.start + 1
        val accent = thumbColor ?: Color(theme.palette.elementAccent)
        val track = trackColor ?: Color(theme.palette.elementAccent)
        val trackInactive = Color(theme.palette.elementPrimary)
        val sliderColors = AdmiralSliderColor(
            thumbColor = accent,
            disabledThumbColor = accent.copy(alpha = 0.6f),
            activeTrackColor = track,
            disabledActiveTrackColor = track.copy(alpha = 0.6f),
            inactiveTrackColor = trackInactive,
            disabledInactiveTrackColor = trackInactive.copy(alpha = 0.6f)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RectangleShape),
            contentAlignment = Alignment.Center
        ) {
            HackedSlider(
                value = textState.value.text.toFloat(),
                steps = steps.toInt(),
                enabled = isEnabled || !isReadOnly,
                valueRange = valueRange,
                onValueChange = {
                    textState.value = TextFieldValue(it.toInt().toString())
                },
                colors = sliderColors
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = ADDITIONAL_TEXT_PADDING.dp)
                    .align(Alignment.CenterVertically)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = focusRequester::requestFocus
                    ),
                text = valueRange.start.toInt().toString(),
                style = typography.subhead2,
                color = Color(additionalTextColor),
                fontSize = TEXT_FONT_SIZE.sp
            )

            Text(
                modifier = Modifier
                    .padding(bottom = ADDITIONAL_TEXT_PADDING.dp)
                    .align(Alignment.CenterVertically)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = focusRequester::requestFocus
                    ),
                text = valueRange.endInclusive.toInt().toString(),
                style = typography.subhead2,
                color = Color(additionalTextColor),
                textAlign = TextAlign.End,
                fontSize = TEXT_FONT_SIZE.sp
            )
        }

        additionalText?.let {
            Text(
                modifier = Modifier
                    .padding(bottom = ADDITIONAL_TEXT_PADDING.dp)
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

private const val SLIDER_BOX_PADDING = 16
private const val SLIDER_BOX_WIDTH = 240
private const val SLIDER_PREVIEW_VALUE = 200.0f
private const val SLIDER_PREVIEW_VALUE_RANGE_START = 200f
private const val SLIDER_PREVIEW_VALUE_RANGE_END = 10000f
private const val ADDITIONAL_TEXT_PADDING = 4
private const val TEXT_FONT_SIZE = 14
private const val ICON_PADDING = 4
private const val ICON_SIZE = 24
private const val ICON_VERTICAL_BIAS = 0.5f
private const val ICON_HORIZONTAL_BIAS = 1f
private const val TEXT_FIELD_PADDING = 4
private const val ALPHA_ANIMATION_TIME = 300

internal fun isInRange(a: Int, b: Int, c: Int): Boolean {
    return if (b > a) c in a..b else c in b..a
}

internal data class AdmiralSliderColor(
    private val thumbColor: Color,
    private val disabledThumbColor: Color,
    private val activeTrackColor: Color,
    private val inactiveTrackColor: Color,
    private val disabledActiveTrackColor: Color,
    private val disabledInactiveTrackColor: Color
) : SliderColors {

    @Composable
    override fun thumbColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) thumbColor else disabledThumbColor)
    }

    @Composable
    override fun trackColor(enabled: Boolean, active: Boolean): State<Color> {
        return rememberUpdatedState(
            if (enabled) {
                if (active) activeTrackColor else inactiveTrackColor
            } else {
                if (active) disabledActiveTrackColor else disabledInactiveTrackColor
            }
        )
    }

    @Composable
    override fun tickColor(enabled: Boolean, active: Boolean): State<Color> {
        return trackColor(enabled, active)
    }
}