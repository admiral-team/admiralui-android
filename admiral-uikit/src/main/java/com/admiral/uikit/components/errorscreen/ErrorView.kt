package com.admiral.uikit.components.errorscreen

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.content.res.use
import androidx.core.view.isGone
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.components.button.Button
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.common.foundation.ColorState

class ErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Secondary text placed above the button.
     */
    var titleText: String? = null
        set(value) {
            field = value
            titleTextView.text = value
            titleTextView.isGone = value.isNullOrEmpty()
        }

    /**
     * Color for the title text.
     */
    @ColorInt
    var titleTextColor: Int? = null
        set(value) {
            field = value
            invalidateTitleColor()
        }

    /**
     * Text for the button, placed at the bottom.
     */
    var buttonText: String? = null
        set(value) {
            field = value
            button.text = value
            button.isGone = value.isNullOrEmpty()
        }

    val titleTextView: TextView by lazy { findViewById(R.id.admiralErrorScreenTitle) }
    val button: Button by lazy { findViewById(R.id.admiralErrorScreenButton) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_error_view, this)
        orientation = VERTICAL

        parseAttrs(attrs, R.styleable.ErrorScreen).use {
            titleText = it.getString(R.styleable.ErrorScreen_admiralTitleText)
            buttonText = it.getString(R.styleable.ErrorScreen_admiralButtonText)

            titleTextColor = it.getColorOrNull(R.styleable.ErrorScreen_admiralTitleColorNormalEnabled)
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
        invalidateTitleColor()
    }

    private fun invalidateTitleColor() {
        titleTextView.textColor =
            ColorState(normalEnabled = titleTextColor ?: ThemeManager.theme.palette.textSecondary)
    }
}