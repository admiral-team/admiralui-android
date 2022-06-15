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

class TabsAutoTest : ClickerTest {
    private val intent = Intent(
        ApplicationProvider.getApplicationContext(),
        AppActivity::class.java
    ).putExtra(AppActivity.KEY_IS_TEST, true)

    @get:Rule var activityScenarioRule = activityScenarioRule<AppActivity>(intent)

    @Test
    fun openStandardTabsScreen() {
        onView(withId(R.id.tabsButton)).perform(click())
        onView(withId(R.id.btnStandardTabs)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.two2)).perform(click())
        onView(withId(R.id.one2)).perform(click())
        onView(withId(R.id.two)).perform(click())
        onView(withId(R.id.three)).perform(click())
        onView(withId(R.id.two)).perform(click())
        onView(withId(R.id.one)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    @Test
    fun openLogoTabsScreen() {
        onView(withId(R.id.tabsButton)).perform(click())
        onView(withId(R.id.btnLogoTabs)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.two2)).perform(click())
        onView(withId(R.id.one2)).perform(click())
        onView(withId(R.id.two)).perform(click())
        onView(withId(R.id.three)).perform(click())
        onView(withId(R.id.two)).perform(click())
        onView(withId(R.id.one)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    @Test
    fun openInformerTabsScreen() {
        onView(withId(R.id.tabsButton)).perform(click())
        onView(withId(R.id.btnInformerTabs)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.two2)).perform(click())
        onView(withId(R.id.one2)).perform(click())
        onView(withId(R.id.two)).perform(click())
        onView(withId(R.id.three)).perform(click())
        onView(withId(R.id.two)).perform(click())
        onView(withId(R.id.one)).perform(click())
        onView(withId(R.id.linearLayout)).perform(swipeDown())
        onView(withId(R.id.linearLayout)).perform(swipeUp())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    @Test
    fun openOutlineSliderTabScreen() {
        onView(withId(R.id.tabsButton)).perform(click())
        onView(withId(R.id.btnOutlineTabs)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.one2)).perform(click())
        onView(withId(R.id.two2)).perform(click())
        onView(withId(R.id.three2)).perform(click())
        onView(withId(R.id.one)).perform(click())
        onView(withId(R.id.two)).perform(click())
        onView(withId(R.id.three)).perform(click())
        onView(withId(R.id.four)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    @Test
    fun openUnderlineSliderTabScreen() {
        onView(withId(R.id.tabsButton)).perform(click())
        onView(withId(R.id.btnUnderlineTabs)).perform(click())
        onView(withId(R.id.sliderTabs)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.one2)).perform(click())
        onView(withId(R.id.two2)).perform(click())
        onView(withId(R.id.three2)).perform(click())
        onView(withId(R.id.one)).perform(click())
        onView(withId(R.id.two)).perform(click())
        onView(withId(R.id.three)).perform(click())
        onView(withId(R.id.four)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    @Test
    fun openUnderlineCenterTabScreen() {
        onView(withId(R.id.tabsButton)).perform(click())
        onView(withId(R.id.btnUnderlineTabs)).perform(click())
        onView(withId(R.id.centerTabs)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.tabOne2)).perform(click())
        onView(withId(R.id.tabTwo2)).perform(click())
        onView(withId(R.id.tabOne3)).perform(click())
        onView(withId(R.id.tabTwo3)).perform(click())
        onView(withId(R.id.tabThree3)).perform(click())
        onView(withId(R.id.tabOne4)).perform(click())
        onView(withId(R.id.tabTwo4)).perform(click())
        onView(withId(R.id.tabThree4)).perform(click())
        onView(withId(R.id.tabFour4)).perform(click())
        onView(withId(R.id.tabOne5)).perform(click())
        onView(withId(R.id.tabTwo5)).perform(click())
        onView(withId(R.id.tabThree5)).perform(click())
        onView(withId(R.id.tabFour5)).perform(click())
        onView(withId(R.id.tabFive5)).perform(click())
        onView(withId(R.id.tabNotification)).perform(BetterScrollTo())
        onView(withId(R.id.tabNotification)).perform(click())
        onView(withId(R.id.tabNotification2)).perform(click())
        onView(withId(R.id.tabNotificationAdditional)).perform(click())
        onView(withId(R.id.toolbar)).perform(BetterScrollTo())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    @Test
    fun openIconTabScreen() {
        onView(withId(R.id.tabsButton)).perform(click())
        onView(withId(R.id.btnIconTabs)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.two2)).perform(click())
        onView(withId(R.id.one2)).perform(click())
        onView(withId(R.id.two)).perform(click())
        onView(withId(R.id.three)).perform(click())
        onView(withId(R.id.two)).perform(click())
        onView(withId(R.id.one)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }
}