package com.admiral.themes.compose

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager

object ThemeManagerCompose {

    private val mutableState: MutableState<Theme> = mutableStateOf(ThemeManager.theme)

    val theme: State<Theme>
        get() = mutableState

    var typography: AdmiralTypography = defaultAdmiralTypography()

    fun setCurrentTheme(newTheme: Theme) {
        ThemeManager.theme = newTheme
        mutableState.value = newTheme
    }
}