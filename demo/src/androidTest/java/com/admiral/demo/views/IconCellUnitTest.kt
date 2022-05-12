package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.databinding.TestViewIconCellUnitBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.cell.unit.IconCellUnit
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class IconCellUnitTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val layoutInflater = LayoutInflater.from(context)
    private val binding = TestViewIconCellUnitBinding.inflate(layoutInflater)

    private fun IconCellUnit.check() {
        measureUnspecifiedHeight(350)
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkByInflation() {
        with(binding.root) {
            check()
        }
    }

    @Test
    fun checkByInflationEnabledState() {
        checkByInflation()
    }
}