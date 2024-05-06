package com.admiral.uikit.compose.checkbox

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.admiral.themes.compose.AdmiralTheme
import kotlin.math.floor
import kotlin.math.max

/**
 * @see [CheckBox] if you want a simple component that represents Boolean state
 *
 * @param state whether TriStateCheckbox is checked, unchecked or in indeterminate state
 * @param onClick callback to be invoked when checkbox is being clicked,
 * therefore the change of ToggleableState state is requested.  If null, then this is passive
 * and relies entirely on a higher-level component to control the state.
 * @param modifier Modifier to be applied to the layout of the checkbox
 * @param isEnabled whether the component is enabled or grayed out
 * @param interactionSource the [MutableInteractionSource] representing the stream of
 * [Interaction]s for this TriStateCheckbox. You can create and pass in your own remembered
 * [MutableInteractionSource] if you want to observe [Interaction]s and customize the
 * appearance / behavior of this TriStateCheckbox in different [Interaction]s.
 * @param colors [CheckboxColors] that will be used to determine the color of the checkmark / box
 * / border in different states. See [CheckboxDefaults.colors].
 */
@Composable
fun TriStateCheckbox(
    state: ToggleableState,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isError: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: AdmiralCheckBoxColor = checkBoxColor()
) {
    val toggleableModifier =
        if (onClick != null) {
            Modifier.triStateToggleable(
                state = state,
                onClick = onClick,
                enabled = isEnabled,
                role = Role.Checkbox,
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = false,
                    radius = CheckboxRippleRadius
                )
            )
        } else {
            Modifier
        }

    CheckboxImpl(
        isEnabled = isEnabled,
        isError = isError,
        value = state,
        modifier = modifier
            .then(toggleableModifier),
        colors = colors
    )
}

@Composable
private fun CheckboxImpl(
    isEnabled: Boolean,
    isError: Boolean,
    value: ToggleableState,
    modifier: Modifier,
    colors: AdmiralCheckBoxColor
) {
    val transition = updateTransition(value, label = "")

    val checkDrawFraction by transition.animateFloat(
        transitionSpec = {
            when {
                initialState == ToggleableState.Off -> tween(durationMillis = CheckAnimationDuration)
                targetState == ToggleableState.Off -> snap(BoxOutDuration)
                else -> spring()
            }
        }, label = ""
    ) {
        when (it) {
            ToggleableState.On -> 1f
            ToggleableState.Off -> 0f
            ToggleableState.Indeterminate -> 1f
        }
    }

    val checkCenterGravitationShiftFraction by transition.animateFloat(
        transitionSpec = {
            when {
                initialState == ToggleableState.Off -> snap()
                targetState == ToggleableState.Off -> snap(BoxOutDuration)
                else -> tween(durationMillis = CheckAnimationDuration)
            }
        }, label = ""
    ) {
        when (it) {
            ToggleableState.On -> 0f
            ToggleableState.Off -> 0f
            ToggleableState.Indeterminate -> 1f
        }
    }

    val checkCache = remember { CheckDrawingCache() }
    val checkColor = AdmiralTheme.colors.elementStaticWhite
    val boxColor = if (value == ToggleableState.On) {
        colors.getCheckedColor(isEnabled = isEnabled, isError = isError).value
    } else {
        Color.Transparent
    }

    val borderColor = colors.getNormalColor(isEnabled = isEnabled, isError = isError).value

    Canvas(
        modifier
            .wrapContentSize(Alignment.Center)
            .requiredSize(CheckboxSize)
    ) {
        val strokeWidthPx = floor(StrokeWidth.toPx())
        drawBox(
            boxColor = boxColor,
            borderColor = borderColor,
            radius = RadiusSize.toPx(),
            strokeWidth = strokeWidthPx
        )
        drawCheck(
            checkColor = checkColor,
            checkFraction = checkDrawFraction,
            crossCenterGravitation = checkCenterGravitationShiftFraction,
            strokeWidthPx = strokeWidthPx,
            drawingCache = checkCache
        )
    }
}

private fun DrawScope.drawBox(
    boxColor: Color,
    borderColor: Color,
    radius: Float,
    strokeWidth: Float
) {
    val halfStrokeWidth = strokeWidth / 2.0f
    val stroke = Stroke(strokeWidth)
    val checkboxSize = size.width
    if (boxColor == borderColor) {
        drawRoundRect(
            boxColor,
            size = Size(checkboxSize, checkboxSize),
            cornerRadius = CornerRadius(radius),
            style = Fill
        )
    } else {
        drawRoundRect(
            boxColor,
            topLeft = Offset(strokeWidth, strokeWidth),
            size = Size(checkboxSize - strokeWidth * 2, checkboxSize - strokeWidth * 2),
            cornerRadius = CornerRadius(max(0f, radius - strokeWidth)),
            style = Fill
        )
        drawRoundRect(
            borderColor,
            topLeft = Offset(halfStrokeWidth, halfStrokeWidth),
            size = Size(checkboxSize - strokeWidth, checkboxSize - strokeWidth),
            cornerRadius = CornerRadius(radius - halfStrokeWidth),
            style = stroke
        )
    }
}

private fun DrawScope.drawCheck(
    checkColor: Color,
    checkFraction: Float,
    crossCenterGravitation: Float,
    strokeWidthPx: Float,
    drawingCache: CheckDrawingCache
) {
    val stroke = Stroke(width = strokeWidthPx, cap = StrokeCap.Square)
    val width = size.width
    val checkCrossX = 0.4f
    val checkCrossY = 0.7f
    val leftX = 0.2f
    val leftY = 0.5f
    val rightX = 0.8f
    val rightY = 0.3f

    val gravitatedCrossX = lerp(checkCrossX, 0.5f, crossCenterGravitation)
    val gravitatedCrossY = lerp(checkCrossY, 0.5f, crossCenterGravitation)
    // gravitate only Y for end to achieve center line
    val gravitatedLeftY = lerp(leftY, 0.5f, crossCenterGravitation)
    val gravitatedRightY = lerp(rightY, 0.5f, crossCenterGravitation)

    with(drawingCache) {
        checkPath.reset()
        checkPath.moveTo(width * leftX, width * gravitatedLeftY)
        checkPath.lineTo(width * gravitatedCrossX, width * gravitatedCrossY)
        checkPath.lineTo(width * rightX, width * gravitatedRightY)
        pathMeasure.setPath(checkPath, false)
        pathToDraw.reset()
        pathMeasure.getSegment(
            0f, pathMeasure.length * checkFraction, pathToDraw, true
        )
    }
    drawPath(drawingCache.pathToDraw, checkColor, style = stroke)
}

@Immutable
private class CheckDrawingCache(
    val checkPath: Path = Path(),
    val pathMeasure: PathMeasure = PathMeasure(),
    val pathToDraw: Path = Path()
)

private const val BoxOutDuration = 100
private const val CheckAnimationDuration = 100
private val CheckboxRippleRadius = 24.dp
private val CheckboxSize = 20.dp
private val StrokeWidth = 2.dp
private val RadiusSize = 4.dp