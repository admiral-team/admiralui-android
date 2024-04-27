package com.admiral.uikit.compose.checkbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme.colors
import com.admiral.themes.compose.withAlpha

@Immutable
data class AdmiralCheckBoxColor(
    val checkedEnable: Color,
    val checkedDisable: Color,
    val normalEnable: Color,
    val normalDisable: Color,
    val textCheckedEnable: Color,
    val textCheckedDisable: Color,
    val textNormalEnable: Color,
    val textNormalDisable: Color,
) {
    @Composable
    fun getCheckedColor(isEnabled: Boolean, isError: Boolean) =
        rememberUpdatedState(
            when {
                isError -> if (isEnabled) colors.elementError else colors.elementError.withAlpha()
                else -> if (isEnabled) checkedEnable else checkedDisable
            }
        )

    @Composable
    fun getNormalColor(isEnabled: Boolean, isError: Boolean) =
        rememberUpdatedState(
            when {
                isError -> if (isEnabled) colors.elementError else colors.elementError.withAlpha()
                else -> if (isEnabled) normalEnable else normalDisable
            }
        )

    @Composable
    fun getTextCheckedColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) textCheckedEnable else textCheckedDisable)

    @Composable
    fun getTextNormalColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) textNormalEnable else textNormalDisable)
}

@Composable
fun checkBoxColor(
    checkedEnable: Color = colors.elementAccent,
    checkedDisable: Color = colors.elementAccent.withAlpha(),
    normalEnable: Color = colors.elementAccent,
    normalDisable: Color = colors.elementAccent.withAlpha(),
    textCheckedEnable: Color = colors.textPrimary,
    textCheckedDisable: Color = colors.textPrimary.withAlpha(),
    textNormalEnable: Color = colors.textPrimary,
    textNormalDisable: Color = colors.textPrimary.withAlpha(),
) = AdmiralCheckBoxColor(
    checkedEnable = checkedEnable,
    checkedDisable = checkedDisable,
    normalEnable = normalEnable,
    normalDisable = normalDisable,
    textCheckedEnable = textCheckedEnable,
    textCheckedDisable = textCheckedDisable,
    textNormalEnable = textNormalEnable,
    textNormalDisable = textNormalDisable,
)