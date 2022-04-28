package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewBadgeBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.badge.Badge
import com.admiral.uikit.components.badge.BadgeSize
import com.admiral.uikit.components.badge.BadgeType
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class BadgeTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(context)
    private val binding = TestViewBadgeBinding.inflate(layoutInflater)

    private fun Badge.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkByInflation(
        isEnabled: Boolean = true,
        size: BadgeSize = BadgeSize.NORMAL,
        isError: Boolean = false,
        text: String = context.getString(R.string.badges_example)
    ) {
        with(binding.root) {
            this.isEnabled = isEnabled
            this.badgeSize = size
            if (isError) {
                this.badgeType = BadgeType.ERROR
            }
            this.text = text

            check()
        }
    }

    private fun checkProgrammatically(
        isEnabled: Boolean = true,
        size: BadgeSize = BadgeSize.NORMAL,
        isError: Boolean = false,
        text: String = context.getString(R.string.badges_example)
    ) {
        Badge(wrappedContext).apply {
            this.isEnabled = isEnabled
            this.badgeSize = size
            if (isError) {
                this.badgeType = BadgeType.ERROR
            }
            this.text = text

            check()
        }
    }

    // region check by inflation
    @Test
    fun checkByInflationEnabledState() {
        checkByInflation()
    }

    @Test
    fun checkByInflationDisabledState() {
        checkByInflation(
            isEnabled = false
        )
    }

    @Test
    fun checkByInflationEnabledSmallState() {
        checkByInflation(
            size = BadgeSize.SMALL
        )
    }

    @Test
    fun checkByInflationDisabledSmallState() {
        checkByInflation(
            isEnabled = false,
            size = BadgeSize.SMALL
        )
    }

    @Test
    fun checkByInflationEnabledErrorState() {
        checkByInflation(
            isError = true
        )
    }

    @Test
    fun checkByInflationEnabledBigTextState() {
        checkByInflation(
            text = context.getString(R.string.badges_example_big_text)
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


    @Test
    fun checkProgrammaticallyEnabledSmallState() {
        checkProgrammatically(
            size = BadgeSize.SMALL
        )
    }

    @Test
    fun checkProgrammaticallyDisabledSmallState() {
        checkProgrammatically(
            isEnabled = false,
            size = BadgeSize.SMALL
        )
    }

    @Test
    fun checkProgrammaticallyEnabledErrorState() {
        checkProgrammatically(
            isError = true
        )
    }

    @Test
    fun checkProgrammaticallyEnabledBigTextState() {
        checkProgrammatically(
            text = context.getString(R.string.badges_example_big_text)
        )
    }
    // endregion
}