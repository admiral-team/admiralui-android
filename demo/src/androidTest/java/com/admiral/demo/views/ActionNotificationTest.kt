package com.admiral.demo.views

import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.view.ContextThemeWrapper
import androidx.test.platform.app.InstrumentationRegistry
import com.admiral.demo.R
import com.admiral.demo.ext.measureUnspecified
import com.admiral.uikit.components.notifications.action.ActionNotification
import com.admiral.uikit.core.components.notification.ActionNotificationCloseType
import com.karumi.shot.ScreenshotTest
import org.junit.Test

class ActionNotificationTest : ScreenshotTest {
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val wrappedContext = ContextThemeWrapper(context, R.style.Theme_AdmiralUIAndroid_Launcher)
    private val duration = 5000
    private val margin = 70
    private val singleLineText = "Сообщение"
    private val multiLineText = "Многострочное\nсообщение"

    private fun View.check() {
        measureUnspecified()
        compareScreenshot(
            view = this,
            widthInPx = measuredWidth,
            heightInPx = measuredHeight
        )
    }

    private fun checkActionNotification(
        text: String,
        type: ActionNotificationCloseType,
        isTimerVisible: Boolean
    ) {
        val rootView = FrameLayout(wrappedContext)
        val builder = ActionNotification.Builder(wrappedContext, rootView)
            .setText(text)
            .setDuration(duration)
            .setMargins(bottom = margin)
            .setTimerVisibility(isTimerVisible)
            .setCloseButtonType(type)
            .setCloseButtonText(context.getString(R.string.notifications_action_cancel_text))
        val notification = builder.build()

        notification.show(isTimerEnabled = false)
        notification.snackBarInstance.view.check()
    }

    @Test
    fun checkActionNotificationVisibleTimerCloseTypeTextMultilineState() {
        checkActionNotification(
            text = multiLineText,
            type = ActionNotificationCloseType.TEXT,
            isTimerVisible = true
        )
    }

    @Test
    fun checkActionNotificationVisibleTimerCloseTypeTextSingleLineState() {
        checkActionNotification(
            text = singleLineText,
            type = ActionNotificationCloseType.TEXT,
            isTimerVisible = true
        )
    }

    @Test
    fun checkActionNotificationVisibleTimerCloseTypeIconMultilineState() {
        checkActionNotification(
            text = multiLineText,
            type = ActionNotificationCloseType.ICON,
            isTimerVisible = true
        )
    }

    @Test
    fun checkActionNotificationVisibleTimerCloseTypeIconSingleLineState() {
        checkActionNotification(
            text = singleLineText,
            type = ActionNotificationCloseType.ICON,
            isTimerVisible = true
        )
    }

    @Test
    fun checkActionNotificationInvisibleTimerCloseTypeTextMultilineState() {
        checkActionNotification(
            text = multiLineText,
            type = ActionNotificationCloseType.TEXT,
            isTimerVisible = false
        )
    }

    @Test
    fun checkActionNotificationInvisibleTimerCloseTypeTextSingleLineState() {
        checkActionNotification(
            text = singleLineText,
            type = ActionNotificationCloseType.TEXT,
            isTimerVisible = false
        )
    }

    @Test
    fun checkActionNotificationInvisibleTimerCloseTypeIconMultilineState() {
        checkActionNotification(
            text = multiLineText,
            type = ActionNotificationCloseType.ICON,
            isTimerVisible = false
        )
    }

    @Test
    fun checkActionNotificationInvisibleTimerCloseTypeIconSingleLineState() {
        checkActionNotification(
            text = singleLineText,
            type = ActionNotificationCloseType.ICON,
            isTimerVisible = false
        )
    }
}