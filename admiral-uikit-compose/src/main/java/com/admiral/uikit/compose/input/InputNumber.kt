package com.admiral.uikit.compose.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X9
import com.admiral.uikit.core.components.input.InputType
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

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
    isAutoWidth: Boolean = true,
    onValueChange: ((old: Int, new: Int) -> Unit)? = null,
) {

    var currentValue by remember(value) { mutableIntStateOf(value) }
    var previousValue by remember { mutableIntStateOf(value) }
    var incrementEnabled by remember { mutableStateOf(true) }
    var decrementEnabled by remember { mutableStateOf(true) }

    val dfs = DecimalFormatSymbols()
    dfs.groupingSeparator = ' '
    val df = DecimalFormat(DecimalFormatPattern, dfs)
    val valueWithSpaceState by remember(currentValue) {
        mutableStateOf(df.format(currentValue).toString())
    }

    val iconPadding = when (inputType) {
        InputType.OVAL -> DIMEN_X2
        else -> RectangleIconPadding
    }

    val textFieldMargin = when (inputType) {
        InputType.OVAL -> DIMEN_X1
        InputType.RECTANGLE -> DIMEN_X2
        InputType.TEXT_FIELD -> TextFieldMargin
    }

    val textFieldMinWidth = when (inputType) {
        InputType.TEXT_FIELD -> LargeWidthTextField
        else -> DefaultWidthTextField
    }

    val textFieldPadding = when (inputType) {
        InputType.TEXT_FIELD -> DIMEN_X2
        else -> 0.dp
    }

    LaunchedEffect(key1 = previousValue, key2 = currentValue) {
        when {
            currentValue in (minValue + 1) until maxValue -> {
                incrementEnabled = true
                decrementEnabled = true
                onValueChange?.invoke(previousValue, currentValue)
            }

            currentValue >= maxValue -> incrementEnabled = false
            currentValue <= minValue -> decrementEnabled = false
        }
    }

    ConstraintLayout(
        modifier = modifier,
    ) {

        val (
            optionalTextId,
            decrementIconId,
            valueTextFieldId,
            incrementIconId,
        ) = createRefs()

        optionalText?.let { text ->
            Text(
                modifier = Modifier
                    .constrainAs(optionalTextId) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(anchor = parent.start, margin = DIMEN_X4)
                        end.linkTo(anchor = decrementIconId.start, margin = DIMEN_X3)
                        width = Dimension.fillToConstraints
                    },
                text = text,
                style = AdmiralTheme.typography.body1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = colors.getTextColor(isEnabled = isEnabled).value,
            )
        }

        Box(
            modifier = Modifier
                .constrainAs(decrementIconId) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(anchor = valueTextFieldId.start, margin = textFieldMargin)
                }
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
                .clickable(
                    onClick = {
                        previousValue = currentValue
                        currentValue--
                    },
                    enabled = decrementEnabled && isEnabled,
                    indication = rememberRipple(color = colors.getRippleColor(isEnabled = isEnabled && decrementEnabled).value),
                    interactionSource = remember { MutableInteractionSource() },
                )
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
                .constrainAs(valueTextFieldId) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(anchor = incrementIconId.start, margin = textFieldMargin)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                }
                .background(color = colors.getTextFieldBackgroundColor(isEnabled = isEnabled).value),
            value = valueWithSpaceState,
            onValueChange = {},
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
            decorationBox = @Composable { innerTextField ->
                Row(
                    modifier = Modifier
                        .padding(textFieldPadding),
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

        Box(modifier = Modifier
            .constrainAs(incrementIconId) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(anchor = parent.end, margin = DIMEN_X4)
            }
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
            .clickable(
                onClick = {
                    previousValue = currentValue
                    currentValue++
                },
                enabled = incrementEnabled && isEnabled,
                indication = rememberRipple(color = colors.getRippleColor(isEnabled = isEnabled && incrementEnabled).value),
                interactionSource = remember { MutableInteractionSource() },
            )
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
private const val DecimalFormatPattern = "###,###"
private val RectangleIconPadding = 6.dp
private val TextFieldMargin = 2.dp
private val BorderWidth = 2.dp

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
            inputType = inputType,
            colors = colors,
            value = defaultValue,
            optionalText = "Optional text",
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