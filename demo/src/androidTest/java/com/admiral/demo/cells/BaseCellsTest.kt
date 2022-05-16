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

class BaseCellsTest : ScreenshotTest {

    @get:Rule
    var activityRule: IntentsTestRule<AppActivity> = IntentsTestRule(AppActivity::class.java, true, false)

    @Test
    fun checkBaseCellsLeadingEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.cellsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnBaseCells)).perform(ViewActions.click())
        onView(withId(R.id.btnLeading)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkBaseCellsLeadingDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.cellsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnBaseCells)).perform(ViewActions.click())
        onView(withId(R.id.btnLeading)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkBaseCellsCenterEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.cellsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnBaseCells)).perform(ViewActions.click())
        onView(withId(R.id.btnCenter)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkBaseCellsCenterDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.cellsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnBaseCells)).perform(ViewActions.click())
        onView(withId(R.id.btnCenter)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkBaseCellsTrailingEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.cellsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnBaseCells)).perform(ViewActions.click())
        onView(withId(R.id.btnTrailing)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkBaseCellsTrailingDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.cellsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnBaseCells)).perform(ViewActions.click())
        onView(withId(R.id.btnTrailing)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }
}