package com.admiral.uikit.compose.actionbar

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.util.DIMEN_X2
import kotlin.math.max
import kotlin.math.roundToInt

@Suppress("MagicNumber")
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun BaseRevealSwipe(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    colors: ActionBarColor,
    actionSize: Dp,
    hiddenContent: @Composable RowScope.() -> Unit,
    content: @Composable (Shape) -> Unit,
) {
    Box {
        val shapeSize: Size by remember { mutableStateOf(Size(0f, 0f)) }
        val shape = RoundedCornerShape(0.dp)
        val density = LocalDensity.current
        val actionSizePx = with(density) { actionSize.toPx() }

        val state = remember {
            AnchoredDraggableState(
                initialValue = DragAnchors.Center,
                anchors = DraggableAnchors {
                    DragAnchors.Start at -actionSizePx
                    DragAnchors.Center at 0f
                    DragAnchors.End at 0f
                },
                positionalThreshold = { distance: Float -> distance * 0.5f },
                velocityThreshold = { with(density) { 100.dp.toPx() } },
                animationSpec = tween(),
            )
        }

        val cornerRadiusBottomEnd = remember(shapeSize, density) {
            shape.bottomEnd.toPx(
                shapeSize = shapeSize,
                density = density
            )
        }
        val cornerRadiusTopEnd = remember(shapeSize, density) {
            shape.topEnd.toPx(
                shapeSize = shapeSize,
                density = density
            )
        }

        val minDragAmountForStraightCorner =
            max(cornerRadiusTopEnd, cornerRadiusBottomEnd)

        val cornerFactorEnd =
            (-state.offset / minDragAmountForStraightCorner).nonNaNorZero().coerceIn(0f, 1f)

        val animatedCornerRadiusTopEnd: Float = lerp(0f, 16f, cornerFactorEnd)
        val animatedCornerRadiusBottomEnd: Float = lerp(0f, 16f, cornerFactorEnd)

        val animatedShape = shape.copy(
            bottomStart = CornerSize(0f),
            bottomEnd = CornerSize(animatedCornerRadiusBottomEnd),
            topStart = CornerSize(0f),
            topEnd = CornerSize(animatedCornerRadiusTopEnd)
        )

        Surface(
            modifier = Modifier
                .matchParentSize(),
            color = colors.background,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                content = hiddenContent
            )
        }

        Box(
            modifier = modifier
                .then(
                    Modifier
                        .offset {
                            IntOffset(
                                x = state
                                    .requireOffset()
                                    .roundToInt(),
                                y = 0,
                            )
                        }
                        .anchoredDraggable(
                            state = state,
                            orientation = Orientation.Horizontal,
                            enabled = isEnabled,
                            reverseDirection = LocalLayoutDirection.current == LayoutDirection.Rtl
                        )
                )
        ) {
            content(animatedShape)
        }
    }
}

internal val DefaultActionSize = 240.dp
internal val SecondaryActionSize = 270.dp

internal fun Float.nonNaNorZero() = if (isNaN()) 0f else this


@Preview
@Composable
private fun RevealPreview() {
    AdmiralTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = AdmiralTheme.colors.backgroundBasic),
        ) {
            Spacer(modifier = Modifier.size(DIMEN_X2))
            BaseRevealSwipe(
                colors = ActionBarDefault.actionBarColors(),
                actionSize = DefaultActionSize,
                hiddenContent = {
                    Row {
                        Icon(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                        Icon(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                        Icon(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                        Icon(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null
                        )
                    }
                }
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(80.dp),
                    color = AdmiralTheme.colors.backgroundBasic,
                    shape = it,
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = DIMEN_X2),
                            text = "Reveal is here"
                        )
                    }
                }
            }
        }
    }
}