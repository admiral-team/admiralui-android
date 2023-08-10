package com.admiral.uikit.components.informer

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
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
import com.admiral.uikit.common.util.ComponentsRadius
import com.admiral.uikit.components.imageview.ImageView
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.createRoundedColoredRectangleDrawable

class InformerSmall @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Sets the radius to the view. By default the radius is 0. You can use admiralRadius attribute from xml.
     */
    var radius: ComponentsRadius = ComponentsRadius.RADIUS_4
        set(value) {
            field = value
            invalidateBackground()
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
     * Default style is [InformerStyle.Info]
     */
    var informerStyle: InformerStyle = InformerStyle.Info
        set(value) {
            field = value
            invalidateBackground()
            invalidateTextColors()
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
     * Informer text.
     */
    var info: String? = null
        set(value) {
            field = value
            infoTextView.text = value
            infoTextView.isVisible = value?.isNotEmpty() == true
        }

    private val infoTextView: TextView by lazy { findViewById(R.id.infoTextView) }
    private val topPointerImageView: ImageView by lazy { findViewById(R.id.topPointerImageView) }
    private val bottomPointerImageView: ImageView by lazy { findViewById(R.id.bottomPointerImageView) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_informer_small, this)

        parseAttrs(attrs, R.styleable.InformerSmall).use {
            parseBackgroundColors(it)
            parseTextColors(it)
            parseStyle(it)
            parsePointerGravity(it)

            isFocusable = true
            isClickable = true
            isEnabled = it.getBoolean(R.styleable.InformerSmall_enabled, true)
            info = it.getString(R.styleable.InformerSmall_admiralInfoText)
            pointerBias = it.getFloat(R.styleable.InformerSmall_admiralPointerBias, 1.0f)

            radius = ComponentsRadius.from(
                it.getInt(
                    R.styleable.InformerSmall_admiralRadius,
                    ComponentsRadius.RADIUS_4.ordinal
                )
            )
        }

        backgroundTintList = colorStateList(
            enabled = Color.TRANSPARENT,
            disabled = Color.TRANSPARENT,
            pressed = Color.TRANSPARENT
        )
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
        infoTextView.isEnabled = enabled
        topPointerImageView.isEnabled = enabled
        bottomPointerImageView.isEnabled = enabled
        super.setEnabled(enabled)
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateBackground()
        invalidateTextColors()
    }

    private fun parseStyle(a: TypedArray) {
        informerStyle = InformerStyle.fromIndex(
            a.getInt(R.styleable.InformerSmall_admiralInformerStyle, 0)
        )
    }

    private fun parsePointerGravity(a: TypedArray) {
        val index = a.getInt(R.styleable.InformerSmall_admiralPointerGravity, 0)

        topPointerImageView.isVisible = index == 0
        bottomPointerImageView.isVisible = index != 0
    }

    private fun parseBackgroundColors(a: TypedArray) {
        backgroundColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.InformerSmall_admiralBackgroundColorNormalEnabled),
            pressed = a.getColorOrNull(R.styleable.InformerSmall_admiralBackgroundColorPressed),
            normalDisabled = a.getColorOrNull(R.styleable.InformerSmall_admiralBackgroundColorNormalDisabled),
            focused = a.getColorOrNull(R.styleable.InformerSmall_admiralBackgroundColorPressed),
        )
    }

    private fun parseTextColors(a: TypedArray) {
        textColors = ColorState(
            normalEnabled = a.getColorOrNull(R.styleable.InformerSmall_admiralTextColorNormalEnabled),
            pressed = a.getColorOrNull(R.styleable.InformerSmall_admiralTextColorPressed),
            normalDisabled = a.getColorOrNull(R.styleable.InformerSmall_admiralTextColorNormalDisabled),
            focused = a.getColorOrNull(R.styleable.InformerSmall_admiralTextColorPressed)
        )
    }

    private fun invalidateBackground() {
        val backgroundColor = when (informerStyle) {
            InformerStyle.Info -> ThemeManager.theme.palette.backgroundAdditionalOne
            InformerStyle.Attention -> ThemeManager.theme.palette.backgroundAttention
            InformerStyle.Success -> ThemeManager.theme.palette.backgroundSuccess
            InformerStyle.Error -> ThemeManager.theme.palette.backgroundError
        }

        val colorState = ColorState(
            normalEnabled = backgroundColors?.normalEnabled ?: backgroundColor,
            normalDisabled = backgroundColors?.normalDisabled ?: backgroundColor,
            pressed = backgroundColors?.pressed ?: backgroundColor
        )

        infoTextView.background = createRoundedColoredRectangleDrawable(radius, colorState)
        topPointerImageView.imageTintColorState = colorState
        bottomPointerImageView.imageTintColorState = colorState
    }

    private fun invalidateTextColors() {
        val enabled = ThemeManager.theme.palette.textPrimary
        val disabled = ThemeManager.theme.palette.textPrimary.withAlpha()

        val colorState = ColorState(
            normalEnabled = textColors?.normalEnabled ?: enabled,
            normalDisabled = textColors?.normalDisabled ?: disabled,
            pressed = textColors?.pressed ?: enabled
        )

        infoTextView.textColor = colorState
    }

    private fun invalidatePointer() {
        ConstraintSet().also {
            it.clone(this)
            it.setHorizontalBias(topPointerImageView.id, pointerBias)
            it.setHorizontalBias(bottomPointerImageView.id, pointerBias)
            it.applyTo(this)
        }
    }
}