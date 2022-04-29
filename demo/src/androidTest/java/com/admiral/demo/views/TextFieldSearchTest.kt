package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewTextFieldSearchBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.textfield.TextFieldSearch
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class TextFieldSearchTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(wrappedContext)
    private val binding = TestViewTextFieldSearchBinding.inflate(layoutInflater)

    private fun TextFieldSearch.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkTextFieldByInflation() {
        with(binding.root) {
            this.isEnabled = isEnabled
            check()
        }
    }

    @Test
    fun checkByInflationTextFieldStandardWithTextEnabledState() {
        checkTextFieldByInflation()
    }
}