package com.admiral.uikit.components.button

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.coloredDrawable
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.ext.ripple
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState

class ButtonGooglePay @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    var backgroundColor: ColorState? = null
        set(value) {
            field = value
            invalidateBackground()
        }

    var textColors: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    private val startTextView: TextView by lazy { findViewById<TextView>(R.id.tvStart) }
    private val endTextView: TextView by lazy { findViewById<TextView>(R.id.tvEnd) }
    private val googleIcon: ImageView by lazy { findViewById<ImageView>(R.id.googleIcon) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_btn_google_pay, this)

        startTextView.text = context.getString(R.string.google_play_button_add_into)
        endTextView.text = context.getString(R.string.google_play_button_pay)
        googleIcon.setImageDrawable(drawable(R.drawable.admiral_ic_google_pay))

        background = drawable(R.drawable.admiral_bg_rectangle_clickable_8dp)
        isClickable = true
        isFocusable = true

        setPadding(
            pixels(R.dimen.module_x3),
            pixels(R.dimen.module_x3),
            pixels(R.dimen.module_x3),
            pixels(R.dimen.module_x3),
        )

        parseAttrs(attrs, R.styleable.ButtonGooglePay).use {
            parseBackgroundColors(it)
            parseTextColors(it)
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

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        googleIcon.alpha = if (enabled) ALPHA_ENABLED else ALPHA_DISABLED
        startTextView.isEnabled = enabled
        endTextView.isEnabled = enabled
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateBackground()
        invalidateTextColors()
    }

    private fun parseBackgroundColors(a: TypedArray) {
        backgroundColor = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.ButtonGooglePay_admiralBackgroundColorNormalEnabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.ButtonGooglePay_admiralBackgroundColorPressed
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.ButtonGooglePay_admiralBackgroundColorNormalDisabled
            ),
            focused = a.getColorOrNull(
                R.styleable.ButtonGooglePay_admiralBackgroundColorPressed
            )
        )
    }

    private fun parseTextColors(a: TypedArray) {
        textColors = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.ButtonGooglePay_admiralTextColorNormalEnabled
            ),
            pressed = a.getColorOrNull(
                R.styleable.ButtonGooglePay_admiralTextColorPressed
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.ButtonGooglePay_admiralTextColorNormalDisabled
            ),
            focused = a.getColorOrNull(
                R.styleable.ButtonGooglePay_admiralTextColorPressed
            )
        )
    }

    private fun invalidateBackground() {
        val rippleColor = backgroundColor?.pressed ?: ThemeManager.theme.palette.backgroundBasic.withAlpha(RIPPLE_ALPHA)
        val mask = context.drawable(R.drawable.admiral_bg_btn_mask)
        val contentStateList = colorStateList(
            enabled = backgroundColor?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
            disabled = backgroundColor?.normalDisabled ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = backgroundColor?.pressed ?: ThemeManager.theme.palette.textPrimary
        )
        val content = context.coloredDrawable(R.drawable.admiral_bg_btn_mask, contentStateList)

        background = ripple(rippleColor, content, mask)
    }

    private fun invalidateTextColors() {
        val textColor = ColorState(
            normalEnabled = textColors?.normalEnabled ?: ThemeManager.theme.palette.backgroundBasic,
            normalDisabled = textColors?.normalDisabled ?: ThemeManager.theme.palette.backgroundBasic.withAlpha(),
            pressed = textColors?.pressed ?: ThemeManager.theme.palette.backgroundBasic
        )

        startTextView.textColor = textColor
        endTextView.textColor = textColor
    }

    private companion object {
        const val RIPPLE_ALPHA = 0.1F
        const val ALPHA_ENABLED = 1F
        const val ALPHA_DISABLED = 0.6F
    }
}