package com.admiral.uikit.components.chat

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.imageview.ImageView
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.parseAttrs

class TextMessage @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * There are three types of MessageStatus: NONE, LOADING and DONE.
     * Depending on this parameter, the view changes the icon's drawable.
     */
    var messageStatus: MessageStatus = MessageStatus.NONE
        set(value) {
            field = value
            invalidateIcon()
        }

    /**
     * Top text.
     * Gone if text is null or empty.
     */
    var text: String? = null
        set(value) {
            field = value
            textMessageTextView.text = value
            textMessageTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Bottom text.
     * Gone if text is null or empty.
     */
    var time: String? = null
        set(value) {
            field = value
            timeTextView.text = value
            timeTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Determines direction of the message.
     * Depending on this parameter, the view changes the color of the it's background texts.
     */
    var isOutgoing: Boolean = false
        set(value) {
            field = value
            invalidateBackground()
        }

    /**
     * Determines whether the message is the last one.
     * Depending on this parameter, the view changes its shape.
     */
    var isLast: Boolean = false
        set(value) {
            field = value
            invalidateBackground()
        }

    /**
     * Determines gravity of the message.
     */
    var messageGravity: TextMessageGravity = TextMessageGravity.END
        set(value) {
            field = value

            textMessageTextView.gravity = when (value) {
                TextMessageGravity.START -> Gravity.START
                TextMessageGravity.CENTER -> Gravity.CENTER
                TextMessageGravity.END -> Gravity.END
            }

            invalidate()
        }

    private val textMessageTextView: TextView by lazy { findViewById(R.id.admiralTextMessageTextView) }
    private val timeTextView: TextView by lazy { findViewById(R.id.admiralTextMessageTimeTextView) }
    private val statusImageView: ImageView by lazy { findViewById(R.id.admiralTextMessageIconImageView) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_message_text, this)

        parseAttrs(attrs, R.styleable.TextMessage).use {
            text = it.getString(R.styleable.TextMessage_admiralText)
            time = it.getString(R.styleable.TextMessage_admiralTimeText)

            isLast = it.getBoolean(R.styleable.TextMessage_admiralIsLast, false)
            isOutgoing = it.getBoolean(R.styleable.TextMessage_admiralIsOutgoing, false)

            messageStatus = MessageStatus.from(
                it.getInt(
                    R.styleable.TextMessage_admiralMessageStatus,
                    MessageStatus.NONE.ordinal
                )
            )
            messageGravity = TextMessageGravity.from(
                it.getInt(
                    R.styleable.TextMessage_admiralMessageGravity,
                    TextMessageGravity.END.ordinal
                )
            )
        }

        background = drawable(R.drawable.admiral_bg_rectangle_12dp)
            ?.colored(ThemeManager.theme.palette.backgroundAccent)
    }

    /**
     * Subscribe for theme change.
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.subscribe(this)
    }

    /**
     * Unsubscribe for theme change.
     */
    override fun onDetachedFromWindow() {
        ThemeManager.unsubscribe(this)
        super.onDetachedFromWindow()
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateTextMessageColors()
        invalidateTimeTextColors()
        invalidateBackground()
        invalidateIcon()
    }

    override fun setEnabled(enabled: Boolean) {
        textMessageTextView.isEnabled = enabled
        timeTextView.isEnabled = enabled
        super.setEnabled(enabled)
    }

    private fun invalidateBackground() {
        background = if (isOutgoing) {
            if (isLast) {
                drawable(R.drawable.admiral_bg_message_incoming_last)
                    ?.colored(ThemeManager.theme.palette.backgroundAccent)
            } else {
                drawable(R.drawable.admiral_bg_message_chat)
                    ?.colored(ThemeManager.theme.palette.backgroundAccent)
            }
        } else {
            if (isLast) {
                drawable(R.drawable.admiral_bg_message_outgoing_last)
                    ?.colored(ThemeManager.theme.palette.backgroundAdditionalOne)
            } else {
                drawable(R.drawable.admiral_bg_message_chat)
                    ?.colored(ThemeManager.theme.palette.backgroundAdditionalOne)
            }
        }
    }

    private fun invalidateTextMessageColors() {
        val colorState = if (isOutgoing) {
            ColorState(
                normalEnabled = ThemeManager.theme.palette.textStaticWhite,
                normalDisabled = ThemeManager.theme.palette.textStaticWhite.withAlpha(),
                pressed = ThemeManager.theme.palette.textStaticWhite
            )
        } else {
            ColorState(
                normalEnabled = ThemeManager.theme.palette.textPrimary,
                normalDisabled = ThemeManager.theme.palette.textPrimary.withAlpha(),
                pressed = ThemeManager.theme.palette.textPrimary
            )
        }

        textMessageTextView.textColor = colorState
    }

    private fun invalidateTimeTextColors() {
        timeTextView.setTimeTextColors(isOutgoing)
    }

    private fun invalidateIcon() {
        statusImageView.setMessageStatusIcon(messageStatus, isOutgoing)
    }
}
