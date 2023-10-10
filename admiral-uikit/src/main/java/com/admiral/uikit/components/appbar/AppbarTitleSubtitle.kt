package com.admiral.uikit.components.appbar

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.use
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.dpToPx
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.ext.setMargins
import com.admiral.uikit.core.foundation.ColorState

class AppbarTitleSubtitle @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : Toolbar(ContextThemeWrapper(context, R.style.Widget_AppCompat_Toolbar), attrs, defStyleAttr), ThemeObserver {

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

    /**
     * Color for the title text view.
     * In case color is null, the selected color theme will be used.
     */
    @ColorRes
    var titleTextColor: Int? = null
        set(value) {
            field = value
            invalidateTitleColor()
        }

    /**
     * Color for the subtitle text view.
     * In case color is null, the selected color theme will be used.
     */
    @ColorRes
    var subtitleTextColor: Int? = null
        set(value) {
            field = value
            invalidateSubtitleColor()
        }

    /**
     * Color for the background.
     * In case color is null, the selected color theme will be used.
     */
    @ColorRes
    var backgroundColor: Int? = null
        set(value) {
            field = value
            invalidateBackgroundColor()
        }

    /**
     * Drawable for the title text view.
     * Placed at the end of the view.
     * In case drawable is null, nothing will be shown.
     */
    var titleDrawableEndIcon: Drawable? = null
        set(value) {
            field = value
            setDrawableEnd(titleDrawableEndIcon)
        }

    /**
     * Subtitle text view.
     * Placed at the top of the view.
     */
    val subtitleTextView = TextView(context).apply {
        textStyle = ThemeManager.theme.typography.overline
    }

    /**
     * Subtitle text view.
     * Placed at the bottom of the view.
     */
    val titleTextView = TextView(context).apply {
        textStyle = ThemeManager.theme.typography.title2
        setMargins(TITLE_MARGIN_TOP, TITLE_MARGIN_END, 0, 0)
    }

    init {
        parseAttrs(attrs, R.styleable.AppbarTitleSubtitle).use {
            titleText = it.getString(R.styleable.AppbarTitleSubtitle_admiralTitleText)
            subtitleText = it.getString(R.styleable.AppbarTitleSubtitle_admiralSubtitleText)

            titleTextColor = it.getColorOrNull(R.styleable.AppbarTitleSubtitle_admiralTitleTextColor)
            subtitleTextColor = it.getColorOrNull(R.styleable.AppbarTitleSubtitle_admiralSubtitleTextColor)
            backgroundColor =
                it.getColorOrNull(R.styleable.AppbarTitleSubtitle_admiralBackgroundColorNormalEnabled)

            titleDrawableEndIcon = it.getDrawable(R.styleable.AppbarTitleSubtitle_admiralIcon)
        }

        addView(LinearLayout(context).apply {
            setBackgroundColor(ThemeManager.theme.palette.backgroundAccent)
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            setMargins(0, 0, 0, VIEW_MARGIN_START)
            orientation = LinearLayout.VERTICAL

            addView(subtitleTextView)
            addView(titleTextView)
        })
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
        invalidateBackgroundColor()
        invalidateSubtitleColor()
        invalidateTitleColor()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, VIEW_HEIGHT.dpToPx(context))
        setMeasuredDimension(measuredWidth, VIEW_HEIGHT.dpToPx(context))
    }

    private fun invalidateBackgroundColor() {
        background = ColorDrawable(backgroundColor ?: ThemeManager.theme.palette.backgroundAccent)
    }

    private fun invalidateSubtitleColor() {
        subtitleTextView.textColor = ColorState(
            normalEnabled = subtitleTextColor ?: ThemeManager.theme.palette.textStaticWhite
        )
    }

    private fun invalidateTitleColor() {
        titleTextView.textColor = ColorState(
            normalEnabled = titleTextColor ?: ThemeManager.theme.palette.textStaticWhite
        )
    }

    private fun setDrawableEnd(drawable: Drawable?) {
        drawable?.colored(titleTextColor ?: ThemeManager.theme.palette.elementStaticWhite).also {
            titleTextView.compoundDrawablePadding = pixels(R.dimen.module_x1)
            titleTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, it, null)
        }
    }

    private companion object {
        const val TITLE_MARGIN_TOP = 32
        const val TITLE_MARGIN_END = 24

        const val VIEW_HEIGHT = 128
        const val VIEW_MARGIN_START = 24
    }
}