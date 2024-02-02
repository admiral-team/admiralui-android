package com.admiral.uikit.compose.tabs.underline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.core.ext.withAlpha

@Immutable
data class UnderlineTabColor(
    val textEnable: Color,
    val textDisable: Color,
    val selectBottomIndicatorEnabled: Color,
    val selectBottomIndicatorDisable: Color,
    val unSelectBottomIndicator: Color,
)

object AdmiralUnderlineTabColorColor {
    @Composable
    fun normal(
        textEnable: Color = textSPrimary,
        textDisable: Color = textSPrimaryWithAlfa,
        selectBottomIndicatorEnabled: Color = elementAccent,
        selectBottomIndicatorDisable: Color = elementAccentWithAlfa,
        unSelectBottomIndicator: Color = transparent,
    ): UnderlineTabColor = remember(
        textEnable,
        textDisable,
        selectBottomIndicatorEnabled,
        selectBottomIndicatorDisable,
        unSelectBottomIndicator,
    ) {
        UnderlineTabColor(
            textEnable = textEnable,
            textDisable = textDisable,
            selectBottomIndicatorEnabled = selectBottomIndicatorEnabled,
            selectBottomIndicatorDisable = selectBottomIndicatorDisable,
            unSelectBottomIndicator = unSelectBottomIndicator,
        )
    }
}

private val palette = ThemeManagerCompose.theme.value.palette
private val textSPrimary = Color(palette.textPrimary)
private val textSPrimaryWithAlfa = Color(palette.textPrimary.withAlpha())
private val elementAccent = Color(palette.elementAccent)
private val elementAccentWithAlfa = Color(palette.elementAccent.withAlpha())
private val transparent = Color(palette.transparent)