package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.test.platform.app.InstrumentationRegistry
import androidx.viewbinding.ViewBinding
import com.karumi.shot.ScreenshotTest
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewRadioButtonErrorWithTextBinding
import com.admiral.demo.databinding.TestViewRadioButtonErrorWithoutTextBinding
import com.admiral.demo.databinding.TestViewRadioButtonWithTextBinding
import com.admiral.demo.databinding.TestViewRadioButtonWithoutTextBinding
import com.admiral.demo.ext.measureUnspecified
import com.admiral.uikit.components.radiobutton.RadioButton
import org.junit.Test

class RadioButtonTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val layoutInflater = LayoutInflater.from(context)
    private val radioButtonWithTextBinding =
        TestViewRadioButtonWithTextBinding.inflate(layoutInflater)
    private val radioButtonWithoutTextBinding =
        TestViewRadioButtonWithoutTextBinding.inflate(layoutInflater)
    private val radioButtonErrorWithTextBinding =
        TestViewRadioButtonErrorWithTextBinding.inflate(layoutInflater)
    private val radioButtonErrorWithoutTextBinding =
        TestViewRadioButtonErrorWithoutTextBinding.inflate(layoutInflater)

    private fun RadioButton.check() {
        measureUnspecified()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun RadioButton.setIsChecked(isChecked: Boolean) {
        runOnUi {
            // We do it on UI thread otherwise
            // `AndroidRuntimeException: Animators may only be run on Looper threads` will be thrown.
            // Also, it's necessary to use `runOnUi` otherwise the checkbox won't be checked on screenshots.
            this.isChecked = isChecked
        }
    }

    private fun checkRadioButtonByInflation(
        viewBinding: ViewBinding,
        isEnabled: Boolean,
        isChecked: Boolean
    ) {
        with(viewBinding.root as RadioButton) {
            this.isEnabled = isEnabled
            setIsChecked(isChecked)
            check()
        }
    }

    private fun checkRadioButtonProgrammatically(
        isEnabled: Boolean,
        isChecked: Boolean,
        isError: Boolean,
        @StringRes textResId: Int?
    ) {
        val radioButton = RadioButton(context).apply {
            textResId?.let { this.text = context.getString(it) }
            this.isEnabled = isEnabled
            this.isError = isError
            setIsChecked(isChecked)
        }

        radioButton.check()
    }

    // region radioButtonWithText
    // region check by inflation
    @Test
    fun checkByInflationRadioButtonWithTextEnabledCheckedState() {
        checkRadioButtonByInflation(
            viewBinding = radioButtonWithTextBinding,
            isEnabled = true,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationRadioButtonWithTextDisabledCheckedState() {
        checkRadioButtonByInflation(
            viewBinding = radioButtonWithTextBinding,
            isEnabled = false,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationRadioButtonWithTextEnabledUncheckedState() {
        checkRadioButtonByInflation(
            viewBinding = radioButtonWithTextBinding,
            isEnabled = true,
            isChecked = false
        )
    }

    @Test
    fun checkByInflationRadioButtonWithTextDisabledUncheckedState() {
        checkRadioButtonByInflation(
            viewBinding = radioButtonWithTextBinding,
            isEnabled = false,
            isChecked = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyRadioButtonWithTextEnabledCheckedState() {
        checkRadioButtonProgrammatically(
            isEnabled = true,
            isChecked = true,
            isError = false,
            textResId = R.string.radio_buttons_text
        )
    }

    @Test
    fun checkProgrammaticallyRadioButtonWithTextDisabledCheckedState() {
        checkRadioButtonProgrammatically(
            isEnabled = false,
            isChecked = true,
            isError = false,
            textResId = R.string.radio_buttons_text
        )
    }

    @Test
    fun checkProgrammaticallyRadioButtonWithTextEnabledUncheckedState() {
        checkRadioButtonProgrammatically(
            isEnabled = true,
            isChecked = false,
            isError = false,
            textResId = R.string.radio_buttons_text
        )
    }

    @Test
    fun checkProgrammaticallyRadioButtonWithTextDisabledUncheckedState() {
        checkRadioButtonProgrammatically(
            isEnabled = false,
            isChecked = false,
            isError = false,
            textResId = R.string.radio_buttons_text
        )
    }
    // endregion
    // endregion

    // region radioButtonWithoutText
    // region check by inflation
    @Test
    fun checkByInflationRadioButtonWithoutTextEnabledCheckedState() {
        checkRadioButtonByInflation(
            viewBinding = radioButtonWithoutTextBinding,
            isEnabled = true,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationRadioButtonWithoutTextDisabledCheckedState() {
        checkRadioButtonByInflation(
            viewBinding = radioButtonWithoutTextBinding,
            isEnabled = false,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationRadioButtonWithoutTextEnabledUncheckedState() {
        checkRadioButtonByInflation(
            viewBinding = radioButtonWithoutTextBinding,
            isEnabled = true,
            isChecked = false
        )
    }

    @Test
    fun checkByInflationRadioButtonWithoutTextDisabledUncheckedState() {
        checkRadioButtonByInflation(
            viewBinding = radioButtonWithoutTextBinding,
            isEnabled = false,
            isChecked = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyRadioButtonWithoutTextEnabledCheckedState() {
        checkRadioButtonProgrammatically(
            isEnabled = true,
            isChecked = true,
            isError = false,
            textResId = null
        )
    }

    @Test
    fun checkProgrammaticallyRadioButtonWithoutTextDisabledCheckedState() {
        checkRadioButtonProgrammatically(
            isEnabled = false,
            isChecked = true,
            isError = false,
            textResId = null
        )
    }

    @Test
    fun checkProgrammaticallyRadioButtonWithoutTextEnabledUncheckedState() {
        checkRadioButtonProgrammatically(
            isEnabled = true,
            isChecked = false,
            isError = false,
            textResId = null
        )
    }

    @Test
    fun checkProgrammaticallyRadioButtonWithoutTextDisabledUncheckedState() {
        checkRadioButtonProgrammatically(
            isEnabled = false,
            isChecked = false,
            isError = false,
            textResId = null
        )
    }
    // endregion
    // endregion

    // region radioButtonErrorWithTextBinding
    // region check by inflation
    @Test
    fun checkByInflationRadioButtonErrorWithTextEnabledCheckedState() {
        checkRadioButtonByInflation(
            viewBinding = radioButtonErrorWithTextBinding,
            isEnabled = true,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationRadioButtonErrorWithTextDisabledCheckedState() {
        checkRadioButtonByInflation(
            viewBinding = radioButtonErrorWithTextBinding,
            isEnabled = false,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationRadioButtonErrorWithTextEnabledUncheckedState() {
        checkRadioButtonByInflation(
            viewBinding = radioButtonErrorWithTextBinding,
            isEnabled = true,
            isChecked = false
        )
    }

    @Test
    fun checkByInflationRadioButtonErrorWithTextDisabledUncheckedState() {
        checkRadioButtonByInflation(
            viewBinding = radioButtonErrorWithTextBinding,
            isEnabled = false,
            isChecked = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyRadioButtonErrorWithTextEnabledCheckedState() {
        checkRadioButtonProgrammatically(
            isEnabled = true,
            isChecked = true,
            isError = true,
            textResId = R.string.radio_buttons_text
        )
    }

    @Test
    fun checkProgrammaticallyRadioButtonErrorWithTextDisabledCheckedState() {
        checkRadioButtonProgrammatically(
            isEnabled = false,
            isChecked = true,
            isError = true,
            textResId = R.string.radio_buttons_text
        )
    }

    @Test
    fun checkProgrammaticallyRadioButtonErrorWithTextEnabledUncheckedState() {
        checkRadioButtonProgrammatically(
            isEnabled = true,
            isChecked = false,
            isError = true,
            textResId = R.string.radio_buttons_text
        )
    }

    @Test
    fun checkProgrammaticallyRadioButtonErrorWithTextDisabledUncheckedState() {
        checkRadioButtonProgrammatically(
            isEnabled = false,
            isChecked = false,
            isError = true,
            textResId = R.string.radio_buttons_text
        )
    }
    // endregion
    // endregion

    // region radioButtonErrorWithoutTextBinding
    // region check by inflation
    @Test
    fun checkByInflationRadioButtonErrorWithoutTextEnabledCheckedState() {
        checkRadioButtonByInflation(
            viewBinding = radioButtonErrorWithoutTextBinding,
            isEnabled = true,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationRadioButtonErrorWithoutTextDisabledCheckedState() {
        checkRadioButtonByInflation(
            viewBinding = radioButtonErrorWithoutTextBinding,
            isEnabled = false,
            isChecked = true
        )
    }

    @Test
    fun checkByInflationRadioButtonErrorWithoutTextEnabledUncheckedState() {
        checkRadioButtonByInflation(
            viewBinding = radioButtonErrorWithoutTextBinding,
            isEnabled = true,
            isChecked = false
        )
    }

    @Test
    fun checkByInflationRadioButtonErrorWithoutTextDisabledUncheckedState() {
        checkRadioButtonByInflation(
            viewBinding = radioButtonErrorWithoutTextBinding,
            isEnabled = false,
            isChecked = false
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyRadioButtonErrorWithoutTextEnabledCheckedState() {
        checkRadioButtonProgrammatically(
            isEnabled = true,
            isChecked = true,
            isError = true,
            textResId = null
        )
    }

    @Test
    fun checkProgrammaticallyRadioButtonErrorWithoutTextDisabledCheckedState() {
        checkRadioButtonProgrammatically(
            isEnabled = false,
            isChecked = true,
            isError = true,
            textResId = null
        )
    }

    @Test
    fun checkProgrammaticallyRadioButtonErrorWithoutTextEnabledUncheckedState() {
        checkRadioButtonProgrammatically(
            isEnabled = true,
            isChecked = false,
            isError = true,
            textResId = null
        )
    }

    @Test
    fun checkProgrammaticallyRadioButtonErrorWithoutTextDisabledUncheckedState() {
        checkRadioButtonProgrammatically(
            isEnabled = false,
            isChecked = false,
            isError = true,
            textResId = null
        )
    }
    // endregion
    // endregion
}