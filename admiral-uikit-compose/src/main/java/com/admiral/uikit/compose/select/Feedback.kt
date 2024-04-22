package com.admiral.uikit.compose.select

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4

@Composable
fun Feedback(
    modifier: Modifier = Modifier,
    rating: Int,
    icon: Painter = painterResource(id = R.drawable.admiral_ic_star_solid),
    iconSize: Dp = IconSize,
    colors: FeedbackColors = feedbackColors(),
    isAnimationEnabled: Boolean = true,
    isEnabled: Boolean = true,
    onValueChange: ((rating: Int) -> Unit)? = null
) {
    val maxRating = 5

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(DIMEN_X3)
    ) {
        for (it in 0 until maxRating) {
            val onClick: (() -> Unit)? = if (isEnabled) {
                { onValueChange?.invoke(it + 1) }
            } else null

            StarIcon(
                icon = icon,
                selected = it < rating,
                onClick = onClick,
                iconTintNormal = colors.getIconTintNormalColor(enabled = isEnabled).value,
                iconTintSelected = colors.getIconTintSelectedColor(enabled = isEnabled).value,
                modifier = Modifier
                    .size(size = iconSize)
            )
        }
    }
}

@Composable
internal fun StarIcon(
    icon: Painter,
    onClick: (() -> Unit)?,
    iconTintNormal: Color,
    iconTintSelected: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    val color = if (selected) iconTintSelected else iconTintNormal

    val selectableModifier = if (onClick != null) {
        Modifier.selectable(
            selected = selected,
            onClick = onClick,
            enabled = enabled,
            interactionSource = interactionSource,
            indication = null
        )
    } else {
        Modifier
    }

    Icon(
        painter = icon,
        contentDescription = null,
        modifier = modifier.then(selectableModifier),
        tint = color
    )
}

private val IconSize = 24.dp

@Suppress("MagicNumber")
@Preview
@Composable
fun FeedbackPreview() {
    val interact = remember { mutableStateOf(1) }
    Column {
        Feedback(rating = interact.value, onValueChange = { interact.value = it })
        Spacer(modifier = Modifier.size(DIMEN_X4))
        Feedback(rating = interact.value, onValueChange = { interact.value = it }, isEnabled = false)
    }
}