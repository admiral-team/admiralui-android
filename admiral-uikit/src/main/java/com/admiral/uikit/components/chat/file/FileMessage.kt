package com.admiral.uikit.components.chat.file

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.components.chat.MessageStatus
import com.admiral.uikit.components.chat.setMessageStatusIcon
import com.admiral.uikit.components.chat.setTimeTextColors
import com.admiral.uikit.databinding.AdmiralViewMessageFileBinding
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.layout.ConstraintLayout

class FileMessage @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    private val binding = AdmiralViewMessageFileBinding
        .inflate(LayoutInflater.from(context), this)

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
     * Bottom text.
     * Gone if text is null or empty.
     */
    var time: String? = null
        set(value) {
            field = value
            binding.admiralFileMessageTimeTextView.text = value
            binding.admiralFileMessageTimeTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Determines direction of the message.
     * Depending on this parameter, the view changes the color of the it's background texts.
     */
    var isOutgoing: Boolean = false
        set(value) {
            field = value
            views.forEach {
                it.isOutgoing = value
            }
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
     * Container of [ChatFileView] shown at the LinearLayout.
     */
    val views = mutableListOf<ChatFileView>()

    init {
        parseAttrs(attrs, R.styleable.FileMessage).use {
            time = it.getString(R.styleable.FileMessage_admiralTimeText)

            isLast = it.getBoolean(R.styleable.FileMessage_admiralIsLast, false)
            isOutgoing = it.getBoolean(R.styleable.FileMessage_admiralIsOutgoing, false)

            messageStatus = MessageStatus.from(
                it.getInt(
                    R.styleable.FileMessage_admiralMessageStatus,
                    MessageStatus.NONE.ordinal
                )
            )
        }

        background = drawable(R.drawable.admiral_bg_rectangle_12dp)
            ?.colored(ThemeManager.theme.palette.backgroundAccent)
        invalidateIcon()
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
        invalidateTimeTextColors()
        invalidateBackground()
        invalidateIcon()
    }

    override fun setEnabled(enabled: Boolean) {
        binding.admiralFileMessageTimeTextView.isEnabled = enabled
        super.setEnabled(enabled)
    }

    /**
     * add [ChatFileView] to the view vertically.
     */
    fun addChatFileView(chatFileView: ChatFileView) {
        views.add(chatFileView.apply {
            isOutgoing = this@FileMessage.isOutgoing
        })

        showViews()
    }

    private fun showViews() {
        binding.admiralFileMessageLinear.removeAllViews()

        views.forEach {
            binding.admiralFileMessageLinear.addView(it)
        }
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

    private fun invalidateTimeTextColors() {
        binding.admiralFileMessageTimeTextView.setTimeTextColors(isOutgoing)
    }

    private fun invalidateIcon() {
        binding.admiralFileMessageIconImageView.setMessageStatusIcon(messageStatus, isOutgoing)
    }
}
