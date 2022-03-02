package com.admiral.demo.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.admiral.demo.BuildConfig
import com.admiral.demo.features.home.theme.utils.ThemeStorageDAO
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        ThemeStorageDAO.init(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}