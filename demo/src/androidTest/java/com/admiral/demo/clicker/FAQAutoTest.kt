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
import com.admiral.demo.R
import com.admiral.demo.app.AppActivity
import com.admiral.demo.ext.BetterScrollTo
import com.admiral.demo.filters.ClickerTest
import org.junit.Rule
import org.junit.Test

class FAQAutoTest : ClickerTest {
    private val intent = Intent(
        ApplicationProvider.getApplicationContext(),
        AppActivity::class.java
    ).putExtra(AppActivity.KEY_IS_TEST, true)

    @get:Rule var activityScenarioRule = activityScenarioRule<AppActivity>(intent)

    @Test
    fun openFAQScreen() {
        onView(withId(R.id.mainMenuInfo)).perform(click())
        onView(withId(R.id.faqButton)).perform(click())
        onView(withId(R.id.expandable)).perform(click())
        onView(withId(R.id.expandable2)).perform(click())
        onView(withId(R.id.expandable3)).perform(click())
        onView(withId(R.id.expandable4)).perform(BetterScrollTo())
        onView(withId(R.id.expandable4)).perform(click())
        onView(withId(R.id.expandable5)).perform(BetterScrollTo())
        onView(withId(R.id.expandable5)).perform(click())
        onView(withId(R.id.expandable6)).perform(BetterScrollTo())
        onView(withId(R.id.expandable6)).perform(click())
        onView(withId(R.id.expandable7)).perform(BetterScrollTo())
        onView(withId(R.id.expandable7)).perform(click())
        onView(withId(R.id.expandable8)).perform(BetterScrollTo())
        onView(withId(R.id.expandable8)).perform(click())
        onView(withId(R.id.expandable9)).perform(BetterScrollTo())
        onView(withId(R.id.expandable9)).perform(click())
        onView(withId(R.id.expandable10)).perform(BetterScrollTo())
        onView(withId(R.id.expandable10)).perform(click())
        onView(withId(R.id.expandable11)).perform(BetterScrollTo())
        onView(withId(R.id.expandable11)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }
}