package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewPageControlCircleBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.control.CirclePageControl
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class CirclePageControlTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(context)
    private val binding = TestViewPageControlCircleBinding.inflate(layoutInflater)

    private fun CirclePageControl.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkByInflation(progress: Int = 50, isContrast: Boolean = false) {
        with(binding.root) {
            this.setProgress(progress)
            this.isContrast = isContrast
            check()
        }
    }

    private fun checkProgrammatically(progress: Int = 50, isContrast: Boolean = false) {
        CirclePageControl(wrappedContext).apply {
            this.isAnimationEnabled = false
            this.isContrast = isContrast
            this.setProgress(progress)
            check()
        }
    }

    // region check by inflation
    @Test
    fun checkByInflationFullContrastState() {
        checkByInflation(100, isContrast = true)
    }

    @Test
    fun checkByInflationFullState() {
        checkByInflation(100)
    }

    @Test
    fun checkByInflationHalfState() {
        checkByInflation()
    }

    @Test
    fun checkByInflationQuarterState() {
        checkByInflation(25)
    }

    @Test
    fun checkByInflationEmptyState() {
        checkByInflation(0)
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyFullContrastState() {
        checkProgrammatically(100, isContrast = true)
    }

    @Test
    fun checkProgrammaticallyFullState() {
        checkProgrammatically(100)
    }

    @Test
    fun checkProgrammaticallyHalfState() {
        checkProgrammatically()
    }

    @Test
    fun checkProgrammaticallyQuarterState() {
        checkProgrammatically(25)
    }

    @Test
    fun checkProgrammaticallyEmptyState() {
        checkProgrammatically(0)
    }
    // endregion
}