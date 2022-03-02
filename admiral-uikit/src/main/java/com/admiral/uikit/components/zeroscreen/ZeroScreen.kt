package com.admiral.uikit.components.zeroscreen

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.content.res.use
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.button.Button
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.setMargins

class ZeroScreen @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Drawable placed at the top.
     */
    var icon: Drawable? = null
        set(value) {
            field = value
            invalidateIcon()
            invalidateMargins()
        }

    /**
     * Color state for icon.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var iconTintColor: ColorState? = null
        set(value) {
            field = value
            invalidateIcon()
        }

    /**
     * Bolt text placed under the icon.
     */
    var titleText: String? = null
        set(value) {
            field = value
            titleTextView.text = value
            titleTextView.isGone = value.isNullOrEmpty()
            invalidateMargins()
        }

    /**
     * Normal text placed under the titleText.
     */
    var subtitleText: String? = null
        set(value) {
            field = value
            subtitleTextView.text = value
            subtitleTextView.isGone = value.isNullOrEmpty()
            invalidateMargins()
        }

    /**
     * Color for the title text.
     */
    @ColorInt
    var subtitleTextColor: Int? = null
        set(value) {
            field = value
            invalidateSubtitleColor()
        }

    /**
     * Text for the only button, placed at the bottom.
     */
    var buttonText: String? = null
        set(value) {
            field = value
            button.text = value
            button.isGone = value.isNullOrEmpty()
        }

    val titleTextView: TextView by lazy { findViewById(R.id.admiralZeroScreenTitle) }
    val subtitleTextView: TextView by lazy { findViewById(R.id.admiralZeroScreenSubtitle) }
    val button: Button by lazy { findViewById(R.id.admiralZeroScreenButton) }

    private val iconImageView: ImageView by lazy { findViewById(R.id.admiralZeroScreenIcon) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_zero_screen, this)
        orientation = VERTICAL

        parseAttrs(attrs, R.styleable.ZeroScreen).use {
            parseDrawableColors(it)

            icon = it.getDrawable(R.styleable.ZeroScreen_admiralIcon)
            titleText = it.getString(R.styleable.ZeroScreen_admiralTitleText)
            subtitleText = it.getString(R.styleable.ZeroScreen_admiralSubtitleText)
            buttonText = it.getString(R.styleable.ZeroScreen_admiralButtonText)

            subtitleTextColor = it.getColorOrNull(R.styleable.ZeroScreen_admiralSubtitleColorNormalEnabled)
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
        invalidateIcon()
        invalidateSubtitleColor()
    }

    private fun parseDrawableColors(a: TypedArray) {
        iconTintColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.ZeroScreen_admiralIconTintColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.ZeroScreen_admiralIconTintColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.ZeroScreen_admiralIconTintColorNormalDisabled,
            )
        )
    }

    private fun invalidateIcon() {
        iconImageView.setImageDrawable(
            icon?.colored(
                colorStateList(
                    enabled = iconTintColor?.normalEnabled ?: ThemeManager.theme.palette.elementSuccess,
                    disabled = iconTintColor?.normalDisabled ?: ThemeManager.theme.palette.elementSuccess.withAlpha(),
                    pressed = iconTintColor?.pressed ?: ThemeManager.theme.palette.elementSuccess,
                )
            )
        )

        iconImageView.isVisible = icon != null
    }

    private fun invalidateSubtitleColor() {
        subtitleTextView.textColor =
            ColorState(normalEnabled = subtitleTextColor ?: ThemeManager.theme.palette.textSecondary)
    }

    private fun invalidateMargins() {
        if (titleText != null && subtitleText != null) {
            subtitleTextView.setMargins(MARGIN_SUBTITLE_TOP, MARGIN_SIDES, 0, MARGIN_SIDES)
        } else if (titleText == null && subtitleText != null) {
            subtitleTextView.setMargins(0, MARGIN_SIDES, 0, MARGIN_SIDES)
        }

        if (icon != null && titleText != null) {
            titleTextView.setMargins(MARGIN_TITLE_TOP, MARGIN_SIDES, 0, MARGIN_SIDES)
        } else if (icon == null && titleText != null) {
            titleTextView.setMargins(0, MARGIN_SIDES, 0, MARGIN_SIDES)
        }
    }

    private companion object {
        const val MARGIN_TITLE_TOP = 57
        const val MARGIN_SUBTITLE_TOP = 28
        const val MARGIN_SIDES = 16
    }
}