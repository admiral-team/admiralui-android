package com.admiral.uikit.compose.tag

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X4
import com.admiral.uikit.compose.util.DIMEN_X5
import com.admiral.uikit.compose.util.DIMEN_X6
import com.admiral.uikit.compose.util.DIMEN_X7
import com.admiral.uikit.compose.util.DIMEN_X9

@Immutable
data class TagSize(
    val tagHeight: Dp,
    val iconSize: Dp,
    val textStyle: TextStyle,
    val paddingVertical: Dp,
    val paddingHorizontal: Dp,
)

object AdmiralTagSize {
    @Composable
    fun large(): TagSize =
        TagSize(
            tagHeight = DIMEN_X9,
            iconSize = DIMEN_X7,
            paddingVertical = LARGE_VERTICAL_PADDING.dp,
            paddingHorizontal = DIMEN_X4,
            textStyle = AdmiralTheme.typography.body1,
        )

    @Composable
    fun medium(): TagSize =
        TagSize(
            tagHeight = DIMEN_X7,
            iconSize = DIMEN_X6,
            paddingVertical = VERTICAL_PADDING.dp,
            paddingHorizontal = DIMEN_X3,
            textStyle = AdmiralTheme.typography.body1,
        )

    @Composable
    fun small(): TagSize =
        TagSize(
            tagHeight = DIMEN_X5,
            iconSize = DIMEN_X5,
            paddingVertical = VERTICAL_PADDING.dp,
            paddingHorizontal = DIMEN_X2,
            textStyle = AdmiralTheme.typography.subhead2,
        )
}

private const val LARGE_VERTICAL_PADDING = 6
private const val VERTICAL_PADDING = 2