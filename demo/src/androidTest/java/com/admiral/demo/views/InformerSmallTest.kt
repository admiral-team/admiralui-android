package com.admiral.demo.views

import android.view.LayoutInflater
import com.karumi.shot.ScreenshotTest
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.databinding.TestViewButtonGooglePlayBinding
import com.admiral.demo.databinding.TestViewInformerSmallBinding
import com.admiral.demo.ext.measureUnspecified
import com.admiral.uikit.components.informer.InformerSmall
import com.admiral.uikit.components.informer.InformerStyle
import org.junit.Test

class InformerSmallTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val layoutInflater = LayoutInflater.from(context)
    private val binding = TestViewInformerSmallBinding.inflate(layoutInflater)

    private fun InformerSmall.check() {
        measureUnspecified()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkInformerSmallProgrammatically(isEnabled: Boolean, style: InformerStyle) {
        val informerSmall = InformerSmall(context).apply {
            this.isEnabled = isEnabled
            this.informerStyle = style
            this.info = "Text informer"
            this.pointerBias = 0.9f
        }
        informerSmall.check()
    }

    // region check programmatically
    @Test
    fun checkProgrammaticallyInformerSmallEnabledAttentionState() {
        checkInformerSmallProgrammatically(isEnabled = true, style = InformerStyle.Attention)
    }

    @Test
    fun checkProgrammaticallyInformerSmallEnabledSuccessState() {
        checkInformerSmallProgrammatically(isEnabled = true, style = InformerStyle.Success)
    }

    @Test
    fun checkProgrammaticallyInformerSmallEnabledErrorState() {
        checkInformerSmallProgrammatically(isEnabled = true, style = InformerStyle.Error)
    }

    @Test
    fun checkProgrammaticallyInformerSmallEnabledInfoState() {
        checkInformerSmallProgrammatically(isEnabled = true, style = InformerStyle.Info)
    }

    @Test
    fun checkProgrammaticallyInformerSmallDisabledAttentionState() {
        checkInformerSmallProgrammatically(isEnabled = false, style = InformerStyle.Attention)
    }

    @Test
    fun checkProgrammaticallyInformerSmallDisabledSuccessState() {
        checkInformerSmallProgrammatically(isEnabled = false, style = InformerStyle.Success)
    }

    @Test
    fun checkProgrammaticallyInformerSmallDisabledErrorState() {
        checkInformerSmallProgrammatically(isEnabled = false, style = InformerStyle.Error)
    }

    @Test
    fun checkProgrammaticallyInformerSmallDisabledInfoState() {
        checkInformerSmallProgrammatically(isEnabled = false, style = InformerStyle.Info)
    }
    // endregion
}