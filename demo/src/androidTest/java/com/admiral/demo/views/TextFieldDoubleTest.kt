package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewTextFieldDoubleBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.textfield.DoubleTextField
import com.karumi.shot.ScreenshotTest
import org.junit.Before
import org.junit.Test

class TextFieldDoubleTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(wrappedContext)
    private lateinit var textFieldDoubleBinding: TestViewTextFieldDoubleBinding

    @Before
    fun setUp() {
        // NB: we do initialization in `runOnUi` block since inflation with admiralText attribute causes
        // animation and we don't want to have exception `AndroidRuntimeException: Animators may only be
        // run on Looper threads`
        runOnUi {
            textFieldDoubleBinding = TestViewTextFieldDoubleBinding.inflate(layoutInflater)
        }
    }

    private fun DoubleTextField.disableInputLayoutAnimation() {
        // It's necessary to set `isHintAnimationEnabled` to false
        // otherwise screenshots will be inconsistent
        leftTextField.inputLayout.isHintAnimationEnabled = false
        rightTextField.inputLayout.isHintAnimationEnabled = false
    }

    private fun DoubleTextField.check() {
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
        with(textFieldDoubleBinding.root) {
            disableInputLayoutAnimation()
            this.leftTextField.optionalText = context.getString(R.string.text_fields_optional_label)
            this.rightTextField.optionalText = context.getString(R.string.text_fields_optional_label)
            this.additionalText = context.getString(R.string.text_fields_example_slider_additional)
            this.isEnabled = isEnabled
            textResId?.let {
                this.leftTextField.inputText = context.getString(it)
                this.rightTextField.inputText = context.getString(it)
            }
            isReadonly?.let {
                leftTextField.isEditEnabled = isReadonly.not()
                rightTextField.isEditEnabled = isReadonly.not()
            }
            isError?.let { this.isError = isError }

            check()
        }
    }

    private fun checkTextFieldProgrammatically(
        isEnabled: Boolean = true,
        isReadonly: Boolean? = null,
        isError: Boolean? = null,
        isAdditionalTextSingle: Boolean = true,
        @StringRes textResId: Int? = null,
        @StringRes additionalText: Int = R.string.text_fields_example_slider_additional
    ) {
        val textField = DoubleTextField(wrappedContext).apply {
            disableInputLayoutAnimation()
            this.leftTextField.optionalText = context.getString(R.string.text_fields_optional_label)
            this.rightTextField.optionalText = context.getString(R.string.text_fields_optional_label)
            this.additionalText = context.getString(additionalText)
            this.isEnabled = isEnabled
            textResId?.let {
                this.leftTextField.inputText = context.getString(it)
                this.rightTextField.inputText = context.getString(it)
            }
            isReadonly?.let {
                leftTextField.isEditEnabled = isReadonly.not()
                rightTextField.isEditEnabled = isReadonly.not()
            }
            isError?.let { this.isError = isError }
            this.isAdditionalTextSingle = isAdditionalTextSingle
            this.leftTextField.errorText = "error text first"
            this.rightTextField.errorText = "error text second"
        }

        textField.check()
    }

    // region TextFieldDoubleWithText
    // region check by inflation
    @Test
    fun checkByInflationTextFieldDoubleWithTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true,
            textResId = R.string.text_fields_text
        )
    }

    @Test
    fun checkByInflationTextFieldDoubleWithTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false,
            textResId = R.string.text_fields_text
        )
    }

    @Test
    fun checkByInflationTextFieldDoubleErrorWithTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true,
            isError = true,
            textResId = R.string.text_fields_text
        )
    }

    @Test
    fun checkByInflationTextFieldDoubleErrorWithTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false,
            isError = true,
            textResId = R.string.text_fields_text
        )
    }

    @Test
    fun checkByInflationTextFieldDoubleReadOnlyWithTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true,
            isReadonly = true,
            textResId = R.string.text_fields_text
        )
    }

    @Test
    fun checkByInflationTextFieldDoubleReadOnlyWithTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false,
            isReadonly = true,
            textResId = R.string.text_fields_text
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyTextFieldDoubleWithTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            textResId = R.string.text_fields_text,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldDoubleWithTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            textResId = R.string.text_fields_text,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldDoubleErrorWithTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            isError = true,
            textResId = R.string.text_fields_text,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldDoubleSingleErrorWithTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            isError = true,
            textResId = R.string.text_fields_text,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldDoubleSingeErrorWithLargeTextEnabledState() {
        checkTextFieldProgrammatically(
            isError = true,
            textResId = R.string.text_fields_text,
            additionalText = R.string.cell_lorem_ipsum,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldDoubleBothErrorWithTextEnabledState() {
        checkTextFieldProgrammatically(
            isError = true,
            textResId = R.string.text_fields_text,
            isAdditionalTextSingle = false,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldDoubleReadOnlyWithTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            isReadonly = true,
            textResId = R.string.text_fields_text,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldDoubleReadOnlyWithTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            isReadonly = true,
            textResId = R.string.text_fields_text,
        )
    }
    // endregion
    // endregion

    // region TextFieldDoubleWithoutText
    // region check by inflation
    @Test
    fun checkByInflationTextFieldDoubleWithoutTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationTextFieldDoubleWithoutTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false
        )
    }

    @Test
    fun checkByInflationTextFieldDoubleErrorWithoutTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true,
            isError = true
        )
    }

    @Test
    fun checkByInflationTextFieldDoubleErrorWithoutTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false,
            isError = true
        )
    }

    @Test
    fun checkByInflationTextFieldDoubleReadOnlyWithoutTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true,
            isReadonly = true
        )
    }

    @Test
    fun checkByInflationTextFieldDoubleReadOnlyWithoutTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false,
            isReadonly = true
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyTextFieldDoubleWithoutTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldDoubleWithoutTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldDoubleErrorWithoutTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            isError = true,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldDoubleErrorWithoutTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            isError = true,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldDoubleReadOnlyWithoutTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            isReadonly = true,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldDoubleReadOnlyWithoutTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            isReadonly = true,
        )
    }
    // endregion
    // endregion
}