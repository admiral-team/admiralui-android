package com.admiral.demo

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.karumi.shot.ScreenshotTest
import com.admiral.demo.app.AppActivity
import com.admiral.demo.app.AppActivity.Companion.KEY_IS_TEST
import com.admiral.demo.ext.BetterScrollTo
import org.junit.Rule
import org.junit.Test

class ButtonsTest : ScreenshotTest {

    @get:Rule
    var activityRule: IntentsTestRule<AppActivity> = IntentsTestRule(AppActivity::class.java, true, false)

    @Test
    fun checkPrimaryButtonsEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.buttonsButton)).perform(ViewActions.click())

        onView(withId(R.id.btnPrimarySmall)).perform(BetterScrollTo())
        compareScreenshot(activity)
    }

    @Test
    fun checkPrimaryButtonsDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.buttonsButton)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        onView(withId(R.id.btnPrimarySmall)).perform(BetterScrollTo())
        compareScreenshot(activity)
    }

    @Test
    fun checkSecondaryButtonsEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.buttonsButton)).perform(ViewActions.click())

        onView(withId(R.id.btnSecondarySmall)).perform(BetterScrollTo())
        compareScreenshot(activity)
    }

    @Test
    fun checkSecondaryButtonsDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.buttonsButton)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        onView(withId(R.id.btnSecondarySmall)).perform(BetterScrollTo())
        compareScreenshot(activity)
    }

    @Test
    fun checkGhostButtonsEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.buttonsButton)).perform(ViewActions.click())

        onView(withId(R.id.btnGhostMedium)).perform(BetterScrollTo())
        compareScreenshot(activity)
    }

    @Test
    fun checkGhostButtonsDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.buttonsButton)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        onView(withId(R.id.btnGhostMedium)).perform(BetterScrollTo())
        compareScreenshot(activity)
    }

    @Test
    fun checkRulesButtonsEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.buttonsButton)).perform(ViewActions.click())

        onView(withId(R.id.rule)).perform(BetterScrollTo())
        compareScreenshot(activity)
    }

    @Test
    fun checkRulesButtonsDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.buttonsButton)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        onView(withId(R.id.rule)).perform(BetterScrollTo())
        compareScreenshot(activity)
    }

    @Test
    fun checkOtherButtonsEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.buttonsButton)).perform(ViewActions.click())

        onView(withId(R.id.btnGoogle)).perform(BetterScrollTo())
        compareScreenshot(activity)
    }

    @Test
    fun checkOtherButtonsDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.buttonsButton)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        onView(withId(R.id.btnGoogle)).perform(BetterScrollTo())
        compareScreenshot(activity)
    }
}