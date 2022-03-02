package com.admiral.demo.features.home.theme.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.admiral.themes.THEME_DARK
import com.admiral.themes.THEME_LIGHT
import com.admiral.themes.Theme
import com.admiral.themes.darkSMETheme
import com.admiral.themes.darkTheme
import com.admiral.themes.lightSMETheme
import com.admiral.themes.lightTheme
import com.admiral.themes.toJson
import com.admiral.themes.toTheme
import org.json.JSONObject

internal class ThemeStorage(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS, MODE_PRIVATE)

    fun isThemeAuto(): Boolean {
        return prefs.getBoolean(KEY_THEME_AUTO, true)
    }

    fun setIsThemeAuto(isThemeAuto: Boolean) {
        prefs.edit().putBoolean(KEY_THEME_AUTO, isThemeAuto).apply()
    }

    fun currentTheme(): String {
        val string = prefs.getString(KEY_CURRENT_THEME, THEME_LIGHT)
        return string ?: THEME_LIGHT
    }

    fun setCurrentTheme(themeName: String) {
        prefs.edit().putString(KEY_CURRENT_THEME, themeName).apply()
    }

    fun getAll(): List<Theme> {
        val themes = prefs
            .all
            .filter { it.key != KEY_CURRENT_THEME }
            .filter { it.value is String }
            .map { it.value as String }
            .map { JSONObject(it).toTheme() }

        return if (themes.isEmpty()) {
            val light = lightTheme()
            val dark = darkTheme()
            val darkSME = lightSMETheme()
            val lightSME = darkSMETheme()

            saveTheme(light)
            saveTheme(dark)
            saveTheme(darkSME)
            saveTheme(lightSME)

            listOf(light, dark, darkSME, lightSME)
        } else {
            themes
        }
    }

    fun saveTheme(theme: Theme) {
        prefs
            .edit()
            .putString(theme.name, theme.toJson().toString())
            .apply()
    }

    fun removeTheme(theme: Theme) {
        if (theme.name == THEME_LIGHT || theme.name == THEME_DARK) {
            throw IllegalArgumentException("It is illegal to remove default theme, please reconsider your intentions")
        }

        prefs
            .edit()
            .remove(theme.name)
            .apply()
    }

    private companion object {
        private const val KEY_THEME_AUTO = "KEY_THEME_AUTO"
        private const val KEY_CURRENT_THEME = "KEY_CURRENT_THEME"
        private const val PREFS = "admiral_ui_theme_storage"
    }
}