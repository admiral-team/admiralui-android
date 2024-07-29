package com.admiral.uikit.compose.control

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.R
import com.admiral.uikit.compose.control.circle.AdmiralCirclePageControlColor
import com.admiral.uikit.compose.control.circle.CirclePageControlColor
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X12
import com.admiral.uikit.compose.util.DIMEN_X18
import com.admiral.uikit.compose.util.DIMEN_X7

@Composable
fun CirclePageControl(
    modifier: Modifier = Modifier,
    pageCount: Int = 2,
    currentPage: Int = 0,
    onButtonClicked: ((currentPage: Int) -> Unit)? = null,
    color: CirclePageControlColor = AdmiralCirclePageControlColor.default(),
    iconPainter: Painter = painterResource(id = R.drawable.admiral_ic_arrow_right_outline),
    iconContentDescription: String = ""
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            modifier = Modifier
                .size(DIMEN_X12)
                .background(
                    color = color.iconBackground,
                    shape = RoundedCornerShape(IconBackgroundCornerValue)
                ),
            onClick = {
                onButtonClicked?.invoke(currentPage)
            }) {
            Icon(
                painter = iconPainter,
                tint = color.iconColor,
                modifier = Modifier.size(DIMEN_X7),
                contentDescription = iconContentDescription
            )
        }

        CircularProgressIndicator(
            modifier = modifier
                .size(DIMEN_X18),
            color = color.progressColor,
            trackColor = Color.Transparent,
            strokeWidth = DIMEN_X1,
            progress = {
                currentPage.toFloat() / pageCount
            }
        )
    }
}

private const val IconBackgroundCornerValue = 100

@Preview
@Composable
private fun PagerPreview() {
    AdmiralTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AdmiralTheme.colors.backgroundBasic
        ) {
            CirclePageControl(
                pageCount = 8
            )
        }
    }
}