package com.admiral.demo.clicker

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import com.admiral.demo.R
import com.admiral.demo.app.AppActivity
import com.admiral.demo.ext.BetterScrollTo
import com.admiral.demo.filters.ClickerTest
import org.junit.Rule
import org.junit.Test

class HorizontalCalendarTest : ClickerTest {
    private val intent = Intent(
        ApplicationProvider.getApplicationContext(),
        AppActivity::class.java
    ).putExtra(AppActivity.KEY_IS_TEST, true)

    @get:Rule
    var activityScenarioRule = activityScenarioRule<AppActivity>(intent)

    @Test
    fun openHorizontalCalendarScreen() {
        onView(withId(R.id.calendarButton)).perform(BetterScrollTo())
        onView(withId(R.id.calendarButton)).perform(click())
        onView(withId(R.id.btnHorizontal)).perform(click())
        onView(withId(R.id.previous)).perform(click())
        onView(withId(R.id.next)).perform(click())
        onView(withId(R.id.calendarTitle)).perform(click())
        onView(withText("Февраль")).perform(click())
        onView(withText("2021")).perform(click())
        onView(withId(R.id.select)).perform(click())
        onView(withText("8")).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())

    }
}