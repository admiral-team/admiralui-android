package com.admiral.demo.clicker

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu
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

class BottomSheetsAutoTest : ClickerTest{
    private val intent = Intent(
        ApplicationProvider.getApplicationContext(),
        AppActivity::class.java
    ).putExtra(AppActivity.KEY_IS_TEST, true)

    @get:Rule var activityScenarioRule = activityScenarioRule<AppActivity>(intent)

    @Test
    fun openBottomSheetsScreen() {
        onView(withId(R.id.bottomSheetButton)).perform(BetterScrollTo())
        onView(withId(R.id.bottomSheetButton)).perform(click())
        onView(withId(R.id.buttonShowBottomSheet)).perform(click())
        onView(withId(R.id.checkBoxCellUnit1)).perform(click())
        onView(withId(R.id.checkBoxCellUnit1)).perform(click())
        onView(withId(R.id.checkBoxCellUnit2)).perform(click())
        onView(withId(R.id.checkBoxCellUnit2)).perform(click())
        onView(withId(R.id.checkBoxCellUnit3)).perform(click())
        onView(withId(R.id.checkBoxCellUnit3)).perform(click())
        onView(withId(R.id.checkBoxCellUnit4)).perform(click())
        onView(withId(R.id.checkBoxCellUnit4)).perform(click())
        onView(withId(R.id.checkBoxCellUnit5)).perform(click())
        onView(withId(R.id.checkBoxCellUnit5)).perform(click())
        onView(withId(R.id.checkBoxCellUnit6)).perform(click())
        onView(withId(R.id.checkBoxCellUnit6)).perform(click())
        onView(withId(R.id.btn_choose_all_cards)).perform(click())
        onView(withId(R.id.btn_ready)).perform(click())
    }
}