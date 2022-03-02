package com.admiral.demo.features.home.onboarding

import android.content.Context
import android.content.Context.MODE_PRIVATE

internal class OnboardingStorage(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS, MODE_PRIVATE)

    fun wasOpened(): Boolean {
        return prefs.getBoolean(KEY_ONBOARDING, false)
    }

    fun setOpened(isOpened: Boolean) {
        prefs.edit().putBoolean(KEY_ONBOARDING, isOpened).apply()
    }

    private companion object {
        private const val KEY_ONBOARDING = "KEY_ONBOARDING"
        private const val PREFS = "admiral_ui_theme_storage"
    }
}