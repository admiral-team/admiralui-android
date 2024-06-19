package com.admiral.uikit.compose.actionbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.AdmiralTheme.colors
import com.admiral.themes.compose.withAlpha

data class ActionBarColor(
    val background: Color,
    val moreIconTintEnable: Color,
    val moreIconTintDisable: Color,
    val moreIconTintPressed: Color,
    val upIconTintEnable: Color,
    val upIconTintDisable: Color,
    val upIconTintPressed: Color,
    val downIconTintEnable: Color,
    val downIconTintDisable: Color,
    val downIconTintPressed: Color,
    val editIconTintEnable: Color,
    val editIconTintDisable: Color,
    val editIconTintPressed: Color,
    val deleteIconTintEnable: Color,
    val deleteIconTintDisable: Color,
    val deleteIconTintPressed: Color,
) {
    @Composable
    fun getMoreIconTintColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) moreIconTintEnable else moreIconTintDisable)

    @Composable
    fun getUpIconTintColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) upIconTintEnable else upIconTintDisable)

    @Composable
    fun getDownIconTintColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) downIconTintEnable else downIconTintDisable)

    @Composable
    fun getEditIconTintColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) editIconTintEnable else editIconTintDisable)

    @Composable
    fun getDeleteIconTintColor(isEnabled: Boolean) =
        rememberUpdatedState(if (isEnabled) deleteIconTintEnable else deleteIconTintDisable)

}

object ActionBarDefault {
    @Composable
    fun actionBarColors(
        background: Color = colors.backgroundAdditionalOne,
        moreIconTintEnable: Color = colors.elementAccent,
        moreIconTintDisable: Color = colors.elementAccent.withAlpha(),
        moreIconTintPressed: Color = colors.elementAccentPressed,
        upIconTintEnable: Color = colors.elementPrimary,
        upIconTintDisable: Color = colors.elementPrimary.withAlpha(),
        upIconTintPressed: Color = colors.elementPrimary,
        downIconTintEnable: Color = colors.elementPrimary,
        downIconTintDisable: Color = colors.elementPrimary.withAlpha(),
        downIconTintPressed: Color = colors.elementPrimary,
        editIconTintEnable: Color = colors.elementAccent,
        editIconTintDisable: Color = colors.elementAccent.withAlpha(),
        editIconTintPressed: Color = colors.elementAccentPressed,
        deleteIconTintEnable: Color = colors.elementError,
        deleteIconTintDisable: Color = colors.elementError.withAlpha(),
        deleteIconTintPressed: Color = colors.elementErrorPressed,
    ) = ActionBarColor(
        background = background,
        moreIconTintEnable = moreIconTintEnable,
        moreIconTintDisable = moreIconTintDisable,
        moreIconTintPressed = moreIconTintPressed,
        upIconTintEnable = upIconTintEnable,
        upIconTintDisable = upIconTintDisable,
        upIconTintPressed = upIconTintPressed,
        downIconTintEnable = downIconTintEnable,
        downIconTintDisable = downIconTintDisable,
        downIconTintPressed = downIconTintPressed,
        editIconTintEnable = editIconTintEnable,
        editIconTintDisable = editIconTintDisable,
        editIconTintPressed = editIconTintPressed,
        deleteIconTintEnable = deleteIconTintEnable,
        deleteIconTintDisable = deleteIconTintDisable,
        deleteIconTintPressed = deleteIconTintPressed,
    )
}
