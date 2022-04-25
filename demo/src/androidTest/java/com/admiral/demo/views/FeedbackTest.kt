package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewFeedbackBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.select.Feedback
import com.karumi.shot.ScreenshotTest
import org.junit.Before
import org.junit.Test

class FeedbackTest : ScreenshotTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(wrappedContext)
    private lateinit var feedbackBinder: TestViewFeedbackBinding

    @Before
    fun setUp() {
        // NB: we do initialization in `runOnUi` block since inflation with admiralText attribute causes
        // animation and we don't want to have exception `AndroidRuntimeException: Animators may only be
        // run on Looper threads`
        feedbackBinder = TestViewFeedbackBinding.inflate(layoutInflater)
    }

    private fun Feedback.check() {
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
        isFilled: Boolean = true
    ) {
        with(feedbackBinder.root) {
            this.isEnabled = isEnabled
            this.isAnimationEnabled = false
            if (isFilled) {
                updateRatingWithAnimation(5)
            }

            check()
        }
    }

    private fun checkSliderProgrammatically(
        isEnabled: Boolean = true,
        isFilled: Boolean = true
    ) {
        val slider = Feedback(wrappedContext).apply {
            this.isEnabled = isEnabled
            this.isAnimationEnabled = false
            if (isFilled) {
                updateRatingWithAnimation(5)
            }
        }

        slider.check()
    }

    // region check programmatically
    @Test
    fun checkProgrammaticallyFeedbackEnabledFilled() {
        checkSliderProgrammatically()
    }

    @Test
    fun checkProgrammaticallyFeedbackDisabledFilled() {
        checkSliderProgrammatically(isEnabled = false)
    }

    @Test
    fun checkProgrammaticallyFeedbackEnabledEmpty() {
        checkSliderProgrammatically(isFilled = false)
    }

    @Test
    fun checkProgrammaticallyFeedbackDisabledEmpty() {
        checkSliderProgrammatically(isFilled = false, isEnabled = false)
    }
    // endregion

    // region check by inflation
    @Test
    fun checkByInflationFeedbackEnabledFilled() {
        checkSliderByInflation()
    }

    @Test
    fun checkByInflationFeedbackDisabledFilled() {
        checkSliderByInflation(isEnabled = false)
    }

    @Test
    fun checkByInflationFeedbackEnabledEmpty() {
        checkSliderByInflation(isFilled = false)
    }

    @Test
    fun checkByInflationFeedbackDisabledEmpty() {
        checkSliderByInflation(isFilled = false, isEnabled = false)
    }
    // endregion
}