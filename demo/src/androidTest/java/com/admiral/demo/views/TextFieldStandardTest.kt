package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import androidx.viewbinding.ViewBinding
import com.karumi.shot.ScreenshotTest
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewTextFieldStandardBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.textfield.TextField
import org.junit.Before
import org.junit.Test

class TextFieldStandardTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(wrappedContext)
    private lateinit var textFieldStandardBinding: TestViewTextFieldStandardBinding

    @Before
    fun setUp() {
        // NB: we do initialization in `runOnUi` block since inflation with admiralText attribute causes
        // animation and we don't want to have exception `AndroidRuntimeException: Animators may only be
        // run on Looper threads`
        runOnUi {
            textFieldStandardBinding = TestViewTextFieldStandardBinding.inflate(layoutInflater)
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
        viewBinding: ViewBinding,
        isEnabled: Boolean,
        isReadonly: Boolean? = null,
        isError: Boolean? = null,
        @StringRes textResId: Int? = null
    ) {
        with(viewBinding.root as TextField) {
            disableInputLayoutAnimation()

            this.isEnabled = isEnabled
            isReadonly?.let { this.isEditEnabled = isReadonly.not() }
            isError?.let { this.isError = isError }
            textResId?.let { this.inputText = context.getString(it) }
            check()
        }
    }

    private fun checkTextFieldProgrammatically(
        isEnabled: Boolean,
        isReadonly: Boolean? = null,
        isError: Boolean? = null,
        @StringRes additionalTextResId: Int,
        @StringRes optionalTextResId: Int,
        @StringRes textResId: Int? = null
    ) {
        val textField = TextField(wrappedContext).apply {
            disableInputLayoutAnimation()
            this.optionalText = context.getString(optionalTextResId)
            this.additionalText = context.getString(additionalTextResId)
            this.isEnabled = isEnabled

            textResId?.let { this.inputText = context.getString(it) }
            isReadonly?.let { this.isEditEnabled = isReadonly.not() }
            isError?.let { this.isError = isError }
        }

        textField.check()
    }

    // region TextFieldStandardWithText
    // region check by inflation
    @Test
    fun checkByInflationTextFieldStandardWithTextEnabledState() {
        checkTextFieldByInflation(
            viewBinding = textFieldStandardBinding,
            isEnabled = true,
            textResId = R.string.text_fields_text
        )
    }

    @Test
    fun checkByInflationTextFieldStandardWithTextDisabledState() {
        checkTextFieldByInflation(
            viewBinding = textFieldStandardBinding,
            isEnabled = false,
            textResId = R.string.text_fields_text
        )
    }

    @Test
    fun checkByInflationTextFieldStandardErrorWithTextEnabledState() {
        checkTextFieldByInflation(
            viewBinding = textFieldStandardBinding,
            isEnabled = true,
            isError = true,
            textResId = R.string.text_fields_text
        )
    }

    @Test
    fun checkByInflationTextFieldStandardErrorWithTextDisabledState() {
        checkTextFieldByInflation(
            viewBinding = textFieldStandardBinding,
            isEnabled = false,
            isError = true,
            textResId = R.string.text_fields_text
        )
    }

    @Test
    fun checkByInflationTextFieldStandardReadOnlyWithTextEnabledState() {
        checkTextFieldByInflation(
            viewBinding = textFieldStandardBinding,
            isEnabled = true,
            isReadonly = true,
            textResId = R.string.text_fields_text
        )
    }

    @Test
    fun checkByInflationTextFieldStandardReadOnlyWithTextDisabledState() {
        checkTextFieldByInflation(
            viewBinding = textFieldStandardBinding,
            isEnabled = false,
            isReadonly = true,
            textResId = R.string.text_fields_text
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyTextFieldStandardWithTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            textResId = R.string.text_fields_text,
            additionalTextResId = R.string.text_fields_example_slider_additional,
            optionalTextResId = R.string.text_fields_optional_label
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldStandardWithTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            textResId = R.string.text_fields_text,
            additionalTextResId = R.string.text_fields_example_slider_additional,
            optionalTextResId = R.string.text_fields_optional_label
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldStandardErrorWithTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            isError = true,
            textResId = R.string.text_fields_text,
            additionalTextResId = R.string.text_fields_example_slider_additional,
            optionalTextResId = R.string.text_fields_optional_label
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldStandardErrorWithTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            isError = true,
            textResId = R.string.text_fields_text,
            additionalTextResId = R.string.text_fields_example_slider_additional,
            optionalTextResId = R.string.text_fields_optional_label
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldStandardReadOnlyWithTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            isReadonly = true,
            textResId = R.string.text_fields_text,
            additionalTextResId = R.string.text_fields_example_slider_additional,
            optionalTextResId = R.string.text_fields_optional_label
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldStandardReadOnlyWithTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            isReadonly = true,
            textResId = R.string.text_fields_text,
            additionalTextResId = R.string.text_fields_example_slider_additional,
            optionalTextResId = R.string.text_fields_optional_label
        )
    }
    // endregion
    // endregion

    // region TextFieldStandardWithoutText
    // region check by inflation
    @Test
    fun checkByInflationTextFieldStandardWithoutTextEnabledState() {
        checkTextFieldByInflation(
            viewBinding = textFieldStandardBinding,
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationTextFieldStandardWithoutTextDisabledState() {
        checkTextFieldByInflation(
            viewBinding = textFieldStandardBinding,
            isEnabled = false
        )
    }

    @Test
    fun checkByInflationTextFieldStandardErrorWithoutTextEnabledState() {
        checkTextFieldByInflation(
            viewBinding = textFieldStandardBinding,
            isEnabled = true,
            isError = true
        )
    }

    @Test
    fun checkByInflationTextFieldStandardErrorWithoutTextDisabledState() {
        checkTextFieldByInflation(
            viewBinding = textFieldStandardBinding,
            isEnabled = false,
            isError = true
        )
    }

    @Test
    fun checkByInflationTextFieldStandardReadOnlyWithoutTextEnabledState() {
        checkTextFieldByInflation(
            viewBinding = textFieldStandardBinding,
            isEnabled = true,
            isReadonly = true
        )
    }

    @Test
    fun checkByInflationTextFieldStandardReadOnlyWithoutTextDisabledState() {
        checkTextFieldByInflation(
            viewBinding = textFieldStandardBinding,
            isEnabled = false,
            isReadonly = true
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyTextFieldStandardWithoutTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            additionalTextResId = R.string.text_fields_example_slider_additional,
            optionalTextResId = R.string.text_fields_optional_label
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldStandardWithoutTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            additionalTextResId = R.string.text_fields_example_slider_additional,
            optionalTextResId = R.string.text_fields_optional_label
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldStandardErrorWithoutTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            isError = true,
            additionalTextResId = R.string.text_fields_example_slider_additional,
            optionalTextResId = R.string.text_fields_optional_label
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldStandardErrorWithoutTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            isError = true,
            additionalTextResId = R.string.text_fields_example_slider_additional,
            optionalTextResId = R.string.text_fields_optional_label
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldStandardReadOnlyWithoutTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            isReadonly = true,
            additionalTextResId = R.string.text_fields_example_slider_additional,
            optionalTextResId = R.string.text_fields_optional_label
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldStandardReadOnlyWithoutTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            isReadonly = true,
            additionalTextResId = R.string.text_fields_example_slider_additional,
            optionalTextResId = R.string.text_fields_optional_label
        )
    }
    // endregion
    // endregion
}