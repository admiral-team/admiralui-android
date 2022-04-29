package com.admiral.demo.views

import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.ext.measureUnspecifiedHeight
import com.admiral.uikit.components.chat.MessageStatus
import com.admiral.uikit.components.chat.file.ChatFileView
import com.admiral.uikit.components.chat.file.FileMessage
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class ChatUploadingFileTest : ScreenshotTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)

    private fun FileMessage.check() {
        measureUnspecifiedHeight()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkProgrammatically(
        views: List<ChatFileView>,
        isLast: Boolean = false,
        isOutgoing: Boolean = false
    ) {
        val message = FileMessage(wrappedContext).apply {
            time = "14:52"
            this.isLast = isLast
            this.isOutgoing = isOutgoing
            messageStatus = MessageStatus.LOAD
        }

        views.forEach {
            message.addChatFileView(it)
        }

        message.check()
    }

    @Test
    fun checkProgrammaticallyOutgoingOneView() {
        val views = listOf(ChatFileView(wrappedContext).apply {
            titleText = context.getString(R.string.chat_file)
            subtitleText = context.getString(R.string.chat_file_size)
        })
        checkProgrammatically(
            views,
            isOutgoing = true
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingTwoView() {
        val views = listOf(
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            }
        )
        checkProgrammatically(
            views,
            isOutgoing = true
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingThreeView() {
        val views = listOf(
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            }
        )
        checkProgrammatically(
            views,
            isOutgoing = true
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingFourView() {
        val views = listOf(
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            }
        )
        checkProgrammatically(
            views,
            isOutgoing = true
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingFiveView() {
        val views = listOf(
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            }
        )
        checkProgrammatically(
            views,
            isOutgoing = true
        )
    }

    @Test
    fun checkProgrammaticallyOutgoingOneSixLast() {
        val views = listOf(
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            }
        )
        checkProgrammatically(
            views,
            isLast = true,
            isOutgoing = true
        )
    }

    @Test
    fun checkProgrammaticallyIncomingOneView() {
        val views = listOf(ChatFileView(wrappedContext).apply {
            titleText = context.getString(R.string.chat_file)
            subtitleText = context.getString(R.string.chat_file_size)
        })
        checkProgrammatically(
            views
        )
    }

    @Test
    fun checkProgrammaticallyIncomingTwoView() {
        val views = listOf(
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            }
        )
        checkProgrammatically(
            views
        )
    }

    @Test
    fun checkProgrammaticallyIncomingThreeView() {
        val views = listOf(
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            }
        )
        checkProgrammatically(
            views
        )
    }

    @Test
    fun checkProgrammaticallyIncomingFourView() {
        val views = listOf(
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            }
        )
        checkProgrammatically(
            views
        )
    }

    @Test
    fun checkProgrammaticallyIncomingFiveView() {
        val views = listOf(
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            }
        )
        checkProgrammatically(
            views
        )
    }

    @Test
    fun checkProgrammaticallyIncomingOneSixLast() {
        val views = listOf(
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            },
            ChatFileView(wrappedContext).apply {
                titleText = context.getString(R.string.chat_file)
                subtitleText = context.getString(R.string.chat_file_size)
            }
        )
        checkProgrammatically(
            views,
            isLast = true
        )
    }
}