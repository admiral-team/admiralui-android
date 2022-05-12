package com.admiral.demo.views

import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.ext.measureUnspecified
import com.admiral.uikit.components.chat.MessageStatus
import com.admiral.uikit.components.chat.textOperation.TextOperation
import com.admiral.uikit.components.chat.textOperation.TextOperationType
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class ChatTextOperationTest : ScreenshotTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)

    private fun TextOperation.check() {
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
        text: String? = null,
        operationType: TextOperationType = TextOperationType.DEFAULT
    ) {
        val message = TextOperation(wrappedContext).apply {
            this.dateTime = "13 мая 14:15"
            this.time = "14:52"
            this.title = "- 35 000 ₽"
            this.text = text ?: "НПО «Ромашка»"
            this.isLast = isLast
            this.isOutgoing = isOutgoing
            this.messageStatus = status
            this.operationType = operationType
        }

        message.check()
    }

    @Test
    fun checkProgrammaticallyOutgoingLongTextState() {
        checkProgrammatically(
            text = "At breakpoint boundaries, mini units divide the screen into a fixed master grid, and multiples of " +
                    "mini units map to fluid grid column widths and row heights."
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
    fun checkProgrammaticallyOutgoingLongTextErrorState() {
        checkProgrammatically(
            text = "At breakpoint boundaries, mini units divide the screen into a fixed master grid, and multiples of" +
                    " mini units map to fluid grid column widths and row heights.",
            operationType = TextOperationType.ERROR
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingNoneErrorState() {
        checkProgrammatically(operationType = TextOperationType.ERROR)

    }

    @Test
    fun checkProgrammaticallyOutgoingLoadErrorState() {
        checkProgrammatically(
            status = MessageStatus.LOAD,
            operationType = TextOperationType.ERROR
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingSendingErrorState() {
        checkProgrammatically(
            status = MessageStatus.SENDING,
            operationType = TextOperationType.ERROR
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingSendErrorState() {
        checkProgrammatically(
            status = MessageStatus.SEND,
            operationType = TextOperationType.ERROR
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingReadErrorState() {
        checkProgrammatically(
            status = MessageStatus.READ,
            operationType = TextOperationType.ERROR
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingLastErrorState() {
        checkProgrammatically(
            isOutgoing = true,
            isLast = true,
            operationType = TextOperationType.ERROR
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingLongTextSuccessState() {
        checkProgrammatically(
            text = "At breakpoint boundaries, mini units divide the screen into a fixed master grid, and multiples of" +
                    " mini units map to fluid grid column widths and row heights.",
            operationType = TextOperationType.SUCCESS
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingNoneSuccessState() {
        checkProgrammatically(operationType = TextOperationType.SUCCESS)

    }

    @Test
    fun checkProgrammaticallyOutgoingLoadSuccessState() {
        checkProgrammatically(
            status = MessageStatus.LOAD,
            operationType = TextOperationType.SUCCESS
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingSendingSuccessState() {
        checkProgrammatically(
            status = MessageStatus.SENDING,
            operationType = TextOperationType.SUCCESS
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingSendSuccessState() {
        checkProgrammatically(
            status = MessageStatus.SEND,
            operationType = TextOperationType.SUCCESS
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingReadSuccessState() {
        checkProgrammatically(
            status = MessageStatus.READ,
            operationType = TextOperationType.SUCCESS
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingLastSuccessState() {
        checkProgrammatically(
            isOutgoing = true,
            isLast = true,
            operationType = TextOperationType.SUCCESS
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

    @Test
    fun checkProgrammaticallyIncomingErrorState() {
        checkProgrammatically(
            isOutgoing = false,
            operationType = TextOperationType.ERROR
        )
    }

    @Test
    fun checkProgrammaticallyIncomingLastErrorState() {
        checkProgrammatically(
            isOutgoing = false,
            isLast = true,
            operationType = TextOperationType.ERROR
        )
    }

    @Test
    fun checkProgrammaticallyIncomingSuccessState() {
        checkProgrammatically(
            isOutgoing = false,
            operationType = TextOperationType.SUCCESS
        )
    }

    @Test
    fun checkProgrammaticallyIncomingLastSuccessState() {
        checkProgrammatically(
            isOutgoing = false,
            isLast = true,
            operationType = TextOperationType.SUCCESS
        )
    }
}