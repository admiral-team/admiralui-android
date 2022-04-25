package com.admiral.demo.views

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewTabsStandardBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.tabs.StandardTab
import com.admiral.uikit.components.tabs.StandardTabs
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class StandardTabsTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(context)
    private val binding = TestViewTabsStandardBinding.inflate(layoutInflater)

    private fun StandardTabs.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkByInflation(
        isEnabled: Boolean
    ) {
        with(binding.root) {
            this.isEnabled = isEnabled
            check()
        }
    }

    private fun checkProgrammatically(
        isEnabled: Boolean,
    ) {
        StandardTabs(wrappedContext).apply {
            val checkedId = View.generateViewId()
            this.addView(StandardTab(wrappedContext).apply {
                id = checkedId
                text = context.getString(R.string.tabs_one)
            })
            this.addView(StandardTab(wrappedContext).apply {
                text = context.getString(R.string.tabs_two)
            })
            this.addView(StandardTab(wrappedContext).apply {
                text = context.getString(R.string.tabs_three)
            })
            this.check(checkedId)
            this.isEnabled = isEnabled

            check()
        }
    }

    // region check by inflation
    @Test
    fun checkByInflationEnabledState() {
        checkByInflation(
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationDisabledState() {
        checkByInflation(
            isEnabled = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyEnabledState() {
        checkProgrammatically(
            isEnabled = true
        )
    }

    @Test
    fun checkProgrammaticallyDisabledState() {
        checkProgrammatically(
            isEnabled = false
        )
    }
    // endregion
}