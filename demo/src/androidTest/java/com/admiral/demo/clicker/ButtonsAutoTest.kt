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

class ButtonsAutoTest :ClickerTest {
    private val intent = Intent(
        ApplicationProvider.getApplicationContext(),
        AppActivity::class.java
    ).putExtra(AppActivity.KEY_IS_TEST, true)

    @get:Rule var activityScenarioRule = activityScenarioRule<AppActivity>(intent)

    @Test
    fun openPrimaryButtonsScreen() {
        onView(withId(R.id.buttonsButton)).perform(click())
        onView(withId(R.id.btnPrimaryButtons)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.btnSmall)).perform(BetterScrollTo())
        onView(withId(R.id.btnSmall)).perform(click())
        onView(withId(R.id.btnAddition)).perform(BetterScrollTo())
        onView(withId(R.id.btnAddition)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    @Test
    fun openSecondaryButtonsScreen() {
        onView(withId(R.id.buttonsButton)).perform(click())
        onView(withId(R.id.btnSecondaryButtons)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.btnSmall)).perform(BetterScrollTo())
        onView(withId(R.id.btnSmall)).perform(click())
        onView(withId(R.id.btnAddition)).perform(BetterScrollTo())
        onView(withId(R.id.btnAddition)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    @Test
    fun openGhostButtonsScreen() {
        onView(withId(R.id.buttonsButton)).perform(click())
        onView(withId(R.id.btnGhostButtons)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.btnSmall)).perform(BetterScrollTo())
        onView(withId(R.id.btnSmall)).perform(click())
        onView(withId(R.id.btnAddition)).perform(BetterScrollTo())
        onView(withId(R.id.btnAddition)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    @Test
    fun openRulesScreen() {
        onView(withId(R.id.buttonsButton)).perform(click())
        onView(withId(R.id.btnRules)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.btnRule)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    @Test
    fun openOtherButtonsScreen() {
        onView(withId(R.id.buttonsButton)).perform(click())
        onView(withId(R.id.btnOtherButtons)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.btnGoogle)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }
}