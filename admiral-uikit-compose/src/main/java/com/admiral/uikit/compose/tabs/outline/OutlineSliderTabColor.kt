package com.admiral.uikit.compose.tabs.outline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme.colors
import com.admiral.themes.compose.withAlpha

@Immutable
data class OutlineSliderTabColor(
    val textEnable: Color,
    val textDisable: Color,
    val selectStrokeEnabled: Color,
    val selectStrokeDisable: Color,
    val unSelectStrokeEnable: Color,
    val unSelectStrokeDisable: Color,
) {
    @Composable
    fun getTextColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) textEnable else textDisable)

    @Composable
    fun getSelectStrokeColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) selectStrokeEnabled else selectStrokeDisable)

    @Composable
    fun getUnSelectStrokeColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) unSelectStrokeEnable else unSelectStrokeDisable)
}

object AdmiralOutlineSliderTabColor {
    @Composable
    fun normal(
        textEnable: Color = colors.textPrimary,
        textDisable: Color = colors.textPrimary.withAlpha(),
        selectStrokeEnabled: Color = colors.elementAccent,
        selectStrokeDisable: Color = colors.elementAccent.withAlpha(),
        unSelectStrokeEnable: Color = colors.elementAdditional,
        unSelectStrokeDisable: Color = colors.elementAdditional.withAlpha(),
    ) = OutlineSliderTabColor(
        textEnable = textEnable,
        textDisable = textDisable,
        selectStrokeEnabled = selectStrokeEnabled,
        selectStrokeDisable = selectStrokeDisable,
        unSelectStrokeEnable = unSelectStrokeEnable,
        unSelectStrokeDisable = unSelectStrokeDisable,
    )
}