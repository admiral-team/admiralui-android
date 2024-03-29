package com.admiral.uikit.compose.tabs.underline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme
import com.admiral.themes.compose.AdmiralTheme.colors
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.themes.compose.withAlpha
import com.admiral.uikit.core.ext.withAlpha

@Immutable
data class UnderlineTabColor(
    val textEnable: Color,
    val textDisable: Color,
    val selectBottomIndicatorEnabled: Color,
    val selectBottomIndicatorDisable: Color,
    val unSelectBottomIndicator: Color,
) {
    @Composable
    fun getTextColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) textEnable else textDisable)

    @Composable
    fun getSelectBottomIndicatorColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) selectBottomIndicatorEnabled else selectBottomIndicatorDisable)
}

object AdmiralUnderlineTabColorColor {
    @Composable
    fun normal(
        textEnable: Color = colors.textPrimary,
        textDisable: Color = colors.textPrimary.withAlpha(),
        selectBottomIndicatorEnabled: Color = colors.elementAccent,
        selectBottomIndicatorDisable: Color = colors.elementAccent.withAlpha(),
        unSelectBottomIndicator: Color = Color.Transparent,
    ) = UnderlineTabColor(
        textEnable = textEnable,
        textDisable = textDisable,
        selectBottomIndicatorEnabled = selectBottomIndicatorEnabled,
        selectBottomIndicatorDisable = selectBottomIndicatorDisable,
        unSelectBottomIndicator = unSelectBottomIndicator,
    )
}