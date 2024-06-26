package com.admiral.uikit.compose.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X9
import com.admiral.uikit.core.components.input.InputType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalTextApi::class)
@Suppress("MagicNumber", "LongMethod")
@Composable
fun InputNumber(
    modifier: Modifier = Modifier,
    value: Int = 0,
    optionalText: String? = null,
    incrementIcon: Painter = painterResource(id = R.drawable.admiral_ic_plus_outline),
    decrementIcon: Painter = painterResource(id = R.drawable.admiral_ic_minus_outline),
    inputType: InputType = InputType.RECTANGLE,
    maxValue: Int = DefaultMaxValue,
    minValue: Int = DefaultMinValue,
    colors: InputNumberColors = AdmiralInputNumberColors.oval(),
    isEnabled: Boolean = true,
    onValueChange: ((old: Int, new: Int) -> Unit)? = null,
) {

    var currentValue by remember(value) { mutableIntStateOf(value) }
    var previousValue by remember { mutableIntStateOf(value) }
    var incrementEnabled by remember { mutableStateOf(true) }
    var decrementEnabled by remember { mutableStateOf(true) }
    var autoDecrement by remember { mutableStateOf(false) }
    var autoIncrement by remember { mutableStateOf(false) }
    val incrementInteractionSource = remember { MutableInteractionSource() }
    val decrementInteractionSource = remember { MutableInteractionSource() }
    var runCount by remember { mutableLongStateOf(0L) }

    val iconPadding = when (inputType) {
        InputType.OVAL -> DIMEN_X2
        else -> RectangleIconPadding
    }

    val textFieldPadding = when (inputType) {
        InputType.OVAL -> DIMEN_X1
        InputType.RECTANGLE -> DIMEN_X2
        InputType.TEXT_FIELD -> TextFieldMargin
    }

    val textFieldMinWidth = when (inputType) {
        InputType.TEXT_FIELD -> LargeWidthTextField
        else -> DefaultWidthTextField
    }

    val decorationBoxPadding = when (inputType) {
        InputType.TEXT_FIELD -> DIMEN_X1
        else -> 0.dp
    }

    SideEffect {
        when {
            currentValue in minValue..maxValue -> {
                onValueChange?.invoke(previousValue, currentValue)
            }
        }
    }

    incrementEnabled = autoDecrement.not() && currentValue < maxValue
    decrementEnabled = autoIncrement.not() && currentValue > minValue

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        optionalText?.let { text ->
            Text(
                text = text,
                style = AdmiralTheme.typography.body1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = colors.getTextColor(isEnabled = isEnabled).value,
            )
            Spacer(modifier = Modifier.width(DIMEN_X1))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .padding(start = DIMEN_X1)
                    .background(
                        color = colors.getBackgroundColor(isEnabled = isEnabled && decrementEnabled).value,
                        shape = getIconShape(true, inputType),
                    )
                    .border(
                        brush = SolidColor(colors.getIconBorderColor(isEnabled = isEnabled && decrementEnabled).value),
                        shape = getIconShape(true, inputType),
                        width = BorderWidth,
                    )
                    .clip(getIconShape(true, inputType))
                    .indication(
                        interactionSource = decrementInteractionSource,
                        indication = rememberRipple(color = colors.getRippleColor(isEnabled = isEnabled && decrementEnabled).value)
                    )
                    .pointerInput(isEnabled, decrementEnabled) {
                        detectTapGestures(
                            onPress = { offset: Offset ->
                                val press = PressInteraction.Press(offset)
                                val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
                                if (isEnabled && decrementEnabled) {
                                    decrementInteractionSource.emit(press)
                                    previousValue = currentValue
                                    currentValue--
                                }
                                val heldButtonJob = scope.launch {
                                    while (isEnabled && decrementEnabled) {
                                        delay(DelayAutoChange)
                                        runCount += DelayAutoChange
                                        if (currentValue <= minValue) {
                                            autoDecrement = false
                                            decrementInteractionSource.emit(
                                                PressInteraction.Release(
                                                    press
                                                )
                                            )
                                            return@launch
                                        }
                                        autoDecrement = true
                                        previousValue = currentValue
                                        currentValue -= when {
                                            runCount <= DelayAutoChange * 5 -> {
                                                OneMultiplier
                                            }

                                            runCount < DelayAutoChange * 12 -> {
                                                FiveMultiplier
                                            }

                                            runCount < DelayAutoChange * 20 -> {
                                                TenMultiplier
                                            }

                                            else -> {
                                                HundredMultiplier
                                            }
                                        }
                                    }
                                }
                                tryAwaitRelease()
                                decrementInteractionSource.emit(PressInteraction.Cancel(press))
                                heldButtonJob.cancel()
                                autoDecrement = false
                                runCount = 0L
                            },
                        )
                    }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(iconPadding),
                    painter = decrementIcon,
                    tint = colors.getIconTintColor(isEnabled = isEnabled && decrementEnabled).value,
                    contentDescription = null,
                )
            }

            BasicTextField(
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .defaultMinSize(
                        minWidth = textFieldMinWidth,
                        minHeight = DIMEN_X9
                    )
                    .padding(horizontal = textFieldPadding)
                    .background(color = colors.getTextFieldBackgroundColor(isEnabled = isEnabled).value),
                value = currentValue.toString(),
                onValueChange = { newString ->
                    previousValue = currentValue
                    val newValue = newString.toIntOrNull()
                    println(newValue)
                    when {
                        newValue != null -> {
                            currentValue = when {
                                newValue >= maxValue -> maxValue
                                newValue <= minValue -> minValue
                                else -> newValue
                            }
                        }

                        newString.isEmpty() -> currentValue = 0
                        newString == "-" -> currentValue = 0
                    }
                },
                enabled = isEnabled,
                readOnly = inputType != InputType.TEXT_FIELD,
                maxLines = 1,
                cursorBrush = SolidColor(colors.getTextFieldCursorColor(isEnabled = isEnabled).value),
                textStyle = AdmiralTheme.typography.body1.copy(
                    color = colors.getTextColor(isEnabled = isEnabled).value,
                    textAlign = TextAlign.Center,
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Number
                ),
                visualTransformation = { annotatedString ->
                    numberFormatter(annotatedString.text)
                },
                decorationBox = @Composable { innerTextField ->
                    Row(
                        modifier = Modifier
                            .padding(decorationBoxPadding),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                        ) {
                            innerTextField()
                        }
                    }
                }
            )

            Box(
                modifier = Modifier
                    .background(
                        color = colors.getBackgroundColor(isEnabled = isEnabled && incrementEnabled).value,
                        shape = getIconShape(false, inputType)
                    )
                    .border(
                        brush = SolidColor(colors.getIconBorderColor(isEnabled = isEnabled && incrementEnabled).value),
                        shape = getIconShape(false, inputType),
                        width = BorderWidth,
                    )
                    .clip(getIconShape(false, inputType))
                    .indication(
                        interactionSource = incrementInteractionSource,
                        indication = rememberRipple(color = colors.getRippleColor(isEnabled = isEnabled && incrementEnabled).value)
                    )
                    .pointerInput(isEnabled, incrementEnabled) {
                        detectTapGestures(
                            onPress = { offset: Offset ->
                                val press = PressInteraction.Press(offset)
                                val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
                                if (isEnabled && incrementEnabled) {
                                    incrementInteractionSource.emit(press)
                                    previousValue = currentValue
                                    currentValue++
                                }
                                val heldButtonJob = scope.launch {
                                    while (isEnabled && incrementEnabled) {
                                        delay(DelayAutoChange)
                                        runCount += DelayAutoChange
                                        if (currentValue >= maxValue) {
                                            autoIncrement = false
                                            incrementInteractionSource.emit(
                                                PressInteraction.Release(
                                                    press
                                                )
                                            )
                                            return@launch
                                        }
                                        autoIncrement = true
                                        previousValue = currentValue
                                        currentValue += when {
                                            runCount <= DelayAutoChange * 5 -> {
                                                OneMultiplier
                                            }

                                            runCount < DelayAutoChange * 12 -> {
                                                FiveMultiplier
                                            }

                                            runCount < DelayAutoChange * 20 -> {
                                                TenMultiplier
                                            }

                                            else -> {
                                                HundredMultiplier
                                            }
                                        }
                                    }
                                }
                                tryAwaitRelease()
                                incrementInteractionSource.emit(PressInteraction.Release(press))
                                heldButtonJob.cancel()
                                autoIncrement = false
                                runCount = 0L
                            },
                        )
                    }
            ) {
                Icon(
                    modifier = Modifier
                        .padding(iconPadding),
                    painter = incrementIcon,
                    tint = colors.getIconTintColor(isEnabled = isEnabled && incrementEnabled).value,
                    contentDescription = null
                )
            }
        }
    }
}

private fun getIconShape(isLeft: Boolean, inputType: InputType): RoundedCornerShape {
    return when (inputType) {
        InputType.OVAL -> CircleShape
        InputType.RECTANGLE -> RoundedCornerShape(DIMEN_X2)
        InputType.TEXT_FIELD -> {
            if (isLeft) {
                RoundedCornerShape(
                    topStart = DIMEN_X2,
                    topEnd = 0.dp,
                    bottomStart = DIMEN_X2,
                    bottomEnd = 0.dp
                )
            } else {
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = DIMEN_X2,
                    bottomStart = 0.dp,
                    bottomEnd = DIMEN_X2
                )
            }
        }
    }
}

private const val DefaultMinValue = -99999
private const val DefaultMaxValue = 99999
private val DefaultWidthTextField = 48.dp
private val LargeWidthTextField = 56.dp
private val RectangleIconPadding = 6.dp
private val TextFieldMargin = 2.dp
private val BorderWidth = 2.dp
private const val DelayAutoChange = 300L
private const val OneMultiplier = 1
private const val FiveMultiplier = 5
private const val TenMultiplier = 10
private const val HundredMultiplier = 100

@Composable
private fun InputNumber(
    defaultValue: Int = 1,
    inputType: InputType = InputType.OVAL,
    colors: InputNumberColors = AdmiralInputNumberColors.oval()
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = DIMEN_X1)
    ) {
        InputNumber(
            modifier = Modifier.fillMaxWidth(),
            value = defaultValue,
            optionalText = "Optional text",
            inputType = inputType,
            colors = colors,
            onValueChange = { old, new ->
                println("InputNumberPreview old $old, new $new")
            }
        )
    }
}

@Preview
@Composable
fun InputNumberPreview() {
    AdmiralTheme {
        Surface(
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = DIMEN_X2)
            ) {
                InputNumber()
                Spacer(modifier = Modifier.size(DIMEN_X2))
                InputNumber(
                    inputType = InputType.RECTANGLE,
                    colors = AdmiralInputNumberColors.rectangle()
                )
                Spacer(modifier = Modifier.size(DIMEN_X2))
                InputNumber(
                    inputType = InputType.TEXT_FIELD,
                    colors = AdmiralInputNumberColors.textField()
                )
            }
        }
    }
}