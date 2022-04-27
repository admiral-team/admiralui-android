package com.admiral.demo.views

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewTabsUnderlineBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.tabs.UnderlineSliderTab
import com.admiral.uikit.components.tabs.UnderlineSliderTabs
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class UnderlineTabsTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(context)
    private val binding = TestViewTabsUnderlineBinding.inflate(layoutInflater)

    private fun UnderlineSliderTabs.check() {
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
        UnderlineSliderTabs(wrappedContext).apply {
            val checkedId = View.generateViewId()
            this.addView(UnderlineSliderTab(wrappedContext).apply {
                id = checkedId
                text = context.getString(R.string.tabs_one)
                underlinePadding = 6
            })
            this.addView(UnderlineSliderTab(wrappedContext).apply {
                text = context.getString(R.string.tabs_two)
                underlinePadding = 6
            })
            this.addView(UnderlineSliderTab(wrappedContext).apply {
                text = context.getString(R.string.tabs_three)
                underlinePadding = 6
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