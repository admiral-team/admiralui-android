package com.admiral.demo.views

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewTabsIconBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.tabs.IconTab
import com.admiral.uikit.components.tabs.IconTabs
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class IconTabsTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(context)
    private val binding = TestViewTabsIconBinding.inflate(layoutInflater)

    private fun IconTabs.check() {
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
        IconTabs(wrappedContext).apply {
            val checkedId = View.generateViewId()
            this.addView(IconTab(wrappedContext).apply {
                id = checkedId
                icon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_mobile_solid)
                text = context.getString(R.string.tabs_one)
            })
            this.addView(IconTab(wrappedContext).apply {
                icon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_card_solid)
                text = context.getString(R.string.tabs_two)
            })
            this.addView(IconTab(wrappedContext).apply {
                icon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_card_solid)
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