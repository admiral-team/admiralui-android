package com.admiral.demo.clicker

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import com.admiral.demo.R
import com.admiral.demo.app.AppActivity
import com.admiral.demo.ext.BetterScrollTo
import com.admiral.demo.filters.ClickerTest
import org.junit.Rule
import org.junit.Test

class RadioButtonAutoTest : ClickerTest {
    private val intent = Intent(
        ApplicationProvider.getApplicationContext(),
        AppActivity::class.java
    ).putExtra(AppActivity.KEY_IS_TEST, true)

    @get:Rule var activityScenarioRule = activityScenarioRule<AppActivity>(intent)

    @Test
    fun openRadioButtonScreen() {
        onView(withId(R.id.radioButtonsButton)).perform(BetterScrollTo())
        onView(withId(R.id.radioButtonsButton)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.radioButton1)).perform(click())
        onView(withId(R.id.radioButton2)).perform(click())
        onView(withId(R.id.radioButton3)).perform(click())
        onView(withId(R.id.radioButton4)).perform(click())
        onView(withId(R.id.radioButton5)).perform(click())
        onView(withId(R.id.radioButton6)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }
}