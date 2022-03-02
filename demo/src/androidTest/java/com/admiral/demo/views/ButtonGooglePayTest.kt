package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.test.platform.app.InstrumentationRegistry
import androidx.viewbinding.ViewBinding
import com.karumi.shot.ScreenshotTest
import com.admiral.demo.databinding.TestViewButtonGooglePlayBinding
import com.admiral.demo.ext.measureUnspecified
import com.admiral.uikit.components.button.ButtonGooglePay
import org.junit.Test

class ButtonGooglePayTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val layoutInflater = LayoutInflater.from(context)
    private val binding = TestViewButtonGooglePlayBinding.inflate(layoutInflater)

    private fun ButtonGooglePay.check() {
        measureUnspecified()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkButtonByInflation(
        viewBinding: ViewBinding,
        isEnabled: Boolean
    ) {
        with(viewBinding.root as ButtonGooglePay) {
            this.isEnabled = isEnabled
            check()
        }
    }

    private fun checkButtonProgrammatically(isEnabled: Boolean) {
        val button = ButtonGooglePay(context).apply {
            this.isEnabled = isEnabled
        }

        button.check()
    }

    // region check by inflation
    @Test
    fun checkByInflationButtonGooglePlayEnabledState() {
        checkButtonByInflation(
            viewBinding = binding,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationButtonGooglePlayDisabledState() {
        checkButtonByInflation(
            viewBinding = binding,
            isEnabled = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyButtonGooglePlayEnabledState() {
        checkButtonProgrammatically(isEnabled = true)
    }

    @Test
    fun checkProgrammaticallyButtonGooglePlayDisabledState() {
        checkButtonProgrammatically(isEnabled = false)
    }
    // endregion
}