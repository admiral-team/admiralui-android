package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewInputBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.input.InputNumber
import com.karumi.shot.ScreenshotTest
import org.junit.Before
import org.junit.Test

class InputNumberTest : ScreenshotTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(wrappedContext)
    private lateinit var inputNumberBinding: TestViewInputBinding

    @Before
    fun setUp() {
        // NB: we do initialization in `runOnUi` block since inflation  admiralText attribute causes
        // animation and we don't want to have exception `AndroidRuntimeException: Animators may only be
        // run on Looper threads`
        inputNumberBinding = TestViewInputBinding.inflate(layoutInflater)
    }

    private fun InputNumber.check() {
        measureUnspecifiedHeight()
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    // NB: here we use textResId to set up text programmatically since
    // we don't have the ability to disable the inputLayout animation before the inflation
    private fun checkInputNumberByInflation(
        isEnabled: Boolean = true,
        value: Int = 100
    ) {
        with(inputNumberBinding.root) {
            this.optionalText = context.getString(R.string.text_fields_slider_optional_label)
            this.isEnabled = isEnabled
            this.minValue = -999999999
            this.maxValue = 999999999
            this.value = value

            check()
        }
    }

    private fun checkInputNumberProgrammatically(
        isEnabled: Boolean = true,
        value: Int = 100
    ) {
        val inputNumber = InputNumber(wrappedContext).apply {
            this.optionalText = context.getString(R.string.text_fields_slider_optional_label)
            this.isEnabled = isEnabled
            this.minValue = -999999999
            this.maxValue = 999999999
            this.value = value
        }

        inputNumber.check()
    }

    // region check programmatically
    @Test
    fun checkProgrammaticallyInputNumberEnabledShortState() {
        checkInputNumberProgrammatically()
    }

    @Test
    fun checkProgrammaticallyInputNumberEnabledMediumState() {
        checkInputNumberProgrammatically(
            value = 100000
        )
    }

    @Test
    fun checkProgrammaticallyInputNumberEnabledBigState() {
        checkInputNumberProgrammatically(
            value = 10000000
        )
    }

    @Test
    fun checkProgrammaticallyInputNumberEnabledLargeState() {
        checkInputNumberProgrammatically(
            value = 100000000
        )
    }

    @Test
    fun checkProgrammaticallyInputNumberDisabledShortState() {
        checkInputNumberProgrammatically(
            isEnabled = false
        )
    }

    @Test
    fun checkProgrammaticallyInputNumberDisabledMediumState() {
        checkInputNumberProgrammatically(
            value = 100000,
            isEnabled = false
        )
    }

    @Test
    fun checkProgrammaticallyInputNumberDisabledBigState() {
        checkInputNumberProgrammatically(
            value = 10000000,
            isEnabled = false
        )
    }

    @Test
    fun checkProgrammaticallyInputNumberDisabledLargeState() {
        checkInputNumberProgrammatically(
            value = 1000,
            isEnabled = false
        )
    }
    // endregion

    // region check by inflation
    @Test
    fun checkByInflationInputNumberEnabledShortState() {
        checkInputNumberByInflation()
    }

    @Test
    fun checkByInflationInputNumberEnabledMediumState() {
        checkInputNumberByInflation(
            value = 100000
        )
    }

    @Test
    fun checkByInflationInputNumberEnabledBigState() {
        checkInputNumberByInflation(
            value = 10000000
        )
    }

    @Test
    fun checkByInflationInputNumberEnabledLargeState() {
        checkInputNumberByInflation(
            value = 100000000
        )
    }

    @Test
    fun checkByInflationInputNumberDisabledShortState() {
        checkInputNumberByInflation(
            isEnabled = false
        )
    }

    @Test
    fun checkByInflationInputNumberDisabledMediumState() {
        checkInputNumberByInflation(
            value = 100000,
            isEnabled = false
        )
    }

    @Test
    fun checkByInflationInputNumberDisabledBigState() {
        checkInputNumberByInflation(
            value = 10000000,
            isEnabled = false
        )
    }

    @Test
    fun checkByInflationInputNumberDisabledLargeState() {
        checkInputNumberByInflation(
            value = 100000000,
            isEnabled = false
        )
    }
    // endregion
}