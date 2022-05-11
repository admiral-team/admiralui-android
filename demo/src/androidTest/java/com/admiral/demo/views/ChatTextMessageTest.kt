package com.admiral.demo.views

import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.ext.measureUnspecified
import com.admiral.uikit.components.chat.MessageStatus
import com.admiral.uikit.components.chat.TextMessage
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class ChatTextMessageTest : ScreenshotTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)

    private fun TextMessage.check() {
        measureUnspecified()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkProgrammatically(
        isLast: Boolean = false,
        isOutgoing: Boolean = true,
        status: MessageStatus = MessageStatus.NONE,
        text: String? = null
    ) {
        val message = TextMessage(wrappedContext).apply {
            time = "14:52"
            this.text = text ?: "Text message"
            this.isLast = isLast
            this.isOutgoing = isOutgoing
            this.messageStatus = status
        }

        message.check()
    }

    @Test
    fun checkProgrammaticallyOutgoingLongTextState() {
        checkProgrammatically(
            text = "At breakpoint boundaries, mini units divide the screen into a fixed master grid, and multiples of mini units map to fluid grid column widths and row heights."
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingNoneState() {
        checkProgrammatically()
    }

    @Test
    fun checkProgrammaticallyOutgoingLoadState() {
        checkProgrammatically(
            status = MessageStatus.LOAD
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingSendingState() {
        checkProgrammatically(
            status = MessageStatus.SENDING
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingSendState() {
        checkProgrammatically(
            status = MessageStatus.SEND
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingReadState() {
        checkProgrammatically(
            status = MessageStatus.READ
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingLastState() {
        checkProgrammatically(
            isOutgoing = true,
            isLast = true
        )
    }

    @Test
    fun checkProgrammaticallyIncomingState() {
        checkProgrammatically(
            isOutgoing = false
        )
    }

    @Test
    fun checkProgrammaticallyIncomingLastState() {
        checkProgrammatically(
            isOutgoing = false,
            isLast = true
        )
    }
}