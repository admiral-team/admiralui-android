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

class TitleCellsTest : ScreenshotTest {

    @get:Rule
    var activityRule: IntentsTestRule<AppActivity> = IntentsTestRule(AppActivity::class.java, true, false)

    @Test
    fun checkTitleCellsPrimaryEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.cellsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnTitleCells)).perform(ViewActions.click())
        onView(withId(R.id.btnPrimary)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTitleCellsPrimaryDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.cellsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnTitleCells)).perform(ViewActions.click())
        onView(withId(R.id.btnPrimary)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTitleCellsSecondaryEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.cellsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnTitleCells)).perform(ViewActions.click())
        onView(withId(R.id.btnSecondary)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTitleCellsSecondaryDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.cellsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnTitleCells)).perform(ViewActions.click())
        onView(withId(R.id.btnSecondary)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }
}