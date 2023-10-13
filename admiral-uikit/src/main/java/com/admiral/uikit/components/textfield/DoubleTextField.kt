package com.admiral.uikit.components.textfield

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.use
import androidx.core.view.isGone
import com.admiral.themes.Theme
import com.admiral.themes.ThemeManager
import com.admiral.themes.ThemeObserver
import com.admiral.uikit.R
import com.admiral.uikit.core.ext.withAlpha
import com.admiral.uikit.core.foundation.ColorState
import com.admiral.uikit.components.text.TextView
import com.admiral.uikit.ext.getColorOrNull
import com.admiral.uikit.ext.parseAttrs

class DoubleTextField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ThemeObserver {

    /**
     * Additional text which is placed under divider.
     */
    var additionalText: String? = null
        set(value) {
            field = value
            additionalTextView.text = value

            if (value.isNullOrEmpty() && errorText.isNullOrEmpty()) {
                additionalTextView.isGone = value.isNullOrEmpty()
            } else {
                additionalTextView.isGone = false
            }
        }

    /**
     * Max lines for the [additionalTextView].
     */
    var additionalTextViewMaxLines: Int = Int.MAX_VALUE
        set(value) {
            field = value
            additionalTextView.maxLines = value
        }

    /**
     * Error text which is placed under divider when the view is in a Error state.
     */
    var errorText: String? = null
        set(value) {
            field = value
            if (value.isNullOrEmpty() && value.isNullOrEmpty()) {
                additionalTextView.isGone = value.isNullOrEmpty()
            }
        }

    /**
     * Handles visibility of the [additionalTextView].
     */
    var isAdditionalTextVisible: Boolean = true
        set(value) {
            field = value
            setAdditionalTextVisibility()
        }

    /**
     * Enable or disable error state.
     */
    var isError: Boolean = false
        set(value) {
            field = value
            invalidateError()
        }

    /**
     * Define ratio of text fields.
     * @see DoubleTextFieldRatio
     */
    @DoubleTextFieldRatio
    var ratio: Int = RATIO_EQUAL
        set(value) {
            field = value
            invalidateRatio(value)
        }

    var isAdditionalTextSingle: Boolean = true
        set(value) {
            field = value
            invalidateAdditionalTextViews()
        }

    val leftTextField: TextField by lazy { findViewById(R.id.leftTextField) }
    val rightTextField: TextField by lazy { findViewById(R.id.rightTextField) }

    private val additionalTextView: TextView by lazy { findViewById(R.id.doubleSliderAdditionalTextView) }

    init {
        LayoutInflater.from(context).inflate(R.layout.admiral_view_double_text_field, this)

        parseAttrs(attrs, R.styleable.DoubleTextField).use {
            parseDefaultColors(it)
            parseTexts(it)
            parseErrorColor(it)
            parseIcons(it)
            invalidateRatio(it.getInt(R.styleable.DoubleTextField_admiralRatio, RATIO_EQUAL))

            isEnabled = it.getBoolean(R.styleable.DoubleTextField_enabled, true)
            additionalTextViewMaxLines =
                it.getInt(R.styleable.DoubleTextField_admiralTextAdditionalMaxLines, Int.MAX_VALUE)
            isAdditionalTextSingle = it.getBoolean(R.styleable.DoubleTextField_admiralIsAdditionalTextViewSingle, true)
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
        leftTextField.isEnabled = enabled
        rightTextField.isEnabled = enabled
        invalidateColors()
    }

    override fun onThemeChanged(theme: Theme) {
        invalidateColors()
    }

    private fun invalidateAdditionalTextViews() {
        leftTextField.isAdditionalTextVisible = !isAdditionalTextSingle
        rightTextField.isAdditionalTextVisible = !isAdditionalTextSingle
        additionalTextView.isGone = !isAdditionalTextSingle
    }

    private fun invalidateColors() {
        val additionalTextColor: Int = when {
            isError -> leftTextField.errorColor ?: ThemeManager.theme.palette.textError
            !isEnabled -> leftTextField.additionalTextColors?.normalDisabled
                ?: ThemeManager.theme.palette.textSecondary.withAlpha()
            else -> leftTextField.additionalTextColors?.normalEnabled ?: ThemeManager.theme.palette.textSecondary
        }
        additionalTextView.textColor = ColorState(additionalTextColor)
    }

    private fun invalidateError() {
        leftTextField.isError = isError
        rightTextField.isError = isError

        val text = if (isError) {
            if (errorText.isNullOrEmpty()) {
                additionalText
            } else {
                errorText
            }
        } else {
            additionalText
        }

        additionalTextView.text = text
        invalidateColors()
    }

    private fun invalidateRatio(ratio: Int) {
        val set = ConstraintSet()
        set.clone(this)

        val leftRatio = ratio.toFloat() / DIVIDER
        val rightRatio = ONE - leftRatio

        set.constrainPercentWidth(leftTextField.id, leftRatio)
        set.constrainPercentWidth(rightTextField.id, rightRatio)

        set.applyTo(this)
    }

    private fun parseIcons(a: TypedArray) {
        leftTextField.icon = a.getDrawable(R.styleable.DoubleTextField_admiralLeftIcon)
        rightTextField.icon = a.getDrawable(R.styleable.DoubleTextField_admiralRightIcon)

        if (a.hasValue(R.styleable.DoubleTextField_admiralLeftIconTintColor)) {
            leftTextField.iconTintColor = a.getColor(
                R.styleable.DoubleTextField_admiralLeftIconTintColor,
                Color.TRANSPARENT
            )
        }

        if (a.hasValue(R.styleable.DoubleTextField_admiralRightIconTintColor)) {
            rightTextField.iconTintColor = a.getColor(
                R.styleable.DoubleTextField_admiralRightIconTintColor,
                Color.TRANSPARENT
            )
        }
    }

    private fun parseErrorColor(a: TypedArray) {
        if (a.hasValue(R.styleable.DoubleTextField_admiralErrorColor)) {
            val color = a.getInt(R.styleable.DoubleTextField_admiralErrorColor, ThemeManager.theme.palette.textError)
            leftTextField.errorColor = color
            rightTextField.errorColor = color
        }
    }

    private fun parseTexts(a: TypedArray) {
        leftTextField.inputText = a.getString(R.styleable.DoubleTextField_admiralLeftText) ?: ""
        leftTextField.optionalText = a.getString(R.styleable.DoubleTextField_admiralLeftTextOptional)
        leftTextField.placeholderText = a.getString(R.styleable.DoubleTextField_admiralLeftTextPlaceholder)
        leftTextField.additionalText = a.getString(R.styleable.DoubleTextField_admiralLeftTextAdditional)

        rightTextField.inputText = a.getString(R.styleable.DoubleTextField_admiralLeftText) ?: ""
        rightTextField.optionalText = a.getString(R.styleable.DoubleTextField_admiralLeftTextOptional)
        rightTextField.placeholderText = a.getString(R.styleable.DoubleTextField_admiralLeftTextPlaceholder)
        rightTextField.additionalText = a.getString(R.styleable.DoubleTextField_admiralLeftTextAdditional)

        additionalText = a.getString(R.styleable.DoubleTextField_admiralTextAdditional)
    }

    private fun parseDefaultColors(a: TypedArray) {
        if (a.hasValue(R.styleable.DoubleTextField_admiralTextInputColor)) {
            val color = a.getColor(
                R.styleable.DoubleTextField_admiralTextInputColor,
                ThemeManager.theme.palette.textPrimary
            )

            leftTextField.inputTextColor = color
            rightTextField.inputTextColor = color
        }

        val normal = a.hasValue(R.styleable.DoubleTextField_admiralTextColorNormalEnabled)
        val focused = a.hasValue(R.styleable.DoubleTextField_admiralTextColorFocused)
        val disabled = a.hasValue(R.styleable.DoubleTextField_admiralTextColorNormalDisabled)

        if (normal && focused && disabled) {
            val colorState = ColorState(
                normalEnabled = a.getColorOrNull(R.styleable.DoubleTextField_admiralTextColorNormalEnabled),
                pressed = a.getColorOrNull(R.styleable.DoubleTextField_admiralTextColorFocused),
                normalDisabled = a.getColorOrNull(R.styleable.DoubleTextField_admiralTextColorNormalDisabled),
                focused = a.getColorOrNull(R.styleable.DoubleTextField_admiralTextColorFocused)
            )

            leftTextField.additionalTextColors = colorState
            rightTextField.additionalTextColors = colorState
        } else if (normal || focused || disabled) {
            throw IllegalStateException("You must declare all text colors: normal, focused, disabled.")
        }
    }

    private fun setAdditionalTextVisibility() {
        additionalTextView.isGone = !isAdditionalTextVisible
    }

    companion object {
        const val RATIO_EQUAL = 5
        const val RATIO_BIG_RIGHT = 7
        const val RATIO_BIG_LEFT = 3
        private const val DIVIDER = 10
        private const val ONE = 10
    }
}