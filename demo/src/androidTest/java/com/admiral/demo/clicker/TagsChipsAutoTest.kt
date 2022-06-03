package com.admiral.demo.clicker

import android.content.Intent
import androidx.compose.foundation.relocation.BringIntoViewRequester
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

class TagsChipsAutoTest : ClickerTest {
    private val intent = Intent(
        ApplicationProvider.getApplicationContext(),
        AppActivity::class.java
    ).putExtra(AppActivity.KEY_IS_TEST, true)

    @get:Rule var activityScenarioRule = activityScenarioRule<AppActivity>(intent)

    @Test
    fun openTagsChipsScreen() {
        onView(withId(R.id.tagsButton)).perform(BetterScrollTo())
        onView(withId(R.id.tagsButton)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.yellow)).perform(BetterScrollTo())
        onView(withId(R.id.toolbar)).perform(BetterScrollTo())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }
}