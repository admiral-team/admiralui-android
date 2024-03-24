package com.admiral.uikit.components.chat.image

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.components.chat.MessageStatus
import com.admiral.uikit.components.spinner.SpinnerLoading
import com.admiral.uikit.core.ext.createRoundedRectangleDrawable
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.core.util.ComponentsRadius
import com.admiral.uikit.databinding.AdmiralViewChatImageBinding
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.setMargins

class ChatImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ThemeObserver {

    private val binding =
        AdmiralViewChatImageBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * Defines image for the view.
     */
    var drawable: Drawable? = null
        set(value) {
            field = value
            binding.imageView.setImageDrawable(value)
        }

    /**
     * There are three types of [MessageStatus]: NONE, LOADING and DONE.
     * Depending on this parameter, the view changes the icon's drawable.
     */
    internal var messageStatus: MessageStatus = MessageStatus.NONE
        set(value) {
            field = value
            invalidateIcon()
        }

    /**
     * Show / hide time with status.
     */
    internal var isShowStatus: Boolean = false
        set(value) {
            field = value
            updateImageInfoVisibility()
        }

    /**
     * Show / hide loader [loadingSpinner] above the view.
     */
    var isLoading: Boolean = false
        set(value) {
            field = value
            invalidateLoading()
            updateImageInfoVisibility()
        }

    /**
     * View shown on the loading state when [isLoading] == true.
     */
    val loadingSpinner: SpinnerLoading by lazy { findViewById(R.id.loadingSpinner) }

    /**
     * Time text.
     * Component with the text and status icon is Gone if the text is null or empty.
     */
    internal var time: String? = null
        set(value) {
            field = value
            binding.infoBoardTime.text = value
            binding.statusTime.text = value
            updateImageInfoVisibility()
        }

    /**
     * Image name text.
     */
    var imageName: String? = null
        set(value) {
            field = value
            binding.imageName.text = value
            updateImageInfoVisibility()
        }

    /**
     * Image size and extension text.
     */
    var imageSize: String? = null
        set(value) {
            field = value
            binding.imageSize.text = value
            updateImageInfoVisibility()
        }

    internal var imageViewType = ChatImageViewType.TOP
        set(value) {
            field = value
            invalidateImageType()
        }

    init {
        loadingSpinner.isContrast = true

        with(binding) {

            loadingForeground.apply {
                setImageDrawable(drawable(R.drawable.admiral_bg_message_image))

                colorFilter = PorterDuffColorFilter(
                    ThemeManager.theme.palette.backgroundModalView,
                    PorterDuff.Mode.SRC_IN
                )
            }

            infoBoardBackgroundView.backgroundTintList =
                ColorStateList.valueOf(ThemeManager.theme.palette.backgroundModalView)
            imageSize.textColor =
                ColorState(ThemeManager.theme.palette.textStaticWhite.withAlpha(IMAGE_SIZE_ALPHA))
        }

        parseAttrs(attrs, R.styleable.ChatImageView).use {
            drawable = it.getDrawable(R.styleable.ChatImageView_srcCompat)
        }
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
        binding.loadingForeground.apply {
            setImageDrawable(drawable(R.drawable.admiral_bg_message_image))
            colorFilter = PorterDuffColorFilter(
                ThemeManager.theme.palette.backgroundModalView,
                PorterDuff.Mode.SRC_IN
            )
        }
    }

    /**
     * Set click listener for the [loadingSpinner].
     */
    fun setLoaderClickListener(onLoaderClickListener: OnLoaderClickListener) {
        loadingSpinner.setOnClickListener {
            onLoaderClickListener.onLoaderClicked(this)
        }
    }

    private fun invalidateImageType() {
        when (imageViewType) {
            ChatImageViewType.SOLO -> {
                invalidateSolo()
            }

            ChatImageViewType.TOP -> {
                invalidateTop()
            }

            ChatImageViewType.TOP_LEFT -> {
                invalidateTopLeft()
            }

            ChatImageViewType.TOP_RIGHT -> {
                invalidateTopRight()
            }

            ChatImageViewType.MIDDLE_LEFT -> {
                invalidateMiddleLeft()
            }

            ChatImageViewType.MIDDLE_RIGHT -> {
                invalidateMiddleRight()
            }

            ChatImageViewType.LEFT -> {
                invalidateLeft()
            }

            ChatImageViewType.RIGHT -> {
                invalidateRight()
            }

            ChatImageViewType.BOTTOM_LEFT -> {
                invalidateBottomLeft()
            }

            ChatImageViewType.BOTTOM_RIGHT -> {
                invalidateBottomRight()
            }
        }
    }

    private fun invalidateBottomRight() {
        with(binding) {
            infoBoardBackgroundView.apply {
                background = createRoundedRectangleDrawable(
                    topLeft = ComponentsRadius.NONE,
                    topRight = ComponentsRadius.NONE,
                    bottomLeft = ComponentsRadius.NONE,
                    bottomRight = ComponentsRadius.RADIUS_12,
                )

                setMargins(top = 0, right = 0, bottom = 0, left = MARGIN_SIDES)
            }

            imageView.apply {
                setMargins(0, 0, 0, MARGIN_SIDES)
                setCornerRadius(0f, 0f, 0f, CORNER_RADIUS.dpToPx(context).toFloat())
            }
            loadingForeground.apply {
                setMargins(0, 0, 0, MARGIN_SIDES)
                setCornerRadius(0f, 0f, 0f, CORNER_RADIUS.dpToPx(context).toFloat())
            }
        }
    }

    private fun invalidateBottomLeft() {
        with(binding) {
            infoBoardBackgroundView.apply {
                background = createRoundedRectangleDrawable(
                    topLeft = ComponentsRadius.NONE,
                    topRight = ComponentsRadius.NONE,
                    bottomLeft = ComponentsRadius.RADIUS_12,
                    bottomRight = ComponentsRadius.NONE,
                )

                setMargins(top = 0, right = MARGIN_SIDES, bottom = 0, left = 0)
            }

            imageView.apply {
                setMargins(0, MARGIN_SIDES, 0, 0)
                setCornerRadius(0f, 0f, CORNER_RADIUS.dpToPx(context).toFloat(), 0f)
            }
            loadingForeground.apply {
                setMargins(0, MARGIN_SIDES, 0, 0)
                setCornerRadius(0f, 0f, CORNER_RADIUS.dpToPx(context).toFloat(), 0f)
            }
        }
    }

    private fun invalidateRight() {
        with(binding) {
            infoBoardBackgroundView.apply {
                background = createRoundedRectangleDrawable(
                    topLeft = ComponentsRadius.NONE,
                    topRight = ComponentsRadius.NONE,
                    bottomLeft = ComponentsRadius.NONE,
                    bottomRight = ComponentsRadius.RADIUS_12,
                )

                setMargins(top = 0, right = 0, bottom = 0, left = MARGIN_SIDES)
            }

            imageView.apply {
                setCornerRadius(
                    0f,
                    CORNER_RADIUS.dpToPx(context).toFloat(),
                    0f,
                    CORNER_RADIUS.dpToPx(context).toFloat()
                )
                setMargins(0, 0, 0, MARGIN_SIDES)
            }
            loadingForeground.apply {
                setCornerRadius(
                    0f,
                    CORNER_RADIUS.dpToPx(context).toFloat(),
                    0f,
                    CORNER_RADIUS.dpToPx(context).toFloat()
                )
                setMargins(0, 0, 0, MARGIN_SIDES)
            }
        }
    }

    private fun invalidateLeft() {
        with(binding) {
            infoBoardBackgroundView.apply {
                background = createRoundedRectangleDrawable(
                    topLeft = ComponentsRadius.NONE,
                    topRight = ComponentsRadius.NONE,
                    bottomLeft = ComponentsRadius.RADIUS_12,
                    bottomRight = ComponentsRadius.NONE,
                )

                setMargins(top = 0, right = MARGIN_SIDES, bottom = 0, left = 0)
            }
            imageView.apply {
                setCornerRadius(
                    CORNER_RADIUS.dpToPx(context).toFloat(),
                    0f,
                    CORNER_RADIUS.dpToPx(context).toFloat(),
                    0f
                )
                setMargins(0, MARGIN_SIDES, 0, 0)
            }
            loadingForeground.apply {
                setCornerRadius(
                    CORNER_RADIUS.dpToPx(context).toFloat(),
                    0f,
                    CORNER_RADIUS.dpToPx(context).toFloat(),
                    0f
                )
                setMargins(0, MARGIN_SIDES, 0, 0)
            }
        }
    }

    private fun invalidateMiddleRight() {
        with(binding) {
            infoBoardBackgroundView.apply {
                background = createRoundedRectangleDrawable(
                    topLeft = ComponentsRadius.NONE,
                    topRight = ComponentsRadius.NONE,
                    bottomLeft = ComponentsRadius.NONE,
                    bottomRight = ComponentsRadius.NONE,
                )

                setMargins(top = 0, right = 0, bottom = MARGIN_BOTTOM, left = MARGIN_SIDES)
            }
            imageView.apply {
                setCornerRadius(0f, 0f, 0f, 0f)
                setMargins(0, 0, MARGIN_BOTTOM, MARGIN_SIDES)
            }
            loadingForeground.apply {
                setCornerRadius(0f, 0f, 0f, 0f)
                setMargins(0, 0, MARGIN_BOTTOM, MARGIN_SIDES)
            }
        }
    }

    private fun invalidateMiddleLeft() {
        with(binding) {
            infoBoardBackgroundView.apply {
                background = createRoundedRectangleDrawable(
                    topLeft = ComponentsRadius.NONE,
                    topRight = ComponentsRadius.NONE,
                    bottomLeft = ComponentsRadius.NONE,
                    bottomRight = ComponentsRadius.NONE,
                )

                setMargins(top = 0, right = MARGIN_SIDES, bottom = MARGIN_BOTTOM, left = 0)
            }
            imageView.apply {
                setCornerRadius(0f, 0f, 0f, 0f)
                setMargins(0, MARGIN_SIDES, MARGIN_BOTTOM, 0)
            }
            loadingForeground.apply {
                setCornerRadius(0f, 0f, 0f, 0f)
                setMargins(0, MARGIN_SIDES, MARGIN_BOTTOM, 0)
            }
        }
    }

    private fun invalidateTopRight() {
        with(binding) {
            infoBoardBackgroundView.apply {
                background = createRoundedRectangleDrawable(
                    topLeft = ComponentsRadius.NONE,
                    topRight = ComponentsRadius.NONE,
                    bottomLeft = ComponentsRadius.NONE,
                    bottomRight = ComponentsRadius.NONE,
                )

                setMargins(top = 0, right = 0, bottom = MARGIN_BOTTOM, left = MARGIN_SIDES)
            }
            imageView.apply {
                setMargins(0, 0, MARGIN_BOTTOM, MARGIN_SIDES)

                setCornerRadius(0f, CORNER_RADIUS.dpToPx(context).toFloat(), 0f, 0f)
            }
            loadingForeground.apply {
                setMargins(0, 0, MARGIN_BOTTOM, MARGIN_SIDES)

                setCornerRadius(0f, CORNER_RADIUS.dpToPx(context).toFloat(), 0f, 0f)
            }
        }
    }

    private fun invalidateTopLeft() {
        with(binding) {
            infoBoardBackgroundView.apply {
                background = createRoundedRectangleDrawable(
                    topLeft = ComponentsRadius.NONE,
                    topRight = ComponentsRadius.NONE,
                    bottomLeft = ComponentsRadius.NONE,
                    bottomRight = ComponentsRadius.NONE,
                )

                setMargins(top = 0, right = MARGIN_SIDES, bottom = MARGIN_BOTTOM, left = 0)
            }
            imageView.apply {
                setMargins(0, MARGIN_SIDES, MARGIN_BOTTOM, 0)

                setCornerRadius(CORNER_RADIUS.dpToPx(context).toFloat(), 0f, 0f, 0f)
            }
            loadingForeground.apply {
                setMargins(0, MARGIN_SIDES, MARGIN_BOTTOM, 0)

                setCornerRadius(CORNER_RADIUS.dpToPx(context).toFloat(), 0f, 0f, 0f)
            }
        }
    }

    private fun invalidateTop() {
        with(binding) {
            infoBoardBackgroundView.apply {
                background = createRoundedRectangleDrawable(
                    topLeft = ComponentsRadius.NONE,
                    topRight = ComponentsRadius.NONE,
                    bottomLeft = ComponentsRadius.NONE,
                    bottomRight = ComponentsRadius.NONE,
                )

                setMargins(top = 0, right = 0, bottom = MARGIN_BOTTOM, left = 0)
            }
            imageView.apply {
                setMargins(0, 0, MARGIN_BOTTOM, 0)

                setCornerRadius(
                    CORNER_RADIUS.dpToPx(context).toFloat(),
                    CORNER_RADIUS.dpToPx(context).toFloat(),
                    0f,
                    0f
                )
            }

            loadingForeground.apply {
                setMargins(0, 0, MARGIN_BOTTOM, 0)

                setCornerRadius(
                    CORNER_RADIUS.dpToPx(context).toFloat(),
                    CORNER_RADIUS.dpToPx(context).toFloat(),
                    0f,
                    0f
                )
            }
        }
    }

    private fun invalidateSolo() {
        with(binding) {
            infoBoardBackgroundView.apply {
                background = createRoundedRectangleDrawable(
                    topLeft = ComponentsRadius.NONE,
                    topRight = ComponentsRadius.NONE,
                    bottomLeft = ComponentsRadius.RADIUS_12,
                    bottomRight = ComponentsRadius.RADIUS_12,
                )

            }
            imageView.cornerRadius = CORNER_RADIUS.dpToPx(context).toFloat()
            loadingForeground.cornerRadius = CORNER_RADIUS.dpToPx(context).toFloat()
        }
    }

    private fun invalidateLoading() {
        with(binding) {
            loadingForeground.isVisible = isLoading
            loadingSpinner.isVisible = isLoading
        }
    }

    private fun invalidateIcon() {
        val color = ThemeManager.theme.palette.elementStaticWhite

        val drawable = when (messageStatus) {
            MessageStatus.NONE -> null
            MessageStatus.LOAD -> drawable(R.drawable.admiral_ic_time_outline)
            MessageStatus.SENDING -> drawable(R.drawable.admiral_ic_status_one_outline)
            MessageStatus.SEND -> drawable(R.drawable.admiral_ic_status_one_outline)
            MessageStatus.READ -> drawable(R.drawable.admiral_ic_status_two_outline)
        }

        with(binding) {
            if (drawable == null) {
                statusImage.isVisible = false
                infoBoardStatusImage.isVisible = false

                statusTime.setMargins(top = 0, right = TIME_MARGIN_END, bottom = 0, left = 0)
            } else {
                statusImage.isVisible = true
                infoBoardStatusImage.isVisible = true

                statusImage.setImageDrawable(drawable)
                infoBoardStatusImage.setImageDrawable(drawable)
            }

            statusImage.imageTintColorState = ColorState(color)
            infoBoardStatusImage.imageTintColorState = ColorState(color)
        }
    }

    private fun updateImageInfoVisibility() {
        val isInfoBoardVisible =
            imageName.isNullOrEmpty().not() && imageSize.isNullOrEmpty()
                .not() && isLoading.not()
        val isTimeNotEmpty = time.isNullOrEmpty().not()

        with(binding) {
            infoBoardGroup.isVisible = isInfoBoardVisible

            infoBoardContainer.isVisible =
                isInfoBoardVisible && isTimeNotEmpty && isShowStatus

            statusContainer.isVisible =
                isInfoBoardVisible.not() && isTimeNotEmpty && isShowStatus && isLoading.not()
        }
    }

    fun interface OnLoaderClickListener {
        fun onLoaderClicked(chatImageView: ChatImageView)
    }

    private companion object {
        const val CORNER_RADIUS = 12
        const val MARGIN_SIDES = 2
        const val MARGIN_BOTTOM = 4
        const val TIME_MARGIN_END = 4
        const val IMAGE_SIZE_ALPHA = 0.4f
    }
}
