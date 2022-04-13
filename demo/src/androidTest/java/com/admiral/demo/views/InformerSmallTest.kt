package com.admiral.demo.views

import com.karumi.shot.ScreenshotTest
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.ext.measureUnspecified
import com.admiral.uikit.components.informer.InformerSmall
import org.junit.Test


class InformerSmallTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext


    private fun InformerSmall.check() {
        measureUnspecified()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkInformerSmallProgrammatically(isEnabled: Boolean) {
        val informerSmall = InformerSmall(context).apply {
            this.isEnabled = isEnabled
        }
        informerSmall.check()
    }

    // region check programmatically
    @Test
    fun checkProgrammaticallyInformerSmallEnabledState() {
        checkInformerSmallProgrammatically(isEnabled = true)
    }

    @Test
    fun checkProgrammaticallyInformerSmallDisabledState() {
        checkInformerSmallProgrammatically(isEnabled = false)
    }
    // endregion
}