package com.admiral.demo

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import com.karumi.shot.ScreenshotTest
import com.admiral.demo.app.AppActivity
import com.admiral.demo.app.AppActivity.Companion.KEY_IS_TEST
import com.admiral.demo.ext.BetterScrollTo
import org.junit.Rule
import org.junit.Test

class TimePickerTest : ScreenshotTest {

    @get:Rule
    var activityRule: IntentsTestRule<AppActivity> = IntentsTestRule(AppActivity::class.java, true, false)

    @Test
    fun checkTimePickerDefault() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.timePickerButton)).perform(BetterScrollTo(), ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.defaultTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTimePickerDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.timePickerButton)).perform(BetterScrollTo(), ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }
}