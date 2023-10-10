package com.admiral.uikit.compose

import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.admiral.themes.compose.ThemeManagerCompose
import com.admiral.uikit.core.foundation.ColorState

@Composable
fun switcher(
    modifier: Modifier = Modifier,
    checked: Boolean = false,
    isEnabled: Boolean = true,
    thumbColor: ColorState? = null,
    trackColor: ColorState? = null
): Boolean {
    val theme = ThemeManagerCompose.theme.value

    val (value, setValue) = remember { mutableStateOf(checked) }

    Switch(
        modifier = modifier,
        checked = value, onCheckedChange = setValue,
        enabled = isEnabled,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color(thumbColor?.checkedEnabled ?: theme.palette.elementStaticWhite),
            uncheckedThumbColor = Color(thumbColor?.normalEnabled ?: theme.palette.elementStaticWhite),
            disabledCheckedThumbColor = Color(thumbColor?.checkedDisabled ?: theme.palette.elementStaticWhite),
            disabledUncheckedThumbColor = Color(thumbColor?.normalDisabled ?: theme.palette.elementStaticWhite),

            checkedTrackColor = Color(trackColor?.checkedEnabled ?: theme.palette.elementAccent),
            uncheckedTrackColor = Color(trackColor?.normalEnabled ?: theme.palette.elementPrimary),
            disabledCheckedTrackColor = Color(trackColor?.checkedDisabled ?: theme.palette.elementAccent),
            disabledUncheckedTrackColor = Color(trackColor?.normalDisabled ?: theme.palette.elementPrimary),

            checkedTrackAlpha = 1f,
            uncheckedTrackAlpha = 1f,
        )
    )

    return value
}