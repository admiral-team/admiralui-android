package com.admiral.demo.features.home.theme.utils

import android.content.Context
import com.admiral.themes.THEME_DARK
import com.admiral.themes.THEME_LIGHT
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.lightTheme

object ThemeStorageDAO {

    private lateinit var storage: ThemeStorage
    private lateinit var themes: List<Theme>

    var theme: Theme = lightTheme()
        set(value) {
            field = value
            storage.setCurrentTheme(value.name)
            ThemeManager.theme = value
        }

    fun init(context: Context) {
        storage = ThemeStorage(context)
        updateThemes()
    }

    fun getThemes() = themes

    fun saveTheme(theme: Theme) {
        storage.saveTheme(theme)
        updateThemes()
    }

    fun removeTheme(theme: Theme) {
        storage.removeTheme(theme)
        updateThemes()
    }

    fun isThemeAuto(): Boolean {
        return storage.isThemeAuto()
    }

    fun setIsThemeAuto(isAuto: Boolean) {
        return storage.setIsThemeAuto(isAuto)
    }

    private fun updateThemes() {
        themes = storage.getAll().sortedWith(Comparator { t1, t2 ->
            return@Comparator if (t1.name == THEME_LIGHT || t1.name == THEME_DARK) {
                -1
            } else if (t2.name == THEME_LIGHT || t2.name == THEME_DARK) {
                1
            } else {
                t1.name.compareTo(t2.name)
            }
        })
        theme = themes.find { it.name == storage.currentTheme() } ?: lightTheme()
    }
}