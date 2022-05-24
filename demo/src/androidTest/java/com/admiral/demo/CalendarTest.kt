package com.admiral.demo

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import com.karumi.shot.ScreenshotTest
import com.admiral.demo.app.AppActivity
import com.admiral.demo.app.AppActivity.Companion.KEY_IS_TEST
import com.admiral.demo.ext.BetterScrollTo
import com.admiral.demo.ext.waitUntilShown
import com.admiral.uikit.components.calendar.horisontal.HorizontalCalendar
import org.junit.Rule
import org.junit.Test

class CalendarTest : ScreenshotTest {

    @get:Rule
    var activityRule: IntentsTestRule<AppActivity> = IntentsTestRule(AppActivity::class.java, true, false)

    @Test
    fun checkCalendarVertical() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.calendarButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnVertical)).perform(BetterScrollTo(), ViewActions.click())

        getInstrumentation().waitForIdleSync()
        compareScreenshot(activity)
    }

    @Test
    fun checkCalendarHorizontal() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.calendarButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnHorizontal)).perform(BetterScrollTo(), ViewActions.click())

        activity.findViewById<HorizontalCalendar>(R.id.horizontalCalendar).isDebounceEnabled = false
        onView(ViewMatchers.isRoot()).perform(waitUntilShown(R.id.calendarTitle, 5000))

        getInstrumentation().waitForIdleSync()
        compareScreenshot(activity)
    }
}