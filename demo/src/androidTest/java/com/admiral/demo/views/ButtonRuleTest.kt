package com.admiral.demo.views

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.karumi.shot.ScreenshotTest
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewButtonRuleBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.button.ButtonRule
import org.junit.Test

class ButtonRuleTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(context)
    private val binding = TestViewButtonRuleBinding.inflate(layoutInflater)

    private fun View.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun ButtonRule.setIsChecked(isChecked: Boolean) {
        runOnUi {
            // We do it on UI thread otherwise
            // `AndroidRuntimeException: Animators may only be run on Looper threads` will be thrown.
            // Also, it's necessary to use `runOnUi` otherwise the checkbox won't be checked on screenshots.
            ruleCheckBox.isChecked = isChecked
        }
    }

    private fun checkRuleButtonByInflation(
        viewBinding: TestViewButtonRuleBinding,
        isEnabled: Boolean,
        isChecked: Boolean
    ) {
        with(viewBinding.root) {
            this.isEnabled = isEnabled
            setIsChecked(isChecked)
            check()
        }
    }

    private fun checkRuleButtonProgrammatically(
        isEnabled: Boolean,
        isChecked: Boolean,
        @StringRes actionTextResId: Int,
        @StringRes ruleTextResId: Int
    ) {
        val button = ButtonRule(wrappedContext).apply {
            this.actionText = context.getString(actionTextResId)
            this.ruleText = context.getString(ruleTextResId)
            this.isEnabled = isEnabled
            setIsChecked(isChecked)
        }

        button.check()
    }

    // region check by inflation
    @Test
    fun checkByInflationButtonRuleEnabledUncheckedState() {
        checkRuleButtonByInflation(
            viewBinding = binding,
            isEnabled = true,
            isChecked = false
        )
    }

    @Test
    fun checkByInflationButtonRuleEnabledCheckedState() {
        checkRuleButtonByInflation(
            viewBinding = binding,
            isEnabled = true,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationButtonRuleDisabledUncheckedState() {
        checkRuleButtonByInflation(
            viewBinding = binding,
            isEnabled = false,
            isChecked = false
        )
    }

    @Test
    fun checkByInflationButtonRuleDisabledCheckedState() {
        checkRuleButtonByInflation(
            viewBinding = binding,
            isEnabled = false,
            isChecked = true
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyButtonRuleEnabledUncheckedState() {
        checkRuleButtonProgrammatically(
            isEnabled = true,
            isChecked = false,
            actionTextResId = R.string.buttons_example_rules_action,
            ruleTextResId = R.string.buttons_example_rule
        )
    }

    @Test
    fun checkProgrammaticallyButtonRuleEnabledCheckedState() {
        checkRuleButtonProgrammatically(
            isEnabled = true,
            isChecked = true,
            actionTextResId = R.string.buttons_example_rules_action,
            ruleTextResId = R.string.buttons_example_rule
        )
    }

    @Test
    fun checkProgrammaticallyButtonRuleDisabledUncheckedState() {
        checkRuleButtonProgrammatically(
            isEnabled = false,
            isChecked = false,
            actionTextResId = R.string.buttons_example_rules_action,
            ruleTextResId = R.string.buttons_example_rule
        )
    }

    @Test
    fun checkProgrammaticallyButtonRuleDisabledCheckedState() {
        checkRuleButtonProgrammatically(
            isEnabled = false,
            isChecked = true,
            actionTextResId = R.string.buttons_example_rules_action,
            ruleTextResId = R.string.buttons_example_rule
        )
    }
    // endregion
}