package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import androidx.viewbinding.ViewBinding
import com.karumi.shot.ScreenshotTest
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewCheckBoxErrorWithTextBinding
import com.admiral.demo.databinding.TestViewCheckBoxErrorWithoutTextBinding
import com.admiral.demo.databinding.TestViewCheckBoxWithTextBinding
import com.admiral.demo.databinding.TestViewCheckBoxWithoutTextBinding
import com.admiral.demo.ext.measureUnspecified
import com.admiral.uikit.components.checkbox.CheckBox
import org.junit.Test

class CheckBoxTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(wrappedContext)
    private val checkBoxWithTextBinding =
        TestViewCheckBoxWithTextBinding.inflate(layoutInflater)
    private val checkBoxWithoutTextBinding =
        TestViewCheckBoxWithoutTextBinding.inflate(layoutInflater)
    private val checkBoxErrorWithTextBinding =
        TestViewCheckBoxErrorWithTextBinding.inflate(layoutInflater)
    private val checkBoxErrorWithoutTextBinding =
        TestViewCheckBoxErrorWithoutTextBinding.inflate(layoutInflater)

    private fun CheckBox.check() {
        measureUnspecified()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun CheckBox.setIsChecked(isChecked: Boolean) {
        runOnUi {
            // We do it on UI thread otherwise
            // `AndroidRuntimeException: Animators may only be run on Looper threads` will be thrown.
            // Also, it's necessary to use `runOnUi` otherwise the checkbox won't be checked on screenshots.
            this.isChecked = isChecked
        }
    }

    private fun checkCheckBoxByInflation(
        viewBinding: ViewBinding,
        isEnabled: Boolean,
        isChecked: Boolean
    ) {
        with(viewBinding.root as CheckBox) {
            this.isEnabled = isEnabled
            setIsChecked(isChecked)
            check()
        }
    }

    private fun checkCheckBoxProgrammatically(
        isEnabled: Boolean,
        isChecked: Boolean,
        isError: Boolean,
        @StringRes textResId: Int?
    ) {
        val checkBox = CheckBox(wrappedContext).apply {
            textResId?.let { this.text = context.getString(it) }
            this.isEnabled = isEnabled
            this.error = isError
            setIsChecked(isChecked)
        }

        checkBox.check()
    }

    // region checkBoxWithText
    // region check by inflation
    @Test
    fun checkByInflationCheckBoxWithTextEnabledCheckedState() {
        checkCheckBoxByInflation(
            viewBinding = checkBoxWithTextBinding,
            isEnabled = true,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationCheckBoxWithTextDisabledCheckedState() {
        checkCheckBoxByInflation(
            viewBinding = checkBoxWithTextBinding,
            isEnabled = false,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationCheckBoxWithTextEnabledUncheckedState() {
        checkCheckBoxByInflation(
            viewBinding = checkBoxWithTextBinding,
            isEnabled = true,
            isChecked = false
        )
    }

    @Test
    fun checkByInflationCheckBoxWithTextDisabledUncheckedState() {
        checkCheckBoxByInflation(
            viewBinding = checkBoxWithTextBinding,
            isEnabled = false,
            isChecked = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyCheckBoxWithTextEnabledCheckedState() {
        checkCheckBoxProgrammatically(
            isEnabled = true,
            isChecked = true,
            isError = false,
            textResId = R.string.check_box_text
        )
    }

    @Test
    fun checkProgrammaticallyCheckBoxWithTextDisabledCheckedState() {
        checkCheckBoxProgrammatically(
            isEnabled = false,
            isChecked = true,
            isError = false,
            textResId = R.string.check_box_text
        )
    }

    @Test
    fun checkProgrammaticallyCheckBoxWithTextEnabledUncheckedState() {
        checkCheckBoxProgrammatically(
            isEnabled = true,
            isChecked = false,
            isError = false,
            textResId = R.string.check_box_text
        )
    }

    @Test
    fun checkProgrammaticallyCheckBoxWithTextDisabledUncheckedState() {
        checkCheckBoxProgrammatically(
            isEnabled = false,
            isChecked = false,
            isError = false,
            textResId = R.string.check_box_text
        )
    }
    // endregion
    // endregion

    // region checkBoxWithoutText
    // region check by inflation
    @Test
    fun checkByInflationCheckBoxWithoutTextEnabledCheckedState() {
        checkCheckBoxByInflation(
            viewBinding = checkBoxWithoutTextBinding,
            isEnabled = true,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationCheckBoxWithoutTextDisabledCheckedState() {
        checkCheckBoxByInflation(
            viewBinding = checkBoxWithoutTextBinding,
            isEnabled = false,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationCheckBoxWithoutTextEnabledUncheckedState() {
        checkCheckBoxByInflation(
            viewBinding = checkBoxWithoutTextBinding,
            isEnabled = true,
            isChecked = false
        )
    }

    @Test
    fun checkByInflationCheckBoxWithoutTextDisabledUncheckedState() {
        checkCheckBoxByInflation(
            viewBinding = checkBoxWithoutTextBinding,
            isEnabled = false,
            isChecked = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyCheckBoxWithoutTextEnabledCheckedState() {
        checkCheckBoxProgrammatically(
            isEnabled = true,
            isChecked = true,
            isError = false,
            textResId = null
        )
    }

    @Test
    fun checkProgrammaticallyCheckBoxWithoutTextDisabledCheckedState() {
        checkCheckBoxProgrammatically(
            isEnabled = false,
            isChecked = true,
            isError = false,
            textResId = null
        )
    }

    @Test
    fun checkProgrammaticallyCheckBoxWithoutTextEnabledUncheckedState() {
        checkCheckBoxProgrammatically(
            isEnabled = true,
            isChecked = false,
            isError = false,
            textResId = null
        )
    }

    @Test
    fun checkProgrammaticallyCheckBoxWithoutTextDisabledUncheckedState() {
        checkCheckBoxProgrammatically(
            isEnabled = false,
            isChecked = false,
            isError = false,
            textResId = null
        )
    }
    // endregion
    // endregion

    // region checkBoxErrorWithTextBinding
    // region check by inflation
    @Test
    fun checkByInflationCheckBoxErrorWithTextEnabledCheckedState() {
        checkCheckBoxByInflation(
            viewBinding = checkBoxErrorWithTextBinding,
            isEnabled = true,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationCheckBoxErrorWithTextDisabledCheckedState() {
        checkCheckBoxByInflation(
            viewBinding = checkBoxErrorWithTextBinding,
            isEnabled = false,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationCheckBoxErrorWithTextEnabledUncheckedState() {
        checkCheckBoxByInflation(
            viewBinding = checkBoxErrorWithTextBinding,
            isEnabled = true,
            isChecked = false
        )
    }

    @Test
    fun checkByInflationCheckBoxErrorWithTextDisabledUncheckedState() {
        checkCheckBoxByInflation(
            viewBinding = checkBoxErrorWithTextBinding,
            isEnabled = false,
            isChecked = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyCheckBoxErrorWithTextEnabledCheckedState() {
        checkCheckBoxProgrammatically(
            isEnabled = true,
            isChecked = true,
            isError = true,
            textResId = R.string.check_box_text
        )
    }

    @Test
    fun checkProgrammaticallyCheckBoxErrorWithTextDisabledCheckedState() {
        checkCheckBoxProgrammatically(
            isEnabled = false,
            isChecked = true,
            isError = true,
            textResId = R.string.check_box_text
        )
    }

    @Test
    fun checkProgrammaticallyCheckBoxErrorWithTextEnabledUncheckedState() {
        checkCheckBoxProgrammatically(
            isEnabled = true,
            isChecked = false,
            isError = true,
            textResId = R.string.check_box_text
        )
    }

    @Test
    fun checkProgrammaticallyCheckBoxErrorWithTextDisabledUncheckedState() {
        checkCheckBoxProgrammatically(
            isEnabled = false,
            isChecked = false,
            isError = true,
            textResId = R.string.check_box_text
        )
    }
    // endregion
    // endregion

    // region checkBoxErrorWithoutTextBinding
    // region check by inflation
    @Test
    fun checkByInflationCheckBoxErrorWithoutTextEnabledCheckedState() {
        checkCheckBoxByInflation(
            viewBinding = checkBoxErrorWithoutTextBinding,
            isEnabled = true,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationCheckBoxErrorWithoutTextDisabledCheckedState() {
        checkCheckBoxByInflation(
            viewBinding = checkBoxErrorWithoutTextBinding,
            isEnabled = false,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationCheckBoxErrorWithoutTextEnabledUncheckedState() {
        checkCheckBoxByInflation(
            viewBinding = checkBoxErrorWithoutTextBinding,
            isEnabled = true,
            isChecked = false
        )
    }

    @Test
    fun checkByInflationCheckBoxErrorWithoutTextDisabledUncheckedState() {
        checkCheckBoxByInflation(
            viewBinding = checkBoxErrorWithoutTextBinding,
            isEnabled = false,
            isChecked = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyCheckBoxErrorWithoutTextEnabledCheckedState() {
        checkCheckBoxProgrammatically(
            isEnabled = true,
            isChecked = true,
            isError = true,
            textResId = null
        )
    }

    @Test
    fun checkProgrammaticallyCheckBoxErrorWithoutTextDisabledCheckedState() {
        checkCheckBoxProgrammatically(
            isEnabled = false,
            isChecked = true,
            isError = true,
            textResId = null
        )
    }

    @Test
    fun checkProgrammaticallyCheckBoxErrorWithoutTextEnabledUncheckedState() {
        checkCheckBoxProgrammatically(
            isEnabled = true,
            isChecked = false,
            isError = true,
            textResId = null
        )
    }

    @Test
    fun checkProgrammaticallyCheckBoxErrorWithoutTextDisabledUncheckedState() {
        checkCheckBoxProgrammatically(
            isEnabled = false,
            isChecked = false,
            isError = true,
            textResId = null
        )
    }
    // endregion
    // endregion
}