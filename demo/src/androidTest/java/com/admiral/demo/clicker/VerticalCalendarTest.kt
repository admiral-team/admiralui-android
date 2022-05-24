package com.admiral.demo.clicker

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.admiral.demo.R
import com.admiral.demo.app.AppActivity
import com.admiral.demo.ext.BetterScrollTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class VerticalCalendarTest {
    private val intent = Intent(
        ApplicationProvider.getApplicationContext(),
        AppActivity::class.java
    ).putExtra(AppActivity.KEY_IS_TEST, true)

    @get:Rule var activityScenarioRule = activityScenarioRule<AppActivity>(intent)

    @Test
    fun openVerticalCalendarScreen() {
        onView(withId(R.id.calendarButton)).perform(BetterScrollTo())
        onView(withId(R.id.calendarButton)).perform(click())
        onView(withId(R.id.btnVertical)).perform(click())
        onView(withId(R.id.verticalCalendar)).perform(swipeUp())
        onView(withId(R.id.verticalCalendar)).perform(swipeDown())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }
}