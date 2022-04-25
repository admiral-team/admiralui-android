package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewSliderBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.textfield.Slider
import com.karumi.shot.ScreenshotTest
import org.junit.Before
import org.junit.Test

class SliderTest : ScreenshotTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(wrappedContext)
    private lateinit var sliderBinding: TestViewSliderBinding

    @Before
    fun setUp() {
        // NB: we do initialization in `runOnUi` block since inflation with admiralText attribute causes
        // animation and we don't want to have exception `AndroidRuntimeException: Animators may only be
        // run on Looper threads`
        sliderBinding = TestViewSliderBinding.inflate(layoutInflater)
    }

    private fun Slider.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    // NB: here we use textResId to set up text programmatically since
    // we don't have the ability to disable the inputLayout animation before the inflation
    private fun checkSliderByInflation(
        isEnabled: Boolean = true,
        isError: Boolean = false,
        value: Float? = 4000f
    ) {
        with(sliderBinding.root) {
            this.optionalText = context.getString(R.string.text_fields_slider_optional_label)
            this.additionalText = context.getString(R.string.text_fields_example_slider_additional)
            this.isError = isError
            this.isEnabled = isEnabled
            valueFrom = 100f
            valueTo = 10000f
            value?.let {
                this.value = value
            }

            check()
        }
    }

    private fun checkSliderProgrammatically(
        isEnabled: Boolean = true,
        isError: Boolean = false,
        value: Float? = 4000f
    ) {
        val slider = Slider(wrappedContext).apply {
            this.optionalText = context.getString(R.string.text_fields_slider_optional_label)
            this.additionalText = context.getString(R.string.text_fields_example_slider_additional)
            this.isError = isError
            this.isEnabled = isEnabled
            valueFrom = 100f
            valueTo = 10000f
            value?.let {
                this.value = value
            }
        }

        slider.check()
    }

    // region check programmatically
    @Test
    fun checkProgrammaticallySliderWithTextEnabledState() {
        checkSliderProgrammatically()
    }

    @Test
    fun checkProgrammaticallySliderWithTextDisabledState() {
        checkSliderProgrammatically(
            isEnabled = false
        )
    }

    @Test
    fun checkProgrammaticallySliderWithoutTextEnabledState() {
        checkSliderProgrammatically(
            value = null
        )
    }

    @Test
    fun checkProgrammaticallySliderWithoutTextDisabledState() {
        checkSliderProgrammatically(
            value = null,
            isEnabled = false
        )
    }

    @Test
    fun checkProgrammaticallySliderErrorWithTextEnabledState() {
        checkSliderProgrammatically(
            isError = true
        )
    }

    @Test
    fun checkProgrammaticallySliderErrorWithTextDisabledState() {
        checkSliderProgrammatically(
            isError = true,
            isEnabled = false
        )
    }
    // endregion

    // region check by inflation
    @Test
    fun checkByInflationSliderWithTextEnabledState() {
        checkSliderByInflation()
    }

    @Test
    fun checkByInflationSliderWithTextDisabledState() {
        checkSliderByInflation(
            isEnabled = false
        )
    }

    @Test
    fun checkByInflationSliderWithoutTextEnabledState() {
        checkSliderByInflation(
            value = null
        )
    }

    @Test
    fun checkByInflationSliderWithoutTextDisabledState() {
        checkSliderByInflation(
            value = null,
            isEnabled = false
        )
    }

    @Test
    fun checkByInflationSliderErrorWithTextEnabledState() {
        checkSliderByInflation(
            isError = true
        )
    }

    @Test
    fun checkByInflationSliderErrorWithTextDisabledState() {
        checkSliderByInflation(
            isError = true,
            isEnabled = false
        )
    }
    // endregion
}