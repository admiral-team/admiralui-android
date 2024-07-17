package com.admiral.uikit.compose.informer

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.util.ComponentsRadius
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5

@Composable
fun SmallInformer(
    modifier: Modifier = Modifier,
    colors: SmallInformerColor = AdmiralSmallInformerColor.info(),
    radius: ComponentsRadius = ComponentsRadius.RADIUS_4,
    @FloatRange(from = 0.1, to = 0.9)
    pointerBias: Float = 0.9f,
    pointerGravity: PointerGravity = PointerGravity.TOP,
    infoText: String? = null,
    isEnabled: Boolean = true,
    onClick: () -> Unit = {},
) {

    ConstraintLayout(
        modifier = modifier
            .clickable(
                onClick = onClick,
                enabled = isEnabled,
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                }
            ),
    ) {

        val (
            imageTopId,
            textId,
            imageBottomId,
        ) = createRefs()

        if (pointerGravity == PointerGravity.TOP) {
            Icon(
                modifier = Modifier
                    .size(width = DIMEN_X4, height = DIMEN_X2)
                    .constrainAs(imageTopId) {
                        linkTo(parent.start, parent.end, bias = pointerBias)
                        top.linkTo(parent.top)
                    },
                painter = painterResource(id = R.drawable.admiral_img_informer_arrow),
                contentDescription = null,
                tint = colors.background
            )
        }

        infoText?.let {
            Text(
                modifier = Modifier
                    .constrainAs(textId) {
                        top.linkTo(imageTopId.bottom)
                        bottom.linkTo(imageBottomId.top)
                        linkTo(parent.start, parent.end)
                    }
                    .background(color = colors.background, shape = RoundedCornerShape(radius.value))
                    .padding(horizontal = DIMEN_X3, vertical = DIMEN_X2),
                text = infoText,
                style = AdmiralTheme.typography.body2,
                color = colors.getTextColor(isEnabled = isEnabled).value
            )
        }

        if (pointerGravity == PointerGravity.BOTTOM) {
            Icon(
                modifier = Modifier
                    .size(width = DIMEN_X4, height = DIMEN_X2)
                    .constrainAs(imageBottomId) {
                        linkTo(parent.start, parent.end, bias = pointerBias)
                        bottom.linkTo(parent.bottom)
                    }
                    .rotate(ImageRotate),
                painter = painterResource(id = R.drawable.admiral_img_informer_arrow),
                contentDescription = null,
                tint = colors.background
            )
        }
    }
}

private const val ImageRotate = 180f

@Preview
@Composable
private fun SmallInformerPreview() {
    AdmiralTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = DIMEN_X4, vertical = DIMEN_X5),
                verticalArrangement = Arrangement.spacedBy(DIMEN_X2)
            ) {
                SmallInformer(
                    infoText = "Text Informer",
                    pointerBias = 0.9f,
                )
                SmallInformer(
                    infoText = "Text Informer",
                    pointerGravity = PointerGravity.BOTTOM,
                    colors = AdmiralSmallInformerColor.attention(),
                    pointerBias = 0.9f,
                )
            }
        }
    }
}