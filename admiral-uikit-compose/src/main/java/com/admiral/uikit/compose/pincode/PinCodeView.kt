package com.admiral.uikit.compose.pincode

import androidx.annotation.IntRange
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.core.components.pincode.PinCodeState

@Suppress("MagicNumber")
@Composable
fun PinCodeView(
    modifier: Modifier = Modifier,
    @IntRange(from = 0, to = 8) activeDotsCount: Int = 0,
    @IntRange(from = 2, to = 8) dotsCount: Int = 4,
    colors: PinCodeColors = pinCodeColors(),
    state: PinCodeState = PinCodeState.DEFAULT,
    isAnimationEnabled: Boolean = true,
) {
    val shakeErrorAnimatable = remember { Animatable(0f) }

    if (state == PinCodeState.ERROR && isAnimationEnabled) {
        LaunchedEffect(key1 = null) {
            triggerErrorAnimation(shakeErrorAnimatable)
        }
    }

    Row(
        modifier = modifier
            .then(
                Modifier.graphicsLayer {
                    translationX = shakeErrorAnimatable.value
                },
            )
            .padding(vertical = VerticalPadding),
        horizontalArrangement = Arrangement.Center
    ) {
        for (index in 1..dotsCount) {
            val color = when (state) {
                PinCodeState.DEFAULT -> {
                    if (index <= activeDotsCount) colors.activeColor
                    else colors.defaultColor
                }

                PinCodeState.SUCCESS -> colors.successColor
                PinCodeState.ERROR -> colors.errorColor
            }

            if (index != 1) Spacer(modifier = Modifier.width(DIMEN_X5))

            Box(
                modifier = Modifier
                    .size(DIMEN_X3)
                    .background(color, CircleShape)
            )
        }
    }
}

private val VerticalPadding = 26.dp
private const val Stiffness = 10_000_000f
private val defaultShakeAnimationSpec: AnimationSpec<Float> = spring(
    dampingRatio = Spring.DampingRatioHighBouncy,
    stiffness = Stiffness,
)

@Suppress("MagicNumber")
private suspend fun triggerErrorAnimation(
    shakeErrorAnimatable: Animatable<Float, AnimationVector1D>?,
) {
    shakeErrorAnimatable ?: return

    repeat(5) { index ->
        val targetValue = (if (index % 2 == 0) -1 else 1) * 6.25f
        shakeErrorAnimatable.animateTo(
            targetValue = targetValue,
            animationSpec = defaultShakeAnimationSpec,
        )
    }

    shakeErrorAnimatable.animateTo(
        targetValue = 0f,
        animationSpec = defaultShakeAnimationSpec,
    )
}

@Preview
@Suppress("MagicNumber")
@Composable
fun PinCodePreview() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            PinCodeView(
                modifier = Modifier
                    .wrapContentWidth(),
                activeDotsCount = 0,
                state = PinCodeState.ERROR
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        PinCodeView(
            activeDotsCount = 2,
            state = PinCodeState.ERROR,
            isAnimationEnabled = false
        )
        Spacer(modifier = Modifier.size(20.dp))
        PinCodeView(
            activeDotsCount = 2,
            state = PinCodeState.SUCCESS
        )
        Spacer(modifier = Modifier.size(20.dp))
        PinCodeView(
            activeDotsCount = 2,
            state = PinCodeState.DEFAULT,
            isAnimationEnabled = true
        )
        Spacer(modifier = Modifier.size(20.dp))
    }
}