package com.admiral.uikit.components.chat.textOperation

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.components.chat.MessageStatus
import com.admiral.uikit.components.chat.setMessageStatusIcon
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.databinding.AdmiralViewTextOperationBinding
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.core.R as core

class TextOperation @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    private val binding = AdmiralViewTextOperationBinding
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
     * Title text.
     * Gone if text is null or empty.
     */
    var title: String? = null
        set(value) {
            field = value
            binding.title.apply {
                text = value
                isVisible = value?.isNotEmpty() == true
            }
        }

    /**
     * Message text.
     * Gone if text is null or empty.
     */
    var text: String? = null
        set(value) {
            field = value
            binding.message.apply {
                text = value
                isVisible = value?.isNotEmpty() == true
            }
        }

    /**
     * DateTime text.
     * Gone if text is null or empty.
     */
    var dateTime: String? = null
        set(value) {
            field = value
            binding.dateTime.apply {
                text = value
                isVisible = value?.isNotEmpty() == true
            }
        }

    /**
     * Time text.
     * Gone if text is null or empty.
     */
    var time: String? = null
        set(value) {
            field = value
            binding.time.apply {
                text = value
                isVisible = value?.isNotEmpty() == true
            }
        }

    /**
     * Determines type of view
     */
    var operationType: TextOperationType = TextOperationType.DEFAULT
        set(value) {
            field = value

            invalidateBackground()
            invalidateTitleTextColors()
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

    init {
        parseAttrs(attrs, R.styleable.TextOperation).use {
            title = it.getString(R.styleable.TextOperation_admiralTitleText)
            text = it.getString(R.styleable.TextOperation_admiralText)
            dateTime = it.getString(R.styleable.TextOperation_admiralDateTimeText)
            time = it.getString(R.styleable.TextOperation_admiralTimeText)
            isLast = it.getBoolean(R.styleable.TextOperation_admiralIsLast, false)
            isOutgoing = it.getBoolean(R.styleable.TextOperation_admiralIsOutgoing, false)
            messageStatus = MessageStatus.from(
                it.getInt(
                    R.styleable.TextOperation_admiralMessageStatus,
                    MessageStatus.NONE.ordinal
                )
            )
            operationType = TextOperationType.from(
                it.getInt(
                    R.styleable.TextOperation_admiralOperationType,
                    TextOperationType.DEFAULT.ordinal
                )
            )
        }

        val width = resources.getDimensionPixelSize(R.dimen.admiral_chat_text_operation_width)
        binding.root.layoutParams = LayoutParams(width, WRAP_CONTENT).apply {
            minWidth = width
            maxWidth = width
        }

        val verticalPadding = pixels(core.dimen.module_x2)
        val horizontalPadding = pixels(core.dimen.module_x3)
        binding.root.updatePadding(
            left = horizontalPadding,
            top = verticalPadding,
            right = horizontalPadding,
            bottom = verticalPadding
        )

        background = drawable(R.drawable.admiral_bg_rectangle_12dp)
            ?.colored(ThemeManager.theme.palette.backgroundAccent)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        ThemeManager.subscribe(this)
    }

    override fun onDetachedFromWindow() {
        ThemeManager.unsubscribe(this)
        super.onDetachedFromWindow()
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateBackground()
        invalidateTitleTextColors()
        invalidateMessageTextColors()
        invalidateDateTimeTextColors()
        invalidateTimeTextColors()
        invalidateIcon()
    }

    private fun invalidateBackground() {
        val drawable = if (isOutgoing) {
            if (isLast) {
                drawable(R.drawable.admiral_bg_message_incoming_last)
            } else {
                drawable(R.drawable.admiral_bg_message_chat)
            }
        } else {
            if (isLast) {
                drawable(R.drawable.admiral_bg_message_outgoing_last)
            } else {
                drawable(R.drawable.admiral_bg_message_chat)
            }
        }

        val color = when (operationType) {
            TextOperationType.DEFAULT -> ThemeManager.theme.palette.backgroundAdditionalOne
            TextOperationType.SUCCESS -> ThemeManager.theme.palette.backgroundSuccess
            TextOperationType.ERROR -> ThemeManager.theme.palette.backgroundError
        }

        background = drawable?.colored(color)
    }

    private fun invalidateTitleTextColors() {
        val colorState = when (operationType) {
            TextOperationType.DEFAULT -> {
                ColorState(
                    normalEnabled = ThemeManager.theme.palette.textPrimary,
                    normalDisabled = ThemeManager.theme.palette.textPrimary.withAlpha(),
                    pressed = ThemeManager.theme.palette.textPrimary
                )
            }

            TextOperationType.SUCCESS -> {
                ColorState(
                    normalEnabled = ThemeManager.theme.palette.textSuccess,
                    normalDisabled = ThemeManager.theme.palette.textSuccess.withAlpha(),
                    pressed = ThemeManager.theme.palette.textSuccessPressed
                )
            }

            TextOperationType.ERROR -> {
                ColorState(
                    normalEnabled = ThemeManager.theme.palette.textError,
                    normalDisabled = ThemeManager.theme.palette.textError.withAlpha(),
                    pressed = ThemeManager.theme.palette.textErrorPressed
                )
            }
        }

        binding.title.textColor = colorState
    }

    private fun invalidateMessageTextColors() {
        val colorState = ColorState(
            normalEnabled = ThemeManager.theme.palette.textPrimary,
            normalDisabled = ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = ThemeManager.theme.palette.textPrimary
        )
        binding.message.textColor = colorState
    }

    private fun invalidateDateTimeTextColors() {
        val colorState = ColorState(
            normalEnabled = ThemeManager.theme.palette.textSecondary,
            normalDisabled = ThemeManager.theme.palette.textSecondary.withAlpha(),
            pressed = ThemeManager.theme.palette.textSecondary
        )
        binding.dateTime.textColor = colorState
    }

    private fun invalidateTimeTextColors() {
        binding.time.textColor = ColorState(
            normalEnabled = ThemeManager.theme.palette.textSecondary,
            normalDisabled = ThemeManager.theme.palette.textSecondary.withAlpha(),
            pressed = ThemeManager.theme.palette.textSecondary
        )
    }

    private fun invalidateIcon() {
        binding.statusIcon.setMessageStatusIcon(messageStatus, false)
    }
}