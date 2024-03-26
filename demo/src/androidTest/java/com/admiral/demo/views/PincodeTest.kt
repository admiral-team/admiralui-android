package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewPincodeBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.core.components.pincode.PinCodeState
import com.admiral.uikit.components.pincode.PinCodeView
import com.karumi.shot.ScreenshotTest
import org.junit.Before
import org.junit.Test

class PincodeTest : ScreenshotTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(wrappedContext)
    private lateinit var pincodeBinding: TestViewPincodeBinding

    @Before
    fun setUp() {
        // NB: we do initialization in `runOnUi` block since inflation with admiralText attribute causes
        // animation and we don't want to have exception `AndroidRuntimeException: Animators may only be
        // run on Looper threads`
        pincodeBinding = TestViewPincodeBinding.inflate(layoutInflater)
    }

    private fun PinCodeView.check() {
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
        dotsCount: Int = 8,
        state: PinCodeState = PinCodeState.DEFAULT
    ) {
        with(pincodeBinding.root) {
            this.isAnimationEnabled = false
            this.dotsCount = 8
            this.activeDotsCount = dotsCount
            this.state = state

            check()
        }
    }

    private fun checkSliderProgrammatically(
        dotsCount: Int = 8,
        state: PinCodeState = PinCodeState.DEFAULT
    ) {
        val slider = PinCodeView(wrappedContext).apply {
            this.isAnimationEnabled = false
            this.dotsCount = 8
            this.activeDotsCount = dotsCount
            this.state = state
        }

        slider.check()
    }

    // region check programmatically
    @Test
    fun checkProgrammaticallyPincodeFilledDefault() {
        checkSliderProgrammatically()
    }

    @Test
    fun checkProgrammaticallyPincodeEmptyDefault() {
        checkSliderProgrammatically(dotsCount = 0)
    }

    @Test
    fun checkProgrammaticallyPincodeHalfFilledDefault() {
        checkSliderProgrammatically(
            dotsCount = 4
        )
    }

    @Test
    fun checkProgrammaticallyPincodeFilledError() {
        checkSliderProgrammatically(
            state = PinCodeState.ERROR
        )
    }

    @Test
    fun checkProgrammaticallyPincodeFilledSuccess() {
        checkSliderProgrammatically(
            state = PinCodeState.SUCCESS
        )
    }
    // endregion

    // region check by inflation
    @Test
    fun checkByInflationPincodeFilledDefault() {
        checkSliderByInflation()
    }

    @Test
    fun checkByInflationPincodeEmptyDefault() {
        checkSliderByInflation(dotsCount = 0)
    }


    @Test
    fun checkByInflationPincodeHalfFilledDefault() {
        checkSliderByInflation(
            dotsCount = 4
        )
    }

    @Test
    fun checkByInflationPincodeFilledError() {
        checkSliderByInflation(
            state = PinCodeState.ERROR
        )
    }

    @Test
    fun checkByInflationPincodeFilledSuccess() {
        checkSliderByInflation(
            state = PinCodeState.SUCCESS
        )
    }
    // endregion
}