package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import androidx.viewbinding.ViewBinding
import com.karumi.shot.ScreenshotTest
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewSwitcherBinding
import com.admiral.demo.ext.measureUnspecified
import com.admiral.uikit.components.switcher.Switcher
import org.junit.Test

class SwitcherTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(wrappedContext)
    private val binding = TestViewSwitcherBinding.inflate(layoutInflater)

    private fun Switcher.check() {
        measureUnspecified()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun Switcher.setIsChecked(isChecked: Boolean) {
        runOnUi {
            // We do it on UI thread otherwise
            // `AndroidRuntimeException: Animators may only be run on Looper threads` will be thrown.
            // Also, it's necessary to use `runOnUi` otherwise the checkbox won't be checked on screenshots.
            this.isChecked = isChecked
        }
    }

    private fun checkSwitcherByInflation(
        viewBinding: ViewBinding,
        isEnabled: Boolean,
        isChecked: Boolean
    ) {
        with(viewBinding.root as Switcher) {
            this.isEnabled = isEnabled
            setIsChecked(isChecked)
            check()
        }
    }

    private fun checkSwitcherProgrammatically(
        isEnabled: Boolean,
        isChecked: Boolean
    ) {
        val switcher = Switcher(wrappedContext).apply {
            this.isEnabled = isEnabled
            setIsChecked(isChecked)
        }

        switcher.check()
    }

    // region check Switcher by inflation
    @Test
    fun checkByInflationSwitcherEnabledCheckedState() {
        checkSwitcherByInflation(
            viewBinding = binding,
            isEnabled = true,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationSwitcherDisabledCheckedState() {
        checkSwitcherByInflation(
            viewBinding = binding,
            isEnabled = false,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationSwitcherEnabledUncheckedState() {
        checkSwitcherByInflation(
            viewBinding = binding,
            isEnabled = true,
            isChecked = false
        )
    }

    @Test
    fun checkByInflationSwitcherDisabledUncheckedState() {
        checkSwitcherByInflation(
            viewBinding = binding,
            isEnabled = false,
            isChecked = false
        )
    }
    // endregion

    // region check Switcher programmatically
    @Test
    fun checkProgrammaticallySwitcherEnabledCheckedState() {
        checkSwitcherProgrammatically(
            isEnabled = true,
            isChecked = true
        )
    }

    @Test
    fun checkProgrammaticallySwitcherDisabledCheckedState() {
        checkSwitcherProgrammatically(
            isEnabled = false,
            isChecked = true
        )
    }

    @Test
    fun checkProgrammaticallySwitcherEnabledUncheckedState() {
        checkSwitcherProgrammatically(
            isEnabled = true,
            isChecked = false
        )
    }

    @Test
    fun checkProgrammaticallySwitcherDisabledUncheckedState() {
        checkSwitcherProgrammatically(
            isEnabled = false,
            isChecked = false
        )
    }
    // endregion
}