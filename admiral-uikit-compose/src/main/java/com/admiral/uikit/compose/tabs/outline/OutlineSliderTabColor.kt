package com.admiral.uikit.compose.tabs.outline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.core.ext.withAlpha

@Immutable
data class OutlineSliderTabColor(
    val textEnable: Color,
    val textDisable: Color,
    val selectStrokeEnabled: Color,
    val selectStrokeDisable: Color,
    val unSelectStrokeEnable: Color,
    val unSelectStrokeDisable: Color,
)

object AdmiralOutlineSliderTabColor {
    @Composable
    fun normal(
        textEnable: Color = textSPrimary,
        textDisable: Color = textSPrimaryWithAlfa,
        selectStrokeEnabled: Color = elementAccent,
        selectStrokeDisable: Color = elementAccentWithAlfa,
        unSelectStrokeEnable: Color = elementAdditional,
        unSelectStrokeDisable: Color = elementAdditionalWithAlfa,
    ): OutlineSliderTabColor = remember(
        textEnable,
        textDisable,
        selectStrokeEnabled,
        selectStrokeDisable,
        unSelectStrokeEnable,
        unSelectStrokeDisable,
    ) {
        OutlineSliderTabColor(
            textEnable = textEnable,
            textDisable = textDisable,
            selectStrokeEnabled = selectStrokeEnabled,
            selectStrokeDisable = selectStrokeDisable,
            unSelectStrokeEnable = unSelectStrokeEnable,
            unSelectStrokeDisable = unSelectStrokeDisable,
        )
    }
}

private val palette = ThemeManagerCompose.theme.value.palette
private val textSPrimary = Color(palette.textPrimary)
private val textSPrimaryWithAlfa = Color(palette.textPrimary.withAlpha())
private val elementAccent = Color(palette.elementAccent)
private val elementAccentWithAlfa = Color(palette.elementAccent.withAlpha())
private val elementAdditional = Color(palette.elementAdditional)
private val elementAdditionalWithAlfa = Color(palette.elementAdditional.withAlpha())