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
import com.admiral.demo.filters.ClickerTest
import org.junit.Rule
import org.junit.Test

class CellsAutoTest : ClickerTest {
    private val intent = Intent(
        ApplicationProvider.getApplicationContext(),
        AppActivity::class.java
    ).putExtra(AppActivity.KEY_IS_TEST, true)

    @get:Rule
    var activityScenarioRule = activityScenarioRule<AppActivity>(intent)

    @Test
    fun openCellActionBarScreen() {
        onView(withId(R.id.cellsButton)).perform(click())
        onView(withId(R.id.btnActionBar)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.titleCell)).perform(swipeLeft())
        onView(withId(R.id.iconTrailing)).perform(swipeRight())
        onView(withId(R.id.titleCell2)).perform(swipeLeft())
        onView(withId(R.id.iconTrailing2)).perform(swipeRight())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    @Test
    fun openCellsLeadingElementsScreen() {
        onView(withId(R.id.cellsButton)).perform(click())
        onView(withId(R.id.btnBaseCells)).perform(click())
        onView(withId(R.id.btnLeading)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.cellCard)).perform(click())
        onView(withId(R.id.cellLabel)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    @Test
    fun openCellsCenterElementsScreen() {
        onView(withId(R.id.cellsButton)).perform(click())
        onView(withId(R.id.btnBaseCells)).perform(click())
        onView(withId(R.id.btnCenter)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.titleCell)).perform(click())
        onView(withId(R.id.subtitleTitleCell)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    @Test
    fun openCellsTrailingElementsScreen() {
        onView(withId(R.id.cellsButton)).perform(click())
        onView(withId(R.id.btnBaseCells)).perform(click())
        onView(withId(R.id.btnTrailing)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.radioButtonCell)).perform(click())
        onView(withId(R.id.checkBoxCell)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }
}