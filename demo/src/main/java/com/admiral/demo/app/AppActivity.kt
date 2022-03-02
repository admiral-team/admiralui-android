package com.admiral.demo.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.admiral.demo.R
import com.admiral.demo.features.home.onboarding.OnboardingStorage
import com.admiral.demo.features.main.MainFragment

class AppActivity : AppCompatActivity(R.layout.act_app) {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AdmiralUIAndroid)
        super.onCreate(savedInstanceState)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.container, MainFragment())
            .commitNow()

        val isTest = intent.extras?.getBoolean(KEY_IS_TEST) ?: false
        if (isTest) OnboardingStorage(this).setOpened(true)
    }

    companion object {
        const val KEY_IS_TEST = "isRunningFromTest"
    }
}
