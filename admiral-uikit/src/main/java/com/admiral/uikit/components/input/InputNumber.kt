package com.admiral.uikit.components.input

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import androidx.core.view.isGone
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.ext.colorStateList
import com.admiral.uikit.ext.coloredDrawable
import com.admiral.uikit.ext.drawable
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs
import com.admiral.uikit.ext.ripple
import com.admiral.uikit.common.ext.withAlpha
import com.admiral.uikit.common.foundation.ColorState

class InputNumber @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Optional text.
     */
    var optionalText: String? = null
        set(value) {
            field = value
            optionalLabelTextView.text = value
            optionalLabelTextView.isGone = value.isNullOrEmpty()
        }

    /**
     * Current value.
     */
    var value: Int = 0
        set(value) {
            onValueChange?.invoke(field, value)
            field = value
            valueTextView.text = value.toString()
        }

    /**
     * Max value of input number.
     */
    var maxValue: Int = MAX_VALUE
        set(maxValue) {
            if (maxValue > MAX_VALUE) {
                throw IllegalArgumentException("The value must be less or equal then MAX_VALUE: $MAX_VALUE")
            }

            field = maxValue

            if (maxValue < value) {
                value = maxValue
            }
        }

    /**
     * Min value of input number.
     */
    var minValue: Int = MIN_VALUE
        set(minValue) {
            if (minValue < MIN_VALUE) {
                throw IllegalArgumentException("The value must be greater or equal then MIN_VALUE: $MIN_VALUE")
            }

            field = minValue

            if (minValue > value) {
                value = minValue
            }
        }

    /**
     * Color state for all text elements in [InputNumber].
     * States: normal, disabled.
     * In case state is null, the selected color theme will be used.
     */
    var textColors: ColorState? = null
        set(value) {
            field = value
            invalidateTextColors()
        }

    /**
     * In case tint color is null, the selected color theme will be used.
     * States: normal, disabled, pressed.
     */
    var iconBackgroundColors: ColorState? = null
        set(value) {
            field = value
            invalidateIconBackgroundColors()
        }

    /**
     * In case tint color is null, the selected color theme will be used.
     * States: normal, disabled, pressed.
     */
    var iconTintColors: ColorState? = null
        set(value) {
            field = value
            invalidateIconTintColors()
        }

    /**
     * In case Drawable is null, the default icon will be used.
     */
    var incrementIcon: Drawable? = null
        set(value) {
            field = value
            incrementImageView.setImageDrawable(value ?: context.drawable(R.drawable.admiral_ic_plus_outline))
        }

    /**
     * In case Drawable is null, the default icon will be used.
     */
    var decrementIcon: Drawable? = null
        set(value) {
            field = value
            decrementImageView.setImageDrawable(value ?: context.drawable(R.drawable.admiral_ic_minus_outline))
        }

    /**
     * Invoked when the value changes.
     * The value is always between [MIN_VALUE] and [MAX_VALUE]).
     */
    var onValueChange: ((old: Int, new: Int) -> Unit)? = null

    private val optionalLabelTextView: TextView by lazy { findViewById(R.id.optionalLabelTextView) }
    private val valueTextView: TextView by lazy { findViewById(R.id.valueTextView) }
    private val decrementImageView: ImageView by lazy { findViewById(R.id.decrementImageView) }
    private val incrementImageView: ImageView by lazy { findViewById(R.id.incrementImageView) }

    private var autoIncrement = false
    private var autoDecrement = false

    private val incrementRunnable: Runnable = object : Runnable {
        override fun run() {
            if (autoIncrement) {
                increment()
                handler?.postDelayed(this, DELAY)
            }
        }
    }

    private val decrementRunnable: Runnable = object : Runnable {
        override fun run() {
            if (autoDecrement) {
                decrement()
                handler?.postDelayed(this, DELAY)
            }
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_input_number, this)

        parseAttrs(attrs, R.styleable.InputNumber).use {
            parseTextColors(it)
            parseTexts(it)
            parseIconBackgroundColors(it)
            parseIconColors(it)
            parseIcons(it)

            isEnabled = it.getBoolean(R.styleable.InputNumber_enabled, true)
        }

        setupIncrementView()
        setupDecrementView()

        enableIncrementImageView(value != maxValue)
        enableDecrementImageView(value != minValue)
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
        enableIncrementImageView(enabled && value != maxValue)
        enableDecrementImageView(enabled && value != minValue)
        optionalLabelTextView.isEnabled = enabled
        valueTextView.isEnabled = enabled
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateTextColors()
        invalidateIconBackgroundColors()
        invalidateIconTintColors()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupIncrementView() {
        incrementImageView.setOnClickListener {
            increment(isSingleTap = true)
        }

        incrementImageView.setOnLongClickListener {
            autoIncrement = true
            enableDecrementImageView(false)
            handler.postDelayed(incrementRunnable, DELAY)

            return@setOnLongClickListener true
        }

        incrementImageView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP && autoIncrement) {
                enableDecrementImageView(true)
                autoIncrement = false
            }

            return@setOnTouchListener false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupDecrementView() {
        decrementImageView.setOnClickListener {
            decrement(isSingleTap = true)
        }

        decrementImageView.setOnLongClickListener {
            autoDecrement = true
            enableIncrementImageView(false)
            handler.postDelayed(decrementRunnable, DELAY)

            return@setOnLongClickListener true
        }

        decrementImageView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP && autoDecrement) {
                enableIncrementImageView(true)
                autoDecrement = false
            }

            return@setOnTouchListener false
        }
    }

    private fun calculateModifier(isSingleTap: Boolean): Int {
        if (isSingleTap || value == 0) return DEFAULT_MODIFIER

        var tempValue = value
        var modifier = DEFAULT_MODIFIER

        while (tempValue % DEFAULT_MULTIPLIER == 0) {
            modifier *= DEFAULT_MULTIPLIER
            tempValue /= DEFAULT_MULTIPLIER
        }

        return modifier
    }

    private fun increment(isSingleTap: Boolean = false) {
        val newValue = value + calculateModifier(isSingleTap)

        if (newValue <= maxValue) {
            value = newValue
        }

        enableIncrementImageView(value != maxValue)
        enableDecrementImageView(value != minValue)
    }

    private fun decrement(isSingleTap: Boolean = false) {
        val newValue = value - calculateModifier(isSingleTap)

        if (newValue >= minValue) {
            value = newValue
        }

        enableIncrementImageView(value != maxValue)
        enableDecrementImageView(value != minValue)
    }

    private fun enableDecrementImageView(isEnabled: Boolean) {
        val tintNormalColor = iconTintColors?.normalEnabled ?: ThemeManager.theme.palette.elementPrimary
        val backgroundNormalColor =
            iconBackgroundColors?.normalEnabled ?: ThemeManager.theme.palette.backgroundAdditionalOne

        val tintDisabledColor = iconTintColors?.normalEnabled ?: ThemeManager.theme.palette.elementPrimary.withAlpha()
        val backgroundDisabledColor =
            iconBackgroundColors?.normalEnabled ?: ThemeManager.theme.palette.backgroundAdditionalOne.withAlpha()

        val tintColorState: ColorStateList
        val backgroundColorState: ColorStateList

        if (isEnabled) {
            tintColorState = ColorStateList.valueOf(tintNormalColor)
            backgroundColorState = ColorStateList.valueOf(backgroundNormalColor)
        } else {
            tintColorState = ColorStateList.valueOf(tintDisabledColor)
            backgroundColorState = ColorStateList.valueOf(backgroundDisabledColor)
        }

        decrementImageView.isEnabled = isEnabled
        decrementImageView.imageTintList = tintColorState
        decrementImageView.backgroundTintList = backgroundColorState
    }

    private fun enableIncrementImageView(isEnabled: Boolean) {
        val tintNormalColor = iconTintColors?.normalEnabled ?: ThemeManager.theme.palette.elementPrimary
        val backgroundNormalColor =
            iconBackgroundColors?.normalEnabled ?: ThemeManager.theme.palette.backgroundAdditionalOne

        val tintDisabledColor = iconTintColors?.normalDisabled ?: ThemeManager.theme.palette.elementPrimary.withAlpha()
        val backgroundDisabledColor =
            iconBackgroundColors?.normalDisabled ?: ThemeManager.theme.palette.backgroundAdditionalOne.withAlpha()

        val tintColorState: ColorStateList
        val backgroundColorState: ColorStateList

        if (isEnabled) {
            tintColorState = ColorStateList.valueOf(tintNormalColor)
            backgroundColorState = ColorStateList.valueOf(backgroundNormalColor)
        } else {
            tintColorState = ColorStateList.valueOf(tintDisabledColor)
            backgroundColorState = ColorStateList.valueOf(backgroundDisabledColor)
        }

        incrementImageView.isEnabled = isEnabled
        incrementImageView.imageTintList = tintColorState
        incrementImageView.backgroundTintList = backgroundColorState
    }

    private fun parseIcons(a: TypedArray) {
        decrementIcon = a.getDrawable(R.styleable.InputNumber_admiralDecrementIcon)
        incrementIcon = a.getDrawable(R.styleable.InputNumber_admiralIncrementIcon)
    }

    private fun parseIconColors(a: TypedArray) {
        iconTintColors = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.InputNumber_admiralIconTintColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.InputNumber_admiralIconTintColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.InputNumber_admiralIconTintColorNormalDisabled,
            )
        )
    }

    private fun parseIconBackgroundColors(a: TypedArray) {
        iconBackgroundColors = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.InputNumber_admiralIconBackgroundColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.InputNumber_admiralIconBackgroundColorPressed,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.InputNumber_admiralIconBackgroundColorNormalDisabled,
            )
        )
    }

    private fun parseTexts(a: TypedArray) {
        optionalText = a.getString(R.styleable.InputNumber_admiralTextOptional)
        value = a.getInt(R.styleable.InputNumber_admiralInputValue, 0)
        minValue = a.getInt(R.styleable.InputNumber_admiralInputMinValue, MIN_VALUE)
        maxValue = a.getInt(R.styleable.InputNumber_admiralInputMaxValue, MAX_VALUE)
    }

    private fun parseTextColors(a: TypedArray) {
        textColors = ColorState(
            normalEnabled = a.getColorOrNull(
                R.styleable.InputNumber_admiralTextColorNormalEnabled,
            ),
            pressed = a.getColorOrNull(
                R.styleable.InputNumber_admiralTextColorNormalEnabled,
            ),
            normalDisabled = a.getColorOrNull(
                R.styleable.InputNumber_admiralTextColorNormalDisabled,
            )
        )
    }

    private fun invalidateTextColors() {
        val textColorStateList = colorStateList(
            enabled = textColors?.normalEnabled ?: ThemeManager.theme.palette.textPrimary,
            disabled = textColors?.normalDisabled ?: ThemeManager.theme.palette.textPrimary.withAlpha(),
            pressed = textColors?.pressed ?: ThemeManager.theme.palette.textPrimary
        )

        optionalLabelTextView.setTextColor(textColorStateList)
        valueTextView.setTextColor(textColorStateList)
    }

    private fun invalidateIconBackgroundColors() {
        val rippleColor = iconBackgroundColors?.pressed ?: ThemeManager.theme.palette.textPrimary.withAlpha(
            RIPPLE_ALPHA
        )
        val mask = context.drawable(R.drawable.admiral_bg_round)
        val contentStateList = colorStateList(
            enabled = iconBackgroundColors?.normalEnabled ?: ThemeManager.theme.palette.backgroundAdditionalOne,
            disabled = iconBackgroundColors?.normalDisabled
                ?: ThemeManager.theme.palette.backgroundAdditionalOne.withAlpha(),
            pressed = iconBackgroundColors?.pressed ?: ThemeManager.theme.palette.backgroundAdditionalOne
        )
        val content = context.coloredDrawable(R.drawable.admiral_bg_round, contentStateList)

        decrementImageView.backgroundTintList = contentStateList
        incrementImageView.backgroundTintList = contentStateList

        decrementImageView.background = ripple(rippleColor, content, mask)
        incrementImageView.background = ripple(rippleColor, content, mask)
    }

    private fun invalidateIconTintColors() {
        val iconTintColorStateList = colorStateList(
            enabled = iconTintColors?.normalEnabled ?: ThemeManager.theme.palette.elementPrimary,
            disabled = iconTintColors?.normalDisabled ?: ThemeManager.theme.palette.elementPrimary.withAlpha(),
            pressed = iconTintColors?.pressed ?: ThemeManager.theme.palette.elementPrimary
        )

        decrementImageView.imageTintList = iconTintColorStateList
        incrementImageView.imageTintList = iconTintColorStateList
    }

    private companion object {
        private const val DELAY = 300L
        private const val MIN_VALUE = -9999
        private const val MAX_VALUE = 99999
        private const val DEFAULT_MODIFIER = 1
        private const val DEFAULT_MULTIPLIER = 10
        private const val RIPPLE_ALPHA = 0.1f
    }
}