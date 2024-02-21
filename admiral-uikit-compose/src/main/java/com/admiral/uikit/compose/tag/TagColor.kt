package com.admiral.uikit.compose.tag

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.core.ext.withAlpha

@Immutable
data class TagColor(
    val iconEnable: Color,
    val iconDisable: Color,
    val iconPressed: Color,
    val backgroundEnable: Color,
    val backgroundDisable: Color,
    val backgroundPressed: Color,
    val textEnable: Color,
    val textDisable: Color,
    val textPressed: Color,
)

object AdmiralTagColor {
    @Composable
    fun blue(): TagColor = remember {
        TagColor(
            iconEnable = elementAccent,
            iconDisable = elementAccentWithAlpha,
            iconPressed = elementAccentPressed,
            backgroundEnable = backgroundSelected,
            backgroundDisable = backgroundSelected,
            backgroundPressed = backgroundSelectedPressed,
            textEnable = textPrimary,
            textDisable = textPrimaryWithAlpha,
            textPressed = textPrimary,
        )
    }

    @Composable
    fun gray(): TagColor = remember {
        TagColor(
            iconEnable = elementPrimary,
            iconDisable = elementPrimaryWithAlpha,
            iconPressed = elementPrimary,
            backgroundEnable = backgroundAdditionalOne,
            backgroundDisable = backgroundAdditionalOne,
            backgroundPressed = backgroundAdditionalOnePressed,
            textEnable = textPrimary,
            textDisable = textPrimaryWithAlpha,
            textPressed = textPrimary,
        )
    }

    @Composable
    fun green(): TagColor = remember {
        TagColor(
            iconEnable = elementSuccess,
            iconDisable = elementSuccessWithAlpha,
            iconPressed = elementSuccessPressed,
            backgroundEnable = backgroundSuccess,
            backgroundDisable = backgroundSuccess,
            backgroundPressed = backgroundSuccess,
            textEnable = textPrimary,
            textDisable = textPrimaryWithAlpha,
            textPressed = textPrimary,
        )
    }

    @Composable
    fun orange(): TagColor = remember {
        TagColor(
            iconEnable = elementAttention,
            iconDisable = elementAttentionWithAlpha,
            iconPressed = elementAttentionPressed,
            backgroundEnable = backgroundAttention,
            backgroundDisable = backgroundAttention,
            backgroundPressed = backgroundAttention,
            textEnable = textPrimary,
            textDisable = textPrimaryWithAlpha,
            textPressed = textPrimary,
        )
    }

    @Composable
    fun red(): TagColor = remember {
        TagColor(
            iconEnable = elementError,
            iconDisable = elementErrorWithAlpha,
            iconPressed = elementErrorPressed,
            backgroundEnable = backgroundError,
            backgroundDisable = backgroundError,
            backgroundPressed = backgroundError,
            textEnable = textPrimary,
            textDisable = textPrimaryWithAlpha,
            textPressed = textPrimary,
        )
    }
}

private val palette = ThemeManagerCompose.theme.value.palette
private val textPrimary = Color(palette.textPrimary)
private val textPrimaryWithAlpha = Color(palette.textPrimary.withAlpha())
private val elementAccent = Color(palette.elementAccent)
private val elementAccentPressed = Color(palette.elementAccentPressed)
private val elementAccentWithAlpha = Color(palette.elementAccent.withAlpha())
private val backgroundSelected = Color(palette.backgroundSelected)
private val backgroundSelectedPressed = Color(palette.backgroundSelectedPressed)
private val elementPrimary = Color(palette.elementPrimary)
private val elementPrimaryWithAlpha = Color(palette.elementPrimary.withAlpha())
private val backgroundAdditionalOne = Color(palette.backgroundAdditionalOne)
private val backgroundAdditionalOnePressed = Color(palette.backgroundAdditionalOnePressed)
private val elementSuccess = Color(palette.elementSuccess)
private val elementSuccessPressed = Color(palette.elementSuccessPressed)
private val elementSuccessWithAlpha = Color(palette.elementSuccess.withAlpha())
private val backgroundSuccess = Color(palette.backgroundSuccess)
private val elementAttention = Color(palette.elementAttention)
private val elementAttentionPressed = Color(palette.elementAttentionPressed)
private val elementAttentionWithAlpha = Color(palette.elementAttention.withAlpha())
private val backgroundAttention = Color(palette.backgroundAttention)
private val elementError = Color(palette.elementError)
private val elementErrorPressed = Color(palette.elementErrorPressed)
private val elementErrorWithAlpha = Color(palette.elementError.withAlpha())
private val backgroundError = Color(palette.backgroundError)