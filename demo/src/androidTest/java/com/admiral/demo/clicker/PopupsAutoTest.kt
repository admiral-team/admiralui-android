package com.admiral.demo.clicker

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import com.admiral.demo.R
import com.admiral.demo.app.AppActivity
import com.admiral.demo.ext.BetterScrollTo
import com.admiral.demo.filters.ClickerTest
import org.junit.Rule
import org.junit.Test

class PopupsAutoTest : ClickerTest {
    private val intent = Intent(
        ApplicationProvider.getApplicationContext(),
        AppActivity::class.java
    ).putExtra(AppActivity.KEY_IS_TEST, true)

    @get:Rule
    var activityScenarioRule = activityScenarioRule<AppActivity>(intent)

    @Test
    fun openAlertScreen() {
        onView(withId(R.id.alertsButton)).perform(BetterScrollTo())
        onView(withId(R.id.btnAlerts)).perform(click())
        onView(withId(R.id.buttonShowBottomSheet)).perform(click())
    }

    @Test
    fun checkOnboarding() {
        onView(withId(R.id.mainMenuHome)).perform(click())
        onView(withId(R.id.alertsButton)).perform(BetterScrollTo(), click())
        onView(withId(R.id.btnOnboarding)).perform(BetterScrollTo(), click())
    }

    @Test
    fun checkZeroscreen() {
        onView(withId(R.id.mainMenuHome)).perform(click())
        onView(withId(R.id.alertsButton)).perform(BetterScrollTo(), click())
        onView(withId(R.id.btnZeroscreen)).perform(BetterScrollTo(), click())
    }

    @Test
    fun checkErrorview() {
        onView(withId(R.id.mainMenuHome)).perform(click())
        onView(withId(R.id.alertsButton)).perform(BetterScrollTo(), click())
        onView(withId(R.id.btnErrorview)).perform(BetterScrollTo(), click())
    }
}