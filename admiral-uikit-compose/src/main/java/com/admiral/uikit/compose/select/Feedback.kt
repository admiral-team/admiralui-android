package com.admiral.uikit.compose.select

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.toSize
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X6

@Suppress("MagicNumber", "LongMethod")
@Composable
fun Feedback(
    modifier: Modifier = Modifier,
    rating: Int,
    icon: Painter = painterResource(id = R.drawable.admiral_ic_star_solid),
    iconSize: Dp = DIMEN_X6,
    colors: FeedbackColors = AdmiralFeedbackColors.default(),
    isAnimationEnabled: Boolean = true,
    isEnabled: Boolean = true,
    onValueChanged: (rating: Int) -> Unit = {},
    onRatingChanged: (rating: Int) -> Unit = {},
) {
    var rowSize by remember { mutableStateOf(Size.Zero) }

    val direction = LocalLayoutDirection.current
    val density = LocalDensity.current
    val starSizeInPx = remember {
        with(density) { iconSize.toPx() }
    }

    var lastDraggedValue by remember { mutableIntStateOf(0) }


    Row(modifier = modifier
        .onSizeChanged { rowSize = it.toSize() }
        .pointerInput(onValueChanged, isEnabled) {
            if (isEnabled.not()) return@pointerInput
            detectHorizontalDragGestures(
                onDragEnd = {
                    onRatingChanged(lastDraggedValue)
                },
                onHorizontalDrag = { change, _ ->
                    change.consume()
                    val dragX = change.position.x.coerceIn(-1f, rowSize.width)
                    var calculatedStars = calculateStars(dragX, starSizeInPx)
                    if (direction == LayoutDirection.Rtl) {
                        calculatedStars = (rating - calculatedStars)
                    }
                    onValueChanged(calculatedStars)
                    lastDraggedValue = calculatedStars
                }
            )
        }
        .pointerInput(onValueChanged, isEnabled) {
            if (isEnabled.not()) return@pointerInput
            detectTapGestures(
                onTap = {
                    val dragX = it.x.coerceIn(-1f, rowSize.width)
                    var calculatedStars = calculateStars(dragX, starSizeInPx)
                    if (direction == LayoutDirection.Rtl) {
                        calculatedStars = (rating - calculatedStars) + 1
                    }
                    onValueChanged(calculatedStars)
                    onRatingChanged(calculatedStars)
                }
            )
        }
    ) {
        for (it in 0 until StarCount) {
            StarIcon(
                modifier = Modifier
                    .size(size = iconSize),
                icon = icon,
                selected = it < rating,
                iconTintNormal = colors.getIconTintNormalColor(enabled = isEnabled).value,
                iconTintSelected = colors.getIconTintSelectedColor(enabled = isEnabled).value,
            )
        }
    }
}

@Composable
internal fun StarIcon(
    icon: Painter,
    iconTintNormal: Color,
    iconTintSelected: Color,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    val color = if (selected) iconTintSelected else iconTintNormal

    Icon(
        modifier = modifier,
        painter = icon,
        contentDescription = null,
        tint = color
    )
}

private fun calculateStars(
    draggedX: Float,
    starSizeInPx: Float,
): Int {

    if (draggedX <= 0)
        return 0

    for (i in 1..StarCount) {
        if (draggedX < (i * starSizeInPx)) {
            return i
        }
    }

    return 0
}

private const val StarCount = 5

@Suppress("MagicNumber")
@Preview
@Composable
private fun FeedbackPreview() {
    AdmiralTheme {
        var interactFirst by remember { mutableIntStateOf(1) }
        var interactSecond by remember { mutableIntStateOf(3) }

        Column(
            modifier = Modifier
                .background(color = AdmiralTheme.colors.backgroundBasic)
                .fillMaxSize()
                .padding(DIMEN_X4),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Feedback(
                rating = interactFirst,
                onValueChanged = { interactFirst = it }
            )

            Feedback(
                modifier = Modifier
                    .padding(top = DIMEN_X4),
                rating = interactSecond,
                onValueChanged = { rating ->
                    println("onValueChanged $rating")
                    interactSecond = rating
                },
                onRatingChanged = { rating ->
                    println("onRatingChanged $rating")
                },
                isEnabled = false,
            )
        }
    }
}