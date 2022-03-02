package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.test.platform.app.InstrumentationRegistry
import com.karumi.shot.ScreenshotTest
import com.admiral.demo.databinding.TestViewChipsBinding
import com.admiral.demo.ext.measureUnspecified
import com.admiral.uikit.components.chip.Chip
import com.admiral.uikit.components.chip.ChipGroup
import com.admiral.demo.R
import org.junit.Test

class ChipsTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext =
        ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(context)
    private val chipsBinding = TestViewChipsBinding.inflate(layoutInflater).also {
        it.root.children.forEach { view ->
            // NB: I don't know why but screenshots lose font fontFamily
            // even though they exist in running app
            (view as Chip).setTextAppearance(R.style.AdmiralTextAppearance_Body1)
        }
    }
    private val chips = listOf(
        Chip(wrappedContext).apply {
            text = context.getString(R.string.tags_chips_chip)
            chipIcon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_car_solid)
            isCloseIconVisible = true
        },
        Chip(wrappedContext).apply {
            text = context.getString(R.string.tags_chips_chip)
            isCloseIconVisible = true
        },
        Chip(wrappedContext).apply {
            text = context.getString(R.string.tags_chips_icons)
            chipIcon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_car_solid)
        },
        Chip(wrappedContext).apply {
            chipIcon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_car_solid)
        },
        Chip(wrappedContext).apply {
            text = context.getString(R.string.tags_chips_emoji)
        }
    ).map {
        // NB: I don't know why but screenshots lose font fontFamily
        // even though they exist in running app
        it.setTextAppearance(R.style.AdmiralTextAppearance_Body1)
        it
    }

    // region check by inflation
    @Test
    fun checkByInflationPrimaryChipsEnabledState() {
        with(chipsBinding.root) {
            measureUnspecified()
            compareScreenshot(
                view = this,
                widthInPx = measuredWidth,
                heightInPx = measuredHeight
            )
        }
    }

    @Test
    fun checkByInflationPrimaryChipsDisabledState() {
        with(chipsBinding.root) {
            isEnabled = false
            measureUnspecified()
            compareScreenshot(
                view = this,
                widthInPx = measuredWidth,
                heightInPx = measuredHeight
            )
        }
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyPrimaryChipsEnabledState() {
        val chipGroup = ChipGroup(wrappedContext).apply {
            chips.forEach { addView(it) }
        }

        with(chipGroup) {
            measureUnspecified()
            compareScreenshot(
                view = this,
                widthInPx = measuredWidth,
                heightInPx = measuredHeight
            )
        }
    }

    @Test
    fun checkProgrammaticallyPrimaryChipsDisabledState() {
        val chipGroup = ChipGroup(wrappedContext).apply {
            chips.forEach { addView(it) }
            isEnabled = false
        }

        with(chipGroup) {
            measureUnspecified()
            compareScreenshot(
                view = this,
                widthInPx = measuredWidth,
                heightInPx = measuredHeight
            )
        }
    }
    // endregion
}