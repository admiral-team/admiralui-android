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

class TabsTest : ScreenshotTest {

    @get:Rule
    var activityRule: IntentsTestRule<AppActivity> = IntentsTestRule(AppActivity::class.java, true, false)

    @Test
    fun checkStandardTabsEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.tabsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnStandardTabs)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkStandardTabsDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.tabsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnStandardTabs)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkLogoTabsEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.tabsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnLogoTabs)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkLogoTabsDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.tabsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnLogoTabs)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkInformerTabsEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.tabsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnInformerTabs)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkInformerTabsDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.tabsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnInformerTabs)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkOutlineTabsEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.tabsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnOutlineTabs)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkOutlineTabsDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.tabsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnOutlineTabs)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkUnderlineSliderTabsEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.tabsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnUnderlineTabs)).perform(ViewActions.click())
        onView(withId(R.id.sliderTabs)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkUnderlineSliderTabsDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.tabsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnUnderlineTabs)).perform(ViewActions.click())
        onView(withId(R.id.sliderTabs)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkUnderlineCenterTabsEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.tabsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnUnderlineTabs)).perform(ViewActions.click())
        onView(withId(R.id.centerTabs)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkUnderlineCenterTabsDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.tabsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnUnderlineTabs)).perform(ViewActions.click())
        onView(withId(R.id.centerTabs)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkIconTabsEnabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.tabsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnIconTabs)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkIconTabsDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        onView(withId(R.id.mainMenuHome)).perform(ViewActions.click())
        onView(withId(R.id.tabsButton)).perform(BetterScrollTo(), ViewActions.click())
        onView(withId(R.id.btnIconTabs)).perform(ViewActions.click())
        onView(withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }
}