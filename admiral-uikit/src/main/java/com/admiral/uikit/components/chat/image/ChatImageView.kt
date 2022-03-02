package com.admiral.uikit.components.chat.image

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.components.spinner.SpinnerLoading
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.setMargins
import com.admiral.uikit.view.roundedImageView.RoundedImageView

class ChatImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : android.widget.FrameLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Defines image for the view.
     */
    var drawable: Drawable? = null
        set(value) {
            field = value
            imageView.setImageDrawable(value)
        }

    /**
     * Show / hide loader [loadingSpinner] above the view.
     */
    var isLoading: Boolean = false
        set(value) {
            field = value
            invalidateLoading()
        }

    /**
     * View shown on the loading state when [isLoading] == true.
     */
    val loadingSpinner = SpinnerLoading(context).apply {
        layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER
        }
        isVisible = false
        isContrast = true
    }

    internal var imageViewType = ChatImageViewType.TOP
        set(value) {
            field = value
            invalidateImageType()
        }

    private var loadingForeground = RoundedImageView(context).apply {
        isVisible = false
        layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
        scaleType = ImageView.ScaleType.CENTER_CROP

        setImageDrawable(drawable(R.drawable.admiral_bg_message_image))
        colorFilter = PorterDuffColorFilter(ThemeManager.theme.palette.backgroundModalView, PorterDuff.Mode.SRC_IN)
    }

    private val imageView = RoundedImageView(context).apply {
        scaleType = ImageView.ScaleType.CENTER_CROP
    }

    init {
        addView(imageView)
        addView(loadingForeground)
        addView(loadingSpinner)

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
        loadingForeground.apply {
            setImageDrawable(drawable(R.drawable.admiral_bg_message_image))
            colorFilter = PorterDuffColorFilter(ThemeManager.theme.palette.backgroundModalView, PorterDuff.Mode.SRC_IN)
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
        imageView.apply {
            setMargins(0, 0, 0, MARGIN_SIDES)
            setCornerRadius(0f, 0f, 0f, CORNER_RADIUS.dpToPx(context).toFloat())
        }
        loadingForeground.apply {
            setMargins(0, 0, 0, MARGIN_SIDES)
            setCornerRadius(0f, 0f, 0f, CORNER_RADIUS.dpToPx(context).toFloat())
        }
    }

    private fun invalidateBottomLeft() {
        imageView.apply {
            setMargins(0, MARGIN_SIDES, 0, 0)
            setCornerRadius(0f, 0f, CORNER_RADIUS.dpToPx(context).toFloat(), 0f)
        }
        loadingForeground.apply {
            setMargins(0, MARGIN_SIDES, 0, 0)
            setCornerRadius(0f, 0f, CORNER_RADIUS.dpToPx(context).toFloat(), 0f)
        }
    }

    private fun invalidateRight() {
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

    private fun invalidateLeft() {
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

    private fun invalidateMiddleRight() {
        imageView.apply {
            setCornerRadius(0f, 0f, 0f, 0f)
            setMargins(0, 0, MARGIN_BOTTOM, MARGIN_SIDES)
        }
        loadingForeground.apply {
            setCornerRadius(0f, 0f, 0f, 0f)
            setMargins(0, 0, MARGIN_BOTTOM, MARGIN_SIDES)
        }
    }

    private fun invalidateMiddleLeft() {
        imageView.apply {
            setCornerRadius(0f, 0f, 0f, 0f)
            setMargins(0, MARGIN_SIDES, MARGIN_BOTTOM, 0)
        }
        loadingForeground.apply {
            setCornerRadius(0f, 0f, 0f, 0f)
            setMargins(0, MARGIN_SIDES, MARGIN_BOTTOM, 0)
        }
    }

    private fun invalidateTopRight() {
        imageView.apply {
            setMargins(0, 0, MARGIN_BOTTOM, MARGIN_SIDES)

            setCornerRadius(0f, CORNER_RADIUS.dpToPx(context).toFloat(), 0f, 0f)
        }
        loadingForeground.apply {
            setMargins(0, 0, MARGIN_BOTTOM, MARGIN_SIDES)

            setCornerRadius(0f, CORNER_RADIUS.dpToPx(context).toFloat(), 0f, 0f)
        }
    }

    private fun invalidateTopLeft() {
        imageView.apply {
            setMargins(0, MARGIN_SIDES, MARGIN_BOTTOM, 0)

            setCornerRadius(CORNER_RADIUS.dpToPx(context).toFloat(), 0f, 0f, 0f)
        }
        loadingForeground.apply {
            setMargins(0, MARGIN_SIDES, MARGIN_BOTTOM, 0)

            setCornerRadius(CORNER_RADIUS.dpToPx(context).toFloat(), 0f, 0f, 0f)
        }
    }

    private fun invalidateTop() {
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

    private fun invalidateSolo() {
        imageView.cornerRadius = CORNER_RADIUS.dpToPx(context).toFloat()
        loadingForeground.cornerRadius = CORNER_RADIUS.dpToPx(context).toFloat()
    }

    private fun invalidateLoading() {
        loadingForeground.isVisible = isLoading
        loadingSpinner.isVisible = isLoading
    }

    fun interface OnLoaderClickListener {
        fun onLoaderClicked(chatImageView: ChatImageView)
    }

    private companion object {
        const val CORNER_RADIUS = 12
        const val MARGIN_SIDES = 2
        const val MARGIN_BOTTOM = 4
    }
}
