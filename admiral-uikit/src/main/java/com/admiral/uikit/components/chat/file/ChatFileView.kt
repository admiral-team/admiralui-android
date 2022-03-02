package com.admiral.uikit.components.chat.file

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.admiral.themes.ColorPaletteEnum
import com.admiral.themes.ThemeManager
import com.admiral.uikit.R
import com.admiral.uikit.components.cell.unit.IconBackgroundCellUnit
import com.admiral.uikit.components.spinner.SpinnerLoading
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.setMargins
import com.admiral.uikit.layout.LinearLayout

class ChatFileView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : android.widget.LinearLayout(context, attrs, defStyleAttr) {

    /**
     * Show / hide loader [loadingSpinner] at the left of the view.
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
            LOADER_ICON_SIZE.dpToPx(context),
            LOADER_ICON_SIZE.dpToPx(context)
        ).apply {
            gravity = Gravity.CENTER
        }
        isBackgroundColored = true
        backgroundColorPaletteEnum = ColorPaletteEnum.BACKGROUND_ACCENT
        isVisible = false
    }

    /**
     * View shown on the loading state when [isLoading] == false at the left of the view.
     */
    val iconBackgroundCellUnit = IconBackgroundCellUnit(context).apply {
        layoutParams = LayoutParams(
            LOADER_ICON_SIZE.dpToPx(context),
            LOADER_ICON_SIZE.dpToPx(context)
        ).apply {
            gravity = Gravity.CENTER
        }
        icon = drawable(R.drawable.admiral_ic_description_outline)
    }

    /**
     * In case Drawable is null, the icon will be hidden.
     */
    var icon: Drawable? = null
        set(value) {
            field = value
            iconBackgroundCellUnit.icon = drawable(R.drawable.admiral_ic_description_outline)
            iconBackgroundCellUnit.isGone = value == null
        }

    /**
     * Text for the title.
     */
    var titleText: String? = ""
        set(value) {
            field = value
            titleTextView.text = value
        }

    /**
     * Text for the subtitle.
     */
    var subtitleText: String? = ""
        set(value) {
            field = value
            subtitleTextView.text = value
        }

    internal var isOutgoing = false
        set(value) {
            field = value
            invalidateColors()
        }

    private val titleTextView = TextView(context).apply {
        textStyle = ThemeManager.theme.typography.body1
        this.setBackgroundColor(Color.TRANSPARENT)
    }

    private val subtitleTextView = TextView(context).apply {
        textStyle = ThemeManager.theme.typography.caption2
    }

    private val textsContainer = LinearLayout(context).apply {
        setMargins(0, 0, 0, TEXT_MARGIN_START)
        isBackgroundTransparent = true
        orientation = LinearLayoutCompat.VERTICAL

        addView(titleTextView)
        addView(subtitleTextView)
    }

    init {
        addView(iconBackgroundCellUnit)
        addView(loadingSpinner)
        addView(textsContainer)

        setPadding(
            PADDING_SIDES.dpToPx(context),
            PADDING_TOP_BOTTOM.dpToPx(context),
            PADDING_SIDES.dpToPx(context),
            PADDING_TOP_BOTTOM.dpToPx(context)
        )
        invalidateColors()
    }

    /**
     * Set click listener for the [loadingSpinner].
     */
    fun setLoaderClickListener(onLoaderClickListener: OnLoaderClickListener) {
        loadingSpinner.setOnClickListener {
            onLoaderClickListener.onLoaderClicked(this)
        }
    }

    /**
     * Click listener for the [loadingSpinner].
     */
    fun interface OnLoaderClickListener {
        fun onLoaderClicked(chatImageView: ChatFileView)
    }

    private fun invalidateLoading() {
        iconBackgroundCellUnit.isVisible = !isLoading
        loadingSpinner.isVisible = isLoading
    }

    private fun invalidateColors() {
        if (isOutgoing) {
            iconBackgroundCellUnit.apply {
                iconBackgroundColorNormalEnabledPalette = ColorPaletteEnum.ELEMENT_STATIC_WHITE
                iconTintColorNormalEnabledPalette = ColorPaletteEnum.BACKGROUND_ACCENT
            }

            subtitleTextView.textColorNormalEnabledPalette = ColorPaletteEnum.TEXT_ACCENT_ADDITIONAL
            titleTextView.textColorNormalEnabledPalette = ColorPaletteEnum.TEXT_STATIC_WHITE
        } else {
            iconBackgroundCellUnit.apply {
                iconBackgroundColorNormalEnabledPalette = ColorPaletteEnum.BACKGROUND_ACCENT
                iconTintColorNormalEnabledPalette = ColorPaletteEnum.ELEMENT_STATIC_WHITE
            }

            subtitleTextView.textColorNormalEnabledPalette = ColorPaletteEnum.TEXT_SECONDARY
            titleTextView.textColorNormalEnabledPalette = ColorPaletteEnum.TEXT_PRIMARY
        }

        loadingSpinner.isContrast = !isOutgoing
    }

    private companion object {
        const val LOADER_ICON_SIZE = 44
        const val TEXT_MARGIN_START = 16
        const val PADDING_SIDES = 12
        const val PADDING_TOP_BOTTOM = 8
    }
}
