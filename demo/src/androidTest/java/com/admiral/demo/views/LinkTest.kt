package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewLinkBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.links.Link
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class LinkTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(context)
    private val binding = TestViewLinkBinding.inflate(layoutInflater)

    private fun Link.check() {
        measureUnspecifiedHeight(350)
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkByInflation(
        isEnabled: Boolean = true,
        isIconShown: Boolean = false

    ) {
        with(binding.root) {
            this.isEnabled = isEnabled
            text = context.getString(R.string.links_title)
            if (isIconShown) {
                drawableEnd = ContextCompat.getDrawable(context, R.drawable.admiral_ic_arrow_right_outline)
            }

            check()
        }
    }

    private fun checkProgrammatically(
        isEnabled: Boolean = true,
        isIconShown: Boolean = false
    ) {
        Link(wrappedContext).apply {
            this.isEnabled = isEnabled
            text = context.getString(R.string.links_title)
            if (isIconShown) {
                drawableEnd = ContextCompat.getDrawable(context, R.drawable.admiral_ic_arrow_right_outline)
            }

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
    fun checkByInflationEnabledWithIconState() {
        checkByInflation(
            isIconShown = true
        )
    }

    @Test
    fun checkByInflationDisabledWithIconState() {
        checkByInflation(
            isEnabled = false,
            isIconShown = true
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
    fun checkProgrammaticallyEnabledWithIconState() {
        checkProgrammatically(
            isIconShown = true
        )
    }

    @Test
    fun checkProgrammaticallyDisabledWithIconState() {
        checkProgrammatically(
            isEnabled = false,
            isIconShown = true
        )
    }
    // endregion
}