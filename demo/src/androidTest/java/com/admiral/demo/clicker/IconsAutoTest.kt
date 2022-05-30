package com.admiral.demo.clicker

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import com.admiral.demo.R
import com.admiral.demo.app.AppActivity
import com.admiral.demo.filters.ClickerTest
import org.junit.Rule
import org.junit.Test

class IconsAutoTest : ClickerTest {
    private val intent = Intent(
        ApplicationProvider.getApplicationContext(),
        AppActivity::class.java
    ).putExtra(AppActivity.KEY_IS_TEST, true)

    @get:Rule var activityScenarioRule = activityScenarioRule<AppActivity>(intent)

    @Test
    fun openIconsScreen() {
        onView(withId(R.id.iconsButton)).perform(click())
        onView(withId(R.id.tabSolid)).perform(click())
        onView(withId(R.id.tabOutline)).perform(click())
        val hasSibling = hasSibling(withId(R.id.admiralIconCellBadge))
        onView(hasSibling).perform(typeText("Drag"))
        onView(withId(R.id.text_input_end_icon)).perform(click())
        onView(withText("Check")).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }
}