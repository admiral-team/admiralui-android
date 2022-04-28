package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewStaticNotificationBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.notifications.fixed.StaticNotification
import com.admiral.uikit.components.notifications.fixed.StaticNotificationStyle
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class StaticNotificationTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(context)
    private val binding = TestViewStaticNotificationBinding.inflate(layoutInflater)

    private fun StaticNotification.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkByInflation(
        isEnabled: Boolean = true,
        style: StaticNotificationStyle = StaticNotificationStyle.Info,
        isDefault: Boolean = false
    ) {
        with(binding.root) {
            this.isEnabled = isEnabled
            this.notificationStyle = style
            this.notificationText = context.getString(R.string.informers_example_info)
            this.linkText = context.getString(R.string.informers_example_link)
            this.icon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_check_solid)
            this.isBackgroundColorDefault = isDefault

            check()
        }
    }

    private fun checkProgrammatically(
        isEnabled: Boolean = true,
        style: StaticNotificationStyle = StaticNotificationStyle.Info,
        isDefault: Boolean = false
    ) {
        StaticNotification(wrappedContext).apply {
            this.isEnabled = isEnabled
            this.notificationStyle = style
            this.notificationText = context.getString(R.string.informers_example_info)
            this.linkText = context.getString(R.string.informers_example_link)
            this.icon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_check_solid)
            this.isBackgroundColorDefault = isDefault
            check()
        }
    }

    // region check by inflation
    @Test
    fun checkByInflationEnabledAttentionState() {
        checkByInflation(style = StaticNotificationStyle.Attention)
    }

    @Test
    fun checkByInflationEnabledSuccessState() {
        checkByInflation(style = StaticNotificationStyle.Success)
    }

    @Test
    fun checkByInflationEnabledErrorState() {
        checkByInflation(style = StaticNotificationStyle.Error)
    }

    @Test
    fun checkByInflationEnabledInfoState() {
        checkByInflation(style = StaticNotificationStyle.Info)
    }

    @Test
    fun checkByInflationDisabledAttentionState() {
        checkByInflation(isEnabled = false, style = StaticNotificationStyle.Attention)
    }

    @Test
    fun checkByInflationDisabledSuccessState() {
        checkByInflation(isEnabled = false, style = StaticNotificationStyle.Success)
    }

    @Test
    fun checkByInflationDisabledErrorState() {
        checkByInflation(isEnabled = false, style = StaticNotificationStyle.Error)
    }

    @Test
    fun checkByInflationDisabledInfoState() {
        checkByInflation(isEnabled = false, style = StaticNotificationStyle.Info)
    }

    @Test
    fun checkByInflationDefaultState() {
        checkByInflation(isDefault = true)
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyEnabledAttentionState() {
        checkProgrammatically(style = StaticNotificationStyle.Attention)
    }

    @Test
    fun checkProgrammaticallyEnabledSuccessState() {
        checkProgrammatically(style = StaticNotificationStyle.Success)
    }

    @Test
    fun checkProgrammaticallyEnabledErrorState() {
        checkProgrammatically(style = StaticNotificationStyle.Error)
    }

    @Test
    fun checkProgrammaticallyEnabledInfoState() {
        checkProgrammatically(style = StaticNotificationStyle.Info)
    }

    @Test
    fun checkProgrammaticallyDisabledAttentionState() {
        checkProgrammatically(isEnabled = false, style = StaticNotificationStyle.Attention)
    }

    @Test
    fun checkProgrammaticallyDisabledSuccessState() {
        checkProgrammatically(isEnabled = false, style = StaticNotificationStyle.Success)
    }

    @Test
    fun checkProgrammaticallyDisabledErrorState() {
        checkProgrammatically(isEnabled = false, style = StaticNotificationStyle.Error)
    }

    @Test
    fun checkProgrammaticallyDisabledInfoState() {
        checkProgrammatically(isEnabled = false, style = StaticNotificationStyle.Info)
    }

    @Test
    fun checkProgrammaticallyDefaultState() {
        checkProgrammatically(isDefault = true)
    }
    // endregion
}