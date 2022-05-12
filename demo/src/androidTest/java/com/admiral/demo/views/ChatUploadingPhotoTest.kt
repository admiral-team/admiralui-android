package com.admiral.demo.views

import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.chat.MessageStatus
import com.admiral.uikit.components.chat.image.ChatImageView
import com.admiral.uikit.components.chat.image.ImageMessage
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class ChatUploadingPhotoTest : ScreenshotTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)

    private fun ImageMessage.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkProgrammatically(
        views: List<ChatImageView>,
    ) {
        val message = ImageMessage(wrappedContext).apply {
            time = "14:52"
            messageStatus = MessageStatus.LOAD
        }

        views.forEach {
            message.addImageView(it)
        }

        message.check()
    }

    @Test
    fun checkProgrammaticallyOneView() {
        val views = listOf(ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        })
        checkProgrammatically(
            views,
        )
    }

    @Test
    fun checkProgrammaticallyTwoView() {
        val views = listOf(
            ChatImageView(wrappedContext).apply {
                drawable = context.getDrawable(R.drawable.ic_chat_image)
            },
            ChatImageView(wrappedContext).apply {
                drawable = context.getDrawable(R.drawable.ic_chat_image)
            },
        )
        checkProgrammatically(
            views,
        )
    }

    @Test
    fun checkProgrammaticallyThreeView() {
        val views = listOf(ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        }, ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        }, ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        })
        checkProgrammatically(
            views,
        )
    }

    @Test
    fun checkProgrammaticallyFourView() {
        val views = listOf(ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        }, ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        }, ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        }, ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        })
        checkProgrammatically(
            views,
        )
    }

    @Test
    fun checkProgrammaticallyFiveView() {
        val views = listOf(ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        }, ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        }, ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        }, ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        }, ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        })
        checkProgrammatically(
            views,
        )
    }

    @Test
    fun checkProgrammaticallySixView() {
        val views = listOf(ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        }, ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        }, ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        }, ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        }, ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        }, ChatImageView(wrappedContext).apply {
            drawable = context.getDrawable(R.drawable.ic_chat_image)
        })
        checkProgrammatically(
            views,
        )
    }
}