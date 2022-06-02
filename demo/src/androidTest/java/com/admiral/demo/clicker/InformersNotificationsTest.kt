package com.admiral.demo.clicker

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import com.admiral.demo.R
import com.admiral.demo.app.AppActivity
import com.admiral.demo.ext.BetterScrollTo
import com.admiral.demo.filters.ClickerTest
import org.junit.Rule
import org.junit.Test

class InformersNotificationsTest : ClickerTest {
    private val intent = Intent(
        ApplicationProvider.getApplicationContext(),
        AppActivity::class.java
    ).putExtra(AppActivity.KEY_IS_TEST, true)

    @get:Rule var activityScenarioRule = activityScenarioRule<AppActivity>(intent)

    @Test
    fun openBigInformersScreen() {
        onView(withId(R.id.informerButton)).perform(click())
        onView(withId(R.id.btnInformers)).perform(click())
        onView(withId(R.id.btnInformersBig)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    @Test
    fun openSmallInformerScreen() {
        onView(withId(R.id.informerButton)).perform(click())
        onView(withId(R.id.btnInformers)).perform(click())
        onView(withId(R.id.btnInformersSmall)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    @Test
    fun openNotificationsToastScreen() {
        onView(withId(R.id.informerButton)).perform(click())
        onView(withId(R.id.btnNotifications)).perform(click())
        onView(withId(R.id.btnToast)).perform(click())
        onView(withId(R.id.btnToast)).perform(click())
    }

    @Test
    fun OpenNotificationStaticScreen() {
        onView(withId(R.id.informerButton)).perform(click())
        onView(withId(R.id.btnNotifications)).perform(click())
        onView(withId(R.id.btnStatic)).perform(click())
        onView(withId(R.id.disabledTab)).perform(click())
        onView(withId(R.id.defaultTab)).perform(click())
        onView(withId(R.id.notificationError)).perform(BetterScrollTo())
        onView(withId(R.id.notificationDefault)).perform(BetterScrollTo())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }

    @Test
    fun NotificationActionScreen() {
        onView(withId(R.id.informerButton)).perform(click())
        onView(withId(R.id.btnNotifications)).perform(click())
        onView(withId(R.id.btnAction)).perform(click())
        onView(withId(R.id.btnToast)).perform(click())
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
    }
}