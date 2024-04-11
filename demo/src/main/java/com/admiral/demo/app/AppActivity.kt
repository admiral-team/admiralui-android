package com.admiral.demo.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.admiral.demo.features.home.onboarding.OnboardingStorage
import com.admiral.demo.features.main.MainFragment
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.demo.R
import com.admiral.uikit.core.ext.isColorDark

class AppActivity : AppCompatActivity(R.layout.act_app), ThemeObserver {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AdmiralUIAndroid)
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, MainFragment())
                .commitNow()
        }

        val isTest = intent.extras?.getBoolean(KEY_IS_TEST) ?: false
        if (isTest) OnboardingStorage(this).setOpened(true)

        window.statusBarColor = ThemeManager.theme.palette.backgroundBasic
        changeNavigationBarColor(ThemeManager.theme)
        ThemeManager.subscribe(this)
    }

    override fun onThemeChanged(theme: Theme) {
        window.statusBarColor = ThemeManager.theme.palette.backgroundBasic
        changeNavigationBarColor(theme)
    }

    @Suppress("MagicNumber")
    private fun changeNavigationBarColor(theme: Theme) {
        if (theme.palette.backgroundAccentDark.isColorDark(0.6f).not()) {
            window.navigationBarColor = ThemeManager.theme.palette.backgroundAccentDark
        } else {
            window.navigationBarColor = ThemeManager.theme.palette.transparent
        }
    }

    companion object {
        const val KEY_IS_TEST = "isRunningFromTest"
    }
}
