package com.admiral.demo

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import com.karumi.shot.ScreenshotTest
import com.admiral.demo.app.AppActivity
import com.admiral.demo.app.AppActivity.Companion.KEY_IS_TEST
import org.junit.Rule
import org.junit.Test

class TextFieldsTest : ScreenshotTest {

    @get:Rule
    var activityRule: IntentsTestRule<AppActivity> = IntentsTestRule(AppActivity::class.java, true, false)

    @Test
    fun checkTextFieldStandardStandardDefault() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnStandard)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnStandardIcon)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.defaultTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldStandardStandardReadOnly() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnStandard)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnStandardIcon)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.read)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldStandardStandardError() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnStandard)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnStandardIcon)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.error)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldStandardStandardDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnStandard)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnStandardIcon)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.disabled)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldStandardCardNumberDefault() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnStandard)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnCardNumber)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.defaultTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldStandardCardNumberError() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnStandard)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnCardNumber)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.error)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldStandardCardNumberDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnStandard)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnCardNumber)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.disabled)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldStandardSmsCodeDefault() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnStandard)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnSmsCode)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.defaultTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldStandardSmsCodeError() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnStandard)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnSmsCode)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.error)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldStandardSmsCodeDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnStandard)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnSmsCode)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.disabled)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldDoubleDefault() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnDouble)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.defaultTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldDoubleReadOnly() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnDouble)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.read)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldDoubleError() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnDouble)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.error)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldDoubleDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnDouble)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.disabled)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldSliderDefault() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnSlider)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.defaultTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldSliderError() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnSlider)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.error)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldSliderDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnSlider)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.disabled)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldNumberDefault() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnNumber)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.defaultTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldNumberDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnNumber)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldFeedbackDefault() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnFeedback)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.defaultTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldFeedbackDisabled() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnFeedback)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.disabledTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldPincodeDefault() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnPincode)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.defaultTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldPincodeSuccess() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnPincode)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.successTab)).perform(ViewActions.click())

        compareScreenshot(activity)
    }

    @Test
    fun checkTextFieldPincodeError() {
        val intent = Intent().apply {
            putExtra(KEY_IS_TEST, true)
        }
        val activity = activityRule.launchActivity(intent)

        Espresso.onView(ViewMatchers.withId(R.id.mainMenuHome)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.textFieldsButton)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.btnPincode)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.error)).perform(ViewActions.click())

        compareScreenshot(activity)
    }
}