package com.admiral.uikit.components.tabs

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.pixels
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.layout.LinearLayout

class InformerTabContent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    var iconDrawable: Drawable? = null
        set(value) {
            field = value
            invalidateIconDrawable()
        }

    /**
     * Color state for background.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var backgroundColors: ColorState? = null
        set(value) {
            field = value
            invalidateBackground()
        }

    /**
     * Color state for title.
     * States: normal, disabled.
     * In case state is null, the selected color theme will be used.
     */
    var titleTextColors: ColorState? = null
        set(value) {
            field = value
            invalidateTitleColors()
        }

    /**
     * Color state for summ.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var summTextColors: ColorState? = null
        set(value) {
            field = value
            invalidateSummColors()
        }

    /**
     * Color state for the drawable icon.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var drawableColorTint: ColorState? = null
        set(value) {
            field = value
            invalidateIconDrawable()
        }

    /**
     * Color state for subtitle.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var subtitleTextColors: ColorState? = null
        set(value) {
            field = value
            invalidateSubtitleColors()
        }

    /**
     * Top text.
     * Gone if text is null or empty.
     */
    var titleText: String? = null
        set(value) {
            field = value
            titleTextView.text = value
            titleTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Central text.
     * Gone if text is null or empty.
     */
    var summText: String? = null
        set(value) {
            field = value
            summTextView.text = value
            summTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Subtitle text.
     * Gone if text is null or empty.
     */
    var subtitleText: String? = null
        set(value) {
            field = value
            subtitleTextView.text = value
            subtitleTextView.isVisible = value?.isNotEmpty() == true
        }

    private val titleTextView: TextView by lazy { findViewById(R.id.informetTitle) }
    private val summTextView: TextView by lazy { findViewById(R.id.informerSumm) }
    private val subtitleTextView: TextView by lazy { findViewById(R.id.informerSubtitle) }
    private val infoLayout: LinearLayout by lazy { findViewById(R.id.informerRoot) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_tab_informer_content, this)

        parseAttrs(attrs, R.styleable.InformerTabContent).use {
            parseDrawable(it)
            parseText(it)
            parseTextColors(it)
            parseBackgroundColors(it)

            isEnabled = it.getBoolean(R.styleable.InformerTabContent_enabled, true)
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
        titleTextView.isEnabled = enabled
        summTextView.isEnabled = enabled
        subtitleTextView.isEnabled = enabled
        infoLayout.isEnabled = enabled
        super.setEnabled(enabled)
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateBackground()
        invalidateTitleColors()
        invalidateSummColors()
        invalidateSubtitleColors()
        invalidateIconDrawable()
    }

    private fun parseDrawable(a: TypedArray) {
        iconDrawable = a.getDrawable(R.styleable.InformerTabContent_admiralIconDrawable)

        drawableColorTint = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.InformerTabContent_admiralIconTintColorNormalEnabled),
            normalDisabled = a.getColorOrNull(R.styleable.InformerTabContent_admiralIconTintColorNormalDisabled),
            pressed = a.getColorOrNull(R.styleable.InformerTabContent_admiralIconTintColorPressed)
        )
    }

    private fun parseText(a: TypedArray) {
        titleText = a.getString(R.styleable.InformerTabContent_admiralTitleText)
        summText = a.getString(R.styleable.InformerTabContent_admiralSummText)
        subtitleText = a.getString(R.styleable.InformerTabContent_admiralSubtitleText)
    }

    private fun parseBackgroundColors(a: TypedArray) {
        backgroundColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.InformerTabContent_admiralBackgroundColorNormalEnabled),
            pressed = a.getColorOrNull(R.styleable.InformerTabContent_admiralBackgroundColorPressed),
            normalDisabled = a.getColorOrNull(R.styleable.InformerTabContent_admiralBackgroundColorNormalDisabled),
            focused = a.getColorOrNull(R.styleable.InformerTabContent_admiralBackgroundColorPressed),
        )
    }

    private fun parseTextColors(a: TypedArray) {
        titleTextColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.InformerTabContent_admiralTextColorNormalEnabled),
            normalDisabled = a.getColorOrNull(R.styleable.InformerTabContent_admiralTextColorNormalDisabled),
            pressed = a.getColorOrNull(R.styleable.InformerTabContent_admiralTextColorPressed),
        )

        summTextColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.InformerTabContent_admiralSummColorNormalEnabled),
            normalDisabled = a.getColorOrNull(R.styleable.InformerTabContent_admiralSubtitleColorNormalDisabled),
            pressed = a.getColorOrNull(R.styleable.InformerTabContent_admiralSummColorPressed)
        )

        subtitleTextColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.InformerTabContent_admiralSubtitleColorNormalEnabled),
            normalDisabled = a.getColorOrNull(R.styleable.InformerTabContent_admiralSubtitleColorNormalDisabled),
            pressed = a.getColorOrNull(R.styleable.InformerTabContent_admiralSubtitleColorPressed)
        )
    }

    private fun invalidateBackground() {
        val state = ColorState(
            normalEnabled = backgroundColors?.normalEnabled ?: ThemeManager.theme.palette.backgroundAdditionalOne,
            normalDisabled = backgroundColors?.normalDisabled ?: backgroundColors?.normalEnabled
            ?: ThemeManager.theme.palette.backgroundAdditionalOne,
            pressed = backgroundColors?.pressed ?: backgroundColors?.normalEnabled
            ?: ThemeManager.theme.palette.backgroundAdditionalOne
        )

        infoLayout.backgroundColors = state
    }

    private fun invalidateTitleColors() {
        val colorState = ColorState(
            normalEnabled = titleTextColors?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = titleTextColors?.normalDisabled ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = titleTextColors?.pressed ?: ThemeManager.theme.palette.textPrimary
        )

        titleTextView.textColor = colorState
    }

    private fun invalidateSummColors() {
        val colorState = ColorState(
            normalEnabled = summTextColors?.normalEnabled ?: ThemeManager.theme.palette.textAccent,
            normalDisabled = summTextColors?.normalDisabled ?: ThemeManager.theme.palette.textAccent.withAlpha(),
            pressed = summTextColors?.pressed ?: ThemeManager.theme.palette.textAccentPressed
        )

        summTextView.textColor = colorState
    }

    private fun invalidateSubtitleColors() {
        val colorState = ColorState(
            normalEnabled = subtitleTextColors?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
            normalDisabled = subtitleTextColors?.normalDisabled ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = subtitleTextColors?.pressed ?: ThemeManager.theme.palette.textPrimary
        )

        subtitleTextView.textColor = colorState
    }

    private fun invalidateIconDrawable() {
        summTextView.compoundDrawablePadding = pixels(R.dimen.module_x2)
        iconDrawable?.colored(
            colorStateList(
                enabled = drawableColorTint?.normalEnabled ?: ThemeManager.theme.palette.elementPrimary,
                disabled = drawableColorTint?.normalDisabled ?: ThemeManager.theme.palette.elementPrimary.withAlpha(),
                pressed = drawableColorTint?.pressed ?: ThemeManager.theme.palette.elementPrimary
            )
        )
        summTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, iconDrawable, null)
    }
}