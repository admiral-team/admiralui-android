package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewPageControlLinerBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.themes.ThemeManager
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.components.control.LinerPageControl
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class LinerPageControlTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AppCompat)
    private val layoutInflater = LayoutInflater.from(wrappedContext)
    private val binding = TestViewPageControlLinerBinding.inflate(layoutInflater)

    private fun LinerPageControl.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkByInflation(isColored: Boolean = false) {
        with(binding.root) {
            if (isColored) {
                this.backgroundColors = ColorState(
                    normalEnabled = ThemeManager.theme.palette.elementAccent,
                    normalDisabled = ThemeManager.theme.palette.elementAdditional
                )
            }
            check()
        }
    }

    private fun checkProgrammatically(isColored: Boolean = false) {
        LinerPageControl(wrappedContext).apply {
            setTabsCount(6)
            if (isColored) {
                this.backgroundColors = ColorState(
                    normalEnabled = ThemeManager.theme.palette.elementAccent,
                    normalDisabled = ThemeManager.theme.palette.elementAdditional
                )
            }
            check()
        }
    }

    @Test
    fun checkByInflationDefaultState() {
        checkByInflation()
    }

    @Test
    fun checkByInflationColoredState() {
        checkByInflation(isColored = true)
    }

    @Test
    fun checkProgrammaticallyDefaultState() {
        checkProgrammatically()
    }

    @Test
    fun checkProgrammaticallyColoredState() {
        checkProgrammatically(isColored = true)
    }
}