package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewInformerBigBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.informer.InformerBig
import com.admiral.uikit.components.informer.InformerStyle
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class InformerBigTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(context)
    private val binding = TestViewInformerBigBinding.inflate(layoutInflater)

    private fun InformerBig.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkByInflation(isEnabled: Boolean, style: InformerStyle) {
        with(binding.root) {
            this.isEnabled = isEnabled
            this.informerStyle = style
            this.info = context.getString(com.admiral.demo.R.string.informers_example_info)
            this.headline = context.getString(com.admiral.demo.R.string.informers_example_headline)
            this.link = context.getString(com.admiral.demo.R.string.informers_example_link)
            this.pointerBias = 0.9f

            check()
        }
    }

    private fun checkProgrammatically(
        isEnabled: Boolean, style: InformerStyle
    ) {
        InformerBig(wrappedContext).apply {
            this.isEnabled = isEnabled
            this.informerStyle = style
            this.info = context.getString(R.string.informers_example_info)
            this.headline = context.getString(R.string.informers_example_headline)
            this.link = context.getString(R.string.informers_example_link)
            this.pointerBias = 0.9f
            check()
        }
    }

    // region check by inflation
    @Test
    fun checkByInflationEnabledAttentionState() {
        checkByInflation(isEnabled = true, style = InformerStyle.Attention)
    }

    @Test
    fun checkByInflationEnabledSuccessState() {
        checkByInflation(isEnabled = true, style = InformerStyle.Success)
    }

    @Test
    fun checkByInflationEnabledErrorState() {
        checkByInflation(isEnabled = true, style = InformerStyle.Error)
    }

    @Test
    fun checkByInflationEnabledInfoState() {
        checkByInflation(isEnabled = true, style = InformerStyle.Info)
    }

    @Test
    fun checkByInflationDisabledAttentionState() {
        checkByInflation(isEnabled = false, style = InformerStyle.Attention)
    }

    @Test
    fun checkByInflationDisabledSuccessState() {
        checkByInflation(isEnabled = false, style = InformerStyle.Success)
    }

    @Test
    fun checkByInflationDisabledErrorState() {
        checkByInflation(isEnabled = false, style = InformerStyle.Error)
    }

    @Test
    fun checkByInflationDisabledInfoState() {
        checkByInflation(isEnabled = false, style = InformerStyle.Info)
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyEnabledAttentionState() {
        checkProgrammatically(isEnabled = true, style = InformerStyle.Attention)
    }

    @Test
    fun checkProgrammaticallyEnabledSuccessState() {
        checkProgrammatically(isEnabled = true, style = InformerStyle.Success)
    }

    @Test
    fun checkProgrammaticallyEnabledErrorState() {
        checkProgrammatically(isEnabled = true, style = InformerStyle.Error)
    }

    @Test
    fun checkProgrammaticallyEnabledInfoState() {
        checkProgrammatically(isEnabled = true, style = InformerStyle.Info)
    }

    @Test
    fun checkProgrammaticallyDisabledAttentionState() {
        checkProgrammatically(isEnabled = false, style = InformerStyle.Attention)
    }

    @Test
    fun checkProgrammaticallyDisabledSuccessState() {
        checkProgrammatically(isEnabled = false, style = InformerStyle.Success)
    }

    @Test
    fun checkProgrammaticallyDisabledErrorState() {
        checkProgrammatically(isEnabled = false, style = InformerStyle.Error)
    }

    @Test
    fun checkProgrammaticallyDisabledInfoState() {
        checkProgrammatically(isEnabled = false, style = InformerStyle.Info)
    }
    // endregion
}