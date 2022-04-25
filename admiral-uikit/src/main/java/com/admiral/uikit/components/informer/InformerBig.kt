package com.admiral.uikit.components.informer

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.FloatRange
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.use
import androidx.core.view.isVisible
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState
import com.admiral.uikit.components.links.Link
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.colored
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

class InformerBig @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

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
     * Color state for text.
     * States: normal, disabled.
     * In case state is null, the selected color theme will be used.
     */
    var textColors: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    /**
     * Color state for link.
     * States: normal, disabled, pressed.
     * In case state is null, the selected color theme will be used.
     */
    var linkColors: ColorState? = null
        set(value) {
            field = value
            invalidateLinkColors()
        }

    /**
     * Define pointer horizontal location.
     * Like horizontalBias in [ConstraintLayout].
     */
    @FloatRange(from = 0.0, to = 1.0)
    var pointerBias: Float = 1.0f
        set(value) {
            field = value
            invalidatePointer()
        }

    /**
     * Default style is [InformerStyle.Info]
     */
    var informerStyle: InformerStyle = InformerStyle.Info
        set(value) {
            field = value
            invalidateBackground()
            invalidateTextColors()
            invalidateLinkColors()
        }

    /**
     * Top text.
     * Gone if text is null or empty.
     */
    var headline: String? = null
        set(value) {
            field = value
            headlineTextView.text = value
            headlineTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Max lines for the [headline] text.
     */
    var headlineMaxLines: Int = Int.MAX_VALUE
        set(value) {
            field = value
            headlineTextView.maxLines = field
        }

    /**
     * Central text.
     * Gone if text is null or empty.
     */
    var info: String? = null
        set(value) {
            field = value
            infoTextView.text = value
            infoTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Max lines for the [info] text.
     */
    var infoMaxLines: Int = Int.MAX_VALUE
        set(value) {
            field = value
            infoTextView.maxLines = field
        }

    /**
     * Link text.
     * If you want to add click listener, please see [setLinkOnClickListener]
     * Gone if text is null or empty.
     */
    var link: String? = null
        set(value) {
            field = value
            linkTextView.text = value
            linkTextView.isVisible = value?.isNotEmpty() == true
        }

    /**
     * Max lines for the [link] text.
     */
    var linkMaxLines: Int = Int.MAX_VALUE
        set(value) {
            field = value
            linkTextView.maxLines = field
        }

    private val headlineTextView: TextView by lazy { findViewById(R.id.headlineTextView) }
    private val infoTextView: TextView by lazy { findViewById(R.id.infoTextView) }
    private val linkTextView: Link by lazy { findViewById(R.id.linkTextView) }
    private val infoLayout: LinearLayout by lazy { findViewById(R.id.infoLayout) }
    private val pointerImageView: ImageView by lazy { findViewById(R.id.pointerImageView) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_informer_big, this)

        infoLayout.background = drawable(R.drawable.admiral_bg_rectangle_12dp)

        parseAttrs(attrs, R.styleable.InformerBig).use {
            parseBackgroundColors(it)
            parseTextColors(it)
            parseLinkColors(it)
            parseStyle(it)

            isFocusable = true
            isClickable = true
            isEnabled = it.getBoolean(R.styleable.InformerBig_enabled, true)
            headline = it.getString(R.styleable.InformerBig_admiralHeadlineText)
            info = it.getString(R.styleable.InformerBig_admiralInfoText)
            link = it.getString(R.styleable.InformerBig_admiralLinkText)
            pointerBias = it.getFloat(R.styleable.InformerBig_admiralPointerBias, 1.0f)

            infoMaxLines = it.getInt(R.styleable.InformerBig_admiralInfoMaxLines, Int.MAX_VALUE)
            linkMaxLines = it.getInt(R.styleable.InformerBig_admiralInfoMaxLines, Int.MAX_VALUE)
            headlineMaxLines = it.getInt(R.styleable.InformerBig_admiralInfoMaxLines, Int.MAX_VALUE)
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
        headlineTextView.isEnabled = enabled
        infoTextView.isEnabled = enabled
        linkTextView.isEnabled = enabled
        infoLayout.isEnabled = enabled
        pointerImageView.isEnabled = enabled
        super.setEnabled(enabled)
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateBackground()
        invalidateTextColors()
        invalidateLinkColors()
    }

    fun setLinkOnClickListener(onClickListener: OnClickListener) {
        linkTextView.setOnClickListener(onClickListener)
    }

    private fun parseStyle(a: TypedArray) {
        informerStyle = InformerStyle.fromIndex(
            a.getInt(R.styleable.InformerBig_admiralInformerStyle, 0)
        )
    }

    private fun parseBackgroundColors(a: TypedArray) {
        backgroundColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.InformerBig_admiralBackgroundColorNormalEnabled),
            pressed = a.getColorOrNull(R.styleable.InformerBig_admiralBackgroundColorPressed),
            normalDisabled = a.getColorOrNull(R.styleable.InformerBig_admiralBackgroundColorNormalDisabled),
            focused = a.getColorOrNull(R.styleable.InformerBig_admiralBackgroundColorPressed),
        )
    }

    private fun parseTextColors(a: TypedArray) {
        textColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.InformerBig_admiralTextColorNormalEnabled),
            pressed = a.getColorOrNull(R.styleable.InformerBig_admiralTextColorPressed),
            normalDisabled = a.getColorOrNull(R.styleable.InformerBig_admiralTextColorNormalDisabled),
            focused = a.getColorOrNull(R.styleable.InformerBig_admiralTextColorPressed)
        )
    }

    private fun parseLinkColors(a: TypedArray) {
        linkColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.InformerBig_admiralLinkColorNormal),
            pressed = a.getColorOrNull(R.styleable.InformerBig_admiralLinkColorPressed),
            normalDisabled = a.getColorOrNull(R.styleable.InformerBig_admiralLinkColorDisabled),
            focused = a.getColorOrNull(R.styleable.InformerBig_admiralLinkColorPressed)
        )
    }

    private fun invalidateBackground() {
        val backgroundColor = when (informerStyle) {
            InformerStyle.Info -> ThemeManager.theme.palette.backgroundAdditionalOne
            InformerStyle.Attention -> ThemeManager.theme.palette.backgroundAttention
            InformerStyle.Success -> ThemeManager.theme.palette.backgroundSuccess
            InformerStyle.Error -> ThemeManager.theme.palette.backgroundError
        }

        val state = colorStateList(
            enabled = backgroundColors?.normalEnabled ?: backgroundColor,
            disabled = backgroundColors?.normalDisabled ?: backgroundColor,
            pressed = backgroundColors?.pressed ?: backgroundColor
        )

        infoLayout.background = drawable(R.drawable.admiral_bg_rectangle_12dp)?.colored(state)
        pointerImageView.imageTintList = state
    }

    private fun invalidateTextColors() {
        val enabled = ThemeManager.theme.palette.textPrimary
        val disabled = ThemeManager.theme.palette.textPrimary.withAlpha()

        val colorState = ColorState(
            normalEnabled = textColors?.normalEnabled ?: enabled,
            normalDisabled = textColors?.normalDisabled ?: disabled,
            pressed = textColors?.pressed ?: enabled
        )

        headlineTextView.textColor = colorState
        infoTextView.textColor = colorState
    }

    private fun invalidateLinkColors() {
        val colorState = ColorState(
            normalEnabled = linkColors?.normalEnabled ?: ThemeManager.theme.palette.textAccent,
            normalDisabled = linkColors?.normalDisabled ?: ThemeManager.theme.palette.textAccent.withAlpha(),
            pressed = linkColors?.pressed ?: ThemeManager.theme.palette.textAccentPressed
        )

        linkTextView.textColorState = colorState
    }

    private fun invalidatePointer() {
        ConstraintSet().also {
            it.clone(this)
            it.setHorizontalBias(pointerImageView.id, pointerBias)
            it.applyTo(this)
        }
    }
}