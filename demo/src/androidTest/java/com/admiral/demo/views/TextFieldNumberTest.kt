package com.admiral.demo.views

import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewTextFieldStandardBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.textfield.TextField
import com.admiral.uikit.components.textfield.TextFieldStyle
import com.admiral.uikit.ext.dpToPx
import com.karumi.shot.ScreenshotTest
import org.junit.Before
import org.junit.Test

class TextFieldNumberTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val layoutInflater = LayoutInflater.from(wrappedContext)
    private lateinit var textFieldNumberBinding: TestViewTextFieldStandardBinding

    @Before
    fun setUp() {
        // NB: we do initialization in `runOnUi` block since inflation with admiralText attribute causes
        // animation and we don't want to have exception `AndroidRuntimeException: Animators may only be
        // run on Looper threads`
        runOnUi {
            textFieldNumberBinding = TestViewTextFieldStandardBinding.inflate(layoutInflater)
        }
    }

    private fun TextField.disableInputLayoutAnimation() {
        // It's necessary to set `isHintAnimationEnabled` to false
        // otherwise screenshots will be inconsistent
        inputLayout.isHintAnimationEnabled = false
    }

    private fun TextField.check() {
        measureUnspecifiedHeight(280.dpToPx(context))
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
        with(textFieldNumberBinding.root) {
            disableInputLayoutAnimation()

            this.isEnabled = isEnabled
            isReadonly?.let { this.isEditEnabled = isReadonly.not() }
            isError?.let { this.isError = isError }
            textResId?.let { this.inputText = context.getString(it) }
            textFieldStyle = TextFieldStyle.Clipped
            this.optionalText = context.getString(R.string.text_fields_number_text)
            icon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_camera_outline)
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
            this.optionalText = context.getString(R.string.text_fields_number_text)
            this.additionalText = context.getString(R.string.text_fields_example_slider_additional)
            this.isEnabled = isEnabled

            textFieldStyle = TextFieldStyle.Clipped
            textResId?.let { this.inputText = context.getString(it) }
            isReadonly?.let { this.isEditEnabled = isReadonly.not() }
            isError?.let { this.isError = isError }
            icon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_camera_outline)
        }

        textField.check()
    }

    // region TextFieldNumberWithText
    // region check by inflation
    @Test
    fun checkByInflationTextFieldNumberWithTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true,
            textResId = R.string.text_fields_number_text
        )
    }

    @Test
    fun checkByInflationTextFieldNumberNumberWithTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false,
            textResId = R.string.text_fields_number_text
        )
    }

    @Test
    fun checkByInflationTextFieldNumberErrorWithTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true,
            isError = true,
            textResId = R.string.text_fields_number_text
        )
    }

    @Test
    fun checkByInflationTextFieldNumberErrorWithTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false,
            isError = true,
            textResId = R.string.text_fields_number_text
        )
    }

    @Test
    fun checkByInflationTextFieldNumberReadOnlyWithTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true,
            isReadonly = true,
            textResId = R.string.text_fields_number_text
        )
    }

    @Test
    fun checkByInflationTextFieldNumberReadOnlyWithTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false,
            isReadonly = true,
            textResId = R.string.text_fields_number_text
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyTextFieldNumberWithTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            textResId = R.string.text_fields_number_text,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldNumberWithTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            textResId = R.string.text_fields_number_text,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldNumberErrorWithTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            isError = true,
            textResId = R.string.text_fields_number_text,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldNumberErrorWithTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            isError = true,
            textResId = R.string.text_fields_number_text,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldNumberReadOnlyWithTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            isReadonly = true,
            textResId = R.string.text_fields_number_text,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldNumberReadOnlyWithTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            isReadonly = true,
            textResId = R.string.text_fields_number_text,
        )
    }
    // endregion
    // endregion

    // region TextFieldNumberWithoutText
    // region check by inflation
    @Test
    fun checkByInflationTextFieldNumberWithoutTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true
        )
    }

    @Test
    fun checkByInflationTextFieldNumberWithoutTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false
        )
    }

    @Test
    fun checkByInflationTextFieldNumberErrorWithoutTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true,
            isError = true
        )
    }

    @Test
    fun checkByInflationTextFieldNumberErrorWithoutTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false,
            isError = true
        )
    }

    @Test
    fun checkByInflationTextFieldNumberReadOnlyWithoutTextEnabledState() {
        checkTextFieldByInflation(
            isEnabled = true,
            isReadonly = true
        )
    }

    @Test
    fun checkByInflationTextFieldNumberReadOnlyWithoutTextDisabledState() {
        checkTextFieldByInflation(
            isEnabled = false,
            isReadonly = true
        )
    }
    // endregion

    // region check programmatically
    @Test
    fun checkProgrammaticallyTextFieldNumberWithoutTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldNumberWithoutTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldNumberErrorWithoutTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            isError = true,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldNumberErrorWithoutTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            isError = true,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldNumberReadOnlyWithoutTextEnabledState() {
        checkTextFieldProgrammatically(
            isEnabled = true,
            isReadonly = true,
        )
    }

    @Test
    fun checkProgrammaticallyTextFieldNumberReadOnlyWithoutTextDisabledState() {
        checkTextFieldProgrammatically(
            isEnabled = false,
            isReadonly = true,
        )
    }
    // endregion
    // endregion
}