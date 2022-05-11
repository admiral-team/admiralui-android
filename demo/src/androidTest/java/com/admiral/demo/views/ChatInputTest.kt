package com.admiral.demo.views

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.test.platform.app.InstrumentationRegistry
import androidx.viewbinding.ViewBinding
import com.admiral.demo.R
import com.admiral.demo.databinding.TestViewChatInputBinding
import com.admiral.demo.databinding.TestViewChatInputChangedIconBinding
import com.admiral.demo.databinding.TestViewChatInputLargeTextBinding
import com.admiral.demo.databinding.TestViewChatInputSmallTextBinding
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.chat.Input
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class ChatInputTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AppCompat)
    private val layoutInflater = LayoutInflater.from(wrappedContext)

    private val inputDefaultBinding = TestViewChatInputBinding.inflate(layoutInflater)
    private val inputLongTextBinding = TestViewChatInputLargeTextBinding.inflate(layoutInflater)
    private val inputShortTextBinding = TestViewChatInputSmallTextBinding.inflate(layoutInflater)
    private val inputChangeIconBinding = TestViewChatInputChangedIconBinding.inflate(layoutInflater)

    private fun Input.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkByInflation(
        binding: ViewBinding = inputDefaultBinding
    ) {
        //        TODO: uncomment when fix TextFieldSearch component
//        with(binding.root as Input) {
//            check()
//        }
    }

    private fun checkProgrammatically(
        text: String? = null,
        buttonIcon: Drawable? = ContextCompat.getDrawable(context, R.drawable.admiral_ic_arrow_down_outline)
    ) {
//        TODO: uncomment when fix TextFieldSearch component
//        val view = Input(wrappedContext).apply {
//            this.text = text ?: ""
//            this.hintText = "Введите сообщение"
////            this.button.drawableEnd = buttonIcon
//            this.iconStart = ContextCompat.getDrawable(context, R.drawable.admiral_ic_attach_file_outline)
//        }
//
//        view.check()
    }

    // region check programmatically
    @Test
    fun checkProgrammaticallyDefaultState() {
        checkProgrammatically()
    }

    @Test
    fun checkProgrammaticallyLongTextState() {
        checkProgrammatically(
            text = "At breakpoint boundaries, mini units divide the screen into a fixed master grid, and multiples of mini units map to fluid grid column widths and row heights."
        )
    }

    @Test
    fun checkProgrammaticallySmallTextState() {
        checkProgrammatically(
            text = "Text"
        )
    }

    @Test
    fun checkProgrammaticallyChangeIconState() {
        checkProgrammatically(
            buttonIcon = ContextCompat.getDrawable(context, R.drawable.admiral_ic_camera_outline)
        )
    }
    // endregion

    // region check by inflation
    @Test
    fun checkByInflationDefaultState() {
        checkByInflation()
    }

    @Test
    fun checkByInflationLongTextState() {
        checkByInflation(inputLongTextBinding)
    }

    @Test
    fun checkByInflationSmallTextState() {
        checkByInflation(inputShortTextBinding)
    }

    @Test
    fun checkByInflationChangeIconState() {
        checkByInflation(inputChangeIconBinding)
    }
    // endregion
}