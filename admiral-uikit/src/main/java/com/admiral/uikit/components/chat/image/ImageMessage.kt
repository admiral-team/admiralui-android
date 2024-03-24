package com.admiral.uikit.components.chat.image

import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import androidx.core.content.res.use
import androidx.core.view.children
import androidx.core.view.isVisible
import com.admiral.themes.ThemeManager
import com.admiral.uikit.R
import com.admiral.uikit.components.chat.MessageStatus
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.layout.FrameLayout
import com.admiral.uikit.layout.LinearLayout

class ImageMessage @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    /**
     * There are three types of MessageStatus: NONE, LOADING and DONE.
     * Depending on this parameter, the view changes the icon's drawable.
     */
    var messageStatus: MessageStatus = MessageStatus.NONE

    var isError: Boolean = false
        set(value) {
            field = value
            showMessageError(isError)
        }

    /**
     * Bottom text.
     * Component with the text and status icon is Gone if the text is null or empty.
     */
    var time: String? = null

    /**
     * Detailed information card
     * Component with image name, size, extension and status with time
     */
    var isShouldShowDetailInfo: Boolean = false

    /**
     * Container of [ChatImageView] shown at the GridLayout.
     */
    val views = mutableListOf<ChatImageView>()

    private val frameContainer = FrameLayout(context)

    private val imagesContainer = GridLayout(context)

    private val errorImageView = ImageView(context).apply {
        val attributes = LayoutParams(
            ERROR_IMAGE_VIEW_SIZE.dpToPx(context), ERROR_IMAGE_VIEW_SIZE.dpToPx(context)
        ).apply {
            gravity = Gravity.BOTTOM
        }
        layoutParams = attributes
        setImageDrawable(drawable(R.drawable.admiral_ic_error_solid)?.colored(ThemeManager.theme.palette.elementError))
        isVisible = false
    }

    init {
        frameContainer.isBackgroundTransparent = true

        parseAttrs(attrs, R.styleable.ImageMessage).use {
            time = it.getString(R.styleable.ImageMessage_admiralTimeText)
            messageStatus =
                MessageStatus.from(it.getInt(R.styleable.ImageMessage_admiralMessageStatus, 0))
        }

        imagesContainer.columnCount = 2

        frameContainer.addView(imagesContainer)

        this.addView(frameContainer)
        this.addView(errorImageView)

        layoutTransition = LayoutTransition()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initViews()
    }

    private fun initViews() {
        this.children.forEach {
            if (it is ChatImageView) {
                addImageView(it)
                it.isVisible = false
            }
        }

        children.forEachIndexed { indext, view ->
            if (view is ChatImageView) {
                removeViewAt(indext)
            }
        }
    }

    fun addImageView(imageView: ImageView?) {
        val chatImageView = ChatImageView(context).apply {
            this.drawable = imageView?.drawable
        }
        views.add(0, chatImageView)

        showViews()
    }

    fun addImageView(chatImageView: ChatImageView) {
        val chatImageViewCreated = ChatImageView(context).apply {
            this.drawable = chatImageView.drawable
        }
        views.add(0, chatImageViewCreated)

        showViews()
    }

    @Suppress("MagicNumber")
    private fun showViews() {
        imagesContainer.removeAllViews()

        if (views.count() == 1) {
            showOneView()
        }

        if (views.count() == 2) {
            showTwoViews()
        }

        if (views.count() == 3) {
            showThreeViews()
        }

        if (views.count() == 4) {
            showFourViews()
        }

        if (views.count() == 5) {
            showFiveViews()
        }

        if (views.count() == 6) {
            showSixViews()
        }

        views.forEach {
            it.time = time
            it.messageStatus = messageStatus
        }

        views.last().apply {
            isShowStatus = true
        }
    }

    @Suppress("MagicNumber")
    private fun showSixViews() {
        views.forEachIndexed { index, imageView ->
            imageView.layoutParams = getNormalLayoutParams()
            imageView.imageViewType = when (index) {
                0 -> ChatImageViewType.TOP_LEFT
                1 -> ChatImageViewType.TOP_RIGHT
                2 -> ChatImageViewType.MIDDLE_LEFT
                3 -> ChatImageViewType.MIDDLE_RIGHT
                4 -> ChatImageViewType.BOTTOM_LEFT
                else -> ChatImageViewType.BOTTOM_RIGHT
            }

            imagesContainer.addView(imageView)
        }
    }

    @Suppress("MagicNumber")
    private fun showFiveViews() {
        views.forEachIndexed { index, imageView ->
            if (index == 0) {
                imageView.imageViewType = ChatImageViewType.TOP

                imageView.layoutParams = getFullWidthLayoutParams()
            }
            if (index == 1) {
                imageView.layoutParams = getNormalLayoutParams()

                imageView.imageViewType = ChatImageViewType.MIDDLE_LEFT
            }
            if (index == 2) {
                imageView.layoutParams = getNormalLayoutParams()

                imageView.imageViewType = ChatImageViewType.MIDDLE_RIGHT
            }
            if (index == 3) {
                imageView.layoutParams = getNormalLayoutParams()

                imageView.imageViewType = ChatImageViewType.BOTTOM_LEFT
            }
            if (index == 4) {
                imageView.layoutParams = getNormalLayoutParams()

                imageView.imageViewType = ChatImageViewType.BOTTOM_RIGHT
            }

            imagesContainer.addView(imageView)
        }
    }

    private fun showFourViews() {
        views.forEachIndexed { index, imageView ->
            imageView.layoutParams = getNormalLayoutParams()
            imageView.imageViewType = when (index) {
                0 -> ChatImageViewType.TOP_LEFT
                1 -> ChatImageViewType.TOP_RIGHT
                2 -> ChatImageViewType.BOTTOM_LEFT
                else -> ChatImageViewType.BOTTOM_RIGHT
            }

            imagesContainer.addView(imageView)
        }
    }

    private fun showThreeViews() {
        views.forEachIndexed { index, imageView ->
            if (index == 0) {
                imageView.imageViewType = ChatImageViewType.TOP

                imageView.layoutParams = getFullWidthLayoutParams()
            }
            if (index == 1) {
                imageView.layoutParams = getNormalLayoutParams()

                imageView.imageViewType = ChatImageViewType.BOTTOM_LEFT
            }
            if (index == 2) {
                imageView.layoutParams = getNormalLayoutParams()

                imageView.imageViewType = ChatImageViewType.BOTTOM_RIGHT
            }

            imagesContainer.addView(imageView)
        }
    }

    private fun showTwoViews() {
        views.forEachIndexed { index, imageView ->
            imageView.layoutParams = getNormalLayoutParams()
            imageView.imageViewType = when (index) {
                0 -> ChatImageViewType.LEFT
                else -> ChatImageViewType.RIGHT
            }

            imagesContainer.addView(imageView)
        }
    }

    private fun showOneView() {
        val imageView = views[0]
        imageView.imageViewType = ChatImageViewType.SOLO
        imageView.layoutParams = getFullWidthLayoutParams()

        imagesContainer.addView(imageView)
    }

    private fun getFullWidthLayoutParams(): ViewGroup.LayoutParams {
        return GridLayout.LayoutParams(
            GridLayout.spec(GridLayout.UNDEFINED),
            GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL)
        ).apply {
            columnSpec = GridLayout.spec(0, 2)
            height = VIEW_HEIGHT.dpToPx(context)
            width = 232.dpToPx(context)
        }
    }

    private fun getNormalLayoutParams(): ViewGroup.LayoutParams {
        return GridLayout.LayoutParams(
            GridLayout.spec(GridLayout.UNDEFINED), GridLayout.spec(GridLayout.UNDEFINED, 1f)
        ).apply {
            height = VIEW_HEIGHT.dpToPx(context)
            width = VIEW_HEIGHT.dpToPx(context)
        }
    }

    private fun showMessageError(isError: Boolean) {
        errorImageView.isVisible = isError
    }

    private companion object {
        const val ERROR_IMAGE_VIEW_SIZE = 28
        const val VIEW_HEIGHT = 114
    }
}