package com.admiral.themes

object ThemeManager {

    private val observers = mutableSetOf<ThemeObserver>()

    var theme: Theme = lightTheme()
        set(value) {
            field = value
            observers.forEach { it.onThemeChanged(value) }
        }

    fun subscribe(themeObserver: ThemeObserver) {
        observers.add(themeObserver)
        themeObserver.onThemeChanged(theme)
    }

    fun unsubscribe(themeObserver: ThemeObserver) {
        observers.remove(themeObserver)
    }
}

interface ThemeObserver {
    fun onThemeChanged(theme: Theme)
}