package com.admiral.demo.cells

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.karumi.shot.ScreenshotTest
import com.admiral.demo.R
import com.admiral.demo.app.AppActivity
import com.admiral.demo.app.AppActivity.Companion.KEY_IS_TEST
import com.admiral.demo.ext.BetterScrollTo
import org.junit.Rule
import org.junit.Test

class BaseCellsLabelTest : ScreenshotTest {

    @get:Rule
    var activityRule: IntentsTestRule<AppActivity> = IntentsTestRule(AppActivity::class.java, true, false)

    @Test
    fun checkBaseCellLabelLabelEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.cellsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnBaseCellsLable)).perform(ViewActions.click())
        onView(withId(R.id.btnLabel)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkBaseCellLabelLabelDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.cellsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnBaseCellsLable)).perform(ViewActions.click())
        onView(withId(R.id.btnLabel)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkBaseCellLabelLeadingEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.cellsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnBaseCellsLable)).perform(ViewActions.click())
        onView(withId(R.id.btnLeading)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkBaseCellLabelLeadingDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.cellsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnBaseCellsLable)).perform(ViewActions.click())
        onView(withId(R.id.btnLeading)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkBaseCellLabelTrailingEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.cellsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnBaseCellsLable)).perform(ViewActions.click())
        onView(withId(R.id.btnTrailing)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkBaseCellLabelTrailingDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.cellsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnBaseCellsLable)).perform(ViewActions.click())
        onView(withId(R.id.btnTrailing)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }
}