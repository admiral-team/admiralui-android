package com.admiral.demo.views

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import androidx.viewbinding.ViewBinding
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewTabsOutlineBinding
import com.admiral.demo.databinding.TestViewTabsOutlineWithBadgeBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.tabs.OutlineSliderTab
import com.admiral.uikit.components.tabs.OutlineSliderTabs
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class OutlineTabsTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(context)
    private val binding = TestViewTabsOutlineBinding.inflate(layoutInflater)
    private val bindingWithBadge = TestViewTabsOutlineWithBadgeBinding.inflate(layoutInflater)

    private fun OutlineSliderTabs.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkByInflation(
        isEnabled: Boolean = true,
        binding: ViewBinding
    ) {
        with(binding.root as OutlineSliderTabs) {
            this.isEnabled = isEnabled
            check()
        }
    }

    private fun checkProgrammatically(
        isEnabled: Boolean = true,
        isBadgeVisible: Boolean = false
    ) {
        OutlineSliderTabs(wrappedContext).apply {
            val checkedId = View.generateViewId()
            this.addView(OutlineSliderTab(wrappedContext).apply {
                id = checkedId
                text = context.getString(R.string.tabs_one)
                this.isBadgeVisible = isBadgeVisible
            })
            this.addView(OutlineSliderTab(wrappedContext).apply {
                text = context.getString(R.string.tabs_two)
                this.isBadgeVisible = isBadgeVisible
            })
            this.addView(OutlineSliderTab(wrappedContext).apply {
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
            binding = binding
        )
    }

    @Test
    fun checkByInflationDisabledState() {
        checkByInflation(
            binding = binding,
            isEnabled = false
        )
    }

    @Test
    fun checkByInflationWithBadge() {
        checkByInflation(
            binding = bindingWithBadge
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyEnabledState() {
        checkProgrammatically()
    }

    @Test
    fun checkProgrammaticallyDisabledState() {
        checkProgrammatically(
            isEnabled = false
        )
    }

    @Test
    fun checkProgrammaticallyWithBadge() {
        checkProgrammatically(
            isBadgeVisible = true
        )
    }
    // endregion
}