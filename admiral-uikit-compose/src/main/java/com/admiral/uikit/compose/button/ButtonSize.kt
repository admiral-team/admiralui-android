package com.admiral.uikit.compose.button

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp
import com.admiral.uikit.compose.util.DIMEN_X1
import com.admiral.uikit.compose.util.DIMEN_X2
import com.admiral.uikit.compose.util.DIMEN_X3
import com.admiral.uikit.compose.util.DIMEN_X6
import com.admiral.uikit.compose.util.DIMEN_X7

@Immutable
data class ButtonSize(
    val iconHeight: Dp,
    val padding: Dp,
    val defaultWidth: Dp
)

object AdmiralButtonSize {
    @Composable
    fun big() = remember {
        ButtonSize(
            iconHeight = DIMEN_X7,
            padding = DIMEN_X3,
            defaultWidth = DIMEN_X2 * 41
        )
    }

    @Composable
    fun medium() = remember {
        ButtonSize(
            iconHeight = DIMEN_X7,
            padding = DIMEN_X3,
            defaultWidth = DIMEN_X7 * 10
        )
    }

    @Composable
    fun small() = remember {
        ButtonSize(
            iconHeight = DIMEN_X6,
            padding = DIMEN_X3,
            defaultWidth = DIMEN_X1 * 0
        )
    }
}