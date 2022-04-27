package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewTextFieldStandardBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.textfield.TextField
import com.admiral.uikit.components.textfield.TextFieldStyle
import com.admiral.uikit.components.textfield.TextGravity
import com.karumi.shot.ScreenshotTest
import org.junit.Before
import org.junit.Test

class TextFieldSmsTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(wrappedContext)
    private lateinit var textFieldSmsBinding: TestViewTextFieldStandardBinding

    @Before
    fun setUp() {
        // NB: we do initialization in `runOnUi` block since inflation with admiralText attribute causes
        // animation and we don't want to have exception `AndroidRuntimeException: Animators may only be
        // run on Looper threads`
        runOnUi {
            textFieldSmsBinding = TestViewTextFieldStandardBinding.inflate(layoutInflater)
        }
    }

    private fun TextField.disableInputLayoutAnimation() {
        // It's necessary to set `isHintAnimationEnabled` to false
        // otherwise screenshots will be inconsistent
        inputLayout.isHintAnimationEnabled = false
    }

    private fun TextField.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    // NB: here we use textResId to set up text programmatically since
    // we don't have the ability to disable the inputLayout animation before the inflation
    private fun checkTextFieldByInflation(
        isEnabled: Boolean,
        isReadonly: Boolean? = null,
        isError: Boolean? = null,
        @StringRes textResId: Int? = null
    ) {
        with(textFieldSmsBinding.root) {
            disableInputLayoutAnimation()

            this.isEnabled = isEnabled
            isReadonly?.let { this.isEditEnabled = isReadonly.not() }
            isError?.let { this.isError = isError }
            textResId?.let { this.inputText = context.getString(it) }
            textFieldStyle = TextFieldStyle.Clipped
            inputTextGravity = TextGravity.Center
            check()
        }
    }

    private fun checkTextFieldProgrammatically(
        isEnabled: Boolean,
        isReadonly: Boolean? = null,
        isError: Boolean? = null,
        @StringRes textResId: Int? = null
    ) {
        val textField = TextField(wrappedContext).apply {
            disableInputLayoutAnimation()
            this.optionalText = context.getString(R.string.text_fields_optional_label)
            this.additionalText = context.getString(R.string.text_fields_example_slider_additional)
            this.isEnabled = isEnabled

            textFieldStyle = TextFieldStyle.Clipped
            inputTextGravity = TextGravity.Center
            textResId?.let { this.inputText = context.getString(it) }
            isReadonly?.let { this.isEditEnabled = isReadonly.not() }
            isError?.let { this.isError = isError }
        }

        textField.check()
    }

    // region TextFieldSmsWithText
    // region check by inflation
    @Test
    fun checkByInflationTextFieldSmsWithTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true,
            textResId = R.string.text_fields_sms_text
        )
    }

    @Test
    fun checkByInflationTextFieldSmsWithTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false,
            textResId = R.string.text_fields_sms_text
        )
    }

    @Test
    fun checkByInflationTextFieldSmsErrorWithTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true,
            isError = true,
            textResId = R.string.text_fields_sms_text
        )
    }

    @Test
    fun checkByInflationTextFieldSmsErrorWithTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false,
            isError = true,
            textResId = R.string.text_fields_sms_text
        )
    }

    @Test
    fun checkByInflationTextFieldSmsReadOnlyWithTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true,
            isReadonly = true,
            textResId = R.string.text_fields_sms_text
        )
    }

    @Test
    fun checkByInflationTextFieldSmsReadOnlyWithTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false,
            isReadonly = true,
            textResId = R.string.text_fields_sms_text
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyTextFieldSmsWithTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            textResId = R.string.text_fields_sms_text,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldSmsWithTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            textResId = R.string.text_fields_sms_text,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldSmsErrorWithTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            isError = true,
            textResId = R.string.text_fields_sms_text,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldSmsErrorWithTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            isError = true,
            textResId = R.string.text_fields_sms_text,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldSmsReadOnlyWithTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            isReadonly = true,
            textResId = R.string.text_fields_sms_text,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldSmsReadOnlyWithTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            isReadonly = true,
            textResId = R.string.text_fields_sms_text,
        )
    }
    // endregion
    // endregion

    // region TextFieldSmsWithoutText
    // region check by inflation
    @Test
    fun checkByInflationTextFieldSmsWithoutTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationTextFieldSmsWithoutTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false
        )
    }

    @Test
    fun checkByInflationTextFieldSmsErrorWithoutTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true,
            isError = true
        )
    }

    @Test
    fun checkByInflationTextFieldSmsErrorWithoutTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false,
            isError = true
        )
    }

    @Test
    fun checkByInflationTextFieldSmsReadOnlyWithoutTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true,
            isReadonly = true
        )
    }

    @Test
    fun checkByInflationTextFieldSmsReadOnlyWithoutTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false,
            isReadonly = true
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyTextFieldSmsWithoutTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldSmsWithoutTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldSmsErrorWithoutTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            isError = true,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldSmsErrorWithoutTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            isError = true,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldSmsReadOnlyWithoutTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            isReadonly = true,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldSmsReadOnlyWithoutTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            isReadonly = true,
        )
    }
    // endregion
    // endregion
}