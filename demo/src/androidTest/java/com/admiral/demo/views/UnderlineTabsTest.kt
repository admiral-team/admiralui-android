package com.admiral.demo.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewTabsUnderlineCenterBinding
import com.admiral.demo.databinding.TestViewTabsUnderlineSliderBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.tabs.UnderlineSliderTab
import com.admiral.uikit.components.tabs.UnderlineSliderTabs
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class UnderlineTabsTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext =
        ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(wrappedContext)
    private val sliderBinding = TestViewTabsUnderlineSliderBinding.inflate(layoutInflater)
    private val centeredBinding = TestViewTabsUnderlineCenterBinding.inflate(layoutInflater)

    private fun UnderlineSliderTabs.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkByInflation(
        isEnabled: Boolean,
        isCenterTabs: Boolean
    ) {
        with(if (isCenterTabs) centeredBinding.root else sliderBinding.root) {
            this.isEnabled = isEnabled
            check()
        }
    }

    private fun checkProgrammatically(
        isEnabled: Boolean,
        isCenterTabs: Boolean
    ) {
        UnderlineSliderTabs(wrappedContext).apply {
            layoutParams = ViewGroup.LayoutParams(
                MATCH_PARENT,
                WRAP_CONTENT
            )

            val checkedId = View.generateViewId()
            this.addView(
                UnderlineSliderTab(wrappedContext).apply {
                    layoutParams = LinearLayoutCompat.LayoutParams(
                        WRAP_CONTENT,
                        WRAP_CONTENT,
                        if (isCenterTabs) 1f else 0f
                    )
                    id = checkedId
                    text = context.getString(R.string.tabs_one)
                    underlinePadding = 6
                }
            )
            this.addView(
                UnderlineSliderTab(wrappedContext).apply {
                    layoutParams = LinearLayoutCompat.LayoutParams(
                        WRAP_CONTENT,
                        WRAP_CONTENT,
                        if (isCenterTabs) 1f else 0f
                    )
                    text = context.getString(R.string.tabs_two)
                    underlinePadding = 6
                }
            )
            this.addView(
                UnderlineSliderTab(wrappedContext).apply {
                    layoutParams = LinearLayoutCompat.LayoutParams(
                        WRAP_CONTENT,
                        WRAP_CONTENT,
                        if (isCenterTabs) 1f else 0f
                    )
                    text = context.getString(R.string.tabs_three)
                    underlinePadding = 6
                }
            )

            this.check(checkedId)
            this.isEnabled = isEnabled

            check()
        }
    }

    // region check by inflation
    @Test
    fun checkByInflationSliderEnabledState() {
        checkByInflation(
            isEnabled = true,
            isCenterTabs = false
        )
    }

    @Test
    fun checkByInflationSliderDisabledState() {
        checkByInflation(
            isEnabled = false,
            isCenterTabs = false
        )
    }

    @Test
    fun checkByInflationCenterEnabledState() {
        checkByInflation(
            isEnabled = true,
            isCenterTabs = true
        )
    }

    @Test
    fun checkByInflationCenterDisabledState() {
        checkByInflation(
            isEnabled = false,
            isCenterTabs = true
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallySliderEnabledState() {
        checkProgrammatically(
            isEnabled = true,
            isCenterTabs = false
        )
    }

    @Test
    fun checkProgrammaticallySliderDisabledState() {
        checkProgrammatically(
            isEnabled = false,
            isCenterTabs = false
        )
    }

    @Test
    fun checkProgrammaticallyCenterEnabledState() {
        checkProgrammatically(
            isEnabled = true,
            isCenterTabs = true
        )
    }

    @Test
    fun checkProgrammaticallyCenterDisabledState() {
        checkProgrammatically(
            isEnabled = false,
            isCenterTabs = true
        )
    }
    // endregion
}